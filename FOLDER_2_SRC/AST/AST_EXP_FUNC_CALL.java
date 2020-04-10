package AST;
import IR.IR;
import TEMP.TEMP;
import TYPES.*;
import SYMBOL_TABLE.*;
import IR.*;
import TEMP.*;

public class AST_EXP_FUNC_CALL extends AST_EXP
{
	public AST_VAR v;
	public String id1;
	public AST_EXP_COMMA_SEPARATED_LIST eo1;
	
	public AST_EXP_FUNC_CALL(AST_VAR v,String id1,AST_EXP_COMMA_SEPARATED_LIST eo1, int lineNumber) {
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.v = v;
		this.id1 = id1;
		this.eo1 = eo1;
		this.lineNumber = lineNumber;
		System.out.print("====================== exp func call\n");
	}
	
	public TYPE SemantMe() throws Exception{
		TYPE_FUNCTION function;
		if (this.v != null) {
			TYPE variableType = v.SemantMe();
			if (!variableType.isClass()) {
				throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
			}
			TYPE clsField = ((TYPE_CLASS) variableType).searchDataMembers(this.id1);
			if (clsField == null) {
				/*what if someone stupid writes: var.
				 * hello(jgksgk); we need to return the correct line number...*/
				throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
			}
			if (!clsField.isFunction()) {
				throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
			}
			function = (TYPE_FUNCTION) clsField;
		}
		else {
			TYPE existingType = SYMBOL_TABLE.getInstance().find(id1);
			if (existingType == null) {
				throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
			}
			if (!existingType.isFunction()) {
				throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
			}
			function = (TYPE_FUNCTION) existingType;
		}
		
		if (this.eo1 != null) {
			if (!this.eo1.SemantList().sameListAs(function.params)) {
				throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
			}
		}
		else {
			if (function.params != null) {
				throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
			}
		}
		
		if (function.returnType == TYPE_VOID.getInstance()) {
			throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
		}
		
		return function.returnType;	
	}
	
	public void PrintMe()
	{
		System.out.print("AST NODE EXP LONG\n");
		if (v != null) v.PrintMe();
		if (eo1 != null) eo1.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				"LONG\nEXP\n");
		if (v != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,v.SerialNumber);
		if (eo1 != null)AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,eo1.SerialNumber);
	}

	public TEMP IRme()
	{
		if (v == null){
			AST_EXP_COMMA_SEPARATED_LIST arg_list = eo1;
			int number_of_arguments = 0;
			IR.getInstance().Add_IRcommand(new IRcommand_Push(null,lineNumber)); //get space for return value
			while (arg_list != null){
				TEMP t = arg_list.e1.IRme();
				IR.getInstance().Add_IRcommand(new IRcommand_Push(t,lineNumber));
				number_of_arguments++;
				arg_list = arg_list.eo1;
			}
			TEMP function_string_address = TEMP_FACTORY.getInstance().getFreshTEMP();
			IR.getInstance().Add_IRcommand(new IRcommand_Load_Func_Address(function_string_address, id1, lineNumber));
			IR.getInstance().Add_IRcommand(new IRcommand_Push(function_string_address,lineNumber));
			IR.getInstance().Add_IRcommand(new IRcommand_Call(id1, number_of_arguments, false,lineNumber));
			TEMP result_temp = TEMP_FACTORY.getInstance().getFreshTEMP();
			IR.getInstance().Add_IRcommand(new IRcommand_Pop(result_temp,lineNumber));
			return result_temp;
		}
		AST_EXP_COMMA_SEPARATED_LIST arg_list = eo1;
		TEMP instance = v.IRme();
		TEMP error_temp = TEMP_FACTORY.getInstance().getFreshTEMP();
		String valid_ptr = IRcommand.getFreshLabel("ADDR_NOT_NULL");
		IR.getInstance().Add_IRcommand(new IRcommand_Jump_If_Not_Eq_To_Zero(instance, valid_ptr, lineNumber));
		IR.getInstance().Add_IRcommand(new IRcommand_Load_String_Address(error_temp,"invalid_ptr_dref",lineNumber));
		IR.getInstance().Add_IRcommand(new IRcommand_Exit_With_Error(error_temp,lineNumber));
		IR.getInstance().Add_IRcommand(new IRcommand_Label(valid_ptr,lineNumber));
		int number_of_arguments = 0;
		IR.getInstance().Add_IRcommand(new IRcommand_Push(TEMP_FACTORY.getInstance().getFreshTEMP(),lineNumber)); //get space for return value
		IR.getInstance().Add_IRcommand(new IRcommand_Push(instance, lineNumber)); //get space for return value
		while (arg_list != null){
			TEMP t = arg_list.e1.IRme();
			IR.getInstance().Add_IRcommand(new IRcommand_Push(t,lineNumber));
			number_of_arguments++;
			arg_list = arg_list.eo1;
		}
		TEMP function_string_address = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommand_Load_Func_Address(function_string_address, id1, lineNumber));
		IR.getInstance().Add_IRcommand(new IRcommand_Push(function_string_address,lineNumber));
		int offset = ((TYPE_CLASS) v.symbol_table_entry.type).getFieldOffsetMethod(id1);

		TEMP dispatch_table_address = TEMP_FACTORY.getInstance().getFreshTEMP();
		TEMP function_address = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommand_Load_Register(dispatch_table_address, instance, 0, lineNumber));
		IR.getInstance().Add_IRcommand(new IRcommand_Load_Register(function_address, dispatch_table_address, offset, lineNumber));
		IR.getInstance().Add_IRcommand(new IRcommand_Virtual_Call(function_address, number_of_arguments, false,lineNumber)); //offset objectpointer, is_void, line
		TEMP result_temp = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommand_Pop(result_temp,lineNumber));
		return result_temp;

	}
}