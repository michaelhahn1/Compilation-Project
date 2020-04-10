package AST;

import TEMP.TEMP;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_PROGRAM extends AST_Node
{
	public AST_DEC_LIST dl;
	
	public AST_PROGRAM(AST_DEC_LIST dl,int lineNumber) {
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.dl = dl;
		this.lineNumber =lineNumber;
		System.out.print("====================== prog  \n");
	}
	
	public void PrintMe()
	{
		System.out.print("AST NODE PROGRAM\n");

		if (dl != null) dl.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				"PROG\n");
		if (dl != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,dl.SerialNumber);
	}

	@Override
	public TEMP IRme() {
		return dl.IRme();
	}


	public TYPE SemantMe() throws Exception
	{
		SYMBOL_TABLE.getInstance().beginScope();
		if(dl != null) dl.SemantMe();
		SYMBOL_TABLE.getInstance().endScope();
		return null;
	}
}
