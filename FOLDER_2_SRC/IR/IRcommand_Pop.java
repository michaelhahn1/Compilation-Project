package IR;

import MIPS.sir_MIPS_a_lot;
import TEMP.TEMP;

public class IRcommand_Pop extends IRcommand { /*temp = POP*/
    public IRcommand_Pop(TEMP temp, int line){
        this.temp_out = temp;
        this.line = line;
    }

    public void MIPSme(){
        sir_MIPS_a_lot.getInstance().load_local_sp_offset_temp(temp_out, 0, line);
        sir_MIPS_a_lot.getInstance().change_stack(4, line);
    }
}
