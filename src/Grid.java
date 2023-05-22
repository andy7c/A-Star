public class Grid {
  public Grid(int length, int width) {
	  maze = new char[length][width];
	  visible = new boolean[length][width];
	  
	  for (int i = 0; i < length; i++) {
		  for (int j = 0; j < width; j++) {
			  
			  double rand = Math.random();
			  if (rand >= 0.3) {
				  maze[i][j] = ' ';
			  } else {
				  maze[i][j] = 'X';
			  }
		  }
	  }
	  
	start = new State(this, 1, 1, 0, null);
	setNeighborsVisible(start);
	goal = new State(this, 99, 99, Integer.MAX_VALUE, null);
	  
	  maze[1][1] = 'S';
	  maze[99][99] = 'G';
  }
  
  boolean isBackwards;
  boolean isAdaptive;
  
  public State start;
  public State goal;


  public char[][] maze;
  public boolean[][] visible;
  
  public void setBackwards() {
	  if (isBackwards) {
		  goal = new State(this, 1, 1, Integer.MAX_VALUE, null);
		  start = new State(this, 99, 99, 0, null);
		  setNeighborsVisible(start);
	  }
  }

  public void displayGrid() {
	  
	  for (int i = 0; i < maze.length; i++) {
		  System.out.print("-");
		  System.out.print(" ");
	  }
	  System.out.println();
	  
   for (int i = 0; i < maze.length; i++) {
	   for (int j = 0; j < maze[i].length; j++) {
		   System.out.print(maze[i][j]);
		   System.out.print(" ");
	   }
	   System.out.println("");
   }
   
   for (int i = 0; i < maze.length; i++) {
		  System.out.print("-");
		  System.out.print(" ");
	  }
   
  }

  public void setExpanded(State state) {
    maze[state.y][state.x] = '.';
    setNeighborsVisible(state);
    //for adaptive a* update h values also
    if (isAdaptive) {
    	state.h = goal.g - state.g;
    }
  }

  public void setNeighborsVisible(State state) {
    visible[state.y][state.x] = true;
    if (state.y-1 >= 0) {
    	visible[state.y-1][state.x] = true;
    } 
    if (state.y+1 < visible.length) {
    	visible[state.y+1][state.x] = true;
    } 
    if (state.x-1 >= 0) {
    	visible[state.y][state.x-1] = true;
    } 
    if (state.x+1 < visible[0].length) {
    	visible[state.y][state.x+1] = true;
    } 
  }

}
