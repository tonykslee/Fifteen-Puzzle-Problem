
public class Node {

	private String myState;
	private boolean visited;
	private int myDepth;
	private int myHeuristic;
	
	
	public Node() {
		myDepth = -1;
	}
	
	public Node(String theState) {
		myState = theState;
	}

	public int getMyDepth() {
		return myDepth;
	}

	public void setMyDepth(int myDepth) {
		this.myDepth = myDepth;
	}
	
	public String getMyState() {
		return myState;
	}

	public void setMyState(String myState) {
		this.myState = myState;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public int getMyHeuristic() {
		return myHeuristic;
	}

	public void setMyHeuristic(int myMisplaced) {
		this.myHeuristic = myMisplaced;
	}
	
	
}
