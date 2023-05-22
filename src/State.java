import java.util.ArrayList;

public class State implements Comparable<State> {

	public State parent;
public int x, y, g;
public Grid grid;
int h;

  public State(Grid world, int row, int column, int g, State parent) {
	    this.grid = world;
	    this.y = row;
	    this.x = column;
	    this.g = g;
	    this.parent = parent;
  }

  public boolean isValid() {
    if (y < 0 || y >= grid.maze.length) {
      return false;
    }
    if (x < 0 || x >= grid.maze[0].length) {
      return false;
    }
    if (grid.visible[y][x] && grid.maze[y][x] == 'X') {
      return false;
    }
    return true;
  }
  
  public State getNeighbor(int dir) {
    if (dir == 0) {
      return new State(grid, y-1, x, g+1, this);
    }
    else if (dir == 1) {
      return new State(grid, y+1, x, g+1, this);
    }
    else if (dir == 2) {
      return new State(grid, y, x-1, g+1, this);
    }
    else {
      return new State(grid, y, x+1, g+1, this);
    }
  }
  
  public boolean isUnblocked() {
	  if (grid.maze[y][x] == ' ') {
		  return true;
	  }
    return false;
  }
  
  public boolean isBlocked() {
	  if (grid.maze[y][x] == 'X') {
		  return true;
	  }
	  return false;
  }

  public ArrayList<State> getNeighbors() {
    ArrayList<State> neighbors = new ArrayList<State>();
    for (int i = 0; i < 4; i++) {
      State neighbor = getNeighbor(i);
      if (neighbor.isValid()) {
    	  neighbors.add(neighbor);
      }
    }
    return neighbors;
  }
  
  public int g() {
	return g;
  }

  public int f() {
    return h() + this.g;
  }

  public int h() {
    h = Math.abs(grid.goal.y - y) + Math.abs(grid.goal.x - x);
    return h;
  }

  //compares f values
  //preference is given to smaller f values
  //in case of a tie compares g values
  //larger g values are preferred
  public int compareTo(State o) {
    int f_dif = this.f() - o.f();

    if (f_dif == 0) {
    	//larger g values preferred
        int g_dif = o.g() - this.g();
        return g_dif;
        
        //this code prefers smaller g values
        /*int g_dif = this.g() - o.g();
        return g_dif;*/
        
    }
    return f_dif;
  }

  public boolean equals(State state) {
	  if (x == state.x) {
		  if (y == state.y) {
			  return true;
		  }
	  }
    return false;
  }
}

