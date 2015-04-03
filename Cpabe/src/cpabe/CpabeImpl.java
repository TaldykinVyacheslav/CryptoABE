package cpabe;

import domain.CphT;
import domain.MskT;
import domain.PrvT;
import domain.PubT;
import it.unisa.dia.gas.jpbc.Element;
import logic.Core;
import logic.CoreImpl;
import logic.Misc;
import logic.MiscImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class, providing Cpabe functional
 */
class CpabeImpl implements Cpabe {
    private final Core core = new CoreImpl();
    private Misc misc = new MiscImpl();
    private CommonImpl common = new CommonImpl();

    @Override
    public void setup(String pub_file, String msk_file) throws IOException {
        PubT pub = new PubT();
        MskT msk = new MskT();

        core.setup(pub, msk);
        common.spit_file(pub_file, misc.pub_serialize(pub), 1);
        common.spit_file(msk_file, misc.msk_serialize(msk), 1);
    }

    @Override
    public void keygen(String pub_file, String msk_file, String out_file, String[] attrs) throws IOException {
        PubT pub;
        MskT msk;
        PrvT prv;

        pub = misc.pub_unserialize(common.suck_file(pub_file), 1);
        msk = misc.msk_unserialize(pub, common.suck_file(msk_file), 1);

        prv = core.keygen(pub, msk, attrs);
        common.spit_file(out_file, misc.prv_serialize(prv), 1);
    }

    @Override
    public void encrypt(String pub_file, String in_file, String out_file, String policy) throws Exception {
        PubT pub;
        CphT cph;
        int file_len;
        List<Byte> plt;
        List<Byte> cph_buf;
        List<Byte> aes_buf;
        Element m = null;

        pub = misc.pub_unserialize(common.suck_file(pub_file), 1);
        cph = core.enc(pub, policy);

        cph_buf = misc.cph_serialize(cph);

        plt = common.suck_file(in_file);
        file_len = plt.size();
        aes_buf = common.aes_128_cbc_encrypt(plt, m);

        common.write_cpabe_file(out_file, cph_buf, file_len, aes_buf);
    }

    @Override
    public void decrypt(String pub_file, String prv_file, String in_file, String out_file) throws Exception {
        PubT pub;
        PrvT prv;
        int file_len = 0;
        List<Byte> aes_buf = new ArrayList<>();
        List<Byte> plt;
        List<Byte> cph_buf = new ArrayList<>();
        CphT cph;
        Element m = null;

        pub = misc.pub_unserialize(common.suck_file(pub_file), 1);
        prv = misc.prv_unserialize(pub, common.suck_file(prv_file), 1);

        common.read_cpabe_file(in_file, cph_buf, file_len, aes_buf);

        cph = misc.cph_unserialize(pub, cph_buf, 1);
        if( core.dec(pub, prv, cph) == null)
            return;

        plt = common.aes_128_cbc_decrypt(aes_buf, m);

        common.spit_file(out_file, plt, 1);
    }

    public static void main(String[] args) {
        System.out.println("Compiled");
    }
}
