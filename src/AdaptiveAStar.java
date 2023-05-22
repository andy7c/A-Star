import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Iterator;

public class AdaptiveAStar {
	
	public Grid grid;
	public PriorityQueue<State> open;
	public ArrayList<State> closed;

	public AdaptiveAStar(Grid world) {
		this.grid = world;
		this.open = new PriorityQueue<State>();
		this.closed = new ArrayList<State>();
	}

	public State getFromOpen(State state) {
		Iterator<State> i = open.iterator();
		while (i.hasNext()) {
			State next_state = i.next();
			if (state.equals(next_state)) {
				return next_state;
			}
		}
		return null;
	}
	
	public boolean closedContains(State state) {
		for (State s : closed) {
			if (s.equals(state)) {
				return true;
			}
		}
		return false;
	  }

	public LinkedList<State> traversePath(State goal) {
		LinkedList<State> path = new LinkedList<State>();
		State state = goal;
		path.push(state);
		while (!(state = state.parent).equals(grid.start)) {
			path.push(state);
		}
		path.push(state);
		LinkedList<State> unblocked = new LinkedList<State>();
		ListIterator<State> i = path.listIterator(0);
		while (i.hasNext()) {
			State next = i.next();
			if (!next.isBlocked()) {
				unblocked.add(next);
				if (next.isUnblocked()) {
					grid.setExpanded(next);
				}
			} else {
				return unblocked;
			}
		}
		return unblocked;
	}

	public LinkedList<State> computePath() {
		this.open = new PriorityQueue<State>();
		this.closed = new ArrayList<State>();
		open.add(this.grid.start);

		while (!open.isEmpty()) {
			State state = open.poll();
			if (state.equals(grid.goal)) {
				return traversePath(state);
			}
			closed.add(state);
			for (State neighbor : state.getNeighbors()) {
				if (closedContains(neighbor)) {
					continue;
				}
				State prev = getFromOpen(neighbor);
				if (prev != null) {
					if (prev.g() > neighbor.g()) {
						open.remove(prev);
						open.add(neighbor);
					}
				} else {
					open.add(neighbor);
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		
		long startTime = System.nanoTime();
		long endTime;
		Grid g = new Grid(101, 101);
		g.isAdaptive = true;
		AdaptiveAStar search = new AdaptiveAStar(g);
		int count = 1;
		while (true) {
			LinkedList<State> computedPath = search.computePath();

			if (computedPath == null || computedPath.isEmpty()) {
				System.out.println("No path to goal :(");
				endTime = System.nanoTime();
				System.out.println("\nRuntime was " + (endTime-startTime) + " nanoseconds.");
				System.exit(0);
			}
			System.out.println("World after A* iteration " + count);
			State last = computedPath.peekLast();
			if (!last.equals(search.grid.goal)) {
				search.grid.start = last;
				System.out.println("Recomputing path");
				System.out.println();
			} else {
				System.out.println("Goal has been reached!");
				System.out.println();
				break;
			}
			search.grid.displayGrid();
			System.out.println();
			count++;
		}

		search.grid.displayGrid();
		endTime = System.nanoTime();
		System.out.println("\nRuntime was " + (endTime-startTime) + " nanoseconds.");
	}
	
}
