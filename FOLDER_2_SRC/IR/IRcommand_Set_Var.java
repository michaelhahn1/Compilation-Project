package IR;
import MIPS.sir_MIPS_a_lot;
import VAR.*;
import TEMP.*;
public class IRcommand_Set_Var extends IRcommand {
    VAR var;

    public IRcommand_Set_Var(VAR var, TEMP temp, int line) {
        this.var = var;
        this.temp_in1 = temp;
        this.line = line;
    }

    public void MIPSme() {
        if (var.is_global) {
            sir_MIPS_a_lot.getInstance().store(var.name, temp_in1, line);
        } else {
            sir_MIPS_a_lot.getInstance().store_local_fp_offset_temp(temp_in1, var.offset_fp, line);
        }
    }
}