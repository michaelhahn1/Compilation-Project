package AST;
import TEMP.TEMP;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_ARRAY_DEC extends AST_DEC
{
	public String id1;
	public String id2;

	
	public AST_ARRAY_DEC(String id1,String id2, int lineNumber) {
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.id1 = id1;
		this.id2 = id2;
		this.lineNumber = lineNumber;
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== AST_ARRAY_DEC\n");
	}
	
	public TYPE SemantMe(TYPE_CLASS cls) throws Exception
	{ /* cls is a dummy variable*/
		/* check if id2 is valid and check assignment value coherence*/
		TYPE existingDataType = SYMBOL_TABLE.getInstance().find(this.id2);
		if (existingDataType == null) 
		{
			throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
		}
		
		if (SYMBOL_TABLE.getInstance().find(this.id1) != null) {
			throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
		}
		SYMBOL_TABLE.getInstance().enter(this.id1, new TYPE_ARRAY(existingDataType));
		return null;
	}
	public void PrintMe()
	{
		System.out.print("AST NODE ARRAY DEC\n");
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				String.format("ARRAY DEC %s,%s",id1,id2));
		
	}

	@Override
	public TEMP IRme() {
		return null;
	}
}
