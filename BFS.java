import java.util.LinkedList;
import java.util.Queue;

public class BFS extends FifteenPuzzleProblem {

	/**
	 * Default Constructor takes the Current State from the inherited class as
	 * argument.
	 */
	public BFS() {
		performBFS(myCurrentStateString);
	}
	
	/**
	 * Performs a Breadth First Search on the puzzle to find the solution.
	 * 
	 * @param theState
	 */
	@SuppressWarnings("unused")
	public static void performBFS(String theState) {
		int depth = 0;
		Node rootNode = new Node(theState);
		myNumCreated++; //add one for the root node
		rootNode.setVisited(true);
		rootNode.setMyDepth(depth);
		
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(rootNode);
		rootNode.setVisited(true);
		while(!queue.isEmpty()) {
			Node node = (Node)queue.remove();
			myExpandedNodes.add(node);
			if(node.getMyState().equals(myGoalState1) || node.getMyState().equals(myGoalState2)) {
				printSolution(node);
				return;
			}
			Node child = null;
			for (int i = 0; i < 4; i++) {
				if ((child = getUnvisitedChildNode(node, i)) != null) {
					child.setVisited(true);
					child.setMyDepth(node.getMyDepth() + 1);
					queue.add(child);
					if (queue.size() > myMaxFringe) {
						myMaxFringe = queue.size(); //assign max fringe value
						if (myMaxFringe % 5000 == 0 && DEBUG_MODE) {
							System.out.println("max Fringe: " + myMaxFringe + " Current Node Depth: " + node.getMyDepth());
							System.out.print("next up fringe: " + ((Node)queue.peek()).getMyHeuristic());
							printState(convertToArray(node.getMyState()));
						}
					}
				}
			}
		}
		Node noSol = new Node();
		printSolution(noSol);
	}
}
