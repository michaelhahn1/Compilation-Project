package IR;

import MIPS.sir_MIPS_a_lot;
import TEMP.*;

public class IRcommand_PrintString extends IRcommand{
    public IRcommand_PrintString(TEMP t, int line) {
        this.temp_in1 = t;
        this.line = line;
    }

    @Override
    public void MIPSme() {
        sir_MIPS_a_lot.getInstance().print_string(temp_in1, line);
    }
}
