import java.util.ArrayList;

/**
 * This is a set of different algorithms to find solutions to the 15-puzzle problem
 * 
 * Example Inputs for 15 puzzle problem:
 * 
 * “123456789AB DEFC” BFS
 * “123456789AB DEFC” DFS
 * “123456789AB DEFC” DLS 2
 * “123456789AB DEFC” GBFS h1
 * “123456789AB DEFC” AStar h2
 * 
 * Example for 8 puzzle problem:
 * "12345 678" BFS
 * "12345 678" DFS
 * "12345 678" DLS 2
 * "12345 678" GBFS h1
 * "12345 678" AStar h2
 * 
 * The order of expanding nodes for this puzzle will be:
 * RIGHT
 * DOWN
 * LEFT
 * UP
 * 
 * Goal States are one of the two following states:
 * * 15 puzzle:
 * "123456789ABCDEF "
 * "123456789ABCDFE "
 * * 8 puzzle:
 * "12345678 "
 * "12345687 "
 * 
 * h1 = the number of misplaced tiles. 
	
 * h2 = the sum of the distances of the tiles from their goal positions. Because tiles
	cannot move along diagonals, the distance we will count is the sum of the horizontal
	and vertical distances. This is sometimes called the city block distance or Manhattan
	distance. h2 is also admissible because all any move can do is move one tile one step MANHATTAN
	DISTANCE closer to the goal. Tiles 1 to 8 in the start state give a Manhattan distance of
	
 * @author Tony Lee
 *
 */
public class FifteenPuzzleProblem {
	protected static final boolean DEBUG_MODE = false;
	protected static final boolean EXTRA_DEBUG_MODE = false;
	protected static char myCurrentState[][];
	protected static String myCurrentStateString, mySearchMethod, myOptions, myGoalState1, myGoalState2;
	protected static int myGridSize;
	protected static int myNumCreated, myMaxFringe;
	protected static ArrayList<Node> myExpandedNodes;
	
	public static void main(String args[]) {
		ArgumentValidation validate = new ArgumentValidation(args);
		myExpandedNodes = new ArrayList<Node>();
		if (validate.isValid) {
			executeSearch();
		}
	}
	
	/**
	 * Begins the proper search method depending on the command line arguments.
	 */
	public static void executeSearch() {
		if (mySearchMethod.equals("BFS")) {
			new BFS();
		}else if (mySearchMethod.equals("DFS")) {
			new DFS();
		}else if (mySearchMethod.equals("GBFS")) {
			new GBFS();
		}else if (mySearchMethod.equals("AStar")) {
			new AStar();
		}else if (mySearchMethod.equals("DLS")) {
			new DFS();
		}
	}
	
	/**
	 * Function to get the next unvisited node. 
	 * 
	 * @param node
	 * @return
	 */
	protected static Node getUnvisitedChildNode(Node node, int dir) {
		int currentSpot, currentI, currentJ;
		Node newNode = null;
		String theState = node.getMyState();
		currentSpot = theState.indexOf(' ');
		currentI = currentSpot / myGridSize;
		currentJ = currentSpot % myGridSize;
		
		//move blank right
		if (currentJ != (myGridSize - 1) && (dir == 0 || dir == -1)) {
			newNode = swapAndAssign(1, "RIGHT", currentSpot, theState);
			if (newNode != null) {
				return newNode;
			}
		}
		//move blank down
		if (currentI != (myGridSize - 1) && (dir == 1 || dir == -1)) {

			newNode = swapAndAssign(myGridSize, "BOTTOM", currentSpot, theState);
			if (newNode != null) {
				return newNode;
			}
		}
		//move blank left
		if (currentJ != 0 && (dir == 2 || dir == -1)) {
			newNode = swapAndAssign(-1, "LEFT", currentSpot, theState);
			if (newNode != null) {
				return newNode;
			}
		}
		//move blank up
		if (currentI != 0 && (dir == 3 || dir == -1)) {
			newNode = swapAndAssign(myGridSize * -1, "UP", currentSpot, theState);
			if (newNode != null) {
				return newNode;
			}
		}
		
		return newNode;
	}
	
	/**
	 * Helper function to swap letters around and add the state to the
	 * list of expanded nodes and print it.
	 * 
	 * @param offset
	 * @param dir
	 * @param currentSpot
	 * @param theState
	 */
	private static Node swapAndAssign(int offset, String dir, int currentSpot, String theState) {
		int targetSpot;
		char targetSpotLetter;
		Node newNode = null;
		targetSpot = currentSpot + offset;
		targetSpotLetter = theState.charAt(currentSpot + offset);
		String theNewState = swapChars(targetSpot, currentSpot, targetSpotLetter, theState);
		if (!isExpanded(theNewState)) {
			if (EXTRA_DEBUG_MODE) {
				System.out.println("Letter to " + dir + " of Blank = " + targetSpotLetter);
				printState(convertToArray(theNewState));
			}
			myNumCreated++; //increment number of created nodes
			newNode = new Node(theNewState);
			newNode.setVisited(true);
		}
		return newNode;
	}
	
