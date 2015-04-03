package cpabe;

import java.io.IOException;

public interface Cpabe {

    /**
     * generate system parameters, a public key, and a master secret key
     *
     * @param pub_file public key file
     * @param msk_file master key file
     * @throws IOException
     */
    void setup(String pub_file, String msk_file) throws IOException;

    /**
     * Generate a key with the listed attributes using public key and
     * master secret key. Output will be written to the private key file
     *
     * @param pub_file public key file
     * @param msk_file master key file
     * @param out_file private key file
     * @param attrs attributes that define private key
     * @throws IOException
     */
    void keygen(String pub_file, String msk_file, String out_file, String[] attrs) throws IOException;

    /**
     * Encrypt file under the decryption policy using public key.
     * @param pub_file public key file
     * @param in_file source file for encryption
     * @param out_file encrypted file name
     * @param policy encrypted file policy
     * @throws Exception
     */
    void encrypt(String pub_file, String in_file, String out_file, String policy) throws Exception;

    /**
     * Decrypt file using private key and assuming public key.
     *
     * @param pub_file public key file name
     * @param prv_file private key file name
     * @param in_file encrypted file
     * @param out_file decrypted file
     * @throws Exception
     */
    void decrypt(String pub_file, String prv_file, String in_file, String out_file) throws Exception;
}
