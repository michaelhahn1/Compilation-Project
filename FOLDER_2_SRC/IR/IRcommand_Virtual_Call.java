package IR;
import MIPS.sir_MIPS_a_lot;
import TEMP.*;
import IR.*;

public class IRcommand_Virtual_Call extends IRcommand{

    int number_of_arguments;
    boolean is_void;
    public IRcommand_Virtual_Call(TEMP instance, int number_of_arguments, boolean is_void, int linenumber) {
        line = linenumber;
        this.number_of_arguments = number_of_arguments;
        this.is_void = is_void;
        temp_in1 = instance;
    }

    @Override
    public void MIPSme() {
        sir_MIPS_a_lot.getInstance().change_stack(-8,line);
        sir_MIPS_a_lot.getInstance().store_local_sp_offset("$fp", 4, line);
        sir_MIPS_a_lot.getInstance().store_local_sp_offset("$ra", 0, line);
        sir_MIPS_a_lot.getInstance().move_fp_to_sp(line);
        sir_MIPS_a_lot.getInstance().jal_register(temp_in1, line);
        sir_MIPS_a_lot.getInstance().load_local_sp_offset("$fp", 4,line);
        sir_MIPS_a_lot.getInstance().load_local_sp_offset("$ra", 0,line);
        if (is_void){
            sir_MIPS_a_lot.getInstance().change_stack((number_of_arguments + 5) * 4,line); // +3 for $fp and $ra and return value
        }
        else{
            sir_MIPS_a_lot.getInstance().change_stack((number_of_arguments + 4) * 4,line); // +2 for $fp and $ra
        }

    }
}
