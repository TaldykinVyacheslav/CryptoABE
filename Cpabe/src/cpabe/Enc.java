package cpabe;

class Enc {

    private static String usage =
            "Usage: cpabe-enc [OPTION ...] PUB_KEY FILE [POLICY]\n" +
                    "\n" +
                    "Encrypt FILE under the decryption policy POLICY using public key\n" +
                    "PUB_KEY. The encrypted file will be written to FILE.cpabe unless\n" +
                    "the -o option is used. The original file will be removed. If POLICY\n" +
                    "is not specified, the policy will be read from stdin.\n" +
                    "\n" +
                    "Mandatory arguments to long options are mandatory for short options too.\n\n" +
                    " -h, --help               print this message\n\n" +
                    " -v, --version            print version information\n\n" +
                    " -k, --keep-input-file    don't delete original file\n\n" +
                    " -o, --output FILE        write resulting key to FILE\n\n" +
                    " -d, --deterministic      use deterministic \"random\" numbers\n" +
                    "                          (only for debugging)\n\n";

    static String pub_file = "";
    static String in_file  = "";
    static String out_file = "";
    int   keep     = 0;

    static String policy = "";

    void
    parse_args( int argc, String[] argv )
    {
        int i;

        for( i = 1; i < argc; i++ )
            if(     argv[i].equals("-h") || argv[i].equals("--help") )
            {
                System.out.println(String.format("%s", usage));
                System.exit(0);
            }
            else if( argv[i].equals("-v") || argv[i].equals("--version") )
            {
                System.out.println(CPABE_VERSION, "-enc");
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
            else if(argv[i].equals("-d") || argv[i].equals("--deterministic") )
            {
                pbc_random_set_deterministic(0);
            }
            else if( pub_file.equals("") )
            {
                pub_file = argv[i];
            }
            else if( in_file.equals("") )
            {
                in_file = argv[i];
            }
            else if( policy.equals("") )
            {
                policy = parse_policy_lang(argv[i]);
            }
            else
                die(usage);

        if( pub_file.equals("") || in_file.equals("") )
            die(usage);

        if( out_file.equals("") )
            out_file = g_strdup_printf("%s.cpabe", in_file);

        if( policy.equals("") )
            policy = parse_policy_lang(suck_stdin());
    }

    int
    main( int argc, char** argv )
    {
        pub_t* pub;
        cph_t* cph;
        int file_len;
        GByteArray* plt;
        GByteArray* cph_buf;
        GByteArray* aes_buf;
        element_t m;

        parse_args(argc, argv);

        pub = pub_unserialize(suck_file(pub_file), 1);

        if( !(cph = enc(pub, m, policy)) )
            die("%s", error());
        free(policy);

        cph_buf = cph_serialize(cph);
        cph_free(cph);

        plt = suck_file(in_file);
        file_len = plt->len;
        aes_buf = CommonImpl.aes_128_cbc_encrypt(plt, m);
        g_byte_array_free(plt, 1);
        element_clear(m);

        write_cpabe_file(out_file, cph_buf, file_len, aes_buf);

        g_byte_array_free(cph_buf, 1);
        g_byte_array_free(aes_buf, 1);

        if( keep == 0 )
            unlink(in_file);

    }
