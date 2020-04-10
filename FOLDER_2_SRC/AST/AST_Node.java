package AST;

import SYMBOL_TABLE.SYMBOL_TABLE_ENTRY;
import TYPES.TYPE_LIST;
import TYPES.TYPE_NAMED;
import TEMP.*;

public abstract class AST_Node
{
	/*******************************************/
	/* The serial number is for debug purposes */
	/* In particular, it can help in creating  */
	/* a graphviz dot format of the AST ...    */
	/*******************************************/
	public int SerialNumber;
	
	public int lineNumber;

	public SYMBOL_TABLE_ENTRY symbol_table_entry;
	
	/***********************************************/
	/* The default message for an unknown AST node */
	/***********************************************/
	public void PrintMe()
	{
		System.out.print("AST NODE UNKNOWN\n");
	}
	
	static public TYPE_LIST getTypeListFromNamedTypeList(TYPE_LIST namedTypeList) {
		if (namedTypeList == null) return null;
		TYPE_NAMED namedType = (TYPE_NAMED) namedTypeList.head;
		return new TYPE_LIST(namedType.type, getTypeListFromNamedTypeList(namedTypeList.tail));
	}

	public abstract TEMP IRme();


}
