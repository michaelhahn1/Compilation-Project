package IR;
import TEMP.*;
import MIPS.sir_MIPS_a_lot;
public class IRcommand_Set_To_Zero extends IRcommand {
    public IRcommand_Set_To_Zero(TEMP dst, int line){
        this.temp_out = dst;
        this.line = line;
    }
    @Override
    public void MIPSme() {
        sir_MIPS_a_lot.getInstance().set_to_zero(temp_out,line);
    }
}
