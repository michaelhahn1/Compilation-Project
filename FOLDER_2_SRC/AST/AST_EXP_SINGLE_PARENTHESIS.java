package AST;

import TYPES.*;
import TEMP.*;
public class AST_EXP_SINGLE_PARENTHESIS extends AST_EXP
{
	/*******************************************/
	/* The serial number is for debug purposes */
	/* In particular, it can help in creating  */
	/* a graphviz dot format of the AST ...    */
	/*******************************************/

	public AST_EXP e;
	
	public AST_EXP_SINGLE_PARENTHESIS(AST_EXP e,int lineNumber) {
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.e = e;
		this.lineNumber = lineNumber;
		System.out.print("====================== single parenthesis  \n");
	}
	
	/***********************************************/
	/* The default message for an unknown AST node */
	/***********************************************/
	public void PrintMe()
	{
		System.out.print("AST NODE EXP SINGLE PAREN\n");
		if (e != null) e.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				"(EXP)\n");
		if (e != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e.SerialNumber);

	}
	
	public TYPE SemantMe() throws Exception
	{
		return e.SemantMe();
	}
	public TEMP IRme()
	{
		/*the parenthesis don't really add anything at this point so it's safe just to IRme the expression*/
		return e.IRme();
	}
}