package IR;
import MIPS.*;

public class IRcommand_Call extends IRcommand{
    int number_of_arguments;/*number of arguments*/
    boolean is_void;/*is void*/
    String function_label;


    public IRcommand_Call(String function_label, int number_of_arguments, boolean is_void, int line ){
        this.function_label= function_label;
        this.number_of_arguments = number_of_arguments;
        this.is_void = is_void;
        this.line = line;
    }

    public void MIPSme(){
        sir_MIPS_a_lot.getInstance().change_stack(-8,line);
        sir_MIPS_a_lot.getInstance().store_local_sp_offset("$fp", 4, line);
        sir_MIPS_a_lot.getInstance().store_local_sp_offset("$ra", 0, line);
        sir_MIPS_a_lot.getInstance().move_fp_to_sp(line);
        sir_MIPS_a_lot.getInstance().change_stack(-100,line);
        sir_MIPS_a_lot.getInstance().call_function(function_label,line);
        sir_MIPS_a_lot.getInstance().change_stack(100,line);
        sir_MIPS_a_lot.getInstance().load_local_sp_offset("$fp", 4,line);
        sir_MIPS_a_lot.getInstance().load_local_sp_offset("$ra", 0,line);
        if (is_void){
            sir_MIPS_a_lot.getInstance().change_stack((number_of_arguments + 4) * 4,line); // +3 for $fp and $ra and return value
        }
        else{
            sir_MIPS_a_lot.getInstance().change_stack((number_of_arguments + 3) * 4,line); // +2 for $fp and $ra
        }


        /* ON FUNCTION CALL WITH RETURN VALUES STACK LOOKS LIKE THIS:
        ~CALLER FUNCTION VARIABLES~
        RETURN VALUE
        CALLER_FRAME_POINTER
        RETURN ADDRESS FOR ----CALLER---- FUNCTION
        <---------------- FRAME POINTER POINTS NOW HERE
        ~CALLEE FUNCTION VARIABLES~
         */

    }
}
