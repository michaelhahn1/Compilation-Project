package AST;

import TYPES.*;

public abstract class AST_EXP extends AST_Node
{
	public int moish;
	
	public TYPE SemantMe() throws Exception
	{
		return null;
	}	
	public boolean isConstant() {
		return false;
	}
}
