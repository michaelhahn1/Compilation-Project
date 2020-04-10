package AST;

import TYPES.*;
import IR.*;
import TEMP.*;

public class AST_EXP_STRING extends AST_EXP
{
	public String s;
	public AST_EXP_STRING(String s, int lineNumber)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.s = s;
		this.lineNumber = lineNumber;
		System.out.print("====================== exp string  \n");
	}
	/***********************************************/
	/* The default message for an STRING node */
	/***********************************************/
	public void PrintMe()
	{
		System.out.format("AST NODE STRING( %s )\n",s);
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				String.format("STRING\n%s",s.replace('"','\'')));
		
	}
	public TEMP IRme()
	{
		TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();
		String var_name = "str_" + dst.getSerialNumber();
		IR.getInstance().Add_Data_IRcommand(new IRcomamnd_Allocate_String(var_name, s, false, lineNumber));
		IR.getInstance().Add_IRcommand(new IRcommand_Load_String_Address(dst, var_name,lineNumber));
		return dst;
	}
	
	public TYPE SemantMe()
	{
		return TYPE_STRING.getInstance();
	}
	
	public boolean isConstant() {
		return true;
	}
}