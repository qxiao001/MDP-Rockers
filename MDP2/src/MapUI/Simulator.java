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

//import testing.FSP;

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
			String str=f.iLoveToMoveIt();
			paintFsPath();
			paintRobotMap();
			
			String fastestPathstr=f.oneString(str);
			moveForFastestPath(str);
			f.sendMoveByMove(fastestPathstr);
			
			
			
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
					
						case "F010":moveForward1();break;
						case "R000":turnRight1();break;
						case "L000":turnLeft1();break;
						
					}
					//delay
					try {sleep(1000);} 
					catch (InterruptedException ex) {}
					
				}
			} // end of run()
		};  // end of thread
		t.start();
	}

	public void moveForward(int steps) {
		if(Global.realRun==true){
			try {
				
				Global.c.mySend("F0"+steps+"0");
				Global.lastSend="F0"+steps+"0";
				System.out.println("I have sent robot to move forward by.");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			//System.out.println("I have sent robot to move forward.");
		}
		for (int i=0;i<steps;i++)
		{
			int x = Global.currFX - Global.currCX;
			int y = Global.currFY - Global.currCY;
			Global.currCX = Global.currCX + x;
			Global.currCY = Global.currCY + y;
			Global.currFX = Global.currFX + x;
			Global.currFY = Global.currFY + y;
			clearRobot();
			//paintRobotMap();
			setRobot();
		}
		if(!Global.fastestPath){
			try {
				
				robot.senseEnvironment();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		paintRobotMap();
		//clearRobot();
		setRobot();
			//Global.readTimerstart=System.nanoTime();
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
		//clearRobot();
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
		//clearRobot();
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
		//clearRobot();
		paintRobotMap();
		setRobot();
		//Global.readTimerstart=System.nanoTime();
	}
	
public void moveForward1() {
		
		int x = Global.currFX - Global.currCX;
		int y = Global.currFY - Global.currCY;
		Global.currCX = Global.currCX + x;
		Global.currCY = Global.currCY + y;
		Global.currFX = Global.currFX + x;
		Global.currFY = Global.currFY + y;
	
		
		clearRobot();
		paintRobotMap();
		setRobot();
		//Global.readTimerstart=System.nanoTime();
	}
	public void turnRight1() {
		
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
		
		clearRobot();
		paintRobotMap();
		setRobot();

	}

	public void turnLeft1() {
		
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
		
		clearRobot();
		paintRobotMap();
		setRobot();
		
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
    
	/******************************************** Exploration ******************************************/
	public void explore() {
		Thread t = new Thread() {
			//@Override
			public void run() {
				char ori = ' ';
				char lastOri = ' ';
				int minX = 0, rightMinX = 0;
				int adjustCount = 0;
				int pathCX = -1, pathCY = -1;     //turning point to go down
				int faceLeftFrontLeftBlockedY = 15, faceRightFrontLeftBlockedY = 15, faceUpFrontLeftBlockedX = 0, faceDownFrontLeftBlockedY = 0; 
				int middleUnexploredY = 0;
				boolean downRightIsUnexplored = false, upRightUnexplored = false, rightRightUnexplored = false, middleUnexplored = false;
				boolean reachTop = false, reachGoal = false, stop = false;
				boolean anotherPath = false;
				
				boolean faceLeftFrontLeftBlocked = false;   //after reaching goal, dead end found when facing left
				boolean faceRightFrontLeftBlocked = false;  //before reaching goal, dead end found when facing right
				boolean faceUpFrontLeftBlocked = false;
				boolean faceDownFrontLeftBlocked = false;
				
				//boolean leftAndFrontBlocked = false;
				//boolean cave = false;             //before reaching goal, cave found
				boolean caveOnBottomLeft = false; int caveOnBottomLeftX = 0;
				boolean caveOnLeftWallRight = false; int caveOnLeftWallRightY = 0;
				boolean caveOnTopRight = false; int caveOnTopRightX = 0;
				boolean upFrontBlocked = false;
				
				//mapLeftBlocked2 = false
				boolean turnBack = false, turnBack2 = false;
							
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
					lastOri = ori;
					ori = robot.checkOri();
					adjustCount = 0;
					/******************************************************** Adjust Calibration ********************************************/
					if(Global.realRun==true){
						while (adjustCount < 1){
							
							if (!frontEmpty(ori) && (!rightEmpty(ori) || !leftEmpty(ori))){
								try {
									Global.c.mySend(Global.adjust);
									Global.lastSend=Global.adjust;
									adjustCount++;
									
									System.out.println("I have sent robot to adjust calibration.");
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
									adjustCount=0;
								} 
							}
							else
								adjustCount=1;
						}
					}
					
					/******************************************************** Facing Up *****************************************************/
					if (ori == 'U') 
					{						
						//not at top wall
						if (Global.currFX != 0)
						{	
							
							//front is empty
							if (frontEmpty(ori))
							{
								if (!reachGoal)
								{
									
//									if (leftUnexplored(ori))
//										turnLeft();

									if (leftUnexplored(ori) || (leftEmpty(ori) && !mapLeft3RowsExplored(reachGoal)))
									{
										turnLeft();
										if (caveOnLeftWallRight) {caveOnLeftWallRight = false;}
									}
									
//									else if ((Global.currCY >= 2) && (Global.currCX == 1) && ((Global.exploreMap[Global.currCX-1][Global.currCY-2] == 0) || (Global.exploreMap[Global.currCX][Global.currCY-2] == 0)))
//										turnLeft();
//									else if ((lastOri != 'R') && leftEmpty(ori))  //left is sensed but not really explored
//										turnLeft();
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
										middleUnexploredY = Global.currCY;
										faceLeftFrontLeftBlocked = false;
									}
										
									else if (leftUnexplored(ori))
										turnLeft();
													
									else if (faceLeftFrontLeftBlocked && leftEmpty(ori) && !middleUnexplored && (Global.currCX < minX)) //up->left, just came out from a dead end on left, going left
										turnLeft();
									
									else if (middleUnexplored && mapLeft3RowsExplored(reachGoal) && mapRight3RowsExplored())  //both sides are explored when moving up into to middle area of arena 
										{
										turnLeft();
										System.out.println("???????????????????????????");
										}
									else
										moveForward();
								}
							}
							
							//front is blocked
							else
							{
								upFrontBlocked = true;
								
								if (!reachGoal && !mapLeft3RowsExplored(reachGoal))
									turnLeft();
								
								else if (!mapRight3RowsExplored())
									turnRight();
								
								else if (faceLeftFrontLeftBlocked && (leftEmpty(ori)))
									turnLeft();
								
//								else if (!reachGoal && !leftEmpty(ori))
//								{
//									leftAndFrontBlocked = true;
//									turnRight();
//								}
								
								else if (caveOnBottomLeft && leftEmpty(ori) && (Global.currCX < caveOnBottomLeftX))
								{
									turnLeft();
								}
								
								else if (Global.currCY == 1)  //at left wall
								{
									turnRight();
									
								}
								
								else if (Global.currCY == 13)  //at right wall
								{
									turnLeft();
								}
								
								else if (faceLeftFrontLeftBlocked && !leftEmpty(ori))
								{
									caveOnBottomLeft = true;
									faceLeftFrontLeftBlocked = false;
									caveOnBottomLeftX = Global.currCX;
									turnRight();
								}
								else if (faceLeftFrontLeftBlocked && (Global.currCX < minX) && !middleUnexplored && leftEmpty(ori)) //came out from a dead end on left, going left
									turnLeft();
								
								else if (middleUnexplored && mapLeft3RowsExplored(reachGoal) && mapRight3RowsExplored())  //both sides are explored when moving up into to middle area of arena 
									turnLeft();
									
								else if (leftUnexplored(ori)) //before reaching goal
									turnLeft();
								
								else if ((Global.currCY != 1) && (Global.exploreMap[Global.currCX - 1][Global.currCY - 2] == 0)) //top left grid unexplored
									turnLeft();
								else
								{
									if (!reachGoal)
									{
										turnRight();
									}
									
									else
									{
										turnLeft();
									}
										
									
								}	
							}
						}
						//at top wall
						else
						{
//							if (cave && leftEmpty(ori))
//							{
//								turnLeft();
//							}
							
							if (!reachGoal && !mapLeft3RowsExplored(reachGoal))
								turnLeft();
							
							else if (!mapRight3RowsExplored())
								turnRight();
							
							else if ((Global.currCY != 1) && (Global.exploreMap[Global.currCX - 1][Global.currCY - 2] == 0) && (Global.exploreMap[Global.currCX - 1][Global.currCY + 2] == 0)) //top left and right grids unexplored
							{
								turnLeft();
								upRightUnexplored = true;
							}
							else 
							{
								turnRight();
							}
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
							if (frontEmpty(ori))
		   					{
								if (!reachGoal)
								{
									if (leftUnexplored(ori))
				                        turnLeft();
									else if (caveOnLeftWallRight && !leftEmpty(ori))
										moveForward();
									
									else if (caveOnLeftWallRight && leftEmpty(ori) && (Global.currCY > caveOnLeftWallRightY))
									{
										turnLeft();
									}
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
							{ //***
//								if (leftAndFrontBlocked) //before reaching goal (at left wall)
//								{
//									turnRight();
//								}
								if (!leftEmpty(ori))
									faceRightFrontLeftBlocked = true;
								
								if (middleUnexplored && (Global.currCY == middleUnexploredY))
								{
									middleUnexplored = false;
									turnRight();
								}
//								else if (upFrontBlocked && !leftEmpty(ori))
//								{
//									caveOnLeftWallRight = true;
//									turnRight();
//									caveOnLeftWallRightY = Global.currCY;
//								}
								
								else if (reachGoal && (Global.currCX == 18) && (Global.currCY == 1))
									turnLeft();
								
								else if (caveOnBottomLeft && leftEmpty(ori))
									turnLeft();
								
								else if ((Global.currCX == 1) || (Global.currCX == 18) || !leftEmpty(ori))  //at top wall, or bottom wall, or left n front blocked
								{
									turnRight();
									faceRightFrontLeftBlocked = true;
									rightMinX = Global.currCX;
								}
								
								else if (!faceRightFrontLeftBlocked && (leftUnexplored(ori) || (leftEmpty(ori) && !mapLeft3RowsExplored(reachGoal))))  //robot left unexplored
			                        turnLeft(); 
								else
								{
									turnRight();
								}
									
							} 
						}
						//at right wall
						else if (Global.currCY == 13)
						{
							if (Global.currCX == 1)  //at goal zone
							{
								turnRight();
							}
								
							else if (caveOnBottomLeft && leftEmpty(ori))
								turnLeft();
							
							else if (faceLeftFrontLeftBlocked && leftEmpty(ori)) //dead end on left
								turnLeft();
										
							else if ((Global.currCX != 18) && (Global.exploreMap[Global.currCX - 2][Global.currCY + 1] == 0) && (Global.exploreMap[Global.currCX + 2][Global.currCY + 1] == 0))
							{
								turnLeft();
								rightRightUnexplored = true;
							}
							else if (Global.exploreMap[Global.currCX - 2][Global.currCY + 1] == 0)
								turnLeft();
							else
							{
								turnRight();
							}
						}
					} // end of 'R'
					
					/*************************************************** Facing Left ********************************************/

					else if (ori == 'L') 
					{ 			
						if (reachGoal && leftEmpty(ori) && !anotherPath && !faceLeftFrontLeftBlocked)  
						{
							anotherPath = true;
							pathCX = Global.currCX;
							pathCY = Global.currCY;
						}
						
						//not at left wall
						if (Global.currCY != 1)
						{
							if (caveOnTopRight && leftEmpty(ori))   //come out from dead end on right, going down to next X
							{
								turnLeft();
							}
							
							//the front is empty
							else if (frontEmpty(ori))
							{
								if (!reachGoal)
			                    {
									if (caveOnTopRight){
										if (!leftEmpty(ori))
											moveForward();
										else
											turnLeft();
									}
									else if (leftUnexplored(ori))
										turnLeft(); 
									else if (!mapLeft3RowsExplored(reachGoal))
										moveForward();
									
									else if ((Global.currCY == 1) && (Global.exploreMap[Global.currCX + 2][Global.currCY - 1] == 0))
										turnLeft();
									
									else if (faceRightFrontLeftBlocked && !leftEmpty(ori))  //come out from dead end on right
										moveForward();
									
									
									
									else if ((reachTop) && (lastOri == 'U'))  //not came from down
										moveForward();
									
									else if ((reachTop) && (lastOri != 'D') && (Global.robotMap[Global.currCX + 2][Global.currCY + 1] == 0) && (Global.robotMap[Global.currCX + 2][Global.currCY] == 0) && (Global.robotMap[Global.currCX + 2][Global.currCY - 1] == 0))
										turnLeft();

									else if ((Global.currCX == 1) && mapLeft3RowsExplored(reachGoal))
										
										turnLeft();
									else
										moveForward();
			                    }
								//reached goal
								else
								{
									if (!mapFrontColumn3Explored() && !mapFront3ColumnsExplored()){  //when robot has reached the bottom, middle area has not been explored
										turnRight();
										middleUnexplored = true;
										middleUnexploredY = Global.currCY;
									}
									else if (caveOnBottomLeft && (Global.currCY >= faceLeftFrontLeftBlockedY))
									{
										moveForward();
										caveOnBottomLeft = false;
										anotherPath = false;
										//mapLeftBlocked = false;
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
									else if (leftEmpty(ori) && faceLeftFrontLeftBlocked && (Global.currCY >= faceLeftFrontLeftBlockedY) && !middleUnexplored) //up->left->move, just come out from left dead end, moving forward
									{
										faceLeftFrontLeftBlocked = false;
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
										faceLeftFrontLeftBlockedY = 15;
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
								if (!leftEmpty(ori)){
									faceLeftFrontLeftBlocked = true;
									faceLeftFrontLeftBlockedY = Global.currCY;
								}
								
								if (!mapFrontColumn3Explored() && !mapFront3ColumnsExplored()){  //when robot has reached the bottom, middle area has not been explored
									turnRight();
									middleUnexplored = true;
								}
								else if (reachGoal && ((Global.currCX == 18) || (!leftEmpty(ori))))  //at bottom
								{
									turnRight();
									faceLeftFrontLeftBlocked = true;
									minX = Global.currCX + 1;
								}
								else if (Global.currCX == 1)  //at top wall
								{
									turnLeft();
								}
								
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
										faceLeftFrontLeftBlocked = true;
										minX = Global.currCX + 1;
									}
									else if (!leftEmpty(ori) && !rightEmpty(ori))
									{
										faceLeftFrontLeftBlocked = true;
										minX = Global.currCX + 1;
										turnLeft();
									}
									//not confirm
									else
									{
										turnRight();
									}
								}
							}
						}
						//at left wall 
						else
						{
							if ((Global.currCX == 18) && reachGoal)
								turnRight();
							
							else if ((Global.currCX == 1) && leftEmpty(ori)) //at top wall
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
							//**********************
							else if ((caveOnLeftWallRight || (caveOnTopRight && (Global.currCX > caveOnTopRightX))) && leftEmpty(ori))
							{
								turnLeft();
							}
							
							//front is empty
							else if (frontEmpty(ori))
							{
								
								//not at right wall
								if (Global.currCY != 13)
								{
									if ((caveOnLeftWallRight || caveOnTopRight) && !leftEmpty(ori))
										moveForward();
									
									
									
									else if (!reachGoal)
									{
										//if (caveOnLeftWallRight)
										if (faceRightFrontLeftBlocked && leftEmpty(ori) && (Global.currCX > rightMinX))
										{	
											turnLeft();
											faceRightFrontLeftBlocked = false;
										}
											
//										else if (!mapLeft3RowsExplored(reachGoal))
//											turnRight();
//										
										else if ((Global.currCX == 1) && !mapRight3RowsExplored())
											turnLeft();
//										else if (mapRightBlocked)
//											moveForward();
//										
										else if (leftUnexplored(ori))
											turnLeft();
										
										else if ((lastOri != 'L') && (leftEmpty(ori)) && !faceRightFrontLeftBlocked)  //not came out from our right side
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
										else if (!mapRightRow1Explored())
											turnLeft();
//										else if (!mapRight3RowsExplored())  //got left first since will turn back later
//											turnLeft();
										
										else if (!mapLeftRow1Explored()){
											if (!mapRight3RowsExplored())
												turnLeft();
											else
												turnRight();
										}
										else  //both sides explored
											moveForward();
									}
								}
								//at right wall
								else if (Global.currCY == 13)
								{
									if (!mapLeftRow1Explored()) 
										turnRight();
										
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
								if (upFrontBlocked && faceRightFrontLeftBlocked && !leftEmpty(ori)){
									caveOnTopRight = true;
									upFrontBlocked = false;
									faceRightFrontLeftBlocked = false;
									caveOnTopRightX = Global.currCX;
									turnRight();
								}
								else if (!reachGoal)
								{
									if (Global.currCY == 1)  //top left corner
									{
										turnLeft();
									}
									
									else if (faceRightFrontLeftBlocked && leftEmpty(ori))
									{	
										faceRightFrontLeftBlocked = false;
										turnLeft();
									}
									else if (!mapRight3RowsExplored())
										turnLeft();
									
//									else if (!leftEmpty(ori))
//										turnRight();
									else 
									{
										turnRight();
									}
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
									{
										turnRight();
									}
									
									else if (!mapRight3RowsExplored())
										turnLeft();
									
									else if (!mapLeft3RowsExplored(reachGoal))
										turnRight();
									
									else if (!rightEmpty(ori))
									{
										turnLeft();
									}
									else
									{
										turnRight();
									}
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
							{
								turnLeft();
							}
							
							else if ((Global.currCX == 18) && (Global.currCY == 13))  //at bottom right corner
							{
								turnRight();
							}
							
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
							{
								turnRight();
							}
						}
					} // end if 'D' 
							
					//delay
					if (!Global.realRun){
						try {sleep(1000/Global.steps);} 
						catch (InterruptedException ex) {}
					}
					
					if (Global.currCX == 1)
						reachTop = true;

					if ((Global.currCX == 1) && (Global.currCY == 13))
						reachGoal = true;
					
//					if (reachGoal && (Global.currCX == 18) && (Global.currCY == 1) && (robot.checkOri() != 'U'))
//					{
//						char o = robot.checkOri();
//						if (o == 'L')
//							turnRight();
//						else if (o == 'D')
//							turnLeft();
//						else if (o == 'R')
//							turnLeft();
//						if (!Global.realRun){
//							try {sleep(1000/Global.steps);} 
//							catch (InterruptedException ex) {}
//						}
//					}
					
					if (reachGoal && (Global.currCX == 18) && (Global.currCY == 1) && (robot.checkOri() == 'U'))
					{
						System.out.println("Exploration finish!");
						break;
					}
					
					long time = (System.nanoTime() - startComputeTime)/1000000000;
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
	
    /******************************************** End of EXPLORE ***********************************************/
    
	//************************************ return TRUE if map left 3 rows are explored ********************************
 
	public boolean mapLeft3RowsExplored(boolean reachGoal) {
		int x = 3, y = 5;
		if (!reachGoal) {
			x = -1;
			y = 1;
		}
	 
		 int maxObsY = -1, maxExpY = -1;
		 boolean noObstacle = false, noUnexploredGrid = false;;
	     int obstacleY1 = -1, obstacleY2 = -1, obstacleY3 = -1; //store value of Y for row1, 2, 3 where obstacle is found
	     int unexploredY1 = -1, unexploredY2 = -1, unexploredY3 = -1; //store value of Y for row1, 2, 3 where unexplored grid is found
     
		 if (Global.currCY > y) {
			for (int j = -1; j < 2; j++) {
				for (int i = Global.currCY - 2; i > x; i--) {
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
	 
	// ************************************* return TRUE if map right 3 rows are explored ***************************
	
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
			
			if ((noUnexploredGrid) || (!noObstacle && !noUnexploredGrid && ((minObsY - minExpY) < -2)))
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
		  } return row1Explored;
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
	  } return row2Explored;
	}

	public boolean mapLeftRow3Explored() {
		boolean row3Explored = true;
		if (Global.currCY > 5) {
			for (int i = Global.currCY - 2; i > 3; i--) {
				if (Global.robotMap[Global.currCX + 1][i] == 1)
					break;
				else if (Global.exploreMap[Global.currCX + 1][i] == 0)
				{
					row3Explored = false;
					break;
				}
			}
		} return row3Explored;
    }
  
	public boolean mapRightRow1Explored() {
		boolean row1Explored = true;
		if (Global.currCY < 13) {
			for (int i = Global.currCY + 2; i < 15; i++) {
				if (Global.robotMap[Global.currCX - 1][i] == 1)
					break;
				else if (Global.exploreMap[Global.currCX - 1][i] == 0)
				{
					row1Explored = false;
					break;
				}
			}
		} return row1Explored;
	}

	public boolean mapRightRow2Explored() {
		boolean row2Explored = true;
		if (Global.currCY < 13) {
			for (int i = Global.currCY + 2; i < 15; i++) {
				if (Global.robotMap[Global.currCX][i] == 1)
					break;
				else if (Global.exploreMap[Global.currCX][i] == 0)
				{
					row2Explored = false;
					break;
				}
			}
		} return row2Explored;
	}

	public boolean mapRightRow3Explored() {
		boolean row3Explored = true;
		if (Global.currCY < 13) {
			for (int i = Global.currCY + 2; i < 15; i++) {
				if (Global.exploreMap[Global.currCX + 1][i] == 0) {
					row3Explored = false;
					break;
				}
			}
		} return row3Explored;		
	}

	public boolean mapFrontColumn3Explored() {		
		 boolean column3Explored = true;
			for (int i = Global.currCX - 2; i > -1; i--) {
				if ((Global.exploreMap[i][Global.currCY + 1] == 1) && (Global.robotMap[i][Global.currCY + 1] >= 1)) // obstacle found
					break;
				else if (Global.exploreMap[i][Global.currCY + 1] == 0) // grid unexplored
				{
					column3Explored = false;
					break;
				}
			} return column3Explored;
	}
	
	// ****************************** return TRUE if front 3 columns are explored ****************
	
	public boolean mapFront3ColumnsExplored() {		
		 int maxObsX = -1, maxExpX = -1;
		 boolean noObstacle = false, noUnexploredGrid = false;;
	     int obstacleX1 = -1, obstacleX2 = -1, obstacleX3 = -1; //store value of Y for column 1, 2, 3 where obstacle is found
	     int unexploredX1 = -1, unexploredX2 = -1, unexploredX3 = -1; //store value of Y for column 1, 2, 3 where unexplored grid is found
     
	     if (Global.currCX > 1) {
			for (int j = -1; j < 2; j++) {
				for (int i = Global.currCX - 2; i > -1; i--) {
					if ((Global.exploreMap[i][Global.currCY + j] == 1) && (Global.robotMap[i][Global.currCY + j] >= 1)) // obstacle found
					{
						if (j == -1)
							obstacleX1 = i;
						else if (j == 0)
							obstacleX2 = i;
						else
							obstacleX3 = i;
						break;
					}

					else if (Global.exploreMap[i][Global.currCY + j] == 0) // grid unexplored
					{
						if (j == -1)
							unexploredX1 = i;
						else if (j == 0)
							unexploredX2 = i;
						else
							unexploredX3 = i;
						break;
					}
				}
			}
			maxObsX = max(obstacleX1, obstacleX2);
			maxObsX = max(maxObsX, obstacleX3);
			if (maxObsX == -1)
				noObstacle = true;
			
			maxExpX = max(unexploredX1, unexploredX2);
			maxExpX = max(maxExpX, unexploredX3);
			if (maxExpX == -1)
				noUnexploredGrid = true;
			
			if ((noUnexploredGrid) || (!noObstacle && !noUnexploredGrid && ((maxObsX - maxExpX) > 2)))
				return true;
			else
				return false;
		}
		else 
			return true;
	}

	 //****************************** return TRUE if front 3 grids are empty **********************
	
	 public boolean frontEmpty(char orientation){
		      boolean frontIsEmpty = false;
		      switch (orientation){
		         case 'U':  //not at top wall
		                    if ((Global.currCX != 1) && (Global.robotMap[Global.currCX - 2][Global.currCY + 1] == 0) && (Global.robotMap[Global.currCX - 2][Global.currCY] == 0) && (Global.robotMap[Global.currCX - 2][Global.currCY - 1] == 0))
		                    	frontIsEmpty = true;   
		                    break;
		         case 'R':  //not at right wall
		                    if ((Global.currCY != 13) && (Global.robotMap[Global.currCX - 1][Global.currCY + 2] == 0) && (Global.robotMap[Global.currCX][Global.currCY + 2] == 0) && (Global.robotMap[Global.currCX + 1][Global.currCY + 2] == 0))
		                    	 frontIsEmpty = true;
		                     break;
		         case 'L':  //not at left wall
				        	if ((Global.currCY != 1) && (Global.robotMap[Global.currCX + 1][Global.currCY - 2] == 0) && (Global.robotMap[Global.currCX][Global.currCY - 2] == 0) && (Global.robotMap[Global.currCX - 1][Global.currCY - 2] == 0))
				        		frontIsEmpty = true;
		                    break;
		         case 'D':	//not at bottom wall
				        	if ((Global.currCX != 18) && (Global.robotMap[Global.currCX + 2][Global.currCY - 1] == 0) && (Global.robotMap[Global.currCX + 2][Global.currCY] == 0) && (Global.robotMap[Global.currCX + 2][Global.currCY + 1] == 0))
				        		frontIsEmpty = true;
		                    break;
		      }
		      return frontIsEmpty;
	}
	 
	 //****************************** return TRUE if left 3 grids are empty **********************
	
	 public boolean leftEmpty(char orientation){
		      boolean leftIsEmpty = false;
		      switch (orientation){
		         case 'U':  //not at left wall
		                    if ((Global.currCY != 1) && (Global.robotMap[Global.currCX + 1][Global.currCY - 2] == 0) && (Global.robotMap[Global.currCX][Global.currCY - 2] == 0) && (Global.robotMap[Global.currCX - 1][Global.currCY - 2] == 0))
		                    	leftIsEmpty = true;   
		                    break;
		         case 'R':   //not at top wall
		                     if ((Global.currCX != 1) && (Global.robotMap[Global.currCX - 2][Global.currCY] == 0) && (Global.robotMap[Global.currCX - 2][Global.currCY - 1] == 0) && (Global.robotMap[Global.currCX - 2][Global.currCY + 1] == 0))
		                        leftIsEmpty = true;
		                     break;
		         case 'L':  //not at bottom wall
				        	if ((Global.currCX != 18) && (Global.robotMap[Global.currCX + 2][Global.currCY + 1] == 0) && (Global.robotMap[Global.currCX + 2][Global.currCY] == 0) && (Global.robotMap[Global.currCX + 2][Global.currCY - 1] == 0))
				                leftIsEmpty = true;
		                    break;
		         case 'D':	//not at right wall
				        	if ((Global.currCY != 13) && (Global.robotMap[Global.currCX][Global.currCY + 2] == 0) && (Global.robotMap[Global.currCX - 1][Global.currCY + 2] == 0) && (Global.robotMap[Global.currCX + 1][Global.currCY + 2] == 0))
				                leftIsEmpty = true;
		                    break;
		      }
		      return leftIsEmpty;
	}
	 
	 //***************************** return TRUE if right 3 grids are empty **********************
	 
	 public boolean rightEmpty(char orientation){
		      boolean rightIsEmpty = false;
		      switch (orientation){
		         case 'U':  //not at right wall
		                    if ((Global.currCY != 18) && (Global.robotMap[Global.currCX + 1][Global.currCY + 2] == 0) && (Global.robotMap[Global.currCX][Global.currCY + 2] == 0) && (Global.robotMap[Global.currCX - 1][Global.currCY + 2] == 0))
		                    	rightIsEmpty = true;   
		                    break;
		         case 'R':  //not at bottom wall
		                    if ((Global.currCX != 18) && (Global.robotMap[Global.currCX + 2][Global.currCY] == 0) && (Global.robotMap[Global.currCX + 2][Global.currCY - 1] == 0) && (Global.robotMap[Global.currCX + 2][Global.currCY + 1] == 0))
		                        rightIsEmpty = true;
		                    break;
		         case 'L':  //not at top wall
				        	if ((Global.currCX != 1) && (Global.robotMap[Global.currCX - 2][Global.currCY + 1] == 0) && (Global.robotMap[Global.currCX - 2][Global.currCY] == 0) && (Global.robotMap[Global.currCX - 2][Global.currCY - 1] == 0))
				                rightIsEmpty = true;
		                    break;
		         case 'D':  //not at left wall
				        	if ((Global.currCY != 1) && (Global.robotMap[Global.currCX][Global.currCY - 2] == 0) && (Global.robotMap[Global.currCX - 1][Global.currCY - 2] == 0) && (Global.robotMap[Global.currCX + 1][Global.currCY - 2] == 0))
				                rightIsEmpty = true;
		                    break;
		      }
		      return rightIsEmpty;
	}
	 
	//return TRUE if
    //the top left grid have not yet been explored and
	//the 2 lower grids on the left are empty
   
    /*********************************************************might not needed anymore ************/
    
    public boolean leftUnexplored(char orientation){
        boolean unexplore = false;
        switch (orientation){
           case 'U':   //not at left wall
                       if ((Global.currCY != 1) && (Global.robotMap[Global.currCX + 1][Global.currCY - 2] == 0) && (Global.robotMap[Global.currCX][Global.currCY - 2] == 0) && (Global.exploreMap[Global.currFX][Global.currFY - 2] == 0))
                      	unexplore = true;        
                       break;
           case 'R':   //not at top wall
                       if ((Global.currCX != 1) && (Global.robotMap[Global.currCX - 2][Global.currCY] == 0) && (Global.robotMap[Global.currCX - 2][Global.currCY - 1] == 0) && (Global.exploreMap[Global.currFX - 2][Global.currFY] == 0))
                          unexplore = true;
                       break;
           case 'L':  //not at bottom wall
  		        	if ((Global.currCX != 18) && (Global.robotMap[Global.currCX + 2][Global.currCY + 1] == 0) && (Global.robotMap[Global.currCX + 2][Global.currCY] == 0) && (Global.exploreMap[Global.currFX + 2][Global.currFY] == 0))
  		                unexplore = true;
                      break;
           case 'D':  //not at right wall
          	        if ((Global.currCY != 13) && (Global.robotMap[Global.currCX][Global.currCY + 2] == 0) && (Global.robotMap[Global.currCX - 1][Global.currCY + 2] == 0) && (Global.exploreMap[Global.currFX][Global.currFY + 2] == 0))
  		                unexplore = true;
                      break;
        }
        return unexplore;
     }
      
   //return true if 
   //the top right grid has not been explored and
   //the lower right 2 grids are empty
    
    /*********************************************************might not needed anymore ************/
    public boolean rightUnexplored(char orientation){
	      boolean unexplore = false;
	      
	      switch (orientation){
	         case 'U':  //not at right wall
	        	 		if ((Global.currCY != 13) && (Global.exploreMap[Global.currFX][Global.currFY + 2] == 0) && (Global.robotMap[Global.currCX][Global.currCY + 2] == 0) && (Global.robotMap[Global.currCX + 1][Global.currCY + 2] == 0))
	                    	unexplore = true;
	                    break;
	         case 'R':  //not at bottom wall                
	                    if ((Global.currCX != 18) && (Global.exploreMap[Global.currFX + 2][Global.currFY] == 0) && (Global.robotMap[Global.currCX + 2][Global.currCY - 1] == 0) && (Global.robotMap[Global.currCX + 2][Global.currCY] == 0))
	                        unexplore = true;
	                    break;
	         case 'L':  //not at top wall
			            if ((Global.currCX != 1) && (Global.exploreMap[Global.currFX - 2][Global.currFY] == 0) && (Global.robotMap[Global.currCX - 2][Global.currCY] == 0) && (Global.robotMap[Global.currCX - 2][Global.currCY + 1] == 0))
			                unexplore = true;
	                    break;
	         case 'D':	//not at left wall
			            if ((Global.currCY != 1) && (Global.exploreMap[Global.currCX + 1][Global.currCY - 2] == 0) && (Global.robotMap[Global.currCX - 1][Global.currCY - 2] == 0) && (Global.robotMap[Global.currCX][Global.currCY - 2] == 0))
			                unexplore = true;
	                    break;
	      }
	      return unexplore;
    }
    
    public int computeCoverage(){
		int explored = 0;
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 15; j++) {
				if (Global.exploreMap[i][j] == 1) 
					explored++; 
			}
		} return (explored*100/300);
	}
	
	public void move(String howToMove)
	{
		if(howToMove.equals(Global.moveForward))
			moveForward();
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
	
	public static void paintFsPath(){
		for (int i = Global.fsPaint.size(); i > 0; i--) 
			Global.exploreMap[Global.fsPaint.get(i - 1).getX()][Global.fsPaint.get(i - 1).getY()] = -7;
	}
}