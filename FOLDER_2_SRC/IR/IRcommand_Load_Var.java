package IR;
import MIPS.sir_MIPS_a_lot;
import VAR.*;
import TEMP.*;
public class IRcommand_Load_Var extends IRcommand {
    VAR var;

    public IRcommand_Load_Var(VAR var, TEMP temp, int line){
        this.var = var;
        this.temp_out = temp;
        this.line = line;
    }

    public void MIPSme(){
        if (var.is_global){
            sir_MIPS_a_lot.getInstance().load(temp_out, var.name, line);
        }
        else{
            sir_MIPS_a_lot.getInstance().load_local_fp_offset_temp(temp_out, var.offset_fp, line);
        }
    }
}
