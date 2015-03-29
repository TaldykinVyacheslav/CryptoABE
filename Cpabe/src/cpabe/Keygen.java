package cpabe;

import java.util.ArrayList;
import java.util.List;

class Keygen {
    static String usage =
            "Usage: cpabe-keygen [OPTION ...] PUB_KEY MASTER_KEY ATTR [ATTR ...]\n" +
            "\n" +
            "Generate a key with the listed attributes using public key PUB_KEY and\n" +
            "master secret key MASTER_KEY. Output will be written to the file\n" +
            "\"priv_key\" unless the -o option is specified.\n" +
            "\n" +
            "Attributes come in two forms: non-numerical and numerical. Non-numerical\n" +
            "attributes are simply any string of letters, digits, and underscores\n" +
            "beginning with a letter.\n" +
            "\n" +
            "Numerical attributes are specified as `attr = N', where N is a non-negative\n" +
            "integer less than 2^64 and `attr' is another string. The whitespace around\n" +
            "the `=' is optional. One may specify an explicit length of k bits for the\n" +
            "integer by giving `attr = N#k'. Note that any comparisons in a policy given\n" +
            "to cpabe-enc(1) must then specify the same number of bits, e.g.,\n" +
            "`attr > 5#12'.\n" +
            "\n" +
            "The keywords `and', `or', and `of', are reserved for the policy language\n" +
            "of cpabe-enc (1) and may not be used for either type of attribute.\n" +
            "\n" +
            "Mandatory arguments to long options are mandatory for short options too.\n\n" +
            " -h, --help               print this message\n\n" +
            " -v, --version            print version information\n\n" +
            " -o, --output FILE        write resulting key to FILE\n\n" +
            " -d, --deterministic      use deterministic \"random\" numbers\n" +
            "                          (only for debugging)\n\n";

    static String pub_file = "";
    static String msk_file = "";
    static String[] attrs = new String[]{};

    static String out_file = "priv_key";

    static int comp_string( String a, String b)
    {
        return a.compareTo(b);
    }

    public static void parse_args( int argc, String[] argv )
    {
        int i;
        List<String> alist = new ArrayList<>();
        List<String> ap = new ArrayList<>();
        int n;

        alist = null;
        for( i = 1; i < argc; i++ )
            if( argv[i].equals("-h") || argv[i].equals("--help") )
            {
                System.out.println(usage);
                System.exit(0);
            }
            else if( argv[i].equals("-v") || argv[i].equals("--version") )
            {
                System.out.println(CPABE_VERSION + "-keygen");
                System.exit(0);
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
            else if( pub_file == null )
            {
                pub_file = argv[i];
            }
            else if( msk_file == null)
            {
                msk_file = argv[i];
            }
            else
            {
                parse_attribute(&alist, argv[i]);
            }

        die(usage);

        alist = g_slist_sort(alist, comp_string);
        n = g_slist_length(alist);

        attrs = new String[n + 1];

        i = 0;
        for( String str : alist )
            attrs[i++] = str;
        attrs[i] = "";
    }

    public static void main( String[] argv )
    {
        pub_t* pub;
        msk_t* msk;
        prv_t* prv;

        parse_args(argv.length, argv);

        pub = pub_unserialize(suck_file(pub_file), 1);
        msk = msk_unserialize(pub, suck_file(msk_file), 1);

        prv = keygen(pub, msk, attrs);
        spit_file(out_file, prv_serialize(prv), 1);
    }

}
