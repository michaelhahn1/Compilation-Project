package AST;

import IR.*;
import TEMP.*;
import TYPES.*;

public class AST_VAR_SUBSCRIPT extends AST_VAR
{

	public AST_VAR v;
	public AST_EXP e;
	public AST_VAR_SUBSCRIPT(AST_VAR v, AST_EXP e, int lineNumber) {
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.v = v;
		this.e = e;
		this.lineNumber = lineNumber;
		System.out.print("======================  var subscript \n");
	}
	public void PrintMe()
	{
		/*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST NODE SUBSCRIPT VAR\n");

		/****************************************/
		/* RECURSIVELY PRINT VAR + SUBSRIPT ... */
		/****************************************/
		if (v != null) v.PrintMe();
		if (e != null)e.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				"SUBSCRIPT\nVAR\n...[...]");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (v != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,v.SerialNumber);
		if (e != null)AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e.SerialNumber);
	}

	public boolean isSUBSCRIPT(){ return true; }

	@Override
	public TEMP IRme() {
		//TEMP arrStartPointer = ((AST_VAR_SUBSCRIPT)v).v.IRme();
		//TEMP reqIndex = ((AST_VAR_SUBSCRIPT)v).e.IRme();
		TEMP arrStartPointer = v.IRme();
		TEMP reqIndex = e.IRme();
		TEMP address = TEMP_FACTORY.getInstance().getFreshTEMP();
		TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();

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

		//get value
		IR.getInstance().Add_IRcommand(new IRcommand_Load_Register(dst,address,0,lineNumber));

		return dst;
	}

	public TYPE SemantMe() throws Exception
	{
		/*e.g: students[x+7] */
		TYPE typeOfVar=null;
		TYPE_ARRAY typeArray=null;
		TYPE typeOfSubscript=null;

		if(v != null) typeOfVar = v.SemantMe();
		if(e != null) typeOfSubscript = e.SemantMe();

		if (typeOfSubscript != TYPE_INT.getInstance())
		{
			//error because subscript is not an integer
			System.out.format(">> ERROR [%d:%d] subscript is not integer\n",0,0);
			throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
		}
		if (typeOfVar.isArray() == false)
		{
			//error because the variable is not an array
			System.out.format(">> ERROR [%d:%d] this is not an array\n",0,0);
			throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
		}
		else
		{
			typeArray = (TYPE_ARRAY) typeOfVar;
		}

		/*Return the type of the objects in the array*/
		return typeArray.typeOfArray;
	}
} 