package AST;

import IR.*;
import TEMP.*;
import TYPES.*;

public class AST_EXP_MINUS_INT extends AST_EXP
{
	public int i;
	public AST_EXP_MINUS_INT(int i,int lineNumber) {
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.i = i;
		this.lineNumber = lineNumber;
		System.out.format("====================== exp -> MINUS_INT( %d )\n", i);
	}
	
	/***********************************************/
	/* The default message for an unknown AST node */
	/***********************************************/
	public void PrintMe()
	{
		System.out.format("AST NODE MINUS INT( %d )\n",i);
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				String.format("MINUSINT(%d)",i));
	}
	
	public TYPE SemantMe()
	{
		return TYPE_INT.getInstance();
	}
	
	public boolean isConstant() {
		return true;
	}
	
	/*EX 4:*/
	public TEMP IRme()
	{
		/*Generate a temp to hold the value (value is stored in i)*/
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		/*Note- because of how the cup is defined i is the absolute value*/
		IR.getInstance().Add_IRcommand(new IRcommandConstInt(t,(-1)*i,lineNumber));
		return t;
	}
}