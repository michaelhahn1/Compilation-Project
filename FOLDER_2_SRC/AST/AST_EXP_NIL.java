package AST;

import IR.*;
import TEMP.TEMP;
import TYPES.*;
import TEMP.*;

public class AST_EXP_NIL extends AST_EXP
{
	/*******************************************/
	/* The serial number is for debug purposes */
	/* In particular, it can help in creating  */
	/* a graphviz dot format of the AST ...    */
	/*******************************************/
	public AST_EXP_NIL(int lineNumber)
	{
		this.lineNumber = lineNumber;
		SerialNumber = AST_Node_Serial_Number.getFresh();
		System.out.print("====================== nil  \n");
	}
	/***********************************************/
	/* The default message for an unknown AST node */
	/***********************************************/
	public void PrintMe()
	{
		System.out.print("AST NODE XEP NIL\n");
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				"NIL\nEXP\n");
	}

	@Override
	public TEMP IRme() {

		TEMP nil_temp = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommandConstInt(nil_temp, 0, lineNumber));
		return nil_temp;
	}

	public TYPE SemantMe()
	{
		return TYPE_NIL.getInstance();
	}

	public boolean isConstant() {
		return true;
	}
}