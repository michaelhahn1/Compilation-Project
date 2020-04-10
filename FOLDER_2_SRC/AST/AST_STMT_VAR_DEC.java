package AST;

import TEMP.TEMP;
import TYPES.*;

public class AST_STMT_VAR_DEC extends AST_STMT
{
	public AST_VAR_DEC vd;	
	
	public AST_STMT_VAR_DEC(AST_VAR_DEC vd, int lineNumber) {
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.vd = vd;
		this.lineNumber = lineNumber;
		System.out.print("====================== var dec  \n");
	}
	
	public void PrintMe()
	{
		System.out.print("AST NODE DEC VAR STMT\n");

		/****************************************/
		/* RECURSIVELY PRINT */
		/****************************************/
		if (vd != null) vd.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				"STMT\nDEC\nVAR\n");
		if (vd != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,vd.SerialNumber);
	}

	@Override
	public TEMP IRme() {
		return vd.IRme();
	}

	public TYPE SemantMe(TYPE expectedType) throws Exception
	{
		if(vd != null) vd.SemantMe(null);
		return null;
	}
}