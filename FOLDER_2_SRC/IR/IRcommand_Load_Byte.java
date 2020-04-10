package IR;

import MIPS.sir_MIPS_a_lot;
import TEMP.TEMP;

public class IRcommand_Load_Byte extends IRcommand {
	
	public IRcommand_Load_Byte(TEMP dst, TEMP address, int line)
	{
		this.temp_out      = dst;
		this.temp_in1  = address;
		this.line = line;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().load_byte(temp_out, temp_in1, line);
	}
}
