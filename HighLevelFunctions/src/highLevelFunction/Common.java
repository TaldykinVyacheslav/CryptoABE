package highLevelFunction;

import it.unisa.dia.gas.jpbc.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sined on 9/03/15.
 */
public class Common {
    static void init_aes(Element k, int enc, AES_KEY[] key, Byte[] iv) {
        int key_len;
        byte[] key_buf;

        key_len = k.getLengthInBytes() < 17 ? 17 : k.getLengthInBytes();
        key_buf = k.toBytes();

        if (enc != 0)
            AES_set_encrypt_key(key_buf[1], 128, key);
        else
            AES_set_decrypt_key(key_buf[1], 128, key);

        iv = new Byte[16];
    }

    static List<Byte> array_prepend(byte[] len, List<Byte> pt) {
        Byte[] c = new Byte[pt.size() + len.length];
        System.arraycopy(len, 0, c, 0, len.length);
        System.arraycopy(pt, 0, c, len.length, pt.size());
        pt = Arrays.asList(c);
        return pt;
    }

    static List<Byte> aes_128_cbc_encrypt(List<Byte> pt, Element k) {
        AES_KEY[] key;
        Byte[] iv = new Byte[16];
        List<Byte> ct;
        byte[] len = new byte[4];
        byte zero;

        init_aes(k, 1, key, iv);

      /* TODO make less crufty */

      /* stuff in real length (big endian) before padding */
        len[0] = (byte) ((pt.size() & 0xff000000) >> 24);
        len[1] = (byte) ((pt.size() & 0xff0000) >> 16);
        len[2] = (byte) ((pt.size() & 0xff00) >> 8);
        len[3] = (byte) ((pt.size() & 0xff) >> 0);


        array_prepend(len, pt);

      /* pad out to multiple of 128 bit (16 byte) blocks */
        zero = 0;
        while (pt.size() % 16 != 0)
            pt.add(zero);

        ct = new ArrayList<>(pt.size());

        AES_cbc_encrypt(pt, ct, pt.size(), key, iv, AES_ENCRYPT);

        return ct;
    }

    static List<Byte> aes_128_cbc_decrypt( List<Byte> ct, Element k )
    {
        AES_KEY[] key;
        Byte[] iv = new Byte[16];
        List<Byte> pt;
        int len;

        init_aes(k, 0, key, iv);

        pt = new ArrayList<>(ct.size());

        AES_cbc_encrypt(ct, pt, ct.size(), key, iv, AES_DECRYPT);

  /* TODO make less crufty */

  /* get real length */
        len = 0;
        len = len
                | ((pt.get(0))<<24) | ((pt.get(1))<<16)
                | ((pt.get(2))<<8)  | ((pt.get(3))<<0);ls
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
}