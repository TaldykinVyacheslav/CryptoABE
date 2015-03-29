package cpabe;

import it.unisa.dia.gas.jpbc.Element;

import java.io.IOException;
import java.util.List;

interface Common {

    List<Byte> aes_128_cbc_encrypt(List<Byte> pt, Element k) throws Exception;

    List<Byte> aes_128_cbc_decrypt( List<Byte> ct, Element k ) throws Exception;

    String suck_file_str( String file ) throws IOException;

    byte[] suck_file( String fileName ) throws IOException;

    void spit_file( String fileName, List<Byte> b, int free ) throws IOException;

    Object[] read_cpabe_file( String fileName, byte[] cph_buf, long file_len, byte[] aes_buf ) throws IOException;

    void write_cpabe_file( String fileName, byte[] cph_buf, long file_len, byte[] aes_buf ) throws IOException;

}
