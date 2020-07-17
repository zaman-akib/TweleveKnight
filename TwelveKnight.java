import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.HashSet;

public class TwelveKnight{
	static int step=0;
	
	static class A_Star {

	    PriorityQueue<State> open_list;
	    HashSet<String> closed_list;

	    A_Star() {
	        open_list = new PriorityQueue<>(new StateComparator());
	        closed_list = new HashSet<String>();
	    }
	    
	    public boolean a_star_start(State start) {
	        start.g_cost = 0;
	        start.f_cost = start.g_cost + start.heuristic_1();

	        open_list.add(start);
	        while (!open_list.isEmpty()) {
	            State current = open_list.poll();
	            
	            //System.out.println("f_cost -->> "+ current.f_cost);

	            if (current.isGoal()) {
	            	System.out.println("Goal Found!!\n");
	            	System.out.println("Total moves needed -->> "+ current.g_cost+"\n");
	                printPath(current);

	                return true;
	            }

	            closed_list.add(current.toString());

	            for (State neighbor : current.generateNextMoves()) {
	                if (closed_list.contains(neighbor.toString())) {
	                    continue;
	                }

	                neighbor.f_cost = neighbor.g_cost + neighbor.heuristic_1();

	                if (open_list.contains(neighbor) == false) {
	                    open_list.add(neighbor);
	                }
	            }
	        }
	        return false;
	    }

	    private class StateComparator implements Comparator<State> {
	        @Override
	        public int compare(State s1, State s2) { 
	            if (s1.f_cost > s2.f_cost) {
	                return 1;
	            } else if (s1.f_cost < s2.f_cost) {
	                return -1;
	            } else {
	                return 0;
	            }

	        }
	    }

	    private void printPath(State s) {
	        if (s == null) {
	            return;
	        }
	        printPath(s.parent);
	        step++;
	        System.out.println("Step: "+step);
	        System.out.println(s.toString());
	        return;
	    }
	}


	static class State {
		
		State parent;
	    static String [][]goal;
	    String [][]board;
	    int g_cost;
	    int f_cost;

	    State() {

	        parent = null;
	        board = new String[5][5];

	    }

	    public State(State b) {

	        parent = null;
	        g_cost = b.g_cost;
	        board = new String[5][5];

	        for (int i = 0; i < 5; i++) {
	            for (int j = 0; j < 5; j++) {
	                board[i][j] = b.board[i][j];
	            }
	        }
	    }

	    public State(String[][] blocks) {

	        parent = null;
	        board = new String[5][5];
	        g_cost = 0;

	        for (int i = 0; i < 5; i++) {
	            for (int j = 0; j < 5; j++) {
	                board[i][j] = blocks[i][j];
	            }
	        }

	    }
	    
	    public int heuristic_1() {
	        int count = 0;

	        for (int i = 0; i < 5; i++) {
	            for (int j = 0; j < 5; j++) {
	                if (!board[i][j].contains(goal[i][j])) {
	                    count++;
	                }
	            }
	        }
	        return count;
	    }

	    public int heuristic_2() {
	        int count = 0;

	        for (int i = 0; i < 5; i++) {
	            for (int j = 0; j < i; j++) {
	                if (!board[i][j].contains(goal[i][j])) {
	                    count++;
	                }
	            }
	        }
	        return count;
	    }
	    
	    public int heuristic_3() {
	        int count = 0;

	        for (int i = 0; i < 5; i++) {
	            for (int j = i; j < 5; j++) {
	                if (!board[i][j].contains(goal[i][j])) {
	                    count++;
	                }
	            }
	        }
	        return count;
	    }
	    
	    public int heuristic_4() {
	        int count = 0;

	        for (int i = 0; i < 5; i++) {
	            for (int j = 0; j <= i; j++) {
	                if (board[i][j].contains("b")) {
	                    count++;
	                }
	            }
	        }
	        return count;
	    }

	    public boolean isGoal() {
	        if (heuristic_1() > 0) {
	            return false;
	        } else {
	            return true;
	        }
	    }

