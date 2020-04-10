package AST;

import TYPES.*;
import TEMP.*;

public abstract class AST_VAR extends AST_Node
{
	public void PrintMe()
	{
		System.out.print("AST NODE UNKNOWN\n");
	}
	public boolean isVarField(){
		return false;
	}
	public boolean isSUBSCRIPT(){ return false; }
	abstract TYPE SemantMe() throws Exception;
}
