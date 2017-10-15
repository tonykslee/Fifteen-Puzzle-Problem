import java.util.Stack;

public class DFS extends FifteenPuzzleProblem {

	/**
	 * Default Constructor takes the Current State from the inherited class as
	 * argument.
	 */
	public DFS() {
		performDFS(myCurrentStateString);
	}
	
	/**
	 * Uses Depth First Search algorithm to solve the puzzle.
	 * This function is used for both DFS and DLS
	 * 
	 * @param theState
	 */
	@SuppressWarnings("unused")
	private static void performDFS(String theState) {
		int depth = 0;
		Node rootNode = new Node(theState);
		myNumCreated++; //add one for the root node
		rootNode.setVisited(true);
		rootNode.setMyDepth(depth);
		Stack<Node> stack = new Stack<Node>();
		stack.push(rootNode);
		rootNode.setVisited(true);
		while(!stack.isEmpty()) {
			Node node = stack.peek();
			myExpandedNodes.add(node);
			if(node.getMyState().equals(myGoalState1) || node.getMyState().equals(myGoalState2)) {
				if (DEBUG_MODE) {
					printState(convertToArray(node.getMyState()));
				}
				printSolution(node);
				return;
			}
			Node child = null;
			if ((child = getUnvisitedChildNode(node, -1)) != null) {
				if (myOptions == null ||node.getMyDepth() <= Integer.parseInt(myOptions)) {
					child.setVisited(true);
					child.setMyDepth(node.getMyDepth() + 1);
					stack.push(child);
					if (stack.size() > myMaxFringe) {
						myMaxFringe = stack.size(); //assign max fringe value
						
						if (myMaxFringe % 5000 == 0 && DEBUG_MODE) {
							System.out.println("max Fringe: " + myMaxFringe + " Current Node Depth: " + node.getMyDepth());
							printState(convertToArray(node.getMyState()));
						}
					}
				}
				
			} else {
				stack.pop();
			}
		}
		Node noSol = new Node();
		printSolution(noSol);
	}
}
