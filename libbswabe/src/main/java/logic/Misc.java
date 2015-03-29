package logic;

import domain.CphT;
import domain.MskT;
import domain.PrvT;
import domain.PubT;

import java.util.List;

/**
 * methods in application
 * @see 'misc.c'
 */
public interface Misc {

    /**
     * Exactly what it seems.
     */
    public List<Byte> bswabe_pub_serialize( PubT pub );
    public List<Byte> bswabe_msk_serialize( MskT msk );
    public List<Byte> bswabe_prv_serialize( PrvT prv );
    public List<Byte> bswabe_cph_serialize( CphT cph );

    /**
     * Also exactly what it seems. If free is true, the GByteArray passed
     * in will be free'd after it is read.
     */
    public PubT bswabe_pub_unserialize( List<Byte> b, int free );
    public MskT bswabe_msk_unserialize( PubT pub, List<Byte> b, int free );
    public PrvT bswabe_prv_unserialize( PubT pub, List<Byte> b, int free );
    public CphT bswabe_cph_unserialize( PubT pub, List<Byte> b, int free );
}
