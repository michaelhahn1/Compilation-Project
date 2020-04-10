package IR;
import TEMP.*;
import MIPS.sir_MIPS_a_lot;
public class IRcommand_move extends IRcommand {
    public IRcommand_move(TEMP dst, TEMP src, int line){
        this.temp_out = dst;
        this.temp_in1 = src;
        this.line = line;
    }

    @Override
    public void MIPSme() {
        sir_MIPS_a_lot.getInstance().move(temp_out,temp_in1,line);
    }
}
