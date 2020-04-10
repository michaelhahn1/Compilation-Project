package AST;

import TEMP.TEMP;
import TYPES.*;

public class AST_EXP_COMMA_SEPARATED_LIST extends AST_EXP
{
	public AST_EXP e1;
	public AST_EXP_COMMA_SEPARATED_LIST eo1;
	
	public AST_EXP_COMMA_SEPARATED_LIST(AST_EXP e1, AST_EXP_COMMA_SEPARATED_LIST eo1,int lineNumber) {
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.e1 = e1;
		this.eo1 = eo1;
		this.lineNumber = lineNumber;
		System.out.print("====================== AST_EXP_COMMA_SEPARATED_LIST  \n");
	}

	public void PrintMe()
	{
		System.out.print("AST_EXP_COMMA_SEPARATED_LIST\n");
		if (e1 != null) e1.PrintMe();
		if (eo1 != null) eo1.PrintMe(); 
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				"EXP\nLIST\n");
		if (e1 != null)AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e1.SerialNumber);
		if (eo1 != null)AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,eo1.SerialNumber);
	}

	@Override
	public TEMP IRme() {
		return null;
	}

	public TYPE_LIST SemantList() throws Exception
	{
		/*this needs to return a TYPE_LIST so the func call can later check each of the params*/
		TYPE_LIST paramsTypes;
		if (eo1 == null) {
			paramsTypes = new TYPE_LIST(e1.SemantMe(), null);
		}
		else {
			paramsTypes = new TYPE_LIST(e1.SemantMe(),eo1.SemantList());
		}
		return paramsTypes;
	}
	
	public TYPE SemantMe()
	{
		/*there is no semantic value for a list of expressions... this is why this should return null*/
		return null;
	}
}