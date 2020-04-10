package TYPES;

import AST.AST_EXP;

public abstract class TYPE
{
	/******************************/
	/*  Every type has a name ... */
	/******************************/
	public String name;
	public AST_EXP default_value;
	/*************/
	/* isClass() */
	/*************/
	public boolean isClass(){ return false;}

	/*************/
	/* isArray() */
	/*************/
	public boolean isArray(){ return false;}
	
	/*************/
	/* isFucntion() */
	/*************/
	public boolean isFunction(){ return false;}
	
	/*true if and only if every we accept type we should accept this*/
	public boolean isInstanceOf(TYPE type) {
		if (this == TYPE_NIL.getInstance() && (type.isClass() || type.isArray())) {
			return true;
		}
		if (this == type) {
			return true;
		}
		if (this.isClass() && type.isClass()) {
			return ((TYPE_CLASS) type).isAncestorOf((TYPE_CLASS) this);
		}
		return false;
	}
}
