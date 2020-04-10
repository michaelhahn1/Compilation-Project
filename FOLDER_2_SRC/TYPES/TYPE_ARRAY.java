package TYPES;

public class TYPE_ARRAY extends TYPE
{
	public TYPE typeOfArray;
	
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_ARRAY(TYPE typeOfArray)
	{
		/*The symbol table is responsible for the name, similar to TYPE_INT*/
		this.typeOfArray = typeOfArray;
	}
	/*if we are in type class then this will override the "return false" for just any type*/
	public boolean isArray() {
		return true; 
	}	
}
