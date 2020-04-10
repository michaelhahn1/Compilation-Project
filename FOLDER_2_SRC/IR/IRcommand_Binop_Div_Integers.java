package IR;

import MIPS.sir_MIPS_a_lot;
import TEMP.TEMP;

public class IRcommand_Binop_Div_Integers extends IRcommand{

	public IRcommand_Binop_Div_Integers(TEMP temp_out, TEMP temp_in1, TEMP temp_in2, int line) {
		this.temp_in1 = temp_in1;
		this.temp_in2 = temp_in2;
		this.temp_out = temp_out;
		this.line = line;
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().div(temp_out,temp_in1,temp_in2, line);
	}
}
