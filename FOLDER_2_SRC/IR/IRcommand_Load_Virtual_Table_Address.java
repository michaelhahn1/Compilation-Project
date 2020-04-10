package IR;

import MIPS.sir_MIPS_a_lot;
import IR.*;
import TEMP.*;

public class IRcommand_Load_Virtual_Table_Address extends IRcommand {

    String cls;

    public IRcommand_Load_Virtual_Table_Address(TEMP dst, String cls_name, int linenumber) {
        temp_in1 = dst;
        cls = cls_name;
        line = linenumber;
    }


    @Override
    public void MIPSme() {
        sir_MIPS_a_lot.getInstance().load_virtual_table(temp_in1, cls ,line);
    }
}
