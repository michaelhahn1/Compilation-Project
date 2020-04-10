package AST;

import TEMP.*;
import TYPES.*;
import IR.*;

public class AST_VAR_FIELD extends AST_VAR
{
	public AST_VAR v;
	public String fieldName;
	public TYPE_CLASS clsType;
	public boolean isVarField(){
		return true;
	}
	public AST_VAR_FIELD(AST_VAR v,String fieldName,int lineNumber) {
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.v = v;
		this.fieldName = fieldName;
		this.lineNumber = lineNumber;
		System.out.print("====================== var field  \n");
	}

	public TYPE SemantMe() throws Exception
	{
		/*e.g: student.grade */
		TYPE typeOfVar = v.SemantMe();

		if (!typeOfVar.isClass()) {
			throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
		}
		clsType = (TYPE_CLASS) typeOfVar;
		TYPE clsField = ((TYPE_CLASS) typeOfVar).searchDataMembers(this.fieldName);
		if (clsField == null) {
			throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
		}
		if (clsField.isFunction()) {
			throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
		}
		return clsField;
	}

	/***********************************************/
	/* The printing message for a field var AST node */
	/***********************************************/
	public void PrintMe()
	{
		/*********************************/
		/* AST NODE TYPE = AST FIELD VAR */
		/*********************************/
		System.out.print("AST NODE FIELD VAR\n");

		/**********************************************/
		/* RECURSIVELY PRINT VAR, then FIELD NAME ... */
		/**********************************************/
		if (v != null) v.PrintMe();
		System.out.format("FIELD NAME( %s )\n",fieldName);

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				String.format("FIELD\nVAR\n...->%s",fieldName));

		if (v != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,v.SerialNumber);
	}

	@Override
	public TEMP IRme() {
		TEMP instance = v.IRme();
		TEMP error_temp = TEMP_FACTORY.getInstance().getFreshTEMP();
		String valid_ptr = IRcommand.getFreshLabel("ADDR_NOT_NULL");
		IR.getInstance().Add_IRcommand(new IRcommand_Jump_If_Not_Eq_To_Zero(instance, valid_ptr, lineNumber));
		IR.getInstance().Add_IRcommand(new IRcommand_Load_String_Address(error_temp,"invalid_ptr_dref",lineNumber));
		IR.getInstance().Add_IRcommand(new IRcommand_Exit_With_Error(error_temp,lineNumber));
		IR.getInstance().Add_IRcommand(new IRcommand_Label(valid_ptr,lineNumber));
		int offset = clsType.getFieldOffset(fieldName);
		TEMP result_value = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommand_Load_Register(result_value, instance, offset, lineNumber));
		return result_value;
	}
}