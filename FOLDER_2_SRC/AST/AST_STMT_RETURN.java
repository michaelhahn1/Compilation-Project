package AST;

import IR.*;
import SYMBOL_TABLE.SYMBOL_TABLE;
import SYMBOL_TABLE.SYMBOL_TABLE_ENTRY;
import TYPES.*;
import TEMP.*;

public class AST_STMT_RETURN extends AST_STMT
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_EXP e;
	TYPE_FUNCTION function_type;

	/*******************/
	/*  CONSTRUCTOR(S) */

	/*******************/
	public AST_STMT_RETURN(AST_EXP e, int lineNumber) {

		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.e = e;
		this.lineNumber = lineNumber;

		System.out.print("======================return  \n");

	}
	
	public void PrintMe()
	{
		System.out.print("AST NODE STMT RETURN\n");

		/*****************************/
		/* RECURSIVELY PRINT exp ... */
		/*****************************/
		if (e != null) e.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				"RETURN\n");
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (e != null)AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e.SerialNumber);
	}
	
	public TYPE SemantMe(TYPE expectedType) throws Exception 
	{
		TYPE returnedType = null;
		function_type = getFuncType(SYMBOL_TABLE.getInstance().top);
		
		if(e != null) returnedType = e.SemantMe();
		if(e == null) returnedType = TYPE_VOID.getInstance();
		if (!returnedType.isInstanceOf(expectedType)) {
			throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
		}
		return returnedType;
	}

	public TYPE_FUNCTION getFuncType(SYMBOL_TABLE_ENTRY entry){
		while (!entry.type.isFunction()) {
			entry = entry.prevtop;
		}
		return (TYPE_FUNCTION) entry.type;
	}

	public TEMP IRme() {
		int bonus = 0;
		if (function_type.cls != null) {
			bonus += 1;
		}
		if (e != null) {
			if (function_type.params == null) {
				IR.getInstance().Add_IRcommand(
						new IRcommand_Return(e.IRme(), bonus, function_type.name.equals("main"),lineNumber));
			}
			else{
				IR.getInstance().Add_IRcommand(
						new IRcommand_Return(e.IRme(), function_type.params.getLength() + bonus, function_type.name.equals("main"),lineNumber));
			}

		} else {
			IRcommand_Return return_command;
			if (function_type.params == null) {
				return_command = new IRcommand_Return(null, bonus, function_type.name.equals("main"),lineNumber);
			} else {
				return_command = new IRcommand_Return(null, function_type.params.getLength() + bonus, function_type.name.equals("main"),lineNumber);
			}
			IR.getInstance().Add_IRcommand(
					return_command);
		}
		return null;
	}
}