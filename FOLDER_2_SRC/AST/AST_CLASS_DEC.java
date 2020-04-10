package AST;
import IR.IR;
import TEMP.TEMP;
import TYPES.*;
import SYMBOL_TABLE.*;
import TEMP.*;
import IR.*;

public class AST_CLASS_DEC extends AST_DEC
{
	public String id1;
	public String id2;
	public AST_C_FIELD_LIST cfl;

	
	public AST_CLASS_DEC(String id1,String id2,AST_C_FIELD_LIST cfl,int lineNumber) {
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.id1 = id1;
		this.id2 = id2;
		this.cfl = cfl;
		this.lineNumber = lineNumber;
		System.out.print("====================== class dec\n");
	}
	
	public TYPE SemantMe(TYPE_CLASS cls) throws Exception{ /* cls is a dummy variable*/
		if (SYMBOL_TABLE.getInstance().find(id1) != null) {
			throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
		}
		TYPE father = null;
		if (id2 != null) {
			father = SYMBOL_TABLE.getInstance().find(id2);
			if (father == null) {
				throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
			}
			if (!father.isClass()) {
				throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
			}
		}
		
		TYPE_CLASS thisClass = new TYPE_CLASS((TYPE_CLASS) father, this.id1, null);
		symbol_table_entry = SYMBOL_TABLE.getInstance().enter(this.id1, thisClass);
		SYMBOL_TABLE.getInstance().beginScope();
		this.cfl.SemantMe(thisClass);
		SYMBOL_TABLE.getInstance().endScope();
		return null;
	}
	
	public void PrintMe()
	{
		System.out.format("AST CLASS DEC %s,%s\n",id1,id2);
		if (cfl != null) cfl.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				String.format("CLASS %s,%s",id1,id2));
		if (cfl != null)AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cfl.SerialNumber);
	}

	@Override
	public TEMP IRme() {
		TYPE_CLASS cls_type = (TYPE_CLASS) this.symbol_table_entry.type;
		TYPE_LIST it = cls_type.getRelevantDataMembersMethods();

		if (it.tail != null) {
			it = it.tail;
			if (it.head != null) {
				IR.getInstance().Add_Data_IRcommand(new IRcommand_Start_virtualTable(cls_type.name, lineNumber));
			}
		}
		int counter = 0;
		while (it != null) {
			if (it.head != null) {
				IR.getInstance().Add_Data_IRcommand(new IRcommand_Add_virtualTable_entry(((TYPE_NAMED)it.head).name,
						((TYPE_FUNCTION)((TYPE_NAMED)it.head).type).cls.name, counter));
				counter ++;
			}
			it = it.tail;
		}
		IR.getInstance().Add_Data_IRcommand(new IRcommand_Add_virtualTable_entry("","", -1));
		cfl.IRme();
		return null;
	}

}
