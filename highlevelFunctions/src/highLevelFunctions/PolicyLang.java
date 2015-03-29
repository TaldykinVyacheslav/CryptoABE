package highLevelFunctions;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class PolicyLang {
    public static final int YYBISON = 1;
    public static final String YYBISON_VERSION = "3.0.2";
    public static final String YYSKELETON_NAME = "yacc.c";
    public static final int YYPURE = 0;
    public static final int YYPUSH = 0;
    public static final int YYPULL = 1;

    class SizedIntegerT {
        int value;
        int bits;
    }

    class CpabePolicyT {
        int k;
        String attr;
        GPtrArray* children;
    }

    CpabePolicyT final_policy = null;

    public static final int YY_NULLPTR = 0;
    public static final int YYERROR_VERBOSE = 1;
    public static final int YYDEBUG = 0;

    class YYSTYPE {
        String str;
        int nat;
        int sint;
        CpabePolicyT tree;
        GPtrArray* list;

        @Override
        public String toString() {
            return "YYSTYPE{" +
                    "str='" + str + '\'' +
                    ", nat=" + nat +
                    ", sint=" + sint +
                    ", tree=" + tree +
                    '}';
        }
    }

    public static final int YYSTYPE_IS_TRIVIAL = 1;
    public static final int YYSTYPE_IS_DECLARED = 1;

    public static final int YYSIZE_MAXIMUM = -1;

    class Yyalloc
    {
        int yyss_alloc;
        YYSTYPE yyvs_alloc;
    };

    public static final int YYCOPY_NEEDED = 1;
    public static final int YYFINAL = 15;
    public static final int YYLAST =  45;
    public static final int YYNTOKENS = 17;
    public static final int YYNNTS = 5;
    public static final int YYNRULES = 21;
    public static final int YYNSTATES = 44;
    public static final int YYUNDEFTOK = 2;
    public static final int YYMAXUTOK = 264;

    public static int YYTRANSLATE(int YYX) {
        return YYX <= YYMAXUTOK ? yytranslate[YYX] : YYUNDEFTOK;
    }

    static final int yytranslate[] = {
            0, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 10, 2, 2, 2, 2,
            11, 12, 2, 2, 16, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            14, 13, 15, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 1, 2, 3, 4,
            5, 6, 7, 8, 9
    };

    static final int yyrline[] = {
            0, 67, 67, 69, 70, 72, 73, 74, 75, 76,
            77, 78, 79, 80, 81, 82, 83, 84, 85, 86,
            88, 90
    };

    static final String yytname[] =
    {
        "$end", "error", "$undefined", "TAG", "INTLIT", "OR", "AND", "OF",
                "LEQ", "GEQ", "'#'", "'('", "')'", "'='", "'<'", "'>'", "','", "$accept",
                "result", "number", "policy", "arg_list", ""
    };

    static final int yytoknum[] = {
            0, 256, 257, 258, 259, 260, 261, 262, 263, 264,
            35, 40, 41, 61, 60, 62, 44
    };

    public static final int YYPACT_NINF = -5;

    public static boolean yypact_value_is_default(int Yystate) {
        return Yystate == -5;
    }

    public static int YYTABLE_NINF = -1;

    public static int yytable_value_is_error = 0;

    static final int yypact[] =
            {
                    -2,    -1,    -4,    -2,     4,     2,    17,     1,     1,     1,
                    1,     1,    13,    29,    15,    -5,    32,    33,    34,    37,
                    38,    -2,    -2,    35,    -5,    -5,    -5,    -5,    -5,    -2,
                    -5,    -5,    -5,    -5,    -5,    -5,    -5,    19,    -5,    17,
                    22,    -5,    -2,    17
            };

  /* YYDEFACT[STATE-NUM] -- Default reduction number in state STATE-NUM.
     Performed when YYTABLE does not specify something else to do.  Zero
     means the default is an error.  */
    static final int yydefact[] =
            {
                    0,     5,     4,     0,     0,     0,     2,     0,     0,     0,
                    0,     0,     0,     0,     0,     1,     0,     0,     0,     0,
                    0,     0,     0,     4,    12,    13,     9,    10,    11,     0,
                    3,    19,    17,    18,    14,    15,    16,     6,     7,    20,
                    0,     8,     0,    21
            };

  
    static final int yypgoto[] =
            {
                    -5,    -5,    21,    -3,    -5
            };

  
    static final int yydefgoto[] =
            {
                    -1,     4,     5,     6,    40
            };

  /* YYTABLE[YYPACT[STATE-NUM]] -- What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule whose
     number is the opposite.  If YYTABLE_NINF, syntax error.  */
    static final int yytable[] =
            {
                    14,     1,     2,    12,    15,    23,    13,     7,     8,     3,
                    16,    17,     9,    10,    11,    18,    19,    20,    37,    38,
                    21,    22,    21,    22,    29,    22,    39,    31,    24,    25,
                    26,    27,    28,    30,    41,    32,    33,    34,    42,    43,
                    35,    36,     0,     0,     0,    13
            };

    static final int yycheck[] =
            {
                    3,     3,     4,     7,     0,     4,    10,     8,     9,    11,
                    8,     9,    13,    14,    15,    13,    14,    15,    21,    22,
                    5,     6,     5,     6,    11,     6,    29,    12,     7,     8,
                    9,    10,    11,     4,    12,     3,     3,     3,    16,    42,
                    3,     3,    -1,    -1,    -1,    10
            };

  /* YYSTOS[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
    static final int yystos[] =
            {
                    0,     3,     4,    11,    18,    19,    20,     8,     9,    13,
                    14,    15,     7,    10,    20,     0,     8,     9,    13,    14,
                    15,     5,     6,     4,    19,    19,    19,    19,    19,    11,
                    4,    12,     3,     3,     3,     3,     3,    20,    20,    20,
                    21,    12,    16,    20
            };

  
    static final int yyr1[] =
            {
                    0,    17,    18,    19,    19,    20,    20,    20,    20,    20,
                    20,    20,    20,    20,    20,    20,    20,    20,    20,    20,
                    21,    21
            };

  
    static final int yyr2[] =
            {
                    0,     2,     1,     3,     1,     1,     3,     3,     5,     3,
                    3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
                    1,     3
            };

    static final int yyerrok = 0;
    static final int YYEMPTY = -2;
    static final int YYEOF = 0;

    static final int YYTERROR = 1;
    static final int YYERRCODE = 256;

    static void YY_SYMBOL_PRINT(String Title, int Type, YYSTYPE Value, String Location) {
        do {
            if (yydebug != 0) {
                System.err.println(Title);
                yy_symbol_print(System.err,
                        Type, Value);
                System.err.println();
            }
        } while (false);
    }

    static void yy_symbol_value_print (PrintStream yyoutput, int yytype, YYSTYPE yyvaluep)
    {
        PrintStream yyo = yyoutput;
        YYUSE (yyo);
        if (yyvaluep != null)
            return;

        if (yytype < YYNTOKENS)
            YYPRINT(yyoutput, yytoknum[yytype], yyvaluep);

        YYUSE (yytype);
    }

    static void YYPRINT(PrintStream pw, Object...args) {
        List<Object> objs = Arrays.asList(args);
        for (Object obj : objs)
            pw.print(obj + " ");
        pw.println();
    }

    static void YYFPRINTF(PrintStream pw, String format, Object...args) {
        String result = String.format(format, args);
        pw.println(result);
    }

    static void yy_symbol_print (PrintStream pw, int yytype, YYSTYPE yyvaluep)
    {
        YYFPRINTF (pw, "%s %s (",
                yytype < YYNTOKENS ? "token" : "nterm", yytname[yytype]);

        yy_symbol_value_print (pw, yytype, yyvaluep);
        YYFPRINTF (pw, ")");
    }

    static void yy_stack_print (int yybottom, int yytop)
    {
        YYFPRINTF (System.err, "Stack now");
        for (; yybottom <= yytop; yybottom++)
        {
            int yybot = yybottom;
            YYFPRINTF (System.err, " %d", yybot);
        }
        YYFPRINTF (System.err, "\n");
    }

    static void YY_STACK_PRINT(int Bottom, int Top) {
        do {
            if (yydebug != 0)
                yy_stack_print(Bottom, Top);
        }
        while (false);
    }

    static void yy_reduce_print (int[] yyssp, YYSTYPE[] yyvsp, int yyrule)
    {
        long yylno = yyrline[yyrule];
        int yynrhs = yyr2[yyrule];
        int yyi;
        System.err.println("Reducing stack by rule " + (yyrule - 1) + " (line " + yylno + "):\n");
  
        for (yyi = 0; yyi < yynrhs; yyi++)
        {
            System.err.println("   $" + (yyi + 1) + "= ");
            yy_symbol_print (System.err, yystos[yyssp[yyi + 1 - yynrhs]], yyvsp[(yyi + 1) - (yynrhs)]);
            YYFPRINTF (System.err, "\n");
        }
    }

    static int yydebug;

    static final int YYINITDEPTH = 200;
    static final int YYMAXDEPTH = 10000;

    static int yystrlen (String yystr) {
        return yystr.length();
    }

    static int yytnamerr (String yyres, String yystr) {
        if (yystr.charAt(0) == '"')
        {
            int yyn = 0;
            String yyp = yystr;

            for (;;)
                switch (*++yyp)
                {
                    case '\'':
                    case ',':
                        goto do_not_strip_quotes;

                    case '\\':
                        if (*++yyp != '\\')
                        goto do_not_strip_quotes;

                    default:
                        if (!yyres.equals(""))
                            yyres[yyn] =*yyp;
                        yyn++;
                        break;

                    case '"':
                        if (!yyres.equals(""))
                            yyres[yyn] = '\0';
                        return yyn;
                }
            do_not_strip_quotes: ;
        }

        if (! yyres)
            return yystrlen (yystr);

        return yystpcpy (yyres, yystr) - yyres;
    }

    static int yysyntax_error (int[] yymsg_alloc, String[] yymsg, int yyssp, int yytoken) {
        int yysize0 = yytnamerr ("", yytname[yytoken]);
        int yysize = yysize0;
        int YYERROR_VERBOSE_ARGS_MAXIMUM = 5;
        String yyformat = "";
        String[] yyarg = new String[YYERROR_VERBOSE_ARGS_MAXIMUM];
        int yycount = 0;

        if (yytoken != YYEMPTY)
        {
            int yyn = yypact[yyssp];
            yyarg[yycount++] = yytname[yytoken];
            if (!yypact_value_is_default (yyn))
            {
                int yyxbegin = yyn < 0 ? -yyn : 0;
                int yychecklim = YYLAST - yyn + 1;
                int yyxend = yychecklim < YYNTOKENS ? yychecklim : YYNTOKENS;
                int yyx;

                for (yyx = yyxbegin; yyx < yyxend; ++yyx)
                    if (yycheck[yyx + yyn] == yyx && yyx != YYTERROR
                            && !yytable_value_is_error (yytable[yyx + yyn]))
                    {
                        if (yycount == YYERROR_VERBOSE_ARGS_MAXIMUM)
                        {
                            yycount = 1;
                            yysize = yysize0;
                            break;
                        }
                        yyarg[yycount++] = yytname[yyx];
                        {
                            int yysize1 = yysize + yytnamerr ("", yytname[yyx]);
                            if (! (yysize <= yysize1
                                    && yysize1 <= YYSTACK_ALLOC_MAXIMUM))
                                return 2;
                            yysize = yysize1;
                        }
                    }
            }
        }

        switch (yycount)
        {
            case 0: yyformat = "syntax error"; break;
            case 1: yyformat = "syntax error, unexpected %s"; break;
            case 2: yyformat = "syntax error, unexpected %s, expecting %s"; break;
            case 3: yyformat = "syntax error, unexpected %s, expecting %s or %s"; break;
            case 4: yyformat = "syntax error, unexpected %s, expecting %s or %s or %s"; break;
            case 5: yyformat = "syntax error, unexpected %s, expecting %s or %s or %s or %s"; break;
        }

        int yysize1 = yysize + yystrlen (yyformat);
        if (! (yysize <= yysize1 && yysize1 <= YYSTACK_ALLOC_MAXIMUM))
            return 2;
        yysize = yysize1;

        if (yymsg_alloc < yysize)
        {
            yymsg_alloc = 2 * yysize;
            if (! (yysize <= yymsg_alloc
                    && yymsg_alloc <= YYSTACK_ALLOC_MAXIMUM))
            yymsg_alloc = YYSTACK_ALLOC_MAXIMUM;
            return 1;
        }

        String yyp = *yymsg;
        int yyi = 0;
        while ((*yyp = *yyformat) != '\0')
        if (*yyp == '%' && yyformat[1] == 's' && yyi < yycount)
        {
            yyp += yytnamerr (yyp, yyarg[yyi++]);
            yyformat += 2;
        }
        else
        {
            yyp++;
            yyformat++;
        }
        return 0;
    }

    static void yydestruct (String yymsg, int yytype, YYSTYPE yyvaluep)
    {
        YYUSE (yyvaluep);
        if (!yymsg.equals(""))
            yymsg = "Deleting";
        YY_SYMBOL_PRINT (yymsg, yytype, yyvaluep, yylocationp);

        YYUSE (yytype);
    }

    static int yychar;
    static YYSTYPE yylval;
    static int yynerrs;

    static int yyparse (void)
    {
        int yystate;
        int yyerrstatus;

        int[] yyssa = new int[YYINITDEPTH];
        int[] yyss;
        int[] yyssp;

        YYSTYPE[] yyvsa = new YYSTYPE[YYINITDEPTH];
        YYSTYPE[] yyvs;
        YYSTYPE[] yyvsp;

        int yystacksize;

        int yyn;
        int yyresult;

        int yytoken = 0;

        YYSTYPE yyval;

        char[] yymsgbuf = new char[128];
        String yymsg = new String(yymsgbuf);
        int yymsg_alloc;




        int yylen = 0;

        yyssp = yyss = yyssa;
        yyvsp = yyvs = yyvsa;
        yystacksize = YYINITDEPTH;

        System.err.println("Starting parse\n");

        yystate = 0;
        yyerrstatus = 0;
        yynerrs = 0;
        yychar = YYEMPTY; 
        boolean yynewstate = true,
                yysetstate = false;

        while (true)

        if (yynewstate)
        {
            yyssp++;
        }

        if (yysetstate)
        {
            *yyssp = yystate;

            if (yyss + yystacksize - 1 <= yyssp) {

                int yysize = yyssp - yyss + 1;

                #ifdef yyoverflow
                {
                    YYSTYPE * yyvs1 = yyvs;
                    yytype_int16 * yyss1 = yyss;

                    yyoverflow(YY_("memory exhausted"),
                            & yyss1, yysize * sizeof( * yyssp),
                    &yyvs1, yysize * sizeof( * yyvsp),
                    &yystacksize);

                    yyss = yyss1;
                    yyvs = yyvs1;
                }
                #else 
                #ifndef YYSTACK_RELOCATE
                goto yyexhaustedlab;
                #else
      
                if (YYMAXDEPTH <= yystacksize)
                goto yyexhaustedlab;
                yystacksize *= 2;
                if (YYMAXDEPTH < yystacksize)
                    yystacksize = YYMAXDEPTH;

                {
                    yytype_int16 * yyss1 = yyss;
                    union yyalloc*yyptr =
                        (union yyalloc *)YYSTACK_ALLOC(YYSTACK_BYTES(yystacksize));
                    if (!yyptr)
                    goto yyexhaustedlab;
                    YYSTACK_RELOCATE(yyss_alloc, yyss);
                    YYSTACK_RELOCATE(yyvs_alloc, yyvs);
                    #undef YYSTACK_RELOCATE
                    if (yyss1 != yyssa)
                        YYSTACK_FREE(yyss1);
                }
                #endif
                #endif 

                        yyssp = yyss + yysize - 1;
                yyvsp = yyvs + yysize - 1;

                YYDPRINTF((stderr, "Stack size increased to %lu\n",
                        (unsignedlong int)yystacksize));

                if (yyss + yystacksize - 1 <= yyssp)
                    YYABORT;
            }

            YYDPRINTF((stderr, "Entering state %d\n", yystate));

            if (yystate == YYFINAL)
                YYACCEPT;

            goto yybackup;
        }


        yybackup:

        yyn = yypact[yystate];
        if (yypact_value_is_default (yyn))
        goto yydefault;

        if (yychar == YYEMPTY)
        {
            YYDPRINTF ((stderr, "Reading a token: "));
            yychar = yylex ();
        }

        if (yychar <= YYEOF)
        {
            yychar = yytoken = YYEOF;
            YYDPRINTF ((stderr, "Now at end of input.\n"));
        }
        else
        {
            yytoken = YYTRANSLATE (yychar);
            YY_SYMBOL_PRINT ("Next token is", yytoken, &yylval, &yylloc);
        }

        yyn += yytoken;
        if (yyn < 0 || YYLAST < yyn || yycheck[yyn] != yytoken)
        goto yydefault;
        yyn = yytable[yyn];
        if (yyn <= 0)
        {
            if (yytable_value_is_error (yyn))
            goto yyerrlab;
            yyn = -yyn;
            goto yyreduce;
        }

        if (yyerrstatus)
            yyerrstatus--;


        YY_SYMBOL_PRINT ("Shifting", yytoken, &yylval, &yylloc);


        yychar = YYEMPTY;

        yystate = yyn;
        YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
                *++yyvsp = yylval;
        YY_IGNORE_MAYBE_UNINITIALIZED_END

        goto yynewstate;



        yydefault:
        yyn = yydefact[yystate];
        if (yyn == 0)
        goto yyerrlab;
        goto yyreduce;



        yyreduce:
        yylen = yyr2[yyn];

        yyval = yyvsp[1-yylen];


        YY_REDUCE_PRINT (yyn);
        switch (yyn)
        {
            case 2:
                #line 67 "policy_lang.y" 
            { final_policy = (yyvsp[0].tree); }
            #line 1273 "policy_lang.c" 
            break;

            case 3:
                #line 69 "policy_lang.y" 
            { (yyval.sint) = expint((yyvsp[-2].nat), (yyvsp[0].nat)); }
            #line 1279 "policy_lang.c" 
            break;

            case 4:
                #line 70 "policy_lang.y" 
            { (yyval.sint) = flexint((yyvsp[0].nat));    }
            #line 1285 "policy_lang.c" 
            break;

            case 5:
                #line 72 "policy_lang.y" 
            { (yyval.tree) = leaf_policy((yyvsp[0].str));        }
            #line 1291 "policy_lang.c" 
            break;

            case 6:
                #line 73 "policy_lang.y" 
            { (yyval.tree) = kof2_policy(1, (yyvsp[-2].tree), (yyvsp[0].tree)); }
            #line 1297 "policy_lang.c" 
            break;

            case 7:
                #line 74 "policy_lang.y" 
            { (yyval.tree) = kof2_policy(2, (yyvsp[-2].tree), (yyvsp[0].tree)); }
            #line 1303 "policy_lang.c" 
            break;

            case 8:
                #line 75 "policy_lang.y" 
            { (yyval.tree) = kof_policy((yyvsp[-4].nat), (yyvsp[-1].list));     }
            #line 1309 "policy_lang.c" 
            break;

            case 9:
                #line 76 "policy_lang.y" 
            { (yyval.tree) = eq_policy((yyvsp[0].sint), (yyvsp[-2].str));      }
            #line 1315 "policy_lang.c" 
            break;

            case 10:
                #line 77 "policy_lang.y" 
            { (yyval.tree) = lt_policy((yyvsp[0].sint), (yyvsp[-2].str));      }
            #line 1321 "policy_lang.c" 
            break;

            case 11:
                #line 78 "policy_lang.y" 
            { (yyval.tree) = gt_policy((yyvsp[0].sint), (yyvsp[-2].str));      }
            #line 1327 "policy_lang.c" 
            break;

            case 12:
                #line 79 "policy_lang.y" 
            { (yyval.tree) = le_policy((yyvsp[0].sint), (yyvsp[-2].str));      }
            #line 1333 "policy_lang.c" 
            break;

            case 13:
                #line 80 "policy_lang.y" 
            { (yyval.tree) = ge_policy((yyvsp[0].sint), (yyvsp[-2].str));      }
            #line 1339 "policy_lang.c" 
            break;

            case 14:
                #line 81 "policy_lang.y" 
            { (yyval.tree) = eq_policy((yyvsp[-2].sint), (yyvsp[0].str));      }
            #line 1345 "policy_lang.c" 
            break;

            case 15:
                #line 82 "policy_lang.y" 
            { (yyval.tree) = gt_policy((yyvsp[-2].sint), (yyvsp[0].str));      }
            #line 1351 "policy_lang.c" 
            break;

            case 16:
                #line 83 "policy_lang.y" 
            { (yyval.tree) = lt_policy((yyvsp[-2].sint), (yyvsp[0].str));      }
            #line 1357 "policy_lang.c" 
            break;

            case 17:
                #line 84 "policy_lang.y" 
            { (yyval.tree) = ge_policy((yyvsp[-2].sint), (yyvsp[0].str));      }
            #line 1363 "policy_lang.c" 
            break;

            case 18:
                #line 85 "policy_lang.y" 
            { (yyval.tree) = le_policy((yyvsp[-2].sint), (yyvsp[0].str));      }
            #line 1369 "policy_lang.c" 
            break;

            case 19:
                #line 86 "policy_lang.y" 
            { (yyval.tree) = (yyvsp[-1].tree);                     }
            #line 1375 "policy_lang.c" 
            break;

            case 20:
                #line 88 "policy_lang.y" 
            { (yyval.list) = g_ptr_array_new();
                g_ptr_array_add((yyval.list), (yyvsp[0].tree)); }
            #line 1382 "policy_lang.c" 
            break;

            case 21:
                #line 90 "policy_lang.y" 
            { (yyval.list) = (yyvsp[-2].list);
                g_ptr_array_add((yyval.list), (yyvsp[0].tree)); }
            #line 1389 "policy_lang.c" 
            break;


            #line 1393 "policy_lang.c" 
            default: break;
        }

        YY_SYMBOL_PRINT ("-> $$ =", yyr1[yyn], &yyval, &yyloc);

        YYPOPSTACK (yylen);
        yylen = 0;
        YY_STACK_PRINT (yyss, yyssp);

        *++yyvsp = yyval;

        yyn = yyr1[yyn];

        yystate = yypgoto[yyn - YYNTOKENS] + *yyssp;
        if (0 <= yystate && yystate <= YYLAST && yycheck[yystate] == *yyssp)
        yystate = yytable[yystate];
        else
        yystate = yydefgoto[yyn - YYNTOKENS];

        goto yynewstate;



        yyerrlab:

        yytoken = yychar == YYEMPTY ? YYEMPTY : YYTRANSLATE (yychar);

        if (!yyerrstatus)
        {
            ++yynerrs;
            #if ! YYERROR_VERBOSE
            yyerror (YY_("syntax error"));
            #else
            # define YYSYNTAX_ERROR yysyntax_error (&yymsg_alloc, &yymsg, \
            yyssp, yytoken)
            {
                char const *yymsgp = YY_("syntax error");
                int yysyntax_error_status;
                yysyntax_error_status = YYSYNTAX_ERROR;
                if (yysyntax_error_status == 0)
                    yymsgp = yymsg;
                else if (yysyntax_error_status == 1)
                {
                    if (yymsg != yymsgbuf)
                        YYSTACK_FREE (yymsg);
                    yymsg = (char *) YYSTACK_ALLOC (yymsg_alloc);
                    if (!yymsg)
                    {
                        yymsg = yymsgbuf;
                        yymsg_alloc = sizeof yymsgbuf;
                        yysyntax_error_status = 2;
                    }
                    else
                    {
                        yysyntax_error_status = YYSYNTAX_ERROR;
                        yymsgp = yymsg;
                    }
                }
                yyerror (yymsgp);
                if (yysyntax_error_status == 2)
                goto yyexhaustedlab;
            }
            # undef YYSYNTAX_ERROR
            #endif
        }



        if (yyerrstatus == 3)
        {
            if (yychar <= YYEOF)
            {

                if (yychar == YYEOF)
                    YYABORT;
            }
            else
            {
                yydestruct ("Error: discarding",
                        yytoken, &yylval);
                yychar = YYEMPTY;
            }
        }


        goto yyerrlab1;



        yyerrorlab:
        {
            YYPOPSTACK(yylen);
            yylen = 0;
            YY_STACK_PRINT(yyss, yyssp);
            yystate =*yyssp;
            goto yyerrlab1;
        }

        yyerrlab1:
        {
            yyerrstatus = 3;      

            for (; ; ) {
                yyn = yypact[yystate];
                if (!yypact_value_is_default(yyn)) {
                    yyn += YYTERROR;
                    if (0 <= yyn && yyn <= YYLAST && yycheck[yyn] == YYTERROR) {
                        yyn = yytable[yyn];
                        if (0 < yyn)
                            break;
                    }
                }


                if (yyssp == yyss)
                    YYABORT;


                yydestruct("Error: popping",
                        yystos[yystate], yyvsp);
                YYPOPSTACK(1);
                yystate =*yyssp;
                YY_STACK_PRINT(yyss, yyssp);
            }

            YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
                    * ++yyvsp = yylval;
            YY_IGNORE_MAYBE_UNINITIALIZED_END


            YY_SYMBOL_PRINT("Shifting", yystos[yyn], yyvsp, yylsp);

            yystate = yyn;
            goto yynewstate;

        }

        yyacceptlab:
        {
            yyresult = 0;
            goto yyreturn;

            yyabortlab:
            yyresult = 1;
            goto yyreturn;

            #if !defined yyoverflow || YYERROR_VERBOSE

            yyexhaustedlab:
            yyerror(YY_("memory exhausted"));
            yyresult = 2;
  
            #endif
        }
                    
        yyreturn:
        if (yychar != YYEMPTY)
        {
      
            yytoken = YYTRANSLATE (yychar);
            yydestruct ("Cleanup: discarding lookahead",
                    yytoken, &yylval);
        }
  
        YYPOPSTACK (yylen);
        YY_STACK_PRINT (yyss, yyssp);
        while (yyssp != yyss)
        {
            yydestruct ("Cleanup: popping",
                    yystos[*yyssp], yyvsp);
            YYPOPSTACK (1);
        }
        #ifndef yyoverflow
        if (yyss != yyssa)
            YYSTACK_FREE (yyss);
        #endif
        #if YYERROR_VERBOSE
        if (yymsg != yymsgbuf)
            YYSTACK_FREE (yymsg);
        #endif
        return yyresult;
    }
    #line 94 "policy_lang.y"




}
