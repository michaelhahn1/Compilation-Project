package TYPES;

public class TYPE_NAMED extends TYPE
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public TYPE type;
	public String name;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public TYPE_NAMED(TYPE type, String name)
	{
		this.type = type;
		this.name = name;
	}
}