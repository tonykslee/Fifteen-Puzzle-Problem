import java.util.Comparator;
import java.util.PriorityQueue;

public class GBFS extends FifteenPuzzleProblem {

	/**
	 * Default Constructor takes the Current State from the inherited class as
	 * argument.
	 */
	public GBFS() {
		performGBFS(myCurrentStateString);
	}
	
	/**
	 * Performs Greedy BFS algorithm to solve the puzzle.
	 * 
	 * @param theState
	 */
	@SuppressWarnings("unused")
	private static void performGBFS(String theState) {
		int depth = 0;
		Node rootNode = new Node(theState);
		myNumCreated++; //add one for the root node
		rootNode.setVisited(true);
		rootNode.setMyDepth(depth);
		if (myOptions.equals("h1")) {
			rootNode.setMyHeuristic(countMisplaced(rootNode));
		} else if (myOptions.equals("h2")) {
			rootNode.setMyHeuristic(sumDistFromGoal(rootNode));
		}
		PriorityQueue<Node> pQueue = new PriorityQueue<Node>(new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return o1.getMyHeuristic() - o2.getMyHeuristic();
			}
			
		});
		
		pQueue.add(rootNode);
		rootNode.setVisited(true);
		while(!pQueue.isEmpty()) {
			Node node = (Node)pQueue.remove();
			myExpandedNodes.add(node);
			if (myOptions.equals("h1")) {
				node.setMyHeuristic(countMisplaced(node));
			} else if (myOptions.equals("h2")) {
				node.setMyHeuristic(sumDistFromGoal(node));
			}
			if(node.getMyState().equals(myGoalState1) || node.getMyState().equals(myGoalState2)) {
				printSolution(node);
				return;
			}
			Node child = null;
			for (int i = 0; i < 4; i++) {
				if ((child = getUnvisitedChildNode(node, i)) != null) {
					child.setVisited(true);
					child.setMyDepth(node.getMyDepth() + 1);
					if (myOptions.equals("h1")) {
						child.setMyHeuristic(countMisplaced(child));
					} else if (myOptions.equals("h2")) {
						child.setMyHeuristic(sumDistFromGoal(child));
					}
					pQueue.add(child);
					if (pQueue.size() > myMaxFringe) {
						myMaxFringe = pQueue.size(); //assign max fringe value
						if (myMaxFringe % 5000 == 0 && DEBUG_MODE) {
							System.out.println("max Fringe: " + myMaxFringe + " Current Node Depth: " + node.getMyDepth());
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
