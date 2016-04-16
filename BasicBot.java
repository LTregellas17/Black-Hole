package players;

import game.Player;



// Basic player that plays the left most space
//
//  @author LukeJones
 
public class BasicBot extends Player {


	public BasicBot(String name) {
		super(name);
	}

	
	
	public int[] go(int number) {
		
	String [][] board = getBoard(); 
	int [][] empty = getEmpty();
	
	for (int i=0; i < empty.length-1; i++){
		
		
		
			
	}

//	 	Returns the array representing the coordinates 
//		the farthest to the right and down 
	return empty[empty.length-1];
		
	}
	
	public int[] bestMove(int number) {
		int [][] empty = getEmpty();
		if (number == 10){ return empty[empty.length-1];
	}
		return null;
	}
	
	public int[][] endStateThree(int number){
		int [][] empty = getEmpty();
		if (number == 10){ 
			for (int i=0; i< empty.length-1; i++){
				
			}
		}
		
		
		
		
		return empty;
		
	}
	public int[][] sortFinalThree(){
		
		
		return null;
		
	}
	}


