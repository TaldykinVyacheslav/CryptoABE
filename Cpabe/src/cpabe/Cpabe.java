package cpabe;

import java.io.IOException;

public interface Cpabe {
    void setup(String pub_file, String msk_file) throws IOException;

    void keygen(String pub_file, String msk_file, String out_file, String[] attrs) throws IOException;

    void encrypt(String pub_file, String in_file, String out_file) throws IOException;

    void decrypt(String pub_file, String prv_file, String in_file, String out_file);
}
