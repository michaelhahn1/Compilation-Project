package TYPES;

import AST.AST_EXP;

public class TYPE_CLASS extends TYPE
{
	/*********************************************************************/
	/* If this class does not extend a father class this should be null  */
	/*********************************************************************/
	public TYPE_CLASS father;

	/**************************************************/
	/* Gather up all data members in one place        */
	/* Note that data members coming from the AST are */
	/* packed together with the class methods         */
	/**************************************************/
	public TYPE_LIST data_members;
	
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_CLASS(TYPE_CLASS father,String name,TYPE_LIST data_members)
	{
		this.name = name;
		this.father = father;
		this.data_members = data_members;
	}

	/*if we are in type class then this will override the "return false" for just any type*/
	public boolean isClass() {
		return true;
  }
	
	public TYPE searchDataMembers(String name) {
		TYPE dataMemberThisClassOnly = searchDataMembersThisClassOnly(name);
		if (dataMemberThisClassOnly != null) {
			return dataMemberThisClassOnly;
		}
		if (this.father != null) {
			return this.father.searchDataMembers(name);
		}
		return null;
	}

	public int getFieldOffset(String name) {
		TYPE_LIST members = getRelevantDataMembers();
		int offset = -4;
		while (members != null){
			offset += 4;
			if (members.head != null && ((TYPE_NAMED) members.head).name.equals(name)) {
				return offset;
			}
			members = members.tail;
		}
		return offset + 4;
	}

	public int getFieldOffsetMethod(String name) {
		TYPE_LIST members = getRelevantDataMembersMethods();
		int offset = -8;
		while (members != null){
			offset += 4;
			if (members.head != null && ((TYPE_NAMED) members.head).name.equals(name)) {
				return offset;
			}
			members = members.tail;
		}
		return offset + 4;
	}

	public AST_EXP getFieldDefaultValue(String name) {
		TYPE_LIST members = getRelevantDataMembers();
		while (members.tail != null){
			if ( members.head.name.equals(name)) {
				return members.head.default_value;
			}
			members = members.tail;
		}
		return null;
	}

	public TYPE_LIST ListContains(TYPE_LIST lst, String name){
		TYPE_LIST it = lst;
		while (it != null){
			if (it.head != null) {
				if (((TYPE_NAMED)it.head).name.equals(name)) {
					return it;
				}
			}
			it = it.tail;
		}
		return null;
	}

	public TYPE_LIST getRelevantDataMembersMethods(){
		TYPE_LIST result;
		if (father != null) {
			result = father.getRelevantDataMembersMethods();
		}
		else{
			result = new TYPE_LIST(null, null);
		}
		TYPE_LIST it = data_members;
		TYPE_LIST it_result = result;
		while (it_result.tail != null) {
			it_result = it_result.tail;
		}
		while (it != null) {
			if (((TYPE_NAMED)it.head).type.isFunction()) {
				TYPE_LIST existing = ListContains(result, ((TYPE_NAMED)it.head).name);
				if (existing != null) {
					existing.head = it.head;
				} else {
					it_result.tail = new TYPE_LIST(it.head, null);
					it_result = it_result.tail;
				}
			}
			it = it.tail;
		}

		return result;

	}

	public TYPE_LIST getRelevantDataMembers(){
	TYPE_LIST result;
	if (father != null) {
		result = father.getRelevantDataMembers();
	}
	else{
		result = new TYPE_LIST(null, null);
	}
	TYPE_LIST it = data_members;
	TYPE_LIST it_result = result;
	while (it_result.tail != null) {
		it_result = it_result.tail;
	}
	while (it != null) {
		if (!((TYPE_NAMED)it.head).type.isFunction()) {
			TYPE_LIST existing = ListContains(result, ((TYPE_NAMED)it.head).name);
			if (existing != null) {
				existing.head = it.head;
			} else {
				it_result.tail = new TYPE_LIST(it.head, null);
				it_result = it_result.tail;
			}
		}
		it = it.tail;
	}

	return result;

	}
	
	public TYPE searchDataMembersThisClassOnly(String name) {
		TYPE_LIST tmp_data_members = this.data_members; 
		while (tmp_data_members != null) {
			TYPE_NAMED namedType = (TYPE_NAMED) tmp_data_members.head;
			if (namedType.name.equals(name)) {
				return namedType.type;
			}
			tmp_data_members = tmp_data_members.tail;
		}
		return null;
	}

	public boolean isAncestorOf(TYPE_CLASS classType) {
		if (classType == null) {
			return false;
		}
		if (this.name == classType.name)
		{
			return true;
		}
		return this.isAncestorOf(classType.father);
	}
	
	public void addDataMember(String name, TYPE type, AST_EXP default_value) {
		TYPE_NAMED namedType = new TYPE_NAMED(type, name);
		namedType.default_value = default_value;
		if (this.data_members == null) {
			this.data_members =  new TYPE_LIST(namedType, null);
			return;
		}
		TYPE_LIST it = data_members;
		while (it.tail != null) {
			it = it.tail;
		}
		it.tail = new TYPE_LIST(namedType, null);
	}
}
