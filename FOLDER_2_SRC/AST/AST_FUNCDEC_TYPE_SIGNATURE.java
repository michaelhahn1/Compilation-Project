package AST;

import SYMBOL_TABLE.SYMBOL_TABLE;
import TEMP.TEMP;
import TYPES.*;

public class AST_FUNCDEC_TYPE_SIGNATURE extends AST_DEC
{
	public String id1;
	public String id2;
	public AST_FUNCDEC_TYPE_SIGNATURE fdo1;

	
	public AST_FUNCDEC_TYPE_SIGNATURE(String id1,String id2,AST_FUNCDEC_TYPE_SIGNATURE fdo1, int lineNumber) {
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.id1 = id1;
		this.id2 = id2;
		this.fdo1 = fdo1;
		this.lineNumber = lineNumber;
		System.out.print("====================== func dec signature  \n");

	}
	
	public TYPE_LIST SemantList() throws Exception{
		TYPE existingDataType = SYMBOL_TABLE.getInstance().find(id1);
		if (existingDataType == null) 
		{
			throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
		}
		if (SYMBOL_TABLE.getInstance().findScope(this.id2) == SYMBOL_TABLE.getInstance().getScope()) {
			throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
		}
		symbol_table_entry = SYMBOL_TABLE.getInstance().enter(id2, existingDataType);

		if(this.fdo1 == null)
		{
			return new TYPE_LIST(new TYPE_NAMED(existingDataType, id2), null);
		}

		return new TYPE_LIST(new TYPE_NAMED(existingDataType, id2), this.fdo1.SemantList());
	}

	public int getLength(){
		if(fdo1 != null){
			return fdo1.getLength() + 1;
		}
		return 1;
	}
	
	public void PrintMe()
	{
		System.out.print("AST NODE ARGS\n");

		if (fdo1 != null) fdo1.PrintMe();
		
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				String.format("ARGS %s,%s",id1,id2));
		if (fdo1 != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,fdo1.SerialNumber);
	}

	public TEMP IRme(){
		symbol_table_entry.offset = (getLength() + 2) * 4;
		if (fdo1 != null) {
			fdo1.IRme();
		}
		return null;
	}
	
}
