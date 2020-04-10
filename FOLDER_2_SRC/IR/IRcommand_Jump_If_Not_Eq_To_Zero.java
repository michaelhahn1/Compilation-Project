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

public class IRcommand_Jump_If_Not_Eq_To_Zero extends IRcommand
{
	
	public IRcommand_Jump_If_Not_Eq_To_Zero(TEMP t, String label_name, int line)
	{
		this.temp_in1          = t;
		this.branch = label_name;
		this.line = line;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().bnez(temp_in1,branch,line);
	}
}
