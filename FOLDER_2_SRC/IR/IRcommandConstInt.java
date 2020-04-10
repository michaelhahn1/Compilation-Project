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

public class IRcommandConstInt extends IRcommand
{
	int value;
	
	public IRcommandConstInt(TEMP t,int value, int line)
	{
		this.temp_out = t;
		this.value = value;
		this.line = line;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().li(temp_out,value, line);
	}
}
