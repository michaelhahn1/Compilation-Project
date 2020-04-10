package IR;

import MIPS.sir_MIPS_a_lot;
import TEMP.TEMP;

public class IRcommand_Store_Register extends IRcommand {
    int offset;

    public IRcommand_Store_Register(TEMP dst, TEMP src, int offset, int line) {
        temp_in1 = src;
        temp_in2 = dst;
        this.offset = offset;
        this.line = line;
    }


    @Override
    public void MIPSme() {
        sir_MIPS_a_lot.getInstance().store_to_register(temp_in2, temp_in1, offset, line);
    }
}