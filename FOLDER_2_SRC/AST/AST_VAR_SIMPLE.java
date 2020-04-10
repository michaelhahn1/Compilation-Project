package AST;

import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import VAR.VAR;

public class AST_VAR_SIMPLE extends AST_VAR
{
	public String id1;
	public AST_VAR_SIMPLE(String id1, int lineNumber) {
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.id1 = id1;
		this.lineNumber = lineNumber;
		System.out.format("====================== var -> ID( %s )\n",id1);
	}

	public void PrintMe()
	{
		/**********************************/
		/* AST NODE TYPE = AST SIMPLE VAR */
		/**********************************/
		System.out.format("AST NODE SIMPLE VAR(%s)\n", id1);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("SIMPLE\nVAR\n(%s)",id1));	
	}
	
	public TYPE SemantMe() throws Exception
	{
		TYPE simpleVarType = SYMBOL_TABLE.getInstance().find(id1);
		symbol_table_entry = SYMBOL_TABLE.getInstance().findEntry(id1);
		/* note that this doen't need to add anything to the table bc this is not a declaration*/
		if(simpleVarType == null)/*if didn't find this var*/
		{
			throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
		}
		return simpleVarType;
	}
	public TYPE_FUNCTION getFuncType(SYMBOL_TABLE_ENTRY entry){
		while (!entry.type.isFunction()) {
			entry = entry.prevtop;
		}
		return (TYPE_FUNCTION) entry.type;
	}

	public TEMP IRme(){
		if (symbol_table_entry.cls == null) {
			TEMP temp = TEMP_FACTORY.getInstance().getFreshTEMP();
			boolean is_global;
			is_global = symbol_table_entry.scope == 1;
			IR.getInstance().Add_IRcommand(new IRcommand_Load_Var(
					new VAR(symbol_table_entry.offset, symbol_table_entry.name, is_global), temp, lineNumber));
			return temp;
		} else {
			TYPE_FUNCTION func = getFuncType(SYMBOL_TABLE.getInstance().top);
			int args;
			if (func.params != null) {
				args = func.params.getLength();
			} else {
				args = 0;
			}
			int offset = symbol_table_entry.cls.getFieldOffset(symbol_table_entry.name);
			TEMP instance = TEMP_FACTORY.getInstance().getFreshTEMP();
			TEMP result = TEMP_FACTORY.getInstance().getFreshTEMP();
			IR.getInstance().Add_IRcommand(new IRcommand_Load_Var(new VAR((args + 3)*4, "", false), instance, lineNumber));
			IR.getInstance().Add_IRcommand(new IRcommand_Load_Register(result, instance, offset, lineNumber));
			return result;

		}

	}
}