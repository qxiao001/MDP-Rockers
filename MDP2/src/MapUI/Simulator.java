package MapUI;
import Global.*;
import connector.*;

import javax.swing.BorderFactory;
import javax.swing.JFrame; //imports JFrame library
import javax.swing.JButton; //imports JButton library
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout; //imports GridLayout library
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class Simulator extends JFrame implements ActionListener {

	static boolean save = false;
	Container pane = new Container();;
	JPanel gridPanel = new JPanel();
	JPanel simulatePanel = new JPanel();
	JPanel explorePanel = new JPanel();
	JButton[][] grid, gridExplore;
    Robot robot = new Robot();

	// Map constructor
	public Simulator(String name) {
		super(name);
		setPreferredSize(new Dimension(1000, 650));
	}

	public void addComponentsToMap(Container pane) {

		gridPanel.setLayout(new GridLayout(20, 15)); // set layout
		gridPanel.setPreferredSize(new Dimension(430, 604));
		grid = new JButton[20][15]; // allocate the size of grid
		explorePanel.setLayout(new GridLayout(20, 15));
		explorePanel.setPreferredSize(new Dimension(430, 604));
		gridExplore = new JButton[20][15];

		for (int x = 0; x < 20; x++) {
			for (int y = 0; y < 15; y++) {

				grid[x][y] = new JButton(); // creates new button
				grid[x][y].setName(x + "," + y);
				grid[x][y].setBorder(BorderFactory.createRaisedBevelBorder());

				System.out.print(Global.realMap[x][y] + ",");

				if (Global.realMap[x][y] != 1)
					grid[x][y].setBackground(Color.WHITE);
				else
					grid[x][y].setBackground(Color.BLACK);

				grid[x][y].addActionListener(this);
				gridPanel.add(grid[x][y]); // adds button to grid
				gridExplore[x][y] = new JButton(); // creates new button
				gridExplore[x][y].setName(x + "," + y);
				gridExplore[x][y].setBorder(BorderFactory.createRaisedBevelBorder());
				gridExplore[x][y].setBackground(Color.CYAN);
				explorePanel.add(gridExplore[x][y]);
			}
			System.out.println(" ");
		}

		JButton btnExplore = new JButton("Explore");
		btnExplore.setName("Explore");
		btnExplore.setBackground(Color.WHITE);
		btnExplore.setAlignmentX(CENTER_ALIGNMENT);
		btnExplore.addActionListener(this);

		JButton btnFastestPath = new JButton("Fastest Path");
		btnFastestPath.setName("FastestPath");
		btnFastestPath.setBackground(Color.WHITE);
		btnFastestPath.setAlignmentX(CENTER_ALIGNMENT);
		btnFastestPath.addActionListener(this);

		simulatePanel.add(btnExplore, BorderLayout.NORTH);
		simulatePanel.add(btnFastestPath, BorderLayout.SOUTH);
		simulatePanel.setPreferredSize(new Dimension(460, 25));
		// simulatePanel.setLayout(new BoxLayout(simulatePanel,
		// BoxLayout.Y_AXIS));

		pane.add(gridPanel, BorderLayout.WEST);
		pane.add(simulatePanel, BorderLayout.CENTER);
		pane.add(explorePanel, BorderLayout.EAST);
		pane.setPreferredSize(new Dimension(460, 670));
	}

	// Action listener. Do something when any of the button is clicked
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		String name = button.getName();

		if (name.equals("Fastest Path") == true) {
			//
		} 
		else if (name.equals("Explore")) {
			setRobot();
			explore();
		} 
		else {
			int index = name.indexOf(",");
			int x = Integer.parseInt(name.substring(0, index));
			int y = Integer.parseInt(name.substring(index + 1));

			if (button.getBackground() == Color.WHITE) { // set obstacle
				button.setBackground(Color.BLACK);
				Global.realMap[x][y] = 1;
			} 
			else if (button.getBackground() == Color.BLACK) { // remove obstacle
				button.setBackground(Color.WHITE);
				Global.realMap[x][y] = 0;
			}
		}
	}

	// Display robot
	public void setRobot() {
		int x;
		int y;
		x = Global.currCX - 1;
		y = Global.currCY - 1;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				gridExplore[x + i][y + j].setBackground(Color.YELLOW); // color of robot
			}
		}
		gridExplore[Global.currFX][Global.currFY].setBackground(Color.PINK); // indicate the front of robot
	}

	public void clearRobot() {
		int x;
		int y;
		x = Global.currCX - 1;
		y = Global.currCY - 1;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				gridExplore[x + i][y + j].setBackground(Color.WHITE); // explored grid
			}
		}
	}

	public void moveForward() {
		clearRobot();
		try {
			
			Global.c.mySend(Global.moveForward);
			System.out.println("I have sent robot to move forward.");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int x = Global.currFX - Global.currCX;
		int y = Global.currFY - Global.currCY;
		Global.currCX = Global.currCX + x;
		Global.currCY = Global.currCY + y;
		Global.currFX = Global.currFX + x;
		Global.currFY = Global.currFY + y;
		try {
			robot.senseEnvironment();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		paintRobotMap();
		setRobot();
	}

	public void turnRight() {
		clearRobot();
		try {
			Global.c.mySend(Global.turnRight);
			System.out.println("I have sent robot to turn Right.");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int x = Global.currFX - Global.currCX;
		int y = Global.currFY - Global.currCY;

		if (x != 0) {
			if (x < 0) {
				Global.currFX++;
				Global.currFY++;
			} else {
				Global.currFX--;
				Global.currFY--;
			}
		} else {
			if (y < 0) {
				Global.currFX--;
				Global.currFY++;
			} else {
				Global.currFX++;
				Global.currFY--;
			}
		}
		try {
			robot.senseEnvironment();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		paintRobotMap();
		setRobot();

	}

	public void turnLeft() {
		clearRobot();
		try {
			Global.c.mySend(Global.turnLeft);
			System.out.println("I have sent robot to turn Left.");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int x = Global.currFX - Global.currCX;
		int y = Global.currFY - Global.currCY;

		if (x != 0) {
			if (x < 0) // facing upward x=-1,y=0
			{
				Global.currFX++;
				Global.currFY--;
			} else // facing downward
			{
				Global.currFX--;
				Global.currFY++;
			}
		} 
		else {
			if (y < 0) // facing left x=0,y=-1
			{
				Global.currFX++;
				Global.currFY++;
			} else // facing right x=0,y=1
			{
				Global.currFX--;
				Global.currFY--;
			}
		}
		try {
			robot.senseEnvironment();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		paintRobotMap();
		setRobot();
	}
	  
    //return true if
    //the top left grid have not yet been explored and
	//(top left grid will not be explored because of the position of the side sensor)
	//the 2 lower grids on the left are empty
   
   public boolean leftUnexplored(char orientation){
      boolean unexplore = false;
      
      switch (orientation){
         case 'U':   //not at left wall
                     if ((Global.currCY != 1) && (Global.robotMap[Global.currCX + 1][Global.currCY - 2] == 0) && (Global.robotMap[Global.currCX][Global.currCY - 2] == 0) && (Global.exploreMap[Global.currFX][Global.currFY - 2] == 0))
                    	unexplore = true;                   
                     else
                    	unexplore = false;
                     break;
                     
         case 'R':   //not at top wall
                     if ((Global.currCX != 1) && (Global.robotMap[Global.currCX - 2][Global.currCY] == 0) && (Global.robotMap[Global.currCX - 2][Global.currCY - 1] == 0) && (Global.exploreMap[Global.currFX - 2][Global.currFY] == 0))
                        unexplore = true;
                     else 
                    	unexplore = false;
                     break;
                     
         case 'L':  //not at bottom wall
		        	if ((Global.currCX != 18) && (Global.robotMap[Global.currCX + 2][Global.currCY + 1] == 0) && (Global.robotMap[Global.currCX + 2][Global.currCY] == 0) && (Global.exploreMap[Global.currFX + 2][Global.currFY] == 0))
		                unexplore = true;
		            else
		            	unexplore = false;
                    break;
                    
         case 'D':  //not at right wall
        	        if ((Global.currCY != 13) && (Global.robotMap[Global.currCX][Global.currCY + 2] == 0) && (Global.robotMap[Global.currCX - 1][Global.currCY + 2] == 0) && (Global.exploreMap[Global.currFX][Global.currFY + 2] == 0))
		                unexplore = true;
		            else
		            	unexplore = false;
                    break;
      }
      return unexplore;
   }
   
   //return true if 
   //the top right grid has not been explored and
   //the lower right 2 grids are empty
   public boolean rightUnexplored(char orientation){
	      boolean unexplore = false;
	      
	      switch (orientation){
	         case 'U':  //not at right wall
	        	 		if ((Global.currCY != 13) && (Global.exploreMap[Global.currFX][Global.currFY + 2] == 0) && (Global.robotMap[Global.currCX][Global.currCY + 2] == 0) && (Global.robotMap[Global.currCX + 1][Global.currCY + 2] == 0))
	                    	unexplore = true;
	                    else
	                    	unexplore = false;
	                    break;
	                     
	         case 'R':  //not at bottom wall                
	                    if ((Global.currCX != 18) && (Global.exploreMap[Global.currFX + 2][Global.currFY] == 0) && (Global.robotMap[Global.currCX + 2][Global.currCY - 1] == 0) && (Global.robotMap[Global.currCX + 2][Global.currCY] == 0))
	                        unexplore = true;
	                    else 
	                    	unexplore = false;
	                    break;
	                     
	         case 'L':  //not at top wall
			            if ((Global.currCX != 1) && (Global.exploreMap[Global.currFX - 2][Global.currFY] == 0) && (Global.robotMap[Global.currCX - 2][Global.currCY] == 0) && (Global.robotMap[Global.currCX - 2][Global.currCY + 1] == 0))
			                unexplore = true;
			            else
			            	unexplore = false;
	                    break;
	                    
	         case 'D':	//not at left wall
			            if ((Global.currCY != 1) && (Global.exploreMap[Global.currCX + 1][Global.currCY - 2] == 0) && (Global.robotMap[Global.currCX - 1][Global.currCY - 2] == 0) && (Global.robotMap[Global.currCX][Global.currCY - 2] == 0))
			                unexplore = true;
			            else
			                unexplore = false;
	                    break;
	      }
	      return unexplore;
	   }
   
   //check if left 3 grids are empty
   
   public boolean leftEmpty(char orientation){
	      boolean leftIsEmpty = false;
	      
	      switch (orientation){
	         case 'U':  //not at left wall
	                    if ((Global.currCY != 1) && (Global.robotMap[Global.currCX + 1][Global.currCY - 2] == 0) && (Global.robotMap[Global.currCX][Global.currCY - 2] == 0) && (Global.robotMap[Global.currCX - 1][Global.currCY - 2] == 0))
	                    	leftIsEmpty = true;            
	                    else
	                    	leftIsEmpty = false;
	                    break;
	                     
	         case 'R':   //not at top wall
	                     if ((Global.currCX != 1) && (Global.robotMap[Global.currCX - 2][Global.currCY] == 0) && (Global.robotMap[Global.currCX - 2][Global.currCY - 1] == 0) && (Global.robotMap[Global.currCX - 2][Global.currCY + 1] == 0))
	                        leftIsEmpty = true;
	                     else 
	                    	 leftIsEmpty = false;
	                     break;
	                     
	         case 'L':  //not at bottom wall
			        	if ((Global.currCX != 18) && (Global.robotMap[Global.currCX + 2][Global.currCY + 1] == 0) && (Global.robotMap[Global.currCX + 2][Global.currCY] == 0) && (Global.robotMap[Global.currCX + 2][Global.currCY - 1] == 0))
			                leftIsEmpty = true;
			            else
			            	leftIsEmpty = false;
	                    break;
	                    
	         case 'D':	//not at right wall
			        	if ((Global.currCY != 13) && (Global.robotMap[Global.currCX][Global.currCY + 2] == 0) && (Global.robotMap[Global.currCX - 1][Global.currCY + 2] == 0) && (Global.robotMap[Global.currCX + 1][Global.currCY + 2] == 0))
			                leftIsEmpty = true;
			            else
			            	leftIsEmpty = false;
	                    break;
	      }
	      return leftIsEmpty;
	   }
   
   public boolean rightEmpty(char orientation){
	      boolean rightIsEmpty = false;
	      
	      switch (orientation){
	         case 'U':  //not at right wall
	                    if ((Global.currCY != 18) && (Global.robotMap[Global.currCX + 1][Global.currCY + 2] == 0) && (Global.robotMap[Global.currCX][Global.currCY + 2] == 0) && (Global.robotMap[Global.currCX - 1][Global.currCY + 2] == 0))
	                    	rightIsEmpty = true;                   
	                    else
	                    	rightIsEmpty = false;
	                    break;
	                     
	         case 'R':  //not at bottom wall
	                    if ((Global.currCX != 18) && (Global.robotMap[Global.currCX + 2][Global.currCY] == 0) && (Global.robotMap[Global.currCX + 2][Global.currCY - 1] == 0) && (Global.robotMap[Global.currCX + 2][Global.currCY + 1] == 0))
	                        rightIsEmpty = true;
	                    else 
	                    	rightIsEmpty = false;
	                    break;
	                    
	         case 'L':  //not at top wall
			        	if ((Global.currCX != 1) && (Global.robotMap[Global.currCX - 2][Global.currCY + 1] == 0) && (Global.robotMap[Global.currCX - 2][Global.currCY] == 0) && (Global.robotMap[Global.currCX - 2][Global.currCY - 1] == 0))
			                rightIsEmpty = true;
			            else
			            	rightIsEmpty = false;
	                    break;
	                    
	         case 'D':  //not at left wall
			        	if ((Global.currCY != 1) && (Global.robotMap[Global.currCX][Global.currCY - 2] == 0) && (Global.robotMap[Global.currCX - 1][Global.currCY - 2] == 0) && (Global.robotMap[Global.currCX + 1][Global.currCY - 2] == 0))
			                rightIsEmpty = true;
			            else
			                rightIsEmpty = false;
	                    break;
	      }
	      return rightIsEmpty;
	   }
   
   //while reach goal = true
   public boolean mapRight3RowsExplored(){
	      boolean rightIsExplored = true;
	      boolean stop = false;
	      if (Global.currCY < 13)
     	  {
	        	for (int i = Global.currCY + 2; i < 15; i++)
	        	{
	        		for (int j = -1; j < 2; j++)
	        		{
	        			if ((Global.exploreMap[Global.currCX + j][i] == 1) && (Global.robotMap[Global.currCX + j][i] == 1)) //obstacle found
	        			{
	        				stop = true;
		        	 		break;
	        			}
	        			else if (Global.exploreMap[Global.currCX + j][i] == 0) //grid unexplored
		        	 	{
		        	 		rightIsExplored = false;
		        	 		break;
		        	 	}
	        		}
	        		if (!rightIsExplored)
	        			break;
	        	}
     	 }
	      return rightIsExplored;
	   }
   
   public boolean mapLeft3RowsExplored(){
	      boolean leftIsExplored = true;
	      boolean stop = false;
	      if (Global.currCY > 5)
	      {
	        	 for (int i = Global.currCY - 2; i > 3; i--)
	        	 {
	        		for (int j = -1; j < 2; j++)
	        		{
		        	 	if ((Global.exploreMap[Global.currCX + j][i] == 1) && (Global.robotMap[Global.currCX + j][i] == 1)) //obstacle found
		        	 	{
		        	 		stop = true;
		        	 		break;
		        	 	}
		        	 	else if (Global.exploreMap[Global.currCX + j][i] == 0) //grid unexplored
		        	 	{
		        	 		leftIsExplored = false;
		        	 		break;
		        	 	}
		        	 		
	        		}
	        		if (!leftIsExplored || stop)
	        			break;
	        	 }
	      }
	      return leftIsExplored;
   }
   
   public boolean mapFront3ColumnsExplored(){
	      boolean frontIsExplored = true;
	      if (Global.currCX > 1)
	      {
	        	 for (int i = Global.currCX - 2; i >= 0; i--)
	        	 {
	        		for (int j = -1; j < 2; j++)
	        		{
		        	 	if (Global.exploreMap[i][Global.currCY + j] == 0)
		        	 	{
		        	 		frontIsExplored = false;
		        	 		break;
		        	 	}
	        		}
	        		if (!frontIsExplored)
	        			break;
	        	 }
	      }
	      return frontIsExplored;
}
   
	public void explore() {
		
		Thread t = new Thread() {
		//	@Override
			public void run() {
				char ori = ' ';
				char lastOri = ' ';
				int count=0;
				int moveDownwardCounter1 = 0, moveDownwardCounter2 = 0, minX = 0;
				boolean downRightIsUnexplored = false, upRightUnexplored = false, rightRightUnexplored = false, middleUnexplored = false;
				boolean reachTop = false, reachGoal = false;
				boolean anotherPath = false;
				boolean mapLeftBlocked = false, mapLeftBlocked2 = false;
				boolean turnBack = false, turnBack2 = false;
				try {
					robot.senseEnvironment();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				while (true) 
				{
					lastOri = ori;
					count++;
					
					ori = robot.checkOri();
					
					/******************************************************** Facing Up *****************************************************/
					if (ori == 'U') 
					{						
						//not at top wall
						if (Global.currFX != 0)
						{	
							//front is empty
							if ((Global.robotMap[Global.currFX - 1][Global.currFY + 1] == 0) && (Global.robotMap[Global.currFX - 1][Global.currFY] == 0) && (Global.robotMap[Global.currFX - 1][Global.currFY - 1] == 0))
							{
								System.out.println("We have now checked the front grids learance.");
								if (!reachGoal)
								{
									if (leftUnexplored(ori))
										turnLeft();
									
									else if ((lastOri != 'R') && leftEmpty(ori))  //left is sensed but not really explored
										turnLeft();
									else
									{
										System.out.println("Yes I am really movingforward.");
										moveForward();	
									}	
								}
								//reached goal
								else
								{
									if (!mapFront3ColumnsExplored())  //robot does not explore middle area of arena
									{
										moveForward();
										middleUnexplored = true;
									}
										
									else if (leftUnexplored(ori))
										turnLeft();
																                                    
									else if (mapLeftBlocked && leftEmpty(ori) && !middleUnexplored && (Global.currCX < minX)) //up->left, just came out from a dead end on left, going left
										turnLeft();
									
									else if (middleUnexplored && mapLeft3RowsExplored() && mapRight3RowsExplored())  //both sides are explored when moving up into to middle area of arena 
										turnLeft();
									else
										moveForward();
								}
							}
							
							//front is blocked
							else
							{
								if (Global.currCY == 1)  //at left wall
									turnRight();
								
								else if (Global.currCY == 13)  //at right wall
									turnLeft();
								
								else if (mapLeftBlocked && (Global.currCX < minX) && !middleUnexplored && leftEmpty(ori)) //came out from a dead end on left, going left
									turnLeft();
								
								else if (middleUnexplored && mapLeft3RowsExplored() && mapRight3RowsExplored())  //both sides are explored when moving up into to middle area of arena 
									turnLeft();
									
								else if (leftUnexplored(ori)) //before reaching goal
									turnLeft();
								
								else if ((Global.currCY != 1) && (Global.exploreMap[Global.currCX - 1][Global.currCY - 2] == 0)) //top left grid unexplored
									turnLeft();
								else
								{
									if (!reachGoal)
										turnRight();
									else
										turnLeft();
								}	
							}
						}
						//at top wall
						else
						{
							if ((Global.currCY != 1) && (Global.exploreMap[Global.currCX - 1][Global.currCY - 2] == 0) && (Global.exploreMap[Global.currCX - 1][Global.currCY + 2] == 0)) //top left and right grids unexplored
							{
								turnLeft();
								upRightUnexplored = true;
							}
							else 
								turnRight();
						}
					} // end of 'U'

					/*************************************************** Facing Right ********************************************/
					else if (ori == 'R') 
					{
						// not at right wall
						if (Global.currFY != 14)
						{
							//front is empty
							if ((Global.robotMap[Global.currFX-1][Global.currFY+1] == 0) && (Global.robotMap[Global.currFX][Global.currFY+1] == 0) && (Global.robotMap[Global.currFX+1][Global.currFY+1] == 0))
		   					{
								if (!reachGoal)
								{
									if (leftUnexplored(ori))
				                        turnLeft();
									
									else if ((Global.currCX != 1) && (lastOri != 'D') && (leftEmpty(ori)))  //not came from top
										turnLeft();
									else 
										moveForward();
								}						
								//has reached goal
								else
								{
//									if (mapLeftBlocked && !anotherPath)  //dead end on left
//									{
//										if (leftEmpty(ori))
//											turnLeft();
//										else 
//											moveForward();
//									}
//									
									if (leftUnexplored(ori))
				                        turnLeft();
									else
										moveForward();
								}
							}
							//front is blocked
							else
							{
								if (Global.currFX == 1)  //at top wall
									turnRight();
								
								else if (leftUnexplored(ori))
			                        turnLeft(); 
									
								else if (!reachTop && leftEmpty(ori))
									turnLeft();
								else 
									turnRight();
							} 
						}
						//at right wall
						else
						{
							if (Global.currCX == 1)  //at goal zone
								turnRight();
								
							else if (mapLeftBlocked && (leftEmpty(ori))) //dead end on left
								turnLeft();
										
							else if ((Global.currCX != 18) && (Global.exploreMap[Global.currCX - 2][Global.currCY + 1] == 0) && (Global.exploreMap[Global.currCX + 2][Global.currCY + 1] == 0))
							{
								turnLeft();
								rightRightUnexplored = true;
							}
							else if (Global.exploreMap[Global.currCX - 2][Global.currCY + 1] == 0)
								turnLeft();
							else 
								turnRight();
						}
 					} // end of 'R'
					
					/*************************************************** Facing Left ********************************************/

					else if (ori == 'L') 
					{ 						
						//not at left wall
						if (Global.currFY != 0)
						{
							//the front is empty
							if ((Global.robotMap[Global.currFX + 1][Global.currFY - 1] == 0) && (Global.robotMap[Global.currFX][Global.currFY - 1] == 0) && (Global.robotMap[Global.currFX - 1][Global.currFY - 1] == 0))
							{
								if (!reachGoal)
			                    {
									if (leftUnexplored(ori))
										turnLeft(); 

									else if ((Global.currCY == 1) && (Global.exploreMap[Global.currCX + 2][Global.currCY - 1] == 0))
										turnLeft();
									
									else if ((reachTop) && (lastOri == 'U'))  //not came from down
										moveForward();
									
									else if ((reachTop) && (lastOri != 'D') && (Global.robotMap[Global.currCX + 2][Global.currCY + 1] == 0) && (Global.robotMap[Global.currCX + 2][Global.currCY] == 0) && (Global.robotMap[Global.currCX + 2][Global.currCY - 1] == 0))
										turnLeft();
									else
										moveForward();
			                    }
								//reached goal
								else
								{
									if ((Global.currCX < 3) && leftEmpty(ori))  //at top wall
										turnLeft();
									
									else if ((Global.currCY == 13) && rightRightUnexplored)  //at right wall
									{
										rightRightUnexplored = false;
										turnLeft();
									}
									else if ((Global.currCX > 3) && (Global.currCY == 5) && (Global.exploreMap[Global.currCX - 2][Global.currCY - 1] == 0)) //top right grid unexplored at turning point(y=5)
										turnRight();
									
									else if ((Global.currCY < 6) && (Global.currCX != 18) && !mapLeftBlocked2 && !turnBack2) //turn back to right wall
									{
										turnLeft();
										turnBack = true;
										if (middleUnexplored)
											middleUnexplored = false;
									}
									else if (leftEmpty(ori) && mapLeftBlocked && (Global.currCX < minX) && !middleUnexplored) //up->left->move, just come out from left dead end, moving forward
									{
										mapLeftBlocked = false;
										mapLeftBlocked2 = true;
										moveForward();
									}
									else if (turnBack2 && leftEmpty(ori) && !mapLeftBlocked) //come back from left, going down
									{
										turnLeft();
										turnBack2 = false;
									}
									else if ((Global.currCY > 4) && leftEmpty(ori) && mapLeftBlocked2) //come out from left dead end, going down
									{
										turnLeft();	
										mapLeftBlocked2 = false;
									}
									else
										moveForward();
								}
							}
							//front is blocked
							else
							{
								if (reachGoal && Global.currCX == 18)  //at bottom
								{
									turnRight();
									mapLeftBlocked = true;
									minX = Global.currCX + 1;
								}
								else if (Global.currCX == 1)  //at top wall
									turnLeft();
								
								else if ((Global.currCX > 3) && (Global.currCY > 4) && (Global.exploreMap[Global.currCX - 2][Global.currCY - 1] == 0)) //top right grid unexplored before/when reaching turning point(y=5)
									turnRight();
								
								else if (reachGoal && (Global.currCY > 4) && (Global.currCX != 18) && !mapLeftBlocked2 && !turnBack2) //meet obstacle before/when reaching turning point(y=5)
								{
									turnLeft();
									turnBack = true;
									if (middleUnexplored)
										middleUnexplored = false;
								}
								else if (reachGoal && turnBack2 && leftEmpty(ori) && !mapLeftBlocked)//anotherPath
								{
									turnLeft();
									turnBack2 = false;
								}
								else
								{
									if (!rightUnexplored(ori) && leftEmpty(ori))
										turnLeft();
									
									else if (!leftEmpty(ori) && rightEmpty(ori))
									{
										turnRight();
										mapLeftBlocked = true;
										minX = Global.currCX + 1;
									}
									else if (!leftEmpty(ori) && !rightEmpty(ori))
									{
										mapLeftBlocked = true;
										minX = Global.currCX + 1;
										turnLeft();
									}
									//not confirm
									else
										turnRight();
								}
							}
						}
						//at left wall 
						else
						{
							if ((Global.currCX == 1) && leftEmpty(ori)) //at top wall
								turnLeft();
							
							else if ((Global.currCX != 1) && (Global.exploreMap[Global.currFX + 2][Global.currFY] == 0)) //the top left grid has not yet been explored
								 turnLeft();
							
							else if (reachTop)
								turnLeft();
							else
								 turnRight();
						}
					} // end of 'L'
					
					/*************************************************** Facing Down ********************************************/
					
					else 
					{
						//not at bottom wall
						if (Global.currFX != 19)
						{						
							if (upRightUnexplored)
							{
								turnLeft();
								upRightUnexplored = false;
							}
							//front is empty
							else if ((Global.robotMap[Global.currFX + 1][Global.currFY - 1] == 0) && (Global.robotMap[Global.currFX + 1][Global.currFY] == 0) && (Global.robotMap[Global.currFX + 1][Global.currFY + 1] == 0))
							{
								//not at right wall
								if (Global.currCY != 13)
								{
									if (!reachGoal)
									{
										if (leftUnexplored(ori))
											turnLeft();
										
										else if ((lastOri != 'L') && (leftEmpty(ori)))  //not came out from our right side
											turnLeft();
										else 
											moveForward();	
									}
									//has reached goal
									else
									{										
										if (turnBack)
										{
											turnLeft();
											turnBack = false;
											turnBack2 = true;
											System.out.println("down turnBack");
										}
										else if (turnBack2)
										{
											moveForward();
											turnBack2 = false;
											System.out.println("down turnBack2");
										}
										else if (!mapLeft3RowsExplored() && rightEmpty(ori))  //got left first since will turn back later
											turnRight();
										
										else if (!mapRight3RowsExplored() && leftEmpty(ori))
											turnLeft();
										
										else  //both sides explored
											moveForward();
									}
								}
								//at right wall
								else if (Global.currCY == 13)
								{
									//haven't considered unreachable area
									if (!mapLeft3RowsExplored() && rightEmpty(ori))  //map left unexplored
										turnRight();
									else
									{	
										moveForward();
										if (turnBack2)
											turnBack2 = false;										
									}
								}
							}
							//front is blocked							
							else
							{
								if (!reachGoal)
								{
									if (Global.currCY == 1)
										turnLeft();
									
									else if (leftUnexplored(ori))
										turnLeft();
									
									else if (!leftEmpty(ori))
										turnRight();
									else 
										turnLeft();
								}
								else
								{
									if (turnBack)
									{
										turnLeft();
										turnBack = false;
										turnBack2 = true;
									}
									else if (Global.currCY == 13) //at right wall
										turnRight();
									
									else if (leftUnexplored(ori))
										turnLeft();
									
									else if (!rightUnexplored(ori) && leftEmpty(ori))
										turnLeft();
									
									else if (!rightEmpty(ori))
										turnLeft();
									else
										turnRight();
								}
							}
						}
						//at bottom wall
						else
						{
							if (downRightIsUnexplored == true)  //bottom right unexplored
							{
								turnRight();
								downRightIsUnexplored = false;
							}
							else if ((Global.currCX == 18) && (Global.currCY == 1)) //at bottom left corner
								turnLeft();
							
							else if ((Global.currCX == 18) && (Global.currCY == 13))  //at bottom right corner
								turnRight();
							
							else if ((Global.exploreMap[Global.currCX + 1][Global.currCY - 2] == 0) && (Global.exploreMap[Global.currCX + 1][Global.currCY + 2] == 0))  //both sides are unexplored
							{
								turnLeft();
								downRightIsUnexplored = true;
							}
							else if (Global.exploreMap[Global.currCX + 1][Global.currCY + 2] == 0) //left is unexplored
								turnLeft();
							
							else if (Global.exploreMap[Global.currCX + 1][Global.currCY - 2] == 0)  //right is unexplored
								turnRight();
							
							else if (leftEmpty(ori) && (lastOri != 'R') && !reachGoal)
								turnLeft();
							else 
								turnRight();
						}
					} // end if 'D' 
									
					//delay
					try {sleep(200);} 
					catch (InterruptedException ex) {}
					
					if (Global.currCX == 1)
						reachTop = true;

					if ((Global.currCX == 1) && (Global.currCY == 13))
						reachGoal = true;
					
					if ((reachGoal) && (Global.currCX == 18) && (Global.currCY == 1))
					{
						System.out.println("Exploration finish!");
						break;
					}
				} // end of while loop
			} // end of run()
		};  // end of thread
		t.start();
	} // end of explore()

	public void paintRobotMap() {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 15; j++) {
				if (Global.exploreMap[i][j] == 1) {
					gridExplore[i][j].setBackground(Color.WHITE);
					
					
				}
				if (Global.robotMap[i][j] == 1)
					gridExplore[i][j].setBackground(Color.RED); 
			}
		}
	}
}