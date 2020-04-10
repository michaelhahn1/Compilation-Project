package IR;
import MIPS.sir_MIPS_a_lot;
import TEMP.*;

public class IRcommand_Store_FP extends IRcommand{
    public IRcommand_Store_FP(TEMP dst, int line) {
        temp_out = dst;
        this.line = line;
    }

    @Override
    public void MIPSme() {
        sir_MIPS_a_lot.getInstance().move_fp_temp(temp_out, line);
    }
}
