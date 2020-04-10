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

public class IRcommand_Binop_Add_Integers extends IRcommand
{

	
	public IRcommand_Binop_Add_Integers(TEMP dst,TEMP t1,TEMP t2, int line)
	{
		this.line = line;
		this.temp_out = dst;
		this.temp_in1 = t1;
		this.temp_in2 = t2;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().add(temp_out,temp_in1,temp_in2,line);
	}
}
