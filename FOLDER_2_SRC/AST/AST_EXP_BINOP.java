package AST;

import TYPES.*;
import IR.*;
import TEMP.*;

public class AST_EXP_BINOP extends AST_EXP
{
	public AST_EXP e1;
	public AST_EXP e2;
	public int opcode ;
	TYPE type;
	TYPE forop4;
	
	public AST_EXP_BINOP(AST_EXP e1, AST_EXP e2, int opcode,int lineNumber) {
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.e1 = e1;
		this.e2 = e2;
		this.opcode = opcode;
		this.lineNumber = lineNumber;
		System.out.format("======================exp binop opcode%d\n",opcode);
	}
	
	public void PrintMe()
	{
		String sOP="";
		
		/*********************************/
		/* CONVERT OP to a printable sOP */
		/*********************************/
		if (opcode == 0) {sOP = "+";}
		if (opcode == 1) {sOP = "-";}
		if (opcode == 2) {sOP = "*";}
		if (opcode == 3) {sOP = "/";}
		if (opcode == 4) {sOP = "=";}
		if (opcode == 5) {sOP = "<";}
		if (opcode == 6) {sOP = ">";}
		/*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST NODE BINOP EXP\n");

		/**************************************/
		/* RECURSIVELY PRINT left + right ... */
		/**************************************/
		if (e1 != null) e1.PrintMe();
		if (e2 != null) e2.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("BINOP(%s)",sOP));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (e1  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e1.SerialNumber);
		if (e2 != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e2.SerialNumber);
	}
	
	void binopEqualsSemantMe(TYPE left, TYPE right) throws Exception
	{
		if ((!left.isInstanceOf(right)) && (!right.isInstanceOf(left))) {
			throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(e2.lineNumber));
		}
		forop4 = left;
	}
	
	public TYPE SemantMe() throws Exception
	{
		TYPE typeLeft = null;
		TYPE typeRight = null;
		
		if(e1 != null) typeLeft = e1.SemantMe();
		if(e2 != null) typeRight = e2.SemantMe();
		
		if(opcode ==4)
		{
			binopEqualsSemantMe(typeLeft,typeRight);
		}
		else
		{
			if(opcode != 0) /*1 2 3 5 6*/ 
			{
				/*can only be int.. if one of them is not int then error*/
				if(typeLeft != TYPE_INT.getInstance())
				{
					throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
				}
				if(typeRight != TYPE_INT.getInstance())
				{
					throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(e2.lineNumber));
				}
			}
			else
			{
				/*opcode is 0*/
				if(!((typeLeft == typeRight) && ( typeLeft == TYPE_INT.getInstance() || typeLeft == TYPE_STRING.getInstance() ))) {
					/*error, this checks if the type is the same and is either string or integer*/
					throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
				}
				if(typeLeft == TYPE_STRING.getInstance())
				{
					type = TYPE_STRING.getInstance();
					return TYPE_STRING.getInstance();
				}
			}
		}
		type = TYPE_INT.getInstance();
		return TYPE_INT.getInstance();
	}
	
	private void addRangeCheck(TEMP dst) {// check if dst is in the right range
		TEMP upper_bound_temp = TEMP_FACTORY.getInstance().getFreshTEMP();
		TEMP lower_bound_temp = TEMP_FACTORY.getInstance().getFreshTEMP();
		TEMP correct_upper_bound_temp = TEMP_FACTORY.getInstance().getFreshTEMP();
		TEMP correct_lower_bound_temp = TEMP_FACTORY.getInstance().getFreshTEMP();
		
		IR.getInstance().Add_IRcommand(new IRcommandConstInt(upper_bound_temp, 32768,lineNumber));
		IR.getInstance().Add_IRcommand(new IRcommandConstInt(lower_bound_temp, -32769,lineNumber));
		
		IR.getInstance().Add_IRcommand(new IRcommand_Binop_LT_Integers(correct_upper_bound_temp, dst, upper_bound_temp,lineNumber));
		IR.getInstance().Add_IRcommand(new IRcommand_Binop_LT_Integers(correct_lower_bound_temp, lower_bound_temp, dst,lineNumber));
		
		String correct_upper_bound_label = IRcommand.getFreshLabel("CORRECT_UPPER_BOUND");
		IR.getInstance().Add_IRcommand(new IRcommand_Jump_If_Not_Eq_To_Zero(correct_upper_bound_temp, correct_upper_bound_label,lineNumber));
		IR.getInstance().Add_IRcommand(new IRcommandConstInt(dst, 32767,lineNumber));
		IR.getInstance().Add_IRcommand(new IRcommand_Label(correct_upper_bound_label,lineNumber));
		String correct_lower_bound_label = IRcommand.getFreshLabel("CORRECT_LOWER_BOUND");
		IR.getInstance().Add_IRcommand(new IRcommand_Jump_If_Not_Eq_To_Zero(correct_lower_bound_temp, correct_lower_bound_label,lineNumber));
		IR.getInstance().Add_IRcommand(new IRcommandConstInt(dst, -32768,lineNumber));
		IR.getInstance().Add_IRcommand(new IRcommand_Label(correct_lower_bound_label,lineNumber));
		
	}
	public TEMP IRme(){
		TEMP t1 = null;
		TEMP t2 = null;
		TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();
		if (e1  != null) t1 = e1.IRme();
		if (e2 != null) t2 = e2.IRme();
		
		if (opcode == 0)
		{
			if (type == TYPE_INT.getInstance()) {
				IR.
				getInstance().
				Add_IRcommand(new IRcommand_Binop_Add_Integers(dst,t1,t2,lineNumber));
			}
			else {
				TEMP totalLength = TEMP_FACTORY.getInstance().getFreshTEMP();
				TEMP ptrCopy = TEMP_FACTORY.getInstance().getFreshTEMP();
				TEMP currentByte = TEMP_FACTORY.getInstance().getFreshTEMP();

				String length_str1 = IRcommand.getFreshLabel("length_str1");
				String end_length_str1 = IRcommand.getFreshLabel("end_length_str1");
				String length_str2 = IRcommand.getFreshLabel("length_str2");
				String end_length_str2 = IRcommand.getFreshLabel("end_length_str2");
				String copy_str1 = IRcommand.getFreshLabel("copy_str1");
				String end_copy_str1 = IRcommand.getFreshLabel("end_copy_str1");
				String copy_str2 = IRcommand.getFreshLabel("copy_str2");
				String end_copy_str2 = IRcommand.getFreshLabel("end_copy_str2");

				//count total lengths
				IR.getInstance().Add_IRcommand(new IRcommandConstInt(totalLength,0,lineNumber));

				IR.getInstance().Add_IRcommand(new IRcommand_move(ptrCopy,t1,lineNumber));
				//IR.getInstance().Add_IRcommand(new IRcommand_Binop_Addi_Integers(ptrCopy,t1,0,lineNumber));
				IR.getInstance().Add_IRcommand(new IRcommand_Label(length_str1,lineNumber));
					IR.getInstance().Add_IRcommand(new IRcommand_Load_Byte(currentByte,ptrCopy,lineNumber));
					IR.getInstance().Add_IRcommand(new IRcommand_Jump_If_Eq_To_Zero(currentByte,end_length_str1,lineNumber));
					IR.getInstance().Add_IRcommand(new IRcommand_Binop_Addi_Integers(ptrCopy,ptrCopy,1,lineNumber));
					IR.getInstance().Add_IRcommand(new IRcommand_Binop_Addi_Integers(totalLength,totalLength,1,lineNumber));
					IR.getInstance().Add_IRcommand(new IRcommand_Jump_Label(length_str1,lineNumber));
				IR.getInstance().Add_IRcommand(new IRcommand_Label(end_length_str1,lineNumber));

				IR.getInstance().Add_IRcommand(new IRcommand_move(ptrCopy,t2,lineNumber));
				IR.getInstance().Add_IRcommand(new IRcommand_Label(length_str2,lineNumber));
					IR.getInstance().Add_IRcommand(new IRcommand_Load_Byte(currentByte,ptrCopy,lineNumber));
					IR.getInstance().Add_IRcommand(new IRcommand_Jump_If_Eq_To_Zero(currentByte,end_length_str2,lineNumber));
					IR.getInstance().Add_IRcommand(new IRcommand_Binop_Addi_Integers(ptrCopy,ptrCopy,1,lineNumber));
					IR.getInstance().Add_IRcommand(new IRcommand_Binop_Addi_Integers(totalLength,totalLength,1,lineNumber));
					IR.getInstance().Add_IRcommand(new IRcommand_Jump_Label(length_str2,lineNumber));
				IR.getInstance().Add_IRcommand(new IRcommand_Label(end_length_str2,lineNumber));

				IR.getInstance().Add_IRcommand(new IRcommand_Mallocate(dst,totalLength,lineNumber));
				//mallocated string is now in V0

				//copy strings
				IR.getInstance().Add_IRcommand(new IRcommand_move(ptrCopy,t1,lineNumber));
				IR.getInstance().Add_IRcommand(new IRcommand_Label(copy_str1,lineNumber));
					IR.getInstance().Add_IRcommand(new IRcommand_Load_Byte(currentByte,ptrCopy,lineNumber));
					IR.getInstance().Add_IRcommand(new IRcommand_Jump_If_Eq_To_Zero(currentByte,end_copy_str1,lineNumber));
					IR.getInstance().Add_IRcommand(new IRcommand_Store_Byte(dst,currentByte,lineNumber));
					IR.getInstance().Add_IRcommand(new IRcommand_Binop_Addi_Integers(ptrCopy,ptrCopy,1,lineNumber));
					IR.getInstance().Add_IRcommand(new IRcommand_Binop_Addi_Integers(dst,dst,1,lineNumber));
					IR.getInstance().Add_IRcommand(new IRcommand_Jump_Label(copy_str1,lineNumber));
				IR.getInstance().Add_IRcommand(new IRcommand_Label(end_copy_str1,lineNumber));

				IR.getInstance().Add_IRcommand(new IRcommand_move(ptrCopy,t2,lineNumber));
				IR.getInstance().Add_IRcommand(new IRcommand_Label(copy_str2,lineNumber));
					IR.getInstance().Add_IRcommand(new IRcommand_Load_Byte(currentByte,ptrCopy,lineNumber));
					IR.getInstance().Add_IRcommand(new IRcommand_Jump_If_Eq_To_Zero(currentByte,end_copy_str2,lineNumber));
					IR.getInstance().Add_IRcommand(new IRcommand_Store_Byte(dst,currentByte,lineNumber));
					IR.getInstance().Add_IRcommand(new IRcommand_Binop_Addi_Integers(ptrCopy,ptrCopy,1,lineNumber));
					IR.getInstance().Add_IRcommand(new IRcommand_Binop_Addi_Integers(dst,dst,1,lineNumber));
					IR.getInstance().Add_IRcommand(new IRcommand_Jump_Label(copy_str2,lineNumber));
				IR.getInstance().Add_IRcommand(new IRcommand_Label(end_copy_str2,lineNumber));

				IR.getInstance().Add_IRcommand(new IRcommand_takeFromV0(dst,lineNumber));
				return dst;/*used dst as an input*/
			}
			addRangeCheck(dst);
			
		}
		if (opcode == 1)
		{
			if (type == TYPE_INT.getInstance()) {
				IR.
						getInstance().
						Add_IRcommand(new IRcommand_Binop_Sub_Integers(dst,t1,t2,lineNumber));
			}
			addRangeCheck(dst);

		}
		if (opcode == 2)
		{
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_Binop_Mul_Integers(dst,t1,t2,lineNumber));
			addRangeCheck(dst);
		}
		if (opcode == 3) // divides t1 by t2
		{
			String validDivisionLabel = IRcommand.getFreshLabel("VALID_DIVISION");
			IR.getInstance().Add_IRcommand(new IRcommand_Jump_If_Not_Eq_To_Zero(t2, validDivisionLabel,lineNumber));
			TEMP error_temp = TEMP_FACTORY.getInstance().getFreshTEMP();
			IR.getInstance().Add_IRcommand(new IRcommand_Load_String_Address(error_temp, "illegal_div_by_0",lineNumber));
			IR.getInstance().Add_IRcommand(new IRcommand_Exit_With_Error(error_temp,lineNumber));
			
			IR.getInstance().Add_IRcommand(new IRcommand_Label(validDivisionLabel,lineNumber));
			IR.getInstance().Add_IRcommand(new IRcommand_Binop_Div_Integers(dst,t1,t2,lineNumber));
			
			addRangeCheck(dst);
		}
		if (opcode == 4)
		{
			if (forop4 == TYPE_INT.getInstance() || forop4.isClass()) {
				IR.
				getInstance().
				Add_IRcommand(new IRcommand_Binop_EQ_Integers(dst,t1,t2,lineNumber));	
			}
			else { // string comparison
				TEMP crt_left_char_temp = TEMP_FACTORY.getInstance().getFreshTEMP();
				TEMP crt_right_char_temp  = TEMP_FACTORY.getInstance().getFreshTEMP();
				
				TEMP chars_equal_temp  = TEMP_FACTORY.getInstance().getFreshTEMP();
				
				TEMP crt_indx_left_temp = TEMP_FACTORY.getInstance().getFreshTEMP();
				IR.getInstance().Add_IRcommand(new IRcommand_Binop_Addi_Integers(crt_indx_left_temp, t1, 0,lineNumber));
				/*copied address of t1 into that temp*/
				
				TEMP crt_indx_right_temp = TEMP_FACTORY.getInstance().getFreshTEMP();
				IR.getInstance().Add_IRcommand(new IRcommand_Binop_Addi_Integers(crt_indx_right_temp, t2, 0,lineNumber));
				/*copied address of t2 into that temp*/

				String loopLabel = IRcommand.getFreshLabel("LOOP");
				String notEqualLabel = IRcommand.getFreshLabel("NOT_EQUAL");
				String equalLabel = IRcommand.getFreshLabel("EQUAL");
				String endLabel = IRcommand.getFreshLabel("END");
				
				IR.getInstance().Add_IRcommand(new IRcommand_Label(loopLabel,lineNumber));/*start loop*/
				/*get byte of t1 at that index*/
				IR.getInstance().Add_IRcommand(new IRcommand_Load_Byte(crt_left_char_temp, crt_indx_left_temp,lineNumber));
				/*get byte of t2 at that index*/
				IR.getInstance().Add_IRcommand(new IRcommand_Load_Byte(crt_right_char_temp, crt_indx_right_temp,lineNumber));
				/*check if those bytes are equal*/
				IR.getInstance().Add_IRcommand(new IRcommand_Binop_EQ_Integers(chars_equal_temp, crt_left_char_temp, crt_right_char_temp,lineNumber));

				//IR.getInstance().Add_IRcommand(new IRcommand_setTempIns(crt_right_char_temp,null));

				/*if chars_equal_temp is 0 it means that there is a pair of bytes that are not the same*/
				IR.getInstance().Add_IRcommand(new IRcommand_Jump_If_Eq_To_Zero(chars_equal_temp, notEqualLabel,lineNumber));
				/*if crt_left_char_temp is 0, one of the string has ended -> we didn't exit before so they are equal*/
				IR.getInstance().Add_IRcommand(new IRcommand_Jump_If_Eq_To_Zero(crt_left_char_temp, equalLabel,lineNumber)); // if left string terminated -> right string terminated (we know it from the previous command) -> strings are equal
				
				IR.getInstance().Add_IRcommand(new IRcommand_Binop_Addi_Integers(crt_indx_right_temp, crt_indx_right_temp, 1,lineNumber));
				IR.getInstance().Add_IRcommand(new IRcommand_Binop_Addi_Integers(crt_indx_left_temp, crt_indx_left_temp, 1,lineNumber));

				IR.getInstance().Add_IRcommand(new IRcommand_Jump_Label(loopLabel,lineNumber));
				
				IR.getInstance().Add_IRcommand(new IRcommand_Label(equalLabel,lineNumber));
				IR.getInstance().Add_IRcommand(new IRcommandConstInt(dst, 1,lineNumber));
				IR.getInstance().Add_IRcommand(new IRcommand_Jump_Label(endLabel,lineNumber));
				IR.getInstance().Add_IRcommand(new IRcommand_Label(notEqualLabel,lineNumber));
				IR.getInstance().Add_IRcommand(new IRcommandConstInt(dst, 0,lineNumber));
				IR.getInstance().Add_IRcommand(new IRcommand_Label(endLabel,lineNumber));
			}
		}
		
		if (opcode == 5) 
		{
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_Binop_LT_Integers(dst,t1,t2,lineNumber)); // less than
		
		}
		if (opcode == 6)
		{
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_Binop_LT_Integers(dst,t2,t1,lineNumber)); // greater than
			
		}
		return dst;
	}
}