package IR;

import MIPS.sir_MIPS_a_lot;

public class IRcommand_Start_virtualTable extends IRcommand {
     public IRcommand_Start_virtualTable(String nameOfClass, int line){
         label = String.format("Virtual_Table_%s",nameOfClass);
         this.line = line;
     }
    /***************/
    /* MIPS me !!! */
    /***************/
    public void MIPSme()
    {
        sir_MIPS_a_lot.getInstance().virtualTable_label(label);
    }
}
