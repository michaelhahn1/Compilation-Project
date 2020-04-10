package AST;
import TYPES.*;
public abstract class AST_STMT extends AST_Node
{
	/*******************************************/
	/* The serial number is for debug purposes */
	/* In particular, it can help in creating  */
	/* a graphviz dot format of the AST ...    */
	/*******************************************/
	public int moish;
	public TYPE SemantMe(TYPE expectedType) throws Exception{
		return null;
	}
}
