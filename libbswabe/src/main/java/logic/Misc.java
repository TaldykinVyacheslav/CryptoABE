package logic;

import domain.CphT;
import domain.MskT;
import domain.PrvT;
import domain.PubT;

import java.lang.reflect.GenericArrayType;

/**
 * methods in application
 * @see misc.c
 */
public interface Misc {

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
}
