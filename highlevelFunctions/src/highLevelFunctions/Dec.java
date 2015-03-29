package highLevelFunctions;

        import aes_cbc.AES;
        import domain.CphT;
        import domain.PrvT;
        import domain.PubT;
        import it.unisa.dia.gas.jpbc.Element;
        import logic.CoreImpl;

        import java.util.ArrayList;
        import java.util.List;

public class Dec {
    private static String usage =
            "Usage: cpabe-dec [OPTION ...] PUB_KEY PRIV_KEY FILE\n" +
                    "\n" +
                    "Decrypt FILE using private key PRIV_KEY and assuming public key\n" +
                    "PUB_KEY. If the name of FILE is X.cpabe, the decrypted file will\n" +
                    "be written as X and FILE will be removed. Otherwise the file will be\n" +
                    "decrypted in place. Use of the -o option overrides this\n" +
                    "behavior.\n" +
                    "\n" +
                    "Mandatory arguments to long options are mandatory for short options too.\n\n" +
                    " -h, --help               print this message\n\n" +
                    " -v, --version            print version information\n\n" +
                    " -k, --keep-input-file    don't delete original file\n\n" +
                    " -o, --output FILE        write output to FILE\n\n" +
                    " -d, --deterministic      use deterministic \"random\" numbers\n" +
                    "                          (only for debugging)\n\n";

    private static String pub_file   = "";
    private static String prv_file   = "";
    private static String in_file    = "";
    private static String out_file   = "";
    /* int   no_opt_sat = 0; */
/* int   report_ops = 0; */
    private static int   keep       = 0;

    /* int num_pairings = 0; */
/* int num_exps     = 0; */
/* int num_muls     = 0; */

    static void parse_args( int argc, String[] argv )
    {
        int i;

        for( i = 1; i < argc; i++ )
            if (argv[i].equals("-h") || argv[i].equals("--help"))
            {
                System.out.println(String.format("%s", usage));
                System.exit(0);
            }
            else if( argv[i].equals("-v") || argv[i].equals("--version") )
            {
                System.out.println(CPABE_VERSION + "-dec");
                System.exit(0);
            }
            else if( argv[i].equals("-k") || argv[i].equals("--keep-input-file") )
            {
                keep = 1;
            }
            else if( argv[i].equals("-o") || argv[i].equals("--output") )
            {
                if( ++i >= argc )
                    die(usage);
                else
                    out_file = argv[i];
            }
            else if( argv[i].equals("-d") || argv[i].equals("--deterministic") )
            {
                pbc_random_set_deterministic(0);
            }
/* 		else if( !strcmp(argv[i], "-s") || !strcmp(argv[i], "--no-opt-sat") ) */
/* 		{ */
/* 			no_opt_sat = 1; */
/* 		} */
/* 		else if( !strcmp(argv[i], "-n") || !strcmp(argv[i], "--naive-dec") ) */
/* 		{ */
/* 			dec_strategy = DEC_NAIVE; */
/* 		} */
/* 		else if( !strcmp(argv[i], "-f") || !strcmp(argv[i], "--flatten") ) */
/* 		{ */
/* 			dec_strategy = DEC_FLATTEN; */
/* 		} */
/* 		else if( !strcmp(argv[i], "-r") || !strcmp(argv[i], "--report-ops") ) */
/* 		{ */
/* 			report_ops = 1; */
/* 		} */
            else if( !pub_file.equals("") )
            {
                pub_file = argv[i];
            }
            else if( !prv_file.equals("") )
            {
                prv_file = argv[i];
            }
            else if( !in_file.equals("") )
            {
                in_file = argv[i];
            }
            else
                die(usage);

        if( !pub_file.equals("") || !prv_file.equals("") || !in_file.equals("") )
            die(usage);

        if( !out_file.equals("") )
        {
            if(  in_file.length() > 6 &&
                    !in_file + in_file.length() - 6, ".cpabe") )
            out_file = g_strndup(in_file, in_file.length() - 6);
            else
            out_file = strdup(in_file);
        }

        if( keep > 0 && !in_file.equals(out_file) )
            die("cannot keep input file when decrypting file in place (try -o)\n");
    }

    private static byte[] toByteArray(List<Byte> list) {
        Object[] arr = list.toArray();
        byte[] result = new byte[arr.length];
        for (int i = 0; i < arr.length; i++)
            result[i] = (Byte) arr[i];
        return result;
    }

    public static void main(String[] args) {
        PubT pub;
        PrvT prv;
        int file_len = 0;
        List<Byte> aes_buf = new ArrayList<>();
        List<Byte> plt = new ArrayList<>();
        List<Byte> cph_buf = new ArrayList<>();
        CphT cph;
        Element m = null;

        CoreImpl coreImpl = new CoreImpl();
        parse_args(args.length, args);

        pub = coreImpl.bswabe_pub_unserialize(suck_file(pub_file), 1);
        prv = coreImpl.bswabe_prv_unserialize(pub, suck_file(prv_file), 1);

        read_cpabe_file(in_file, cph_buf, file_len, aes_buf);

        cph = coreImpl.bswabe_cph_unserialize(pub, cph_buf, 1);
        if( coreImpl.bswabe_dec(pub, prv, cph) == 0)
            die("%s", coreImpl.bswabe_error());
        coreImpl.bswabe_cph_free(cph);

        plt = Common.aes_128_cbc_decrypt(aes_buf, m);
        g_byte_array_set_size(plt, file_len);
        g_byte_array_free(aes_buf, 1);

        spit_file(out_file, plt, 1);

        if( keep == 0 )
            unlink(in_file);

	/* report ops if necessary */
/* 	if( report_ops ) */
/* 		printf("pairings:        %5d\n" */
/* 					 "exponentiations: %5d\n" */
/* 					 "multiplications: %5d\n", num_pairings, num_exps, num_muls); */
    }
}
