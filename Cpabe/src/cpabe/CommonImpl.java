package cpabe;

import it.unisa.dia.gas.jpbc.Element;
import org.apache.commons.lang.ArrayUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

class CommonImpl {

    //AES_KEY -> SecretKey
    //
    public  Cipher cipher;

    {
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

     SecretKey init_aes(Element k, int enc) throws InvalidKeyException {
        byte[] key_buf = k.toBytes();
        SecretKey key = new SecretKeySpec(key_buf, 0, key_buf.length, "AES");

        if (enc != 0) {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, key);
        }
        return key;
    }

     List<Byte> array_prepend(byte[] len, List<Byte> pt) {
        Byte[] c = new Byte[pt.size() + len.length];
        System.arraycopy(len, 0, c, 0, len.length);
        System.arraycopy(pt, 0, c, len.length, pt.size());
        pt = Arrays.asList(c);
        return pt;
    }

     List<Byte> fromPrimitiveArrayToList(byte[] arr) {
        return Arrays.asList(ArrayUtils.toObject(arr));
    }

     byte[] fromListToPrimitiveArray(List<Byte> list) {
        return ArrayUtils.toPrimitive(list.toArray(new Byte[list.size()]));
    }

     List<Byte> aes_128_cbc_encrypt(List<Byte> pt, Element k) throws Exception {
        byte[] ct;
        byte[] len = new byte[4];
        byte zero;

        len[0] = (byte) ((pt.size() & 0xff000000) >> 24);
        len[1] = (byte) ((pt.size() & 0xff0000) >> 16);
        len[2] = (byte) ((pt.size() & 0xff00) >> 8);
        len[3] = (byte) ((pt.size() & 0xff));


        array_prepend(len, pt);

        zero = 0;
        while (pt.size() % 16 != 0)
            pt.add(zero);

        ct = cipher.doFinal(fromListToPrimitiveArray(pt));

        return fromPrimitiveArrayToList(ct);
    }

     List<Byte> aes_128_cbc_decrypt( List<Byte> ct, Element k ) throws Exception {
        List<Byte> pt;
        int len;

        init_aes(k, 0);

        pt = fromPrimitiveArrayToList(cipher.doFinal(fromListToPrimitiveArray(ct)));

  /* TODO make less crufty */

  /* get real length */
        len = 0;
        len = len
                | ((pt.get(0))<<24) | ((pt.get(1))<<16)
                | ((pt.get(2))<<8)  | ((pt.get(3)));
        pt.remove(0);
        pt.remove(0);
        pt.remove(0);
        pt.remove(0);

  /* truncate any garbage from the padding */

        for (int i = pt.size(); i < len; i++) {
            pt.add((byte)0);
        }

        return pt;
    }

    FileInputStream fopen_read_or_die( String fileName ) throws FileNotFoundException {
        return new FileInputStream(fileName);
    }
    
    FileOutputStream fopen_write_or_die( String fileName ) throws FileNotFoundException {
        return new FileOutputStream(fileName);
    }

    List<Byte> suck_file( String fileName ) throws IOException {
        FileInputStream f;
        Path path = Paths.get(fileName);
        byte[] a;

        f = fopen_read_or_die(fileName);
        a = Files.readAllBytes(path);
        f.close();

        return fromPrimitiveArrayToList(a);
    }

    String suck_file_str( String file ) throws IOException {
        byte[] a;
        String s;
        byte zero;

        a = fromListToPrimitiveArray(suck_file(file));
        a = Arrays.copyOf(a, a.length + 1);
        a[a.length] = 0;
        s = new String(a);

        return s;
    }

    void spit_file( String fileName, List<Byte> b, int free ) throws IOException {
        FileOutputStream f;

        f = fopen_write_or_die(fileName);
        f.write(fromListToPrimitiveArray(b));
        f.close();

        if( free != 0 )
            b.clear();
    }

    Object[] read_cpabe_file( String fileName, List<Byte> cph_buf_list, long file_len, List<Byte> aes_buf_list ) throws IOException {
        FileInputStream f;
        int i;
        int len;
        byte[] cph_buf, aes_buf;

        cph_buf = fromListToPrimitiveArray(cph_buf_list);
        aes_buf = fromListToPrimitiveArray(aes_buf_list);

        f = fopen_read_or_die(fileName);

	    /* read real file len as 32-bit big endian int */
        file_len = 0;
        for( i = 3; i >= 0; i-- )
            file_len |= f.read()<<(i*8);

	    /* read aes buf */
        len = 0;
        for( i = 3; i >= 0; i-- )
            len |= f.read()<<(i*8);
        aes_buf = new byte[len];
        f.read(aes_buf);

	    /* read cph buf */
        len = 0;
        for( i = 3; i >= 0; i-- )
            len |= f.read()<<(i*8);
        cph_buf = new byte[len];
        f.read(cph_buf);

        f.close();

        return new Object[] {cph_buf, file_len, aes_buf};
    }

    void write_cpabe_file( String fileName, List<Byte> cph_buf_list, long file_len, List<Byte> aes_buf_list ) throws IOException {
        FileOutputStream f;
        DataOutputStream dos;
        byte[] cph_buf, aes_buf;
        int i;

        cph_buf = fromListToPrimitiveArray(cph_buf_list);
        aes_buf = fromListToPrimitiveArray(aes_buf_list);

        f = fopen_write_or_die(fileName);
        dos = new DataOutputStream(f);

	    /* write real file len as 32-bit big endian int */
        for( i = 3; i >= 0; i-- )
            dos.writeLong((file_len & 0xff << (i * 8)) >> (i * 8));

	    /* write aes_buf */
        for( i = 3; i >= 0; i-- )
            dos.writeLong((aes_buf.length & 0xff << (i * 8)) >> (i * 8));
        dos.write(aes_buf);

	    /* write cph_buf */
        for( i = 3; i >= 0; i-- )
            dos.writeLong((cph_buf.length & 0xff << (i * 8)) >> (i * 8));
        dos.write(cph_buf);

        dos.close();
    }
}