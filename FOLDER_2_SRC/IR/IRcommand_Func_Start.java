package IR;

import GLOBAL_VARIABLES.GLOBAL_VARIABLES;
import MIPS.sir_MIPS_a_lot;
import TEMP.TEMP;

public class IRcommand_Func_Start extends IRcommand {
    /*mipsme: save 8 register + restore them on return*/
    String class_name;
    public IRcommand_Func_Start(String func_name, String class_name, int line) {
        this.label = func_name;
        this.line = line;
        this.class_name = class_name;
    }

    public void MIPSme() {
        if (class_name == null) {
            sir_MIPS_a_lot.getInstance().label(label,line);
        }
        else{
            sir_MIPS_a_lot.getInstance().label(String.format("%s_%s",label,class_name),line);
        }
        if (label.equals("main")) {
            sir_MIPS_a_lot.getInstance().load_func_address(new TEMP(0), "main", line);
            sir_MIPS_a_lot.getInstance().change_stack(-12, line);
            sir_MIPS_a_lot.getInstance().store_local_sp_offset("$t0", 8, line);
            sir_MIPS_a_lot.getInstance().store_local_sp_offset("$zero", 4, line);
            sir_MIPS_a_lot.getInstance().move_fp_to_sp(line);
        }
        sir_MIPS_a_lot.getInstance().change_stack(-GLOBAL_VARIABLES.NUMBER_OF_REGISTERS * 4, line);
        for (int i = 0; i < GLOBAL_VARIABLES.NUMBER_OF_REGISTERS; i++){
            sir_MIPS_a_lot.getInstance().store_local_fp_offset_temp(new TEMP(i),  - (i + 1) * 4,line);
        }
        sir_MIPS_a_lot.getInstance().change_stack(-100,line);
    }
}
