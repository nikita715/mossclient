package mossclient

/**
 * Name of a programming language
 */
enum class Language {
    C,
    CPP,
    JAVA,
    ML,
    PASCAL,
    ADA,
    LISP,
    SCHEME,
    HASKELL,
    FORTRAN,
    TEXT,
    VHDL,
    PERL,
    MATLAB,
    PYTHON,
    MIPS_ASSEMBLY,
    PROLOG,
    SPICE,
    VISUAL_BASIC,
    CSHARP,
    MODULA2,
    A8086_ASSEMBLY,
    JAVASCRIPT,
    PLSQL,
    VERILOG,
    TCL;

    /**
     * To moss name of the language
     */
    fun ofMoss() =
        when (this) {
            CPP -> "cc"
            TEXT -> "ascii"
            MIPS_ASSEMBLY -> "mips"
            A8086_ASSEMBLY -> "a8086"
            VISUAL_BASIC -> "vb"
            else -> toString()
        }

    override fun toString(): String = name.toLowerCase()
}