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
    public List<Byte> pub_serialize( PubT pub );
    public List<Byte> msk_serialize( MskT msk );
    public List<Byte> prv_serialize( PrvT prv );
    public List<Byte> cph_serialize( CphT cph );

    /**
     * Also exactly what it seems. If free is true, the GByteArray passed
     * in will be free'd after it is read.
     */
    public PubT pub_unserialize( List<Byte> b, int free );
    public MskT msk_unserialize( PubT pub, List<Byte> b, int free );
    public PrvT prv_unserialize( PubT pub, List<Byte> b, int free );
    public CphT cph_unserialize( PubT pub, List<Byte> b, int free );
}
