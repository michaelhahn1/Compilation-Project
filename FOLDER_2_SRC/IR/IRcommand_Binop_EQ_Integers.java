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

public class IRcommand_Binop_EQ_Integers extends IRcommand
{

	public IRcommand_Binop_EQ_Integers(TEMP dst,TEMP t1,TEMP t2, int line )
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
		/*******************************/
		/* [1] Allocate 3 fresh labels */
		/*******************************/
		String label_end        = getFreshLabel("end");
		String label_AssignOne  = getFreshLabel("AssignOne");
		String label_AssignZero = getFreshLabel("AssignZero");
		
		/******************************************/
		/* [2] if (t1==t2) goto label_AssignOne;  */
		/*     if (t1!=t2) goto label_AssignZero; */
		/******************************************/
		sir_MIPS_a_lot.getInstance().beq(temp_in1,temp_in2,label_AssignOne,line);
		sir_MIPS_a_lot.getInstance().bne(temp_in1,temp_in2,label_AssignZero,line);

		/************************/
		/* [3] label_AssignOne: */
		/*                      */
		/*         t3 := 1      */
		/*         goto end;    */
		/*                      */
		/************************/
		sir_MIPS_a_lot.getInstance().label(label_AssignOne,line);
		sir_MIPS_a_lot.getInstance().li(temp_out,1, line);
		sir_MIPS_a_lot.getInstance().jump(label_end, line);

		/*************************/
		/* [4] label_AssignZero: */
		/*                       */
		/*         t3 := 1       */
		/*         goto end;     */
		/*                       */
		/*************************/
		sir_MIPS_a_lot.getInstance().label(label_AssignZero, line);
		sir_MIPS_a_lot.getInstance().li(temp_out,0, line);
		sir_MIPS_a_lot.getInstance().jump(label_end, line);

		/******************/
		/* [5] label_end: */
		/******************/
		sir_MIPS_a_lot.getInstance().label(label_end, line);
	}
}
