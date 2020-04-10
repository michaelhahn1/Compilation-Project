package IR;

import MIPS.sir_MIPS_a_lot;
import TEMP.*;

public class IRcommand_Mallocate extends IRcommand {
    public IRcommand_Mallocate(TEMP dst, TEMP bytes, int line) {
        temp_out = dst;
        temp_in1 = bytes;
        this.line = line;
    }

    @Override
    public void MIPSme() {
        sir_MIPS_a_lot.getInstance().mallocate(temp_in1, null, temp_out, line);
    }
}
