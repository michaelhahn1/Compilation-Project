package AST;

import TEMP.TEMP;
import TYPES.*;

public class AST_C_FIELD extends AST_Node
{
	public AST_DEC d;
	public AST_C_FIELD(AST_DEC d,int lineNumber) {
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.d = d;
		this.lineNumber = lineNumber;
		System.out.print("====================== cfield\n");
	}
	
	public void PrintMe()
	{
		System.out.print("AST NODE C FIELD\n");
		if (d != null) d.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				"C\nFIELD\n");
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
