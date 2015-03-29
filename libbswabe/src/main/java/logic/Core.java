package logic;

import domain.CphT;
import domain.MskT;
import domain.PubT;
import domain.PrvT;
import it.unisa.dia.gas.jpbc.Element;

import java.lang.reflect.GenericArrayType;
import java.util.List;

/**
 * methods in application
 * @see 'core.c'
 */
public interface Core {
    /**
     * Generate a public key and corresponding master secret key, and
     * assign the *pub and *msk pointers to them. The space used may be
     * later freed by calling bswabe_pub_free(*pub) and
     * bswabe_msk_free(*msk).
     */
    public void bswabe_setup(PubT pub, MskT msk);

    /**
     * Generate a private key with the given set of attributes. The final
     * argument should be a null terminated array of pointers to strings,
     * one for each attribute.
     */
    PrvT bswabe_keygen(PubT pub, MskT msk, String[] attributes);

    /**
     *
     * Pick a random group element and encrypt it under the specified
     * access policy. The resulting ciphertext is returned and the
     * element_t given as an argument (which need not be initialized) is
     * set to the random group element.
     *
     * After using this function, it is normal to extract the random data
     * in m using the pbc functions element_length_in_bytes and
     * element_to_bytes and use it as a key for hybrid encryption.
     *
     * The policy is specified as a simple string which encodes a postorder
     * traversal of threshold tree defining the access policy. As an
     * example,
     *
     * "foo bar fim 2of3 baf 1of2"
     *
     * specifies a policy with two threshold gates and four leaves. It is
     * not possible to specify an attribute with whitespace in it (although
     * "_" is allowed).
     *
     * Numerical attributes and any other fancy stuff are not supported.
     *
     * Returns null if an error occured, in which case a description can be
     * retrieved by calling bswabe_error().
     */
    public CphT bswabe_enc(PubT pub, Element m, String policy);


    /**
     * Decrypt the specified ciphertext using the given private key,
     * filling in the provided element m (which need not be initialized)
     * with the result.
     *
     * Returns true if decryption succeeded, false if this key does not
     * satisfy the policy of the ciphertext (in which case m is unaltered).
     */
    public Element bswabe_dec(PubT pub, PrvT prvT, CphT cph);


    /**
     * Return a description of the last error that occured. Call this after
     * bswabe_enc or bswabe_dec returns 0. The returned string does not
     * need to be free'd.
     */
    public char[] bswabe_error();
}