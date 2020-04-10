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
import MIPS.*;

public class IRcommand_Jump_Label extends IRcommand
{
	String branch;
	
	public IRcommand_Jump_Label(String label_name, int line)
	{
		this.branch = label_name;
		this.line = line;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().jump(branch, line);
	}
}
