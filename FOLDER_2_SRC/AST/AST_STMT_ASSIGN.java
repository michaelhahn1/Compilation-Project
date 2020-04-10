package AST;

import IR.*;
import TYPES.*;
import TEMP.*;
import VAR.VAR;
import SYMBOL_TABLE.*;

public class AST_STMT_ASSIGN extends AST_STMT
{
	public AST_VAR v;
	public AST_EXP e;
	public AST_STMT_ASSIGN(AST_VAR v, AST_EXP e, int lineNumber) {
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.v = v;
		this.e = e;
		this.lineNumber = lineNumber;
		System.out.print("====================== stmt -> var ASSIGN exp SEMICOLON\n");
	}
	
	/***********************************************/
	/* The printing message for an assign statement AST node */
	/***********************************************/
	public void PrintMe()
	{
		System.out.print("AST NODE ASSIGN STMT\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/
		if (v != null) v.PrintMe();
		if (e != null) e.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"ASSIGN\nleft := right\n");

		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,v.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e.SerialNumber);
	}
	

	public TYPE	SemantMe(TYPE expectedType) throws Exception
	{System.out.print("Reached semantme of ASSIGN NEW\n");
		TYPE typeLeft = null;
		TYPE typeRight = null;

		if(v != null) typeLeft = v.SemantMe();
		if(e != null) typeRight = e.SemantMe();

		if(typeLeft.isClass() && !typeRight.isInstanceOf(typeLeft))
		{
			/*System.out.format(">> ERROR [%d:%d] type mismatch for var := exp\n",6,6);*/
			throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
		} else if (typeLeft.isArray()) {
			if ((TYPE_ARRAY) typeLeft != typeRight) {
				throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
			}
		}
		return null;
	}
	public TYPE_FUNCTION getFuncType(SYMBOL_TABLE_ENTRY entry){
		while (!entry.type.isFunction()) {
			entry = entry.prevtop;
		}
		return (TYPE_FUNCTION) entry.type;
	}

	public TEMP IRme(){
		if (v.isVarField()){
			TEMP instance = ((AST_VAR_FIELD)v).v.IRme();
			TEMP error_temp = TEMP_FACTORY.getInstance().getFreshTEMP();
			String valid_ptr = IRcommand.getFreshLabel("ADDR_NOT_NULL");
			IR.getInstance().Add_IRcommand(new IRcommand_Jump_If_Not_Eq_To_Zero(instance, valid_ptr, lineNumber));
			IR.getInstance().Add_IRcommand(new IRcommand_Load_String_Address(error_temp,"invalid_ptr_dref",lineNumber));
			IR.getInstance().Add_IRcommand(new IRcommand_Exit_With_Error(error_temp,lineNumber));
			IR.getInstance().Add_IRcommand(new IRcommand_Label(valid_ptr,lineNumber));
			int offset = (((AST_VAR_FIELD)v).clsType).getFieldOffset(((AST_VAR_FIELD) v).fieldName);
			IR.getInstance().Add_IRcommand(new IRcommand_Store_Register(instance, e.IRme(), offset, lineNumber));
		}
		else{
			if(v.isSUBSCRIPT()){
				TEMP arrStartPointer = ((AST_VAR_SUBSCRIPT)v).v.IRme();
				TEMP reqIndex = ((AST_VAR_SUBSCRIPT)v).e.IRme();
				TEMP address = TEMP_FACTORY.getInstance().getFreshTEMP();

				//Check if valid access
				String validAccess = IRcommand.getFreshLabel("VALID_ACCESS");
				TEMP error_temp = TEMP_FACTORY.getInstance().getFreshTEMP();
				TEMP arrayLength = TEMP_FACTORY.getInstance().getFreshTEMP();
				IR.getInstance().Add_IRcommand(new IRcommand_Load_Register(arrayLength,arrStartPointer,0,lineNumber));
				IR.getInstance().Add_IRcommand(new IRcommand_Jump_If_Greater_Then(arrayLength,reqIndex,validAccess,lineNumber));
				IR.getInstance().Add_IRcommand(new IRcommand_Load_String_Address(error_temp, "access_violation",lineNumber));
				IR.getInstance().Add_IRcommand(new IRcommand_Exit_With_Error(error_temp,lineNumber));
				IR.getInstance().Add_IRcommand(new IRcommand_Label(validAccess,lineNumber));

				//get to right address
				IR.getInstance().Add_IRcommand(new IRcommand_Binop_Add_Integers(address,arrStartPointer,reqIndex,lineNumber));
				IR.getInstance().Add_IRcommand(new IRcommand_Binop_Add_Integers(address,address,reqIndex,lineNumber));
				IR.getInstance().Add_IRcommand(new IRcommand_Binop_Add_Integers(address,address,reqIndex,lineNumber));
				IR.getInstance().Add_IRcommand(new IRcommand_Binop_Add_Integers(address,address,reqIndex,lineNumber));
				IR.getInstance().Add_IRcommand(new IRcommand_Binop_Addi_Integers(address,address,4,lineNumber));

				IR.getInstance().Add_IRcommand(new IRcommand_Store_Register(address,e.IRme(),0,lineNumber));

				return null;
			} else if (v.symbol_table_entry.cls == null) {
				VAR var = new VAR(v.symbol_table_entry.offset, v.symbol_table_entry.name, v.symbol_table_entry.scope == 1);
				IR.getInstance().Add_IRcommand(new IRcommand_Set_Var(var, e.IRme(), lineNumber));
			} else {
				TYPE_FUNCTION func = getFuncType(SYMBOL_TABLE.getInstance().top);
				int args;
				if (func.params != null) {
					args = func.params.getLength();
				} else {
					args = 0;
				}
				int offset = v.symbol_table_entry.cls.getFieldOffset(v.symbol_table_entry.name);
				TEMP instance = TEMP_FACTORY.getInstance().getFreshTEMP();
				IR.getInstance().Add_IRcommand(new IRcommand_Load_Var(new VAR((args + 3)*4, "", false), instance, lineNumber));
				IR.getInstance().Add_IRcommand(new IRcommand_Store_Register(instance, e.IRme(), offset, lineNumber));
			}

		}
		return null;
	}
}