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

public class IRcommand_Binop_Mul_Integers extends IRcommand
{
	public IRcommand_Binop_Mul_Integers(TEMP dst,TEMP t1,TEMP t2, int line)
	{
		this.temp_out = dst;
		this.temp_in1 = t1;
		this.temp_in2 = t2;
		this.line = line;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().mul(temp_out,temp_in1,temp_in2, line);
	}
}
