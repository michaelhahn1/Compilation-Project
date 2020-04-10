package IR;

import MIPS.sir_MIPS_a_lot;
import TEMP.TEMP;

public class IRcommand_Binop_Addi_Integers extends IRcommand{

	public int t2;
	
	public IRcommand_Binop_Addi_Integers(TEMP dst, TEMP temp_in1, int t2,int line)
	{
		this.temp_out = dst;
		this.temp_in1 = temp_in1;
		this.t2 = t2;
		this.line = line;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().addi(temp_out, temp_in1,t2,line);
	}
}
