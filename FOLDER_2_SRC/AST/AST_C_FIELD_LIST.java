package AST;

import TEMP.TEMP;
import TYPES.*;

public class AST_C_FIELD_LIST extends AST_Node
{
	public AST_C_FIELD cf;
	public AST_C_FIELD_LIST cfl;
	public AST_C_FIELD_LIST(AST_C_FIELD cf,AST_C_FIELD_LIST cfl, int lineNumber) {
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.cf = cf;
		this.cfl = cfl;
		this.lineNumber = lineNumber;
		System.out.print("====================== Ac field list\n");
	}
	
	public void PrintMe()
	{
		System.out.print("AST NODE FIELD LIST\n");
		if (cf != null) cf.PrintMe();
		if (cfl != null) cfl.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				"FIELD\nLIST\n");
		if (cf != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cf.SerialNumber);
		if (cfl != null)AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cfl.SerialNumber);
	}

	@Override
	public TEMP IRme() {

		if(cf != null) cf.IRme();
		if(cfl != null) cfl.IRme();
		return null;
	}

	public TYPE SemantMe(TYPE_CLASS cls) throws Exception
	{
		if(cf != null) cf.SemantMe(cls);
		if(cfl != null) cfl.SemantMe(cls);
		return null;
	}
}