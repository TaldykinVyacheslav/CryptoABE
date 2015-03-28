package highLevelFunction;

public enum Yytokentype {
    TAG(258), INTLIT(259), OR(260), AND(261), OF(262), LEQ(263), GEQ(264);

    private int value;

    private Yytokentype(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
