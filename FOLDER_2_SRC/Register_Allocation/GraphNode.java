package Register_Allocation;

import java.util.HashSet;


public class GraphNode {
	public HashSet<Integer> edges;
	public boolean is_valid;
	public GraphNode() {
		this.edges = new HashSet<Integer>();
		this.is_valid = true;
	}
	public void copy(GraphNode other) {
		for (int edge : other.edges) {
			edges.add(edge);
		}
	}

}
