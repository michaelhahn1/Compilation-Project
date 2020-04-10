package AST;

import TEMP.TEMP;
import TYPES.*;

public class AST_DEC_LIST extends AST_DEC
{

	public AST_DEC_LIST dl;
	public AST_DEC d;

	public AST_DEC_LIST(AST_DEC d,AST_DEC_LIST dl,int lineNumber) {
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.dl = dl;
		this.d = d;
		this.lineNumber = lineNumber;
		System.out.print("====================== dec list\n");
	}

	public void PrintMe()
	{
		System.out.print("AST NODE DEC LIST\n");
		if (d != null) d.PrintMe();
		if (dl != null) dl.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				"DEC\nLIST\n");
		if (d != null)AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,d.SerialNumber);
		if (dl != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,dl.SerialNumber);

	}

	@Override
	public TEMP IRme() {
		d.IRme();
		if (dl != null) {
			dl.IRme();
		}
		return null;
	}

	public TYPE SemantMe() throws Exception
	{
		/*************************************/
		/* RECURSIVELY SEMANT HEAD + TAIL ... */
		/*************************************/
		if (d != null) d.SemantMe(null);
		if (dl != null) dl.SemantMe();

		return null;
	}
}
