package TYPES;

public class TYPE_LIST
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public TYPE head;
	public TYPE_LIST tail;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public TYPE_LIST(TYPE head,TYPE_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}

	public int getLength(){
		if (tail != null){
			return 1 + tail.getLength();
		}
		return 1;
	}
	
	//typeList's classes can be ancestors
	public boolean sameListAs(TYPE_LIST typeList) {
		if (typeList == null) return false;
		if (!this.head.isInstanceOf(typeList.head)) {
			return false;
		}
		if (this.tail == null)
		{
			return typeList.tail == null;
		}
		return this.tail.sameListAs(typeList.tail);
	}
}
