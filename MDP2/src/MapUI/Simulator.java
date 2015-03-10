package MapUI;
// I AM QIANRU
import Global.*;
import Obstacle.Descriptor;
import connector.*;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame; //imports JFrame library
import javax.swing.JButton; //imports JButton library
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout; //imports GridLayout library
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Simulator extends JFrame implements ActionListener  {

	static boolean save = false;
	Container pane = new Container();;
	JPanel gridPanel = new JPanel();
	JPanel simulatePanel = new JPanel();
	JPanel explorePanel = new JPanel();
	static JButton[][] grid, gridExplore;
    Robot robot = new Robot();
    JFormattedTextField timeTextF = new JFormattedTextField();
    JFormattedTextField coverageTextF = new JFormattedTextField();
    JFormattedTextField speedTextF = new JFormattedTextField();
    JLabel timeDisplayLabel = new JLabel("Time (s)");
    JLabel coverageDisplayLabel = new JLabel("Coverage (%)");
    int timeLimit = 360, coverageLimit = 101, steps = 7;

	// Map constructor
	public Simulator(String name) {
		super(name);
		setPreferredSize(new Dimension(1000, 650));
	}
	public void addComponentsToMap(Container pane) {

		gridPanel.setLayout(new GridLayout(20, 15)); // set layout
		gridPanel.setPreferredSize(new Dimension(430, 604));
		//gridPanel.setBackground(Color.BLACK);
		grid = new JButton[20][15]; // allocate the size of grid
		explorePanel.setLayout(new GridLayout(20, 15));
		explorePanel.setPreferredSize(new Dimension(430, 604));
		//explorePanel.setBackground(Color.BLACK);
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
		btnExplore.addActionListener(this);

		JButton btnFastestPath = new JButton("Fastest Path");
		btnFastestPath.setName("Fastest Path");
		btnFastestPath.setBackground(Color.WHITE);
		btnFastestPath.addActionListener(this);
        
		JLabel blankLabel = new JLabel("Spacing");
		blankLabel.setForeground(Color.WHITE);
		
		JLabel blankLabel2 = new JLabel("Spacing");
		blankLabel2.setForeground(Color.WHITE);
		blankLabel2.setPreferredSize(new Dimension(100,20));
		
		JLabel timeLabel = new JLabel("Time Limit (s):");
		timeTextF.setValue(Global.timeLimit);
		timeTextF.setPreferredSize(new Dimension(40,20));
		
		JLabel coverageLabel = new JLabel("Coverage Limit (%):");
		coverageTextF.setValue("-");
     	coverageTextF.setPreferredSize(new Dimension(40,20));
     	
		JLabel speedLabel = new JLabel("Speed (step/s):");
     	speedTextF.setValue(Global.steps);
     	speedTextF.setName("Speed");
     	speedTextF.setPreferredSize(new Dimension(40,20));
     	
     	JLabel timeDisplay = new JLabel();
     	timeDisplay.setText("Time left (s): ");
     	timeDisplay.setPreferredSize(new Dimension(80,20));
		
		JLabel coverageDisplay = new JLabel();
		coverageDisplay.setText("Coverage (%): ");
		
     	
		timeDisplayLabel.setText("0");
		timeDisplayLabel.setName("Time Display");
		timeDisplayLabel.setPreferredSize(new Dimension(40,20));
		
		
		coverageDisplayLabel.setText("0");
		coverageDisplayLabel.setName("Coverage Display");
		coverageDisplayLabel.setPreferredSize(new Dimension(40,20));
     	
		simulatePanel.add(btnExplore);
		simulatePanel.add(btnFastestPath);
		simulatePanel.add(blankLabel);
		simulatePanel.add(timeLabel);
		simulatePanel.add(timeTextF);
		simulatePanel.add(coverageLabel);
		simulatePanel.add(coverageTextF);
		simulatePanel.add(speedLabel);
		simulatePanel.add(speedTextF);
		simulatePanel.add(blankLabel2);
		simulatePanel.add(timeDisplay);
		simulatePanel.add(timeDisplayLabel);
		simulatePanel.add(coverageDisplay);
		simulatePanel.add(coverageDisplayLabel);
		simulatePanel.setPreferredSize(new Dimension(460, 25));
		simulatePanel.setBackground(Color.WHITE);
		
		pane.add(gridPanel, BorderLayout.WEST);
		pane.add(simulatePanel, BorderLayout.CENTER);
		pane.add(explorePanel, BorderLayout.EAST);
		pane.setPreferredSize(new Dimension(460, 670));
	}



		// Action listener. Do something when any of the button is clicked
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		String btnName = button.getName();

		if (btnName.equals("Fastest Path") == true) {
			// for Fastest part
			Global.fastestPath=true;
			FSP f=new FSP();
			//f.printNodesCostOnly();
			f.findPath();
			paintFsPath();
			String str=f.iLoveToMoveIt();
			//System.out.println(str);
			moveForFastestPath(str);
		} 
		else if (btnName.equals("Explore")) {
			setRobot();
			explore();
		} 
		else {
			int index = btnName.indexOf(",");
			int x = Integer.parseInt(btnName.substring(0, index));
			int y = Integer.parseInt(btnName.substring(index + 1));

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
	 //*********************************The end of robot Movement************************************* 
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
	public void moveForFastestPath (final String str){
		Thread t = new Thread() {
			//@Override
			public void run() {
				String[] part=str.split(",");
				for(int i=0;i<part.length;i++)
				{
					paintFsPath();
					switch(part[i])
					{
					
						case "F010":moveForward();break;
						case "R000":turnRight();break;
						case "L000":turnLeft();break;
						
					}
					//delay
					try {sleep(1000);} 
					catch (InterruptedException ex) {}
					
				}
			} // end of run()
		};  // end of thread
		t.start();
	}

	public void moveForward() {
		if(Global.realRun==true){
			try {
				
				Global.c.mySend(Global.moveForward);
				Global.lastSend=Global.moveForward;
				System.out.println("I have sent robot to move forward.");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			//System.out.println("I have sent robot to move forward.");
		}
		int x = Global.currFX - Global.currCX;
		int y = Global.currFY - Global.currCY;
		Global.currCX = Global.currCX + x;
		Global.currCY = Global.currCY + y;
		Global.currFX = Global.currFX + x;
		Global.currFY = Global.currFY + y;
		if(!Global.fastestPath){
		try {
			
			robot.senseEnvironment();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
		clearRobot();
		paintRobotMap();
		setRobot();
		//Global.readTimerstart=System.nanoTime();
	}

	public void turnRight() {
		if(Global.realRun==true){	
			try {
				
				Global.c.mySend(Global.turnRight);
				Global.lastSend=Global.turnRight;
				System.out.println("I have sent robot to turn right.");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
			System.out.println("I have sent robot to turn right.");
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
		if(!Global.fastestPath){
		try {
			robot.senseEnvironment();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
		clearRobot();
		paintRobotMap();
		setRobot();
		//Global.readTimerstart=System.nanoTime();
	}

	public void turnLeft() {
		if(Global.realRun==true){
			try {
				
				Global.c.mySend(Global.turnLeft);
				Global.lastSend=Global.turnLeft;
				System.out.println("I have sent robot to turn left.");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
				
			System.out.println("I have sent robot to turn left.");
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
		if(!Global.fastestPath){
		try {
			robot.senseEnvironment();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
		clearRobot();
		paintRobotMap();
		setRobot();
		//Global.readTimerstart=System.nanoTime();
	}
	public static void paintRobotMap() {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 15; j++) {
				if (Global.exploreMap[i][j] == 1) {
					gridExplore[i][j].setBackground(Color.WHITE);
					
					
				}
				if((Global.exploreMap[i][j] == -7))
				{
					gridExplore[i][j].setBackground(Color.GREEN);
				}
				if (Global.robotMap[i][j] >= 1)
					gridExplore[i][j].setBackground(Color.RED); 
			}
		}
	}
	 //*********************************The end of robot Movement************************************* 
    public static void paintFsPath()
    {
    	for(int i=Global.fsPaint.size();i>0;i--)
    	{
    		Global.exploreMap[Global.fsPaint.get(i-1).getX()][Global.fsPaint.get(i-1).getY()]=-7;
    	}
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
   
 
 
   //check if left 3 grids are empty
   
   
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
 
 public int max(int num1, int num2){
	 if (num1 > num2)
		 return num1;
	 else
		 return num2;
 }
 
 public int min(int num1, int num2){
	 if (num1 < num2)
		 return num1;
	 else
		 return num2;
 }
 
 public boolean mapLeft3RowsExplored(){
	 int maxObsY = -1, maxExpY = -1;
	 boolean noObstacle = false, noUnexploredGrid = false;;
     int obstacleY1 = -1, obstacleY2 = -1, obstacleY3 = -1; //store value of Y for row1, 2, 3 where obstacle is found
     int unexploredY1 = -1, unexploredY2 = -1, unexploredY3 = -1; //store value of Y for row1, 2, 3 where unexplored grid is found
     
		if (Global.currCY > 5) {
			for (int j = -1; j < 2; j++) {
				for (int i = Global.currCY - 2; i > 3; i--) {
					if ((Global.exploreMap[Global.currCX + j][i] == 1) && (Global.robotMap[Global.currCX + j][i] >= 1)) // obstacle found
					{
						if (j == -1)
							obstacleY1 = i;
						else if (j == 0)
							obstacleY2 = i;
						else
							obstacleY3 = i;
						break;
					} 
					else if (Global.exploreMap[Global.currCX + j][i] == 0) // grid unexplored
					{
						if (j == -1)
							unexploredY1 = i;
						else if (j == 0)
							unexploredY2 = i;
						else
							unexploredY3 = i;
						break;
					}
				}
			}
			
			maxObsY = max(obstacleY1, obstacleY2);
			maxObsY = max(maxObsY, obstacleY3);
			if (maxObsY == -1)
				noObstacle = true;
			System.out.println("maxObsY: " + maxObsY);
			maxExpY = max(unexploredY1, unexploredY2);
			maxExpY = max(maxExpY, unexploredY3);
			if (maxExpY == -1)
				noUnexploredGrid = true;
			System.out.println("maxExpY: " + maxExpY);
			
			if (noUnexploredGrid || (!noObstacle && !noUnexploredGrid && ((maxObsY - maxExpY) > 2)))
				return true;
			else
				return false;
		}
		else 
			return true;
		
}
	 
 public boolean mapRight3RowsExplored(){
	 int minObsY = 15, minExpY = 15;
	 boolean noObstacle = false, noUnexploredGrid = false;;
     int obstacleY1 = 15, obstacleY2 = 15, obstacleY3 = 15; //store value of Y for row1, 2, 3 where obstacle is found
     int unexploredY1 = 15, unexploredY2 = 15, unexploredY3 = 15; //store value of Y for row1, 2, 3 where unexplored grid is found
     
		if (Global.currCY < 13) {
			for (int j = -1; j < 2; j++) {
				for (int i = Global.currCY + 2; i < 15; i++) {
					if ((Global.exploreMap[Global.currCX + j][i] == 1) && (Global.robotMap[Global.currCX + j][i] >= 1)) // obstacle found
					{
						if (j == -1)
							obstacleY1 = i;
						else if (j == 0)
							obstacleY2 = i;
						else
							obstacleY3 = i;
						break;
					}

					else if (Global.exploreMap[Global.currCX + j][i] == 0) // grid unexplored
					{
						if (j == -1)
							unexploredY1 = i;
						else if (j == 0)
							unexploredY2 = i;
						else
							unexploredY3 = i;
						break;
					}
				}
			}
			minObsY = min(obstacleY1, obstacleY2);
			minObsY = min(minObsY, obstacleY3);
			if (minObsY == 15)
				noObstacle = true;
			
			minExpY = min(unexploredY1, unexploredY2);
			minExpY = min(minExpY, unexploredY3);
			if (minExpY == 15)
				noUnexploredGrid = true;
			
			if ((noUnexploredGrid) || (!noObstacle && !noUnexploredGrid && ((minObsY - minExpY) > -2)))
				return true;
			else
				return false;
		}
		else 
			return true;
	}
	 
 public boolean mapLeftRow1Explored(){
	  boolean row1Explored = true;
	  if (Global.currCY > 5){
		  for (int i = Global.currCY - 2; i > 3; i--){  
			  if ((Global.exploreMap[Global.currCX - 1][i] == 1) && (Global.robotMap[Global.currCX - 1][i] >= 1))
				  break;
			  else if (Global.exploreMap[Global.currCX - 1][i] == 0) //grid unexplored
      	 	  {
				  row1Explored = false;
  				  break;
      	 	  }
  		  }
 	  }
	  return row1Explored;
  }

public boolean mapLeftRow2Explored(){
  boolean row2Explored = true;
 if (Global.currCY > 5){
	  for (int i = Global.currCY - 2; i > 3; i--){   			
		  if (Global.robotMap[Global.currCX][i] >= 1)
			  break;
		  else if (Global.exploreMap[Global.currCX][i] == 0) //grid unexplored
  	 	  {
			  row2Explored = false;
				  break;
  	 	  }
		  }
	  }
 return row2Explored;
}

public boolean mapLeftRow3Explored(){
 boolean row3Explored = true;
 if (Global.currCY > 5){
	  for (int i = Global.currCY - 2; i > 3; i--){   			
		  if (Global.robotMap[Global.currCX + 1][i] == 1)
			  break;
		  else if (Global.exploreMap[Global.currCX + 1][i] == 0) //grid unexplored
  	 	  {
			  row3Explored = false;
				  break;
  	 	  }
		  }
	  }
 return row3Explored;
}
  
public boolean mapRightRow1Explored(){
	  boolean row1Explored = true;
	  if (Global.currCY < 13){
		  for (int i = Global.currCY + 2; i < 15; i++){   			
			  if (Global.robotMap[Global.currCX - 1][i] == 1)
				  break;
			  else if (Global.exploreMap[Global.currCX - 1][i] == 0) //grid unexplored
      	 	  {
				  row1Explored = false;
  				  break;
      	 	  }
  		  }
 	  }
	  return row1Explored;
  }

public boolean mapRightRow2Explored(){
  boolean row2Explored = true;
 if (Global.currCY < 13){
	  for (int i = Global.currCY + 2; i < 15; i++){   			
		  if (Global.robotMap[Global.currCX][i] == 1)
			  break;
		  else if (Global.exploreMap[Global.currCX][i] == 0) //grid unexplored
  	 	  {
			  row2Explored = false;
				  break;
  	 	  }
		  }
	  }
 return row2Explored;
}

public boolean mapRightRow3Explored(){
 boolean row3Explored = true;
 if (Global.currCY < 13){
	  for (int i = Global.currCY + 2; i < 15; i++){   			
		  if (Global.exploreMap[Global.currCX + 1][i] == 0) //grid unexplored
  	 	  {
			  row3Explored = false;
				  break;
  	 	  }
		  }
	  }
 return row3Explored;
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
			//@Override
			public void run() {
				char ori = ' ';
				char lastOri = ' ';
				int minX = 0, rightMinX = 0;
				int count=1;
				int pathCX = -1, pathCY = -1;     //turning point to go down
				boolean downRightIsUnexplored = false, upRightUnexplored = false, rightRightUnexplored = false, middleUnexplored = false;
				boolean reachTop = false, reachGoal = false, stop = false;
				boolean anotherPath = false;
				boolean mapLeftBlocked = false;   //after reaching goal, dead end found when facing left
				boolean leftFrontBlocked = false;
				boolean cave = false;             //before reaching goal, cave found
				boolean caveOnBottomLeft = false;
				boolean caveOnLeftWallRight = false;
				int caveOnBottomLeftX = 0;
				//mapLeftBlocked2 = false
				boolean turnBack = false, turnBack2 = false;
				boolean mapRightBlocked = false;  //before reaching goal, dead end found when facing right
							
				Global.timeLimit = Integer.parseInt(timeTextF.getText());
				if (!coverageTextF.getText().equals("-"))
					Global.coverageLimit = Integer.parseInt(coverageTextF.getText());
				Global.steps = Integer.parseInt(speedTextF.getText());
				
				System.out.println("time limit: " + Global.timeLimit);
				System.out.println("coverage limit: " + Global.coverageLimit);
				System.out.println("steps/s: " + Global.steps);
				long startComputeTime = System.nanoTime();
				
				setRobot();
				
				try {
					robot.senseEnvironment();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				while (!stop ) 
				{
				//	count++;
					lastOri = ori;
					
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
								if (!reachGoal)
								{
									if (leftUnexplored(ori))
										turnLeft();
									
									else if ((lastOri != 'R') && leftEmpty(ori))  //left is sensed but not really explored
										turnLeft();
									else
										moveForward();		
								}
								//reached goal
								else
								{
									if (caveOnBottomLeft && leftEmpty(ori) && (Global.currCX < caveOnBottomLeftX))
										turnLeft();
										
									else if (caveOnBottomLeft)
									{
										moveForward();
									}
									
									else if (!mapFront3ColumnsExplored())  //robot does not explore middle area of arena
									{
										moveForward();
										middleUnexplored = true;
										mapLeftBlocked = false;
									}
										
									else if (leftUnexplored(ori))
										turnLeft();
													
									else if (mapLeftBlocked && leftEmpty(ori) && !middleUnexplored && (Global.currCX < minX)) //up->left, just came out from a dead end on left, going left
										turnLeft();
									
									else if (middleUnexplored && mapLeftRow1Explored() && mapLeftRow2Explored() && mapLeftRow3Explored() && mapRightRow1Explored() && mapRightRow2Explored() && mapRightRow3Explored())  //both sides are explored when moving up into to middle area of arena 
										turnLeft();
									else
										moveForward();
								}
							}
							
							//front is blocked
							else
							{
								if (!reachGoal && !leftEmpty(ori))
								{
									leftFrontBlocked = true;
									turnRight();
								}
								
								else if (caveOnBottomLeft && leftEmpty(ori) && (Global.currCX < caveOnBottomLeftX))
								{
									turnLeft();
								}
								
								else if (Global.currCY == 1)  //at left wall
									turnRight();
								
								else if (Global.currCY == 13)  //at right wall
									turnLeft();
								
								else if (mapLeftBlocked && !leftEmpty(ori))
								{
									caveOnBottomLeft = true;
									caveOnBottomLeftX = Global.currCX;
									turnRight();
								}
								else if (mapLeftBlocked && (Global.currCX < minX) && !middleUnexplored && leftEmpty(ori)) //came out from a dead end on left, going left
									turnLeft();
								
								else if (middleUnexplored && mapLeftRow1Explored() && mapLeftRow2Explored() && mapLeftRow3Explored() && mapRightRow1Explored() && mapRightRow2Explored() &&mapRightRow3Explored())  //both sides are explored when moving up into to middle area of arena 
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
							if (cave && leftEmpty(ori))
							{
								turnLeft();
							}
							else if ((Global.currCY != 1) && (Global.exploreMap[Global.currCX - 1][Global.currCY - 2] == 0) && (Global.exploreMap[Global.currCX - 1][Global.currCY + 2] == 0)) //top left and right grids unexplored
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
						if ((Global.currCX == pathCX) && (Global.currCY == pathCY) && !caveOnBottomLeft)  //come back from right, going down from the first 3 empty grids counted from left
							turnRight();
						
						// not at right wall
						else if (Global.currCY != 13)
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
									if (caveOnBottomLeft){
										if (leftEmpty(ori))  //cave on left
											turnLeft();
										else
											moveForward();
									}
									else if (leftUnexplored(ori))
				                        turnLeft();
									else if ((Global.currCX == 18) && mapRight3RowsExplored())
										turnRight();
//									else if (turnBack)
//										moveForward(pathCY - Global.currCY);
									else
										moveForward();
										
								}
							}
							//front is blocked
							else
							{
//								if (leftFrontBlocked) //before reaching goal (at left wall)
//								{
//									caveOnLeftWallRight = true;
//									turnRight();
//								}
								if (caveOnBottomLeft && leftEmpty(ori))
									turnLeft();
								
								else if ((Global.currCX == 1) || (Global.currCX == 18) || !leftEmpty(ori))  //at top wall, or bottom wall, or left n front blocked
								{
									turnRight();
									mapRightBlocked = true;
									rightMinX = Global.currCX;
								}
								
								else if (!mapRightBlocked && (leftUnexplored(ori) || leftEmpty(ori)))  //robot left unexplored
			                        turnLeft(); 
								else 
									turnRight();
							} 
						}
						//at right wall
						else if (Global.currCY == 13)
						{
							if (Global.currCX == 1)  //at goal zone
								turnRight();
								
							else if (caveOnBottomLeft && leftEmpty(ori))
								turnLeft();
							
							else if (mapLeftBlocked && leftEmpty(ori)) //dead end on left
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
						if (reachGoal && leftEmpty(ori) && !anotherPath && !mapLeftBlocked)
						{
							anotherPath = true;
							pathCX = Global.currCX;
							pathCY = Global.currCY;
						}
						
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
									
									else if (mapRightBlocked && !leftEmpty(ori))  //come out from dead end on right
										moveForward();
									
									else if (mapRightBlocked && leftEmpty(ori))   //come out from dead end on right, going down to next X
									{
										turnLeft();
									}
									
									else if ((reachTop) && (lastOri == 'U'))  //not came from down
										moveForward();
									
									else if ((reachTop) && (lastOri != 'D') && (Global.robotMap[Global.currCX + 2][Global.currCY + 1] == 0) && (Global.robotMap[Global.currCX + 2][Global.currCY] == 0) && (Global.robotMap[Global.currCX + 2][Global.currCY - 1] == 0))
										turnLeft();

									else if ((Global.currCX == 1) && mapLeft3RowsExplored())
										
										turnLeft();
									else
										moveForward();
			                    }
								//reached goal
								else
								{
									if (caveOnBottomLeft && (Global.currCX < caveOnBottomLeftX))
									{
										moveForward();
										caveOnBottomLeft = false;
										mapLeftBlocked = false;
									}
									
									else if ((Global.currCX < 3) && leftEmpty(ori))  //at top wall
										turnLeft();
									
									else if ((Global.currCY == 13) && rightRightUnexplored)  //at right wall
									{
										rightRightUnexplored = false;
										turnLeft();
									}
									else if ((Global.currCX > 3) && (Global.currCY == 5) && (Global.exploreMap[Global.currCX - 2][Global.currCY - 1] == 0)) //top right grid unexplored at turning point(y=5)
										turnRight();
									
									else if ((Global.currCY < 6) && (Global.currCX != 18) && anotherPath) //turn back to right wall
									{
										turnLeft();
										turnBack = true;
										//
										if (middleUnexplored)
											middleUnexplored = false;
									}
									else if (leftEmpty(ori) && mapLeftBlocked && (Global.currCX < minX) && !middleUnexplored) //up->left->move, just come out from left dead end, moving forward
									{
										mapLeftBlocked = false;
										anotherPath = false;
										moveForward();
									}
//									else if (leftEmpty(ori) && !mapLeftBlocked) //come back from left, going down
//									{
//										turnLeft();
//									}
									else if ((Global.currCY > 4) && leftEmpty(ori) && !anotherPath && !middleUnexplored) //come out from left dead end, going down
									{
										turnLeft();	
									}
									
									else
									{
										moveForward();
									}
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
								
								else if (reachGoal && (Global.currCY > 4) && (Global.currCX != 18) && anotherPath) //meet obstacle before/when reaching turning point(y=5)
								{
									turnLeft();
									turnBack = true;
									if (middleUnexplored)
										middleUnexplored = false;
								}
								
								else if (reachGoal && (Global.currCY > 4) && (Global.currCX != 18) && middleUnexplored) //meet obstacle before/when reaching turning point(y=5)
								{
									turnLeft();
									turnBack = true;
									if (middleUnexplored)
										middleUnexplored = false;
								}
								else if (reachGoal && turnBack2 && leftEmpty(ori)) //come back from right, going down
								{
									turnLeft();
									turnBack2 = false;
								}
								else
								{
//									if (!rightUnexplored(ori) && leftEmpty(ori))
//										turnLeft();
//									
//									else 
									if (anotherPath)
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
						if ((Global.currCX == pathCX) && (Global.currCY == pathCY)) //after reach goal, at the entering point of 'anotherPath'
						{
							moveForward();
							turnBack = false;
							anotherPath = false;
						}
						
						//not at bottom wall
						else if (Global.currFX != 19)
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
										//if (caveOnLeftWallRight)
										if (mapRightBlocked && leftEmpty(ori) && (Global.currCX > rightMinX))
										{	
											turnLeft();
											mapRightBlocked = false;
										}
											
										
//										else if (mapRightBlocked)
//											moveForward();
//										
										else if (leftUnexplored(ori))
											turnLeft();
										
										else if ((lastOri != 'L') && (leftEmpty(ori)) && !mapRightBlocked)  //not came out from our right side
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
										}
										else if (!mapRightRow1Explored())  //got left first since will turn back later
											turnLeft();
										
										else if (!mapLeftRow1Explored())
											turnRight();
										
										else  //both sides explored
											moveForward();
									}
								}
								//at right wall
								else if (Global.currCY == 13)
								{
									if (!mapLeftRow1Explored())  //map left row 1 unexplored
									{
										turnRight();
									}
									else
									{	
										moveForward();
//										if (turnBack2)
//											turnBack2 = false;
									}
								}
							}
							//front is blocked							
							else
							{
								if (!reachGoal)
								{
									if (Global.currCY == 1)  //top left corner
										turnLeft();
									
									else if (mapRightBlocked && leftEmpty(ori))
									{	
										turnLeft();
										mapRightBlocked = false;
									}
									else if (leftUnexplored(ori))
										turnLeft();
									
//									else if (!leftEmpty(ori))
//										turnRight();
									else 
										turnRight();
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
									
									else if (!mapRight3RowsExplored())
										turnLeft();
									
									else if (!mapLeft3RowsExplored())
										turnRight();
									
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
					try {sleep(1000/Global.steps);} 
					catch (InterruptedException ex) {}
					
					if (Global.currCX == 1)
						reachTop = true;

					if ((Global.currCX == 1) && (Global.currCY == 13))
						reachGoal = true;
					
					if (reachGoal && (Global.currCX == 18) && (Global.currCY == 1) && (robot.checkOri() != 'U'))
					{
						char o = robot.checkOri();
						if (o == 'L')
							turnRight();
						else if (o == 'D')
						{
							turnLeft();
						}
						else if (o == 'R')
							turnLeft();
						
						try {sleep(1000/Global.steps);} 
						catch (InterruptedException ex) {}
					}
					
					if (reachGoal && (Global.currCX == 18) && (Global.currCY == 1) && (robot.checkOri() == 'U'))
					{
						System.out.println("Exploration finish!");
						break;
					}
					
					long time = (System.nanoTime() - startComputeTime)/1000000000;
//					System.out.println("time: " + time + "second");
//					System.out.println("coverage: " + computeCoverage() + "%");
					timeDisplayLabel.setText(String.valueOf(Global.timeLimit - time));
					coverageDisplayLabel.setText(String.valueOf(computeCoverage()));
					
					//if (time >= 330)
						//System.out.println((360-time) + "seconds left!!");
				        //return back to start point 
					
					if ((computeCoverage() >= Global.coverageLimit) || (time >= Global.timeLimit))
						stop = true;
					
				} // end of while loop
				
				Descriptor.generateExploreMap();
				Descriptor.generateObstacleMap();
			} // end of run()
		};  // end of thread
		t.start();
	} // end of explore()
	

	
	public int computeCoverage(){
		int explored = 0;
		
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 15; j++) {
				if (Global.exploreMap[i][j] == 1) 
					explored++; 
			}
		}
		return (explored*100/300);
	}
	public void move(String howToMove)
	{
		if(howToMove.equals(Global.moveForward)){moveForward();};
	}
}