	    public State[] generateNextMoves() {
	    	State[] neighbor = new State[8];

	        for (int i = 0; i < 8; i++) {
	            neighbor[i] = new State(this);
	            neighbor[i].parent = this;
	            neighbor[i].g_cost = this.g_cost + 1;
	        }

	        for (int i = 0; i < 5; i++) {
	            for (int j = 0; j < 5; j++) {

	                if (board[i][j] == "-") {
	                    //move left_1
	                    if (j > 1 && i > 0) {
	                    	neighbor[0].board[i][j] = neighbor[0].board[i-1][j-2];
	                    	neighbor[0].board[i-1][j-2] = "-";
	                    }
	                    
	                    //move left_2
	                    if(j > 1 && i < 4) {
	                    	neighbor[1].board[i][j] = neighbor[1].board[i+1][j-2];
	                    	neighbor[1].board[i+1][j-2] = "-";
	                    }

	                    //move right_1
	                    if (j < 3 && i > 0) {
	                    	neighbor[2].board[i][j] = neighbor[2].board[i-1][j+2];
	                    	neighbor[2].board[i-1][j+2] = "-";
	                    }
	                    
	                    //move right_2
	                    if (j < 3 && i < 4) {
	                    	neighbor[3].board[i][j] = neighbor[3].board[i+1][j+2];
	                    	neighbor[3].board[i+1][j+2] = "-";
	                    }

	                    //move up_1
	                    if (i > 1 && j < 4){
	                    	neighbor[4].board[i][j] = neighbor[4].board[i-2][j+1];
	                    	neighbor[4].board[i-2][j+1] = "-";
	                    }
	                    
	                    //move up_2
	                    if (i > 1 && j > 0){
	                    	neighbor[5].board[i][j] = neighbor[5].board[i-2][j-1];
	                    	neighbor[5].board[i-2][j-1] = "-";
	                    }

	                    //move down_1
	                    if (i < 3 && j < 4) {
	                    	neighbor[6].board[i][j] = neighbor[6].board[i+2][j+1];
	                    	neighbor[6].board[i+2][j+1] = "-";
	                    }
	                    
	                    //move down_2
	                    if (i < 3 && j > 0) {
	                    	neighbor[7].board[i][j] = neighbor[7].board[i+2][j-1];
	                    	neighbor[7].board[i+2][j-1] = "-";
	                    }
	                }
	            }
	        }
	        return neighbor;
	    }

	    public String toString() {
	        String s = "";
	        for (int i = 0; i < 5; i++) {
	            for (int j = 0; j < 5; j++) {
	                s += board[i][j] + " ";
	            }
	            s += "\n";
	        }
	        return s;
	    }

	}

    public static void main(String[] args) {
    	
    	String [][]initial_state = {{"w", "b", "w", "b", "b"}, {"b", "b", "w", "-", "b"}, {"w", "b", "b", "b", "w"}, {"w", "b", "w", "b", "w"}, {"w", "w", "b", "w", "w"}};
        String [][]goal_state = {{"b", "b", "b", "b", "b"}, {"w", "b", "b", "b", "b"}, {"w", "w", "-", "b", "b"}, {"w", "w", "w", "w", "b"}, {"w", "w", "w", "w", "w"}};

        //int initial_state[][] = {{1, 2, 1, 2, 2}, {2, 2, 1, 0, 2}, {1, 2, 2, 2, 1}, {1, 2, 1, 2, 1}, {1, 1, 2, 1, 1}};
        //int goal_state[][] = {{2, 2, 2, 2, 2}, {1, 2, 2, 2, 2}, {1, 1, 0, 2, 2}, {1, 1, 1, 1, 2}, {1, 1, 1, 1, 1}};
       
        State.goal = goal_state;
        
        State s = new State(initial_state);

        A_Star a_star = new A_Star();
        a_star.a_star_start(s);
        
        /*for(int i=0;i<5;i++) {
        	for(int j=0;j<5;j++) {
        		System.out.print(initial_state[i][j]+" ");
        	}
        	System.out.println();
        }*/
    }
}