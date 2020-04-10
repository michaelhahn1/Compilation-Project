package IR;
import TEMP.*;
import MIPS.sir_MIPS_a_lot;
public class IRcommand_takeFromV0 extends IRcommand {
    public IRcommand_takeFromV0(TEMP temp_out, int line){
        this.temp_out = temp_out;
        this.line = line;
    }
    public void MIPSme(){
        sir_MIPS_a_lot.getInstance().takeFromV0(temp_out,line);
    }
}
