package AST;

import IR.*;
import IR.IRcommand_Mallocate;
import TEMP.*;
import TYPES.*;
import SYMBOL_TABLE.*;/*do we need to add something to the table here? i think so..*/

public class AST_NEW_EXP extends AST_EXP
{
	public String id1;
	public AST_EXP e;
	
	public AST_NEW_EXP(String id1, AST_EXP e,int lineNumber) {
		System.out.print("====================== new exp  \n");
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.id1 = id1;
		this.e = e;
		this.lineNumber = lineNumber;
		System.out.print("====================== new exp  \n");
	}

	public void PrintMe()
	{
		System.out.print("AST NODE NEW EXP\n");
		if (e != null) e.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				String.format("NEW EXP %s",id1));
		if (e != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e.SerialNumber);
	}

	@Override
	public TEMP IRme() {
		TEMP instance_pointer = TEMP_FACTORY.getInstance().getFreshTEMP();
		TEMP bytes_to_allocate = TEMP_FACTORY.getInstance().getFreshTEMP();
		if(e != null){/*declaring new array*/
			TEMP requestedSize = e.IRme();
			TEMP arrayLengthInBytes = TEMP_FACTORY.getInstance().getFreshTEMP();
			TEMP four = TEMP_FACTORY.getInstance().getFreshTEMP();

			//calculate size in bytes
			IR.getInstance().Add_IRcommand(new IRcommand_Set_To_Zero(four,lineNumber));
			IR.getInstance().Add_IRcommand(new IRcommand_Binop_Addi_Integers(four,four,4,lineNumber));
			IR.getInstance().Add_IRcommand(new IRcommand_Binop_Addi_Integers(arrayLengthInBytes,requestedSize,0,lineNumber));
			IR.getInstance().Add_IRcommand(new IRcommand_Binop_Mul_Integers(arrayLengthInBytes,arrayLengthInBytes,four,lineNumber));
			IR.getInstance().Add_IRcommand(new IRcommand_Binop_Addi_Integers(arrayLengthInBytes,arrayLengthInBytes,4,lineNumber));

			//Malloc
			IR.getInstance().Add_IRcommand(new IRcommand_Mallocate(instance_pointer,arrayLengthInBytes,lineNumber));

			//add length to the start
			IR.getInstance().Add_IRcommand(new IRcommand_Binop_Addi_Integers(four,requestedSize,-1,lineNumber));
			IR.getInstance().Add_IRcommand(new IRcommand_Store_Register(instance_pointer,four,0,lineNumber));
			// NOTE: new arr[2] means the length stored IS 2. (Not 1)
			return instance_pointer;
		}
		TYPE_CLASS cls = ((TYPE_CLASS) symbol_table_entry.type);
		IR.getInstance().Add_IRcommand(new IRcommandConstInt(bytes_to_allocate, cls.getFieldOffset(null), lineNumber));
		IR.getInstance().Add_IRcommand(new IRcommand_Mallocate(instance_pointer, bytes_to_allocate, lineNumber));
		TYPE_LIST functions_members_cls = cls.getRelevantDataMembersMethods();
		if (functions_members_cls.tail != null) {
			TEMP vt_address = TEMP_FACTORY.getInstance().getFreshTEMP();
			IR.getInstance().Add_IRcommand(new IRcommand_Load_Virtual_Table_Address(vt_address, cls.name, lineNumber));
			IR.getInstance().Add_IRcommand(new IRcommand_Store_Register(instance_pointer, vt_address, 0, lineNumber));
		}
		TYPE_LIST it = cls.getRelevantDataMembers();
		if (it != null) {
			it = it.tail;
		}
		int offset = 4;
		while (it != null) {
			if (it.head != null) {
				 if (it.head.default_value != null) {
					TEMP default_value = it.head.default_value.IRme();
					IR.getInstance().Add_IRcommand(new IRcommand_Store_Register(instance_pointer, default_value, offset, lineNumber));
				} else {
					TEMP default_value =TEMP_FACTORY.getInstance().getFreshTEMP();
					IR.getInstance().Add_IRcommand(new IRcommandConstInt(default_value, 0, lineNumber));
					IR.getInstance().Add_IRcommand(new IRcommand_Store_Register(instance_pointer, default_value, offset, lineNumber));
				}
			}
			offset += 4;
			it = it.tail;
		}
		return instance_pointer;
	}

	public TYPE SemantMe() throws Exception
	{
		/*e.g: new student | new student[x]*/
		/*need to check if id is already in scope!!!*/
		TYPE typeOfNew =  SYMBOL_TABLE.getInstance().find(id1);
		symbol_table_entry = SYMBOL_TABLE.getInstance().findEntry(id1);
		/*if wasn't found*/
		if(typeOfNew == null)
		{
			throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
		}
		if (e == null) return typeOfNew;
		
		/*reahced here: e is not null, it has to be an INTEGER type*/
		if(e.SemantMe() != TYPE_INT.getInstance())
		{
			throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
		}
		return typeOfNew;
	}

}