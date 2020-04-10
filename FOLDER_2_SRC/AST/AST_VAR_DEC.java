package AST;

import GLOBAL_VARIABLES.GLOBAL_VARIABLES;
import IR.*;
import SYMBOL_TABLE.*;
import TEMP.TEMP;
import TYPES.*;
import VAR.VAR;
import TEMP.*;

public class AST_VAR_DEC extends AST_DEC
{
	public String id1;
	public String id2;
	public AST_EXP e;
	public AST_VAR_DEC(String id1,String id2,AST_EXP e, int lineNumber) {
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.id1 = id1;
		this.id2 = id2;
		this.e = e;
		this.lineNumber = lineNumber;
		System.out.print("====================== var dec  \n");
	}
	
	public TYPE SemantMe(TYPE_CLASS cls) throws Exception

	{ System.out.print("reached semantme of VAR_DEC\n");
	/* check if id1 is valid and check assignment value coherence*/
		TYPE existingDataType = SYMBOL_TABLE.getInstance().find(this.id1);
		if (existingDataType == null) 
		{
			throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
		}
		if (this.e != null) {
			TYPE assignmentType = this.e.SemantMe();	
			if (!existingDataType.isArray()) {
				if (!assignmentType.isInstanceOf(existingDataType)){
					throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
				}
			}
			else {
				TYPE_ARRAY existingDataTypeArray = (TYPE_ARRAY) existingDataType;
				if (assignmentType != existingDataTypeArray.typeOfArray && assignmentType != TYPE_NIL.getInstance()) {
					throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
				}
			}
			
		}
		
		/* check variable name validity */
		if (cls != null) 
		{
			if (e != null) {
				if (!e.isConstant()) {
					throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
				}
			}
			if (cls.searchDataMembersThisClassOnly(id2) != null) 
			{
				throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
			}
			TYPE existingDataMember = cls.searchDataMembers(id2);
			if (existingDataMember != null) {
				if (existingDataMember.isFunction())
				{
					throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
				}

				if (existingDataType.isInstanceOf(existingDataMember)) {
					throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
				}
			}
			
		}
		
		if (SYMBOL_TABLE.getInstance().findScope(this.id2) == SYMBOL_TABLE.getInstance().getScope()) {
			throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
		}

		symbol_table_entry = SYMBOL_TABLE.getInstance().enter(this.id2, existingDataType);
		symbol_table_entry.cls = cls;
		if (cls != null) {
			cls.addDataMember(this.id2, existingDataType, e);
		}
		return null;
	}
	
	public void PrintMe()
	{
		System.out.print("AST NODE DEC VAR\n");
		/****************************************/
		/* RECURSIVELY PRINT VAR + FIELDS ... */
		/****************************************/
		if (e != null) e.PrintMe();
		
		System.out.format("FIELD NAME( %s )\n",id1);
		System.out.format("FIELD NAME( %s )\n",id2);

		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				String.format("DEC\nVAR\n...->%s, %s",id1, id2));
		if (e != null)AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e.SerialNumber);
	}

	private int get_number_of_var_up_to_current(){
		SYMBOL_TABLE_ENTRY entry = symbol_table_entry;
		int number_of_vars = 0;
		while (!entry.type.isFunction()){
			entry = entry.prevtop;
			number_of_vars++;
		}
		TYPE_FUNCTION function_type = (TYPE_FUNCTION) entry.type;
		if (function_type.params == null){
			return number_of_vars;
		}
		return number_of_vars - function_type.params.getLength();

		/*we assume symbol table inside a function takes the following form:
		*  FUNC_TYPE
		*  ~PARAMETER ENTRIES~
		*  ~VARIABLE ENTRIES~*/
	}

	public TEMP IRme(){
		symbol_table_entry.offset = -(get_number_of_var_up_to_current() + GLOBAL_VARIABLES.NUMBER_OF_REGISTERS) * 4;
		if (symbol_table_entry.scope == 1) {
			IR.getInstance().Add_Data_IRcommand(new IRcommand_Allocate(symbol_table_entry.name,lineNumber));
		}
		if ((e != null || symbol_table_entry.type.isClass()) && symbol_table_entry.cls == null){
			VAR var = new VAR(symbol_table_entry.offset, symbol_table_entry.name, symbol_table_entry.scope == 1);
			IRcommandList crnt_end = IR.getInstance().getEndOfListIRCommands();
			if (e != null) {
				IR.getInstance().Add_IRcommand(new IRcommand_Set_Var(var, e.IRme(),lineNumber));
			}
			else
			{
				TEMP default_value = TEMP_FACTORY.getInstance().getFreshTEMP();
				IR.getInstance().Add_IRcommand(new IRcommandConstInt(default_value, 0, lineNumber));
				IR.getInstance().Add_IRcommand(new IRcommand_Set_Var(var, default_value, lineNumber));
			}
			if (symbol_table_entry.scope == 1) {
				IRcommandList it = crnt_end;
				while(it!=null){
					IR.getInstance().Add_Start_Of_Main_IRcommand(it.head);
					it = it.tail;
				}
				crnt_end.tail = null;
			}


		}
		return null;
	}

}
