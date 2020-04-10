package IR;
import TEMP.*;
import MIPS.sir_MIPS_a_lot;
public class IRcommand_Store_Byte extends IRcommand {
    public IRcommand_Store_Byte(TEMP dstAddr, TEMP src, int line){
        this.temp_in1 = src;
        this.temp_in2 = dstAddr;
        this.line = line;
    }

    @Override
    public void MIPSme() {
        sir_MIPS_a_lot.getInstance().store_byte(temp_in1,temp_in2,0,line);
    }
}
