package IR;

import MIPS.sir_MIPS_a_lot;
import TEMP.TEMP;

public class IRcommand_Load_String_Address extends IRcommand{
	String var_name;
	
	public IRcommand_Load_String_Address(TEMP dst,String var_name, int line)
	{
		this.temp_out      = dst;
		this.var_name = var_name;
		this.line = line;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().load_string_address(temp_out,var_name,line);
	}
	
}
