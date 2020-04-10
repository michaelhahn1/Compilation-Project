package AST;

import TEMP.TEMP;
import TYPES.*;

public class AST_DEC_BASIC extends AST_DEC
{
	public AST_DEC d;
	
	public AST_DEC_BASIC(AST_DEC d,int lineNumber) {
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.d = d;
		this.lineNumber = lineNumber;
		System.out.print("====================== basic\n");
	}
	
	public void PrintMe()
	{
		System.out.print("AST NODE DEC BASIC\n");
		if (d != null) d.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				"BASIC\nDEC\n");
		if (d != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,d.SerialNumber);
	}

	@Override
	public TEMP IRme() {
		return d.IRme();
	}

	public TYPE SemantMe(TYPE_CLASS cls) throws Exception
	{
		if(d != null) d.SemantMe(cls);
		return null;
	}
}
