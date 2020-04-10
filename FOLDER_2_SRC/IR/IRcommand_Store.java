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

public class IRcommand_Store extends IRcommand
{
	String var_name;
	TEMP temp_in1;
	
	public IRcommand_Store(String var_name,TEMP src, int line)
	{
		this.temp_in1      = src;
		this.var_name = var_name;
		this.line = line;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().store(var_name,temp_in1, line);
	}
}
