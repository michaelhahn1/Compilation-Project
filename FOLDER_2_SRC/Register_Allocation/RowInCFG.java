package Register_Allocation;

import java.util.HashSet;

public class RowInCFG {
	public int ir_command_number;
	public HashSet<Integer> successors;
	public int right_temp1;   //-1 if doesnt exist
	public int right_temp2; //-1 if doesnt exist
	public int left_temp; //-1 if doesnt exis t
	public String branch;
	public HashSet<String> related_labels;
	public RowInCFG(int ir_command_number,int left_temp, int right_temp1, int right_temp2, String branch) {
		this.ir_command_number = ir_command_number;
		this.left_temp = left_temp;
		this.right_temp1 = right_temp1;
		this.right_temp2 = right_temp2;
		this.related_labels = new HashSet<String>();
		this.successors = new HashSet<Integer>();
		this.branch = branch;
	}
	
	

}
