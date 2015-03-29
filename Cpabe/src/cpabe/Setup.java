package cpabe;

class Setup {
    String usage =
            "Usage: cpabe-setup [OPTION ...]\n" +
            "\n" +
            "Generate system parameters, a public key, and a master secret key\n" +
            "for use with cpabe-keygen, cpabe-enc, and cpabe-dec.\n" +
            "\n" +
            "Output will be written to the files \"pub_key\" and \"master_key\"\n" +
            "unless the --output-public-key or --output-master-key options are\n" +
            "used.\n" +
            "\n" +
            "Mandatory arguments to long options are mandatory for short options too.\n\n" +
            " -h, --help                    print this message\n\n" +
            " -v, --version                 print version information\n\n" +
            " -p, --output-public-key FILE  write public key to FILE\n\n" +
            " -m, --output-master-key FILE  write master secret key to FILE\n\n" +
            " -d, --deterministic           use deterministic \"random\" numbers\n" +
            "                               (only for debugging)\n\n";

    String pub_file = "pub_key";
    String msk_file = "master_key";

    static void parse_args( int argc, String[] argv )
    {
        int i;

        for( i = 1; i < argc; i++ )
            if (argv[i].equals("-h") || argv[i].equals("--help") )
            {
                System.out.println(usage);
                System.exit(0);
            }
            else if( argv[i].equals("-v") || argv[i].equals("--version") )
            {
                System.out.println(CPABE_VERSION + "-setup");
                System.exit(0);
            }
            else if(argv[i].equals("-p") || argv[i].equals("--output-public-key") )
            {
                if( ++i >= argc )
                    die(usage);
                else
                    pub_file = argv[i];
            }
            else if( argv[i].equals("-m") || argv[i].equals("--output-master-key") )
            {
                if( ++i >= argc )
                    die(usage);
                else
                    msk_file = argv[i];
            }
            else if( argv[i].equals("-d") || argv[i].equals("--deterministic") )
            {
                pbc_random_set_deterministic(0);
            }
            else
                die(usage);
    }

    public static void main(String[] argv )
    {
        pub_t* pub;
        msk_t* msk;

        parse_args(argc, argv);

        setup(&pub, &msk);
        spit_file(pub_file, pub_serialize(pub), 1);
        spit_file(msk_file, msk_serialize(msk), 1);

        return 0;
    }

}
