package AST;

import TEMP.TEMP;
import TYPES.*;

public class AST_STMT_LIST extends AST_Node
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_STMT head;
	public AST_STMT_LIST tail;
	public boolean enounteredReturn = false;
	public boolean inLoop = false;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_STMT_LIST(AST_STMT head,AST_STMT_LIST tail, int lineNumber)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
   

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (tail != null) System.out.print("====================== stmts -> stmt stmts\n");
		if (tail == null) System.out.print("====================== stmts -> stmt      \n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.head = head;
		this.tail = tail;
		this.lineNumber = lineNumber;
		
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE STMT LIST\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (head != null) head.PrintMe();
		if (tail != null) tail.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"STMT\nLIST\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,head.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,tail.SerialNumber);
	}
	
	public TYPE SemantMe(TYPE expectedType) throws Exception
	{
	TYPE retType = null;
		if (head != null) {
			retType = head.SemantMe(expectedType);
			if (retType != null) {
				this.enounteredReturn = true;
			}
		}
		if (tail != null) {
			tail.enounteredReturn = this.enounteredReturn;
			tail.inLoop = this.inLoop;
			return tail.SemantMe(expectedType);
			
		}
		else {
			// in case there is no return at the end in a void function we add a return node
			if (expectedType == TYPE_VOID.getInstance() && !this.inLoop) {
				this.tail = new AST_STMT_LIST(new AST_STMT_RETURN(null, -1), null, -1);
				this.tail.head.SemantMe(expectedType);
			}
			if (!this.enounteredReturn && expectedType != TYPE_VOID.getInstance() && !this.inLoop) {
				throw new SEMANTIC_ERROR_EXCEPTION(String.valueOf(this.lineNumber));
			}
			return retType;
		}
	}

	public TEMP IRme(){
		if (head != null) {
			head.IRme();
		}
		if (tail != null){
			tail.IRme();
		}
		return null;
	}
}