	/**
	 * Checks to see if the state in the parameter already exists in the
	 * list of expanded nodes. Returns true if it exists in list.
	 * 
	 * @param theState
	 * @return
	 */
	private static boolean isExpanded(String theState) {
		for (Node node : myExpandedNodes) {
			if (theState.equals(node.getMyState())) {
				return true;
			} 
		}
		return false;
	}
	
	/**
	 * Helper function to swap the blank with the target spot in the string.
	 * 
	 * @param targetSpot
	 * @param currentSpot
	 * @param targetSpotLetter
	 * @return
	 */
	private static String swapChars(int targetSpot, int currentSpot, char targetSpotLetter, String theState) {
		StringBuilder theString = new StringBuilder(theState);
		theString.setCharAt(currentSpot, targetSpotLetter);
		theString.setCharAt(targetSpot, ' ');
		return theString.toString();
	}
	
	

	/**
	 * Helper function to count misplaced tiles.
	 * 
	 * @param theState
	 * @return
	 */
	protected static int countMisplaced(Node node) {
		int count = 0;
		String theState = node.getMyState();
		for (int i = 0; i < theState.length(); i++) {
			char c = theState.charAt(i);
			if (c != myGoalState1.charAt(i) && c != myGoalState2.charAt(i)) {
				count++;
			}
		}
		if (mySearchMethod.equals("AStar")) {
			return count + node.getMyDepth();
		} else {
			return count;
		}
	}
	
	/**
	 * Function to find the sum of each of the misplaced letters' distance from their
	 * target location in the goal state.
	 * 
	 * @param node
	 * @return
	 */
	@SuppressWarnings("unused")
	protected static int sumDistFromGoal(Node node) {
		int sum = 0;
		String theState = node.getMyState();
		int targetSpot1, targetSpot2, targetY1, targetX1, targetY2, targetX2, currentY, currentX, total1, total2;
		for (int currentSpot = 0; currentSpot < theState.length(); currentSpot++) {
			char c = theState.charAt(currentSpot);
			if (c != myGoalState1.charAt(currentSpot) && c != myGoalState2.charAt(currentSpot)) {
				targetSpot1 = myGoalState1.indexOf(c);
				targetSpot2 = myGoalState2.indexOf(c);
				currentY = currentSpot / myGridSize;
				currentX = currentSpot % myGridSize;
				targetY1 = targetSpot1 / myGridSize;
				targetX1 = targetSpot1 % myGridSize;
				targetY2 = targetSpot2 / myGridSize;
				targetX2 = targetSpot2 % myGridSize;
				total1 = Math.abs(targetX1 - currentX) + Math.abs(targetY1 - currentY);
				total2 = Math.abs(targetX2 - currentX) + Math.abs(targetY2 - currentY);
				if (total1 < total2) {
					sum += total1;
				} else {
					sum += total2;
				}
				if (EXTRA_DEBUG_MODE) {
					System.out.println("Sum = " + sum);
				}
			} else if (DEBUG_MODE && EXTRA_DEBUG_MODE) {
				System.out.println(c + " in correct spot");
			}
		}
		if (mySearchMethod.equals("AStar")) {
			return sum + node.getMyDepth();
		} else {
			return sum;
		}
	}

	/**
	 * Helper method to convert the state string to a 2 dimensional 
	 * array. 
	 * 
	 * @param theState
	 * @return
	 */
	protected static char[][] convertToArray(String theState) {
		int letterCount = 0;
		char theStateArray[][] = new char[myGridSize][myGridSize];
		for (int i = 0; i < myGridSize; i++) {
			for (int j = 0; j < myGridSize; j++) {
				theStateArray[i][j] = theState.charAt(letterCount);
				letterCount++;
			}
		}
		return theStateArray;
	}

	/**
	 * Helper method to print the state of the puzzle in grid format.
	 * 
	 * @param theState
	 */
	protected static void printState(char theState[][]) {
		int i = 0, j = 0;
		for (i = 0; i < myGridSize + 2; i++) {
			if ((i == 0) || i == myGridSize + 1) {
				System.out.print("+");
				for (int k = 0; k < myGridSize; k++) {
					System.out.print("--");
				}
				System.out.print("-+");
			} else {
				for (j = 0; j < myGridSize; j++) {
					if (j == 0) {
						System.out.print("| ");
					} 
					if (i > 0 && i < myGridSize  + 1) {
						System.out.print(theState[i - 1][j]);
						System.out.print(" ");
					}
					if (j == myGridSize - 1) {
						System.out.print("| ");
					} 
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * Prints the solution to the puzzle.
	 * 
	 * @param goalNode
	 */
	protected static void printSolution(Node goalNode) {
		if (DEBUG_MODE) {
		System.out.println("Depth: " + goalNode.getMyDepth());
		System.out.println("NumCreated: " + myNumCreated);
		System.out.println("NumExpanded: " + myExpandedNodes.size());
		System.out.println("MaxFringe: " + myMaxFringe);
		} else {
			System.out.println(goalNode.getMyDepth() + ", " + myNumCreated + ", " + myExpandedNodes.size() + ", " + myMaxFringe);
		}
	}
}

