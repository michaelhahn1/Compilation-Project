/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;
import MIPS.*;

public class IRcommand_Push extends IRcommand
{

    public IRcommand_Push(TEMP temp, int line)
    {
        this.temp_in1 = temp;
        this.line = line;
    }
    /***************/
    /* MIPS me !!! */
    /***************/
    public void MIPSme()
    {
        sir_MIPS_a_lot.getInstance().change_stack(-4, line);
        if (temp_in1 != null) {
            sir_MIPS_a_lot.getInstance().store_local_sp_offset_temp(temp_in1, 0, line);

        }
    }

}
