package logic;

import domain.CphT;
import domain.MskT;
import domain.PubT;
import domain.PrvT;
import it.unisa.dia.gas.plaf.jpbc.wrapper.jna.PBCElementType;

import java.lang.reflect.GenericArrayType;
import java.util.List;

/**
 * methods in application
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
    public PrvT[] bswabe_keygen(PubT pub);

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
    public CphT[] bswabe_enc(PubT pub, PBCElementType m, String policy);


    /**
     * Decrypt the specified ciphertext using the given private key,
     * filling in the provided element m (which need not be initialized)
     * with the result.
     *
     * Returns true if decryption succeeded, false if this key does not
     * satisfy the policy of the ciphertext (in which case m is unaltered).
     */
    public int bswabe_dec(PubT pub, PrvT prvT, CphT cph, PBCElementType m);

    /**
     * Exactly what it seems.
     */
    public GenericArrayType bswabe_pub_serialize( PubT pub );
    public GenericArrayType bswabe_msk_serialize( MskT msk );
    public GenericArrayType bswabe_prv_serialize( PrvT prv );
    public GenericArrayType bswabe_cph_serialize( CphT cph );

    /**
     * Also exactly what it seems. If free is true, the GByteArray passed
     * in will be free'd after it is read.
     */
    public PubT bswabe_pub_unserialize( GenericArrayType b, int free );
    public MskT bswabe_msk_unserialize( PubT pub, GenericArrayType b, int free );
    public PrvT bswabe_prv_unserialize( PubT pub, GenericArrayType b, int free );
    public CphT bswabe_cph_unserialize( PubT pub, GenericArrayType b, int free );

    /**
     * Again, exactly what it seems.
     */
    public void bswabe_pub_free( PubT pub );
    public void bswabe_msk_free( MskT msk );
    public void bswabe_prv_free( PrvT prv );
    public void bswabe_cph_free( CphT cph );

    /**
     * Return a description of the last error that occured. Call this after
     * bswabe_enc or bswabe_dec returns 0. The returned string does not
     * need to be free'd.
     */
    public char[] bswabe_error();
}