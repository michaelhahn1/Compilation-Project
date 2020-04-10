package AST;

import TYPES.*;
import SYMBOL_TABLE.*;
import TEMP.*;
import IR.*;

public class AST_STMT_IF extends AST_STMT
{
	public AST_EXP e;
	public AST_STMT_LIST sl;
	
	public AST_STMT_IF(AST_EXP e,AST_STMT_LIST sl,int lineNumber) {
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.e = e;
		this.sl = sl;
		this.sl.inLoop = true;
		this.lineNumber = lineNumber;
		System.out.print("====================== stmt if\n");
	}
	
	public void PrintMe()
	{
		System.out.print("AST NODE IF STMT\n");
		if (e != null) e.PrintMe();
		if (sl != null) sl.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				"IF (left)\nTHEN right\n");
		if (e != null)AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e.SerialNumber);
		if (sl != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,sl.SerialNumber);
	}
	
	public TYPE SemantMe(TYPE expectedType) throws Exception
	{
		TYPE condType = null;
		TYPE retType = null;
		if(e != null) condType = e.SemantMe();
		if(condType == null || condType != TYPE_INT.getInstance())
		{
			throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
		}
		/*so now we know the condition if alright*/
		SYMBOL_TABLE.getInstance().beginScope();
		/*NOTE- if there is a RETURN in the sl of IF- that value needs to be returned!!!*/
		if(sl != null) retType = sl.SemantMe(expectedType);
		SYMBOL_TABLE.getInstance().endScope();
		return retType;	
	}
	public TEMP IRme()
	{
		/*Allocate fresh label*/
		String label_end = IRcommand.getFreshLabel("end");

		/*IRme of the condition*/
		TEMP cond_temp = e.IRme();

		/*Jump conditionally to the end*/
		IR.getInstance().Add_IRcommand(new IRcommand_Jump_If_Eq_To_Zero(cond_temp,label_end,lineNumber));

		/*IRme of if's body*/
		sl.IRme();

		/*If end label*/
		IR.getInstance().Add_IRcommand(new IRcommand_Label(label_end,lineNumber));

		/*return null*/
		return null;
	}
}