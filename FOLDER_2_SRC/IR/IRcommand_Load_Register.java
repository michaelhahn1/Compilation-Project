package IR;
import MIPS.sir_MIPS_a_lot;
import TEMP.*;
public class IRcommand_Load_Register extends IRcommand {
    int offset;
    public IRcommand_Load_Register(TEMP dst, TEMP src, int offset, int line) {
        temp_in1 = src;
        temp_out = dst;
        this.offset = offset;
        this.line = line;
    }


    @Override
    public void MIPSme() {
        sir_MIPS_a_lot.getInstance().load_from_register(temp_out, temp_in1, offset, line);
    }
}
