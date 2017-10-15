
public class ArgumentValidation extends FifteenPuzzleProblem {
	protected boolean isValid;
	
	public ArgumentValidation(String arguments[]) {
		isValid = readArgs(arguments);
	}
	
	/**
	 * Reads in the command line arguments and determines their validity.
	 * 
	 * @param args Command line arguments
	 */
	public static boolean readArgs(String args[]) {
		String initialState, searchMethod, options;
		try {
			initialState = args[0];
			if (!validateState(initialState)) {
				throw new IllegalArgumentException("Invalid Initial State.");
			}
			
			searchMethod = args[1];
			if (!validateMethod(searchMethod)) {
				throw new IllegalArgumentException("Invalid Search Method.");
			}
			
			//assign fields the values from command line
			myCurrentStateString = initialState;
			myCurrentState = new char[myGridSize][myGridSize];
			myCurrentState = convertToArray(myCurrentStateString);
			mySearchMethod = searchMethod;
			
			if (args.length == 3) {
				options = args[2];
				if (!validateOptions(options)) {
					throw new IllegalArgumentException("Invalid Options.");
				}

				myOptions = options;
			}

			//print out the parsed command line arguments.
			if (DEBUG_MODE) {
				System.out.println("Initial State: " + initialState);
				System.out.println("Search Method: " + searchMethod);
				if (myOptions != null) {
					System.out.println("Options: " + myOptions);
				}
			} else if (myOptions != null) {
				System.out.println("java FifteenProblem \"" + initialState + "\" " + searchMethod + " " + myOptions);
			} else {
				System.out.println("java FifteenProblem \"" + initialState + "\" " + searchMethod);
			}
			
			if (DEBUG_MODE) {
				printState(myCurrentState);
			}
			return true;
		} catch(IllegalArgumentException ex) {
			System.out.println(ex.getMessage());
			return false;
		} 
	}

	/**
	 * Helper method to validate the initial state string from the command
	 * line arguments. Sets the grid size based on the length of the string.
	 * 
	 * @param initialState
	 * @return
	 */
	public static boolean validateState(String initialState) {
		double gridSize;
		gridSize = Math.sqrt(initialState.length());
		if ((gridSize == Math.floor(gridSize)) && !Double.isInfinite(gridSize)) {
		    myGridSize = (int) gridSize;
		    //set goal states
		    if (myGridSize == 3) {
				myGoalState1 = "12345678 ";
				myGoalState2 = "12345687 ";
		    } else if (myGridSize == 4) {
		    	myGoalState1 = "123456789ABCDEF ";
				myGoalState2 = "123456789ABCDFE ";
		    }
		} else {
			return false;
		}
		return true;
	}
	
	/**
	 * Helper method to validate the search method string from the command
	 * line arguments.
	 * 
	 * @param searchMethod
	 * @return
	 */
	public static boolean validateMethod(String searchMethod) {
		if (searchMethod.equals("BFS") ||
			searchMethod.equals("DFS") ||
			searchMethod.equals("GBFS") ||
			searchMethod.equals("AStar") ||
			searchMethod.equals("DLS") ||
			searchMethod.equals("ID")) {
			return true;	
		}
		return false;
	}
	
	/**
	 * Helper method to validate the options string of the command line.
	 * 
	 * @param options
	 * @return
	 */
	public static boolean validateOptions(String options) {
		int num = 0;
		if (options.equals("h1") || options.equals("h2")) {
			if (mySearchMethod.equals("GBFS") || mySearchMethod.equals("AStar")) {
				return true;
			}
		} 
		
		try {
			num = Integer.parseInt(options);
			if (num > 0 && num < 10) {
				if (mySearchMethod.equals("DLS")) {
					return true;
				}
			}
		} catch (Exception ex) {
			return false;
		}
		return false;
	}
}
