package IR;
import TEMP.*;
import MIPS.sir_MIPS_a_lot;

public class IRcommand_Jump_If_Greater_Then extends IRcommand {
    public IRcommand_Jump_If_Greater_Then(TEMP t1, TEMP t2, String label_name, int line){
        this.temp_in1 = t1;
        this.temp_in2 = t2;
        this.branch = label_name;
        this.line = line;
    }
    @Override
    public void MIPSme() {
        sir_MIPS_a_lot.getInstance().bge(temp_in1,temp_in2,branch,line);
    }
}
