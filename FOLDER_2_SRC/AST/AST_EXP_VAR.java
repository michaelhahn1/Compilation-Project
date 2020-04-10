package AST;

import IR.*;
import TEMP.*;
import TYPES.*;

public class AST_EXP_VAR extends AST_EXP
{
	public AST_VAR v;
	
	public AST_EXP_VAR(AST_VAR v, int lineNumber) {
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.v = v;
		this.lineNumber = lineNumber;
		System.out.print("====================== exp var  \n");
	}
	
	/************************************/
	/* AST NODE TYPE = EXP VAR AST NODE */
	/************************************/
	public void PrintMe()
	{
		System.out.print("AST NODE EXP VAR\n");
		if (v != null) v.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				"EXP\nVAR\n");
		if (v != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,v.SerialNumber);

	}
	
	public TYPE SemantMe() throws Exception
	{
		return v.SemantMe();
	}

	public TEMP IRme()
	{
		return v.IRme();
	}
}