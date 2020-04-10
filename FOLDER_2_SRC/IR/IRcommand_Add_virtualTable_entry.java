package IR;

import MIPS.sir_MIPS_a_lot;

public class IRcommand_Add_virtualTable_entry extends IRcommand{
    String label_;

    int mode;
    public IRcommand_Add_virtualTable_entry(String funcName, String className, int mode)
    {
        this.mode = mode;
        label_ = String.format("%s_%s",funcName,className);
    }
    public void MIPSme() {
        if (mode == -1){
            sir_MIPS_a_lot.getInstance().newline();
            return;
        }
        if (mode == 0) {
            sir_MIPS_a_lot.getInstance().virtualTable_entry_start(label_);
        } else {
            sir_MIPS_a_lot.getInstance().virtualTable_entry(label_);
        }

    }
}

