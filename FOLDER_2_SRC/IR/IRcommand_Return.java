package IR;

import GLOBAL_VARIABLES.GLOBAL_VARIABLES;
import MIPS.sir_MIPS_a_lot;
import TEMP.TEMP;

public class IRcommand_Return extends IRcommand {
    int number_of_arguments;
    boolean is_main;

    public IRcommand_Return(/*TEMP new_line,*/ TEMP temp, int number_of_arguments, boolean is_main, int line) {
        this.temp_in1 = temp;
        /*this.temp_in2 = new_line;*/
        this.number_of_arguments = number_of_arguments;
        this.is_main = is_main;
        this.line = line;
    }

    public void MIPSme(){
        if (temp_in1 != null){
            sir_MIPS_a_lot.getInstance().store_local_fp_offset_temp(temp_in1, (number_of_arguments + 3) * 4,line);
        }
        for (int i = 0; i < GLOBAL_VARIABLES.NUMBER_OF_REGISTERS; i++){
            sir_MIPS_a_lot.getInstance().load_local_fp_offset_temp(new TEMP(i),  - (i + 1) * 4,line);
        }
        sir_MIPS_a_lot.getInstance().change_stack(100,line);
        sir_MIPS_a_lot.getInstance().change_stack(GLOBAL_VARIABLES.NUMBER_OF_REGISTERS * 4, line);
        if (is_main) {
            /*sir_MIPS_a_lot.getInstance().print_string(temp_in2,line);*/
            sir_MIPS_a_lot.getInstance().exit(line);
        } else {
            sir_MIPS_a_lot.getInstance().jump_register("$ra",line);
        }
    }
}
