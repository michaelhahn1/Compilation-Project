package AST;

import TYPES.*;
import TEMP.*;
import IR.*;

public class AST_EXP_INT extends AST_EXP
{
	public int i;
	public AST_EXP_INT(int i,int lineNumber) {
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.i = i;
		this.lineNumber = lineNumber;
		System.out.format("====================== exp -> INT( %d )\n", i);
	}
	
	/***********************************************/
	/* The printing message for an INT EXP AST node */
	/***********************************************/
	public void PrintMe()
	{
		System.out.format("AST NODE INT( %d )\n",i);
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				String.format("INT(%d)",i));	
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
		IR.getInstance().Add_IRcommand(new IRcommandConstInt(t,i,lineNumber));
		return t;
	}
}