package AST;

import GLOBAL_VARIABLES.*;
import IR.*;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;
import Register_Allocation.*;

public class AST_FUNC_DEC extends AST_DEC
{
	public String id1;
	public String id2;
	public AST_FUNCDEC_TYPE_SIGNATURE fdo1;
	public AST_STMT_LIST sl;
	public boolean isFuncDec = true;
	public TYPE_CLASS cls;
	
	public AST_FUNC_DEC(String id1,String id2,AST_FUNCDEC_TYPE_SIGNATURE fdo1,AST_STMT_LIST sl,int lineNumber) {
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.id1 = id1;
		this.id2 = id2;
		this.fdo1 = fdo1;
		this.sl = sl;
		this.lineNumber = lineNumber;
		System.out.print("======================func dec  \n");

	}
	
	public void PrintMe()
	{
		System.out.print("AST NODE FUNC DEC\n");
		if (fdo1 !=null) fdo1.PrintMe();
		if (sl != null) sl.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				String.format("FUNCDEC %s %s",id1,id2));
		if (fdo1 != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,fdo1.SerialNumber);
		if (sl != null)AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,sl.SerialNumber);
	}
	
	public TYPE SemantMe(TYPE_CLASS cls) throws Exception
	{
		/* check if id1 is valid and check return value coherence*/
		SYMBOL_TABLE.getInstance().beginScope();
		SYMBOL_TABLE.getInstance().enter("void", TYPE_VOID.getInstance());
		TYPE existingDataType = SYMBOL_TABLE.getInstance().find(this.id1);
		SYMBOL_TABLE.getInstance().endScope();
		if (existingDataType == null) 
		{
			throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
		}
		if (SYMBOL_TABLE.getInstance().find(this.id2) != null && SYMBOL_TABLE.getInstance().findScope(this.id2) != 1) {
			System.out.print("SYMBOL_TABLE.getInstance().find(this.id2) != null\n");
						throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
		}
		TYPE_FUNCTION newFunction = new TYPE_FUNCTION(existingDataType, id2, null);
		symbol_table_entry = SYMBOL_TABLE.getInstance().enter(this.id2, newFunction);
		SYMBOL_TABLE.getInstance().beginScope();
		TYPE_LIST signatureTypeList;
		if (fdo1 != null) {
			signatureTypeList =  this.fdo1.SemantList();
		}
		else {
			signatureTypeList = null;
		}
		
		newFunction.set_params(getTypeListFromNamedTypeList(signatureTypeList));
	
		
		this.sl.SemantMe(existingDataType);

		SYMBOL_TABLE.getInstance().endScope();
		
		
		/* check function name validity */
		if (cls != null) 
		{
			if (cls.searchDataMembersThisClassOnly(id2) != null) 
			{
				throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
			}
			TYPE existingDataMember = cls.searchDataMembers(id2);
			if (existingDataMember != null) {
				if (!existingDataMember.isFunction())
				{
					throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
				}
				TYPE_FUNCTION existingDataMemberFunction = (TYPE_FUNCTION) existingDataMember;
				
				if(existingDataMemberFunction.params == null && newFunction.params != null)
				{
					throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
				}

				if (!(existingDataMemberFunction.params == null ) && !existingDataMemberFunction.params.sameListAs(newFunction.params)) {
System.out.print("!existingDataMemberFunction.params.sameListAs(params)\n");
					throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
				}

				if (!existingDataType.isInstanceOf(existingDataMemberFunction.returnType)) {
					throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
				}
			}
			this.cls = (TYPE_CLASS) cls;
			newFunction.cls  = cls;
		}

		if (cls != null) {
			cls.addDataMember(this.id2, newFunction, null);
		}

		return null;
	}

	public TEMP IRme(){
		if (fdo1!=null){
			fdo1.IRme();
		}
		IRcommandList function_ir_commands = IR.getInstance().getEndOfListIRCommands();
		if (!FUNCTIONS_DECLARED.getInstance().functions_list.contains(id2)) {
			IR.getInstance().Add_Data_IRcommand(new IRcomamnd_Allocate_String(id2, id2, true, lineNumber));
			FUNCTIONS_DECLARED.getInstance().functions_list.add(id2);
		}
		if (this.cls != null) {
			IR.getInstance().Add_IRcommand(new IRcommand_Func_Start(id2, cls.name, lineNumber));
		}
		else{
			IR.getInstance().Add_IRcommand(new IRcommand_Func_Start(id2,null,lineNumber));
		}
		if (id2.equals("main")) {
			IR.getInstance().getEndOfListIRCommands().tail = IR.getInstance().start_of_main;
		}
		sl.IRme();
		register_allocation.allocate(function_ir_commands);

		return null;
	}
}
