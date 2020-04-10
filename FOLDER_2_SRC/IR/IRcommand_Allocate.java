package IR;

import MIPS.sir_MIPS_a_lot;

public class IRcommand_Allocate extends IRcommand{
    String varname;

    public IRcommand_Allocate(String varname, int line) {
        this.varname = varname;
        this.line = line;
    }

    @Override
    public void MIPSme() {
        sir_MIPS_a_lot.getInstance().allocateWord(varname,line);
    }
}
