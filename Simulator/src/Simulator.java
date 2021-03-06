

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

				grid[x][y] = new JButton(); // creates new button, text can be
											// removed
				grid[x][y].setName(x + "," + y);
				grid[x][y].setBorder(BorderFactory.createRaisedBevelBorder());

				System.out.print(Global.realMap[x][y] + ",");

				if (Global.realMap[x][y] != 1)
					grid[x][y].setBackground(Color.WHITE);
				else
					grid[x][y].setBackground(Color.BLACK);

				grid[x][y].addActionListener(this);
				gridPanel.add(grid[x][y]); // adds button to grid

				gridExplore[x][y] = new JButton(); // creates new button, text
													// can be removed
				gridExplore[x][y].setName(x + "," + y);
				gridExplore[x][y].setBorder(BorderFactory
						.createRaisedBevelBorder());
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
		} else if (name.equals("Explore")) {
			setRobot();
			explore();
		} else {
			int index = name.indexOf(",");
			int x = Integer.parseInt(name.substring(0, index));
			int y = Integer.parseInt(name.substring(index + 1));

			if (button.getBackground() == Color.WHITE) { // set obstacle
				button.setBackground(Color.BLACK);
				Global.realMap[x][y] = 1;
			} else if (button.getBackground() == Color.BLACK) { // remove
																// obstacle
				button.setBackground(Color.WHITE);
				Global.realMap[x][y] = 0;
			}
		}
	}

	// robot color is Pink
	// X0X
	// XXX
	// XXX

	// Display robot
	public void setRobot() {

		int x;
		int y;
		x = Global.currCX - 1;
		y = Global.currCY - 1;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				gridExplore[x + i][y + j].setBackground(Color.YELLOW); // color
																		// of
																		// robot
			}
		}
		gridExplore[Global.currFX][Global.currFY].setBackground(Color.PINK); // indicate
																				// front
																				// of
																				// robot

	}

	public void clearRobot() {
		int x;
		int y;
		x = Global.currCX - 1;
		y = Global.currCY - 1;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				gridExplore[x + i][y + j].setBackground(Color.WHITE); // explored
																		// grid
			}
		}
	}


	public void moveForward() {
		//if(Global.currFX!=0 && Global.currFX!=14 && Global.currFY!=0 && Global.currFY!=19){
			clearRobot();
			int x = Global.currFX - Global.currCX;
			int y = Global.currFY - Global.currCY;
			Global.currCX = Global.currCX + x;
			Global.currCY = Global.currCY + y;
			Global.currFX = Global.currFX + x;
			Global.currFY = Global.currFY + y;
         robot.senseEnvironment();
			paintRobotMap();
			setRobot();
		//}
	}

	public void turnRight() {
		clearRobot();
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
      robot.senseEnvironment();
		paintRobotMap();
		setRobot();

	}

	public void turnLeft() {
		clearRobot();
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
		} else {
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
      robot.senseEnvironment();
		paintRobotMap();
		setRobot();

	}
   public void checkExploreMap(){
      
      
   }
   public boolean checkRobotMapFree(char orientation){
      boolean free=false;
      switch (orientation){
         case 'U':
                     if(Global.currCY!=1)
                      {                       
                        if( ((Global.robotMap[Global.currCX -1][Global.currCY-2] == 0) && (Global.robotMap[Global.currCX][Global.currCY-2] == 0)) && (Global.robotMap[Global.currCX +1][Global.currCY-2] == 0) )
                        {//the robot left side is empty to move
                           free = true;}
                        else
                           free = false;
                      }

                     else
                     { //it is at the left wall
                        free=false;
                     }
                     break;
         case 'R':
                     if(Global.currCX!=1){
                        //System.out.print("Y: "+Global.currCY +";X: "+Global.currCX+"\n");
                                               
                        if( ((Global.robotMap[Global.currCX -2][Global.currCY-1] == 0) && (Global.robotMap[Global.currCX-2][Global.currCY+1] == 0)) && (Global.robotMap[Global.currCX -2][Global.currCY] == 0) )
                        {//the robot left side is empty to move
                           free = true;}
                        else
                           free = false;
                      }
                      else
                      { //it is at the top wall
                        free = false;
                      }
                     break;
         case 'L':   
                     
                     break;
         case 'D':
                     
                     break;
      }
      return free;
   
   }
	public void explore() {
		Thread t = new Thread() {
		//	@Override
			public void run() {
				int i;
		//		Robot robot = new Robot();
				char orientation;
				int x, y, z, moveAfterTurn1 = 0, moveAfterTurn2 = 0;
				char turn = ' ';
				boolean stop = false;
            char lastOrientation='U';
            

				while ((Global.currCX != Global.goalX) || (Global.currCY != Global.goalY) || stop) {
					robot.senseEnvironment();
					orientation = robot.checkOri();
					System.out.println("facing : " + orientation);
					z = 4;
					i = 1;
					moveAfterTurn1 = 0;

					if (orientation == 'U') {
						
						//while front is empty //the robot is not at the front wall
						if((!(checkRobotMapFree(orientation))&&(Global.currFX!=0)) && (Global.robotMap[Global.currFX - 1][Global.currFY + 1] == 0) && (Global.robotMap[Global.currFX - 1][Global.currFY] == 0) && (Global.robotMap[Global.currFX - 1][Global.currFY - 1] == 0) ){ 
	                     moveForward();
								robot.senseEnvironment();
								
								try {sleep(400);} 
								catch (InterruptedException ex) {}
                        //lastOrientation=robot.checkOri();
						
						}
                 else{
					   
						if (Global.currFY < 2 || Global.currFX == 0){  //the robot is at the left
							turnRight();
							robot.senseEnvironment();
								
							try {sleep(400);} 
							catch (InterruptedException ex) {}
						}
						else{ 
                     System.out.println("This is the checkRobotMapFree(): "+checkRobotMapFree(orientation));
                      if((Global.currFY>2)&& (checkRobotMapFree(orientation)))
                        {
                           turnLeft();
                           robot.senseEnvironment();
                           
                           try {sleep(400);} 
   							   catch (InterruptedException ex) {}
                          // System.out.println("This is the orientation after turn left "+robot.checkOrientation());
                        }


                  //any of the grids in front is blocked
							                     //right side is unexplored
                                          
   							else if ((Global.exploreMap[Global.currFX][Global.currFY + 2] == 0) || (Global.exploreMap[Global.currFX + 1][Global.currFY + 2] == 0) || (Global.exploreMap[Global.currFX + 2][Global.currFY + 2] == 0)){ 
   								turnRight();
   								robot.senseEnvironment();
   								
   								try {sleep(400);} 
   								catch (InterruptedException ex) {}
   							}
							//left side is unexplored
   							else if  ((Global.exploreMap[Global.currFX][Global.currFY - 2] == 0) || (Global.exploreMap[Global.currFX + 1][Global.currFY - 2] == 0) || (Global.exploreMap[Global.currFX + 2][Global.currFY - 2] == 0)){ 
   								turnLeft();
   								robot.senseEnvironment();
   								
   								try {sleep(400);} 
   								catch (InterruptedException ex) {}
   							}
                     
						}
						
						}
					}

					else if (orientation == 'R') {
					   
                  System.out.println("*******************************"+ checkRobotMapFree(orientation) +"***************************");
                     if((Global.currFY != 14)&&(!checkRobotMapFree(orientation))&&(Global.robotMap[Global.currFX-1][Global.currFY+1] == 0) && (Global.robotMap[Global.currFX][Global.currFY+1] == 0) && (Global.robotMap[Global.currFX+1][Global.currFY+1] == 0))
   						{
   							moveForward();
   							robot.senseEnvironment();
   							
   							try {sleep(400);} 
   							catch (InterruptedException ex) {}
                       
     						}
                     else if( checkRobotMapFree(orientation) || ((Global.currFY != 14) && (Global.currFX !=1))){
                        turnLeft();
                        robot.senseEnvironment();
                        
                        try {sleep(400);} 
								catch (InterruptedException ex) {}
                        moveForward();
   							robot.senseEnvironment();
   							
   							try {sleep(400);} 
   							catch (InterruptedException ex) {}

                        
                     }
                   
 					}

					else if (orientation == 'L') { 
                  System.out.println("****************************goforwardlah******************");
                     System.out.println(Global.robotMap[Global.currFX+1][Global.currFY - 1] == 0);
                     System.out.println(Global.robotMap[Global.currFX][Global.currFY - 1] == 0);
                     System.out.println(Global.robotMap[Global.currFX-1][Global.currFY - 1] == 0);


               
					   if((Global.currFY!=0)&&(Global.robotMap[Global.currFX+1][Global.currFY - 1] == 0) && (Global.robotMap[Global.currFX][Global.currFY - 1] == 0) && (Global.robotMap[Global.currFX -1][Global.currFY - 1] == 0) )
						{
                     System.out.println("****************************goforwardlah******************");
                     moveForward();
   						robot.senseEnvironment();
   							
   						try {sleep(400);} 
   						catch (InterruptedException ex) {}

                  }
                  else
                  {
                    //  if(Global.currFY == 0)
//                      {
//                         turnRight();
//                         robot.senseEnvironment();
//    							
//    						try {sleep(400);} 
//    						catch (InterruptedException ex) {}
// 
//                      }
                  }

					}

					else {
						

					}

				}
			}

		};
		t.start();

	}


	public void paintRobotMap() {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 15; j++) {
				if (Global.exploreMap[i][j] == 1) {
					gridExplore[i][j].setBackground(Color.WHITE);
					if (Global.robotMap[i][j] == 1)
						gridExplore[i][j].setBackground(Color.RED); // explored
																	// grid
				}

			}
		}
	}
}