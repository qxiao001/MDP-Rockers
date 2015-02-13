
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
 
public class Simulator extends JFrame implements ActionListener{
	
	 static final int WIDTH = 20;
	 static final int LENGTH = 15;
//     int Global.currCX = 18, Global.currCY = 1, Global.currFX = 17, Global.currFY = 1;
     //static boolean blocked = false;
     static boolean save = false;
     Container pane = new Container();;
     JPanel gridPanel = new JPanel();
     JPanel simulatePanel = new JPanel();
     JPanel explorePanel = new JPanel();
     JButton[][] grid,gridExplore;
//     int[][] Global.realMap = new int[20][15];                              //store values of each grid
//    int[][] Global.robotMap = new int[20][15];                             //store values of each grid during exploration
     
     
     
     //Map constructor
     public Simulator(String name){ 
    	 super(name);
    	 setPreferredSize(new Dimension(1000,650));
     }
     
     public void addComponentsToMap(Container pane){
    	
    	 gridPanel.setLayout(new GridLayout(WIDTH,LENGTH));              //set layout
         gridPanel.setPreferredSize(new Dimension(430,604));
         grid = new JButton[WIDTH][LENGTH];                              //allocate the size of grid
         explorePanel.setLayout(new GridLayout(WIDTH,LENGTH));
         explorePanel.setPreferredSize(new Dimension(430,604));
         gridExplore = new JButton[WIDTH][LENGTH];
         
         for(int x = 0; x < WIDTH; x++){
         	for(int y = 0; y < LENGTH; y++){
       
                grid[x][y] = new JButton();                              //creates new button, text can be removed 
                grid[x][y].setName(x + "," + y);
                grid[x][y].setBorder(BorderFactory.createRaisedBevelBorder());
                
                if(Global.realMap[x][y]!=1){
                    grid[x][y].setBackground(Color.WHITE);
                }
                else{
                	grid[x][y].setBackground(Color.BLACK);
                }
                
                grid[x][y].addActionListener(this);
                gridPanel.add(grid[x][y]);                               //adds button to grid 
                
                gridExplore[x][y] = new JButton();                             //creates new button, text can be removed 
                gridExplore[x][y].setName(x + "," + y);
                gridExplore[x][y].setBorder(BorderFactory.createRaisedBevelBorder());
                gridExplore[x][y].setBackground(Color.CYAN);
                explorePanel.add(gridExplore[x][y]); 
         	}
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
		 
    	 simulatePanel.add(btnExplore,BorderLayout.NORTH);
		 simulatePanel.add(btnFastestPath,BorderLayout.SOUTH);
		 simulatePanel.setPreferredSize(new Dimension(460,25));
		 //simulatePanel.setLayout(new BoxLayout(simulatePanel, BoxLayout.Y_AXIS));
    	 
         pane.add(gridPanel,BorderLayout.WEST);
         pane.add(simulatePanel,BorderLayout.CENTER);
         pane.add(explorePanel,BorderLayout.EAST);
         pane.setPreferredSize(new Dimension(460,670));
    }
     
        
    //Action listener. Do something when any of the button is clicked
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton)e.getSource();
        String name = button.getName();
        
        if (name.equals("Save") == true){
        	saveMap();
        }
        else if(name.equals("Explore")){
        	setRobot();
        	explore();
        }
        else{
	        int index = name.indexOf(",");
	        int x = Integer.parseInt(name.substring(0, index));
	        int y = Integer.parseInt(name.substring(index+1));
	        
	        if (button.getBackground() == Color.WHITE){                //set obstacle
	        	button.setBackground(Color.BLACK);
	        	Global.realMap[x][y] = 1;
	        }
	        else if (button.getBackground() == Color.BLACK){           //remove obstacle
	        	button.setBackground(Color.WHITE);
	        	Global.realMap[x][y] = 0;                                     
	        }
        }
    }
      

    public void saveMap() {
    	
    	int count = 0;
    	
    	for(int x = 0; x < WIDTH; x++){
         	for(int y = 0; y < LENGTH; y++){
         			System.out.print(Global.realMap[x][y] + ",");
         	}
         	System.out.println(" ");
        }
    	
    	
    	save = true;
    	//explore();
     }
    
    //    robot color is Pink
    //    X0X
    //    XXX
    //    XXX   
        
    //Display robot
    public void setRobot(){
 
    	int x; int y;
        x = Global.currCX - 1;
        y = Global.currCY - 1;
        
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
               gridExplore[x+i][y+j].setBackground(Color.YELLOW);         //color of robot
            }
        }
    	gridExplore[Global.currFX][Global.currFY].setBackground(Color.PINK);   	      //indicate front of robot
    	
    }
    
    public void clearRobot(){
    	int x; int y;
        x = Global.currCX - 1;
        y = Global.currCY - 1;
     
        for (int i = 0; i < 3; i++){
        	for (int j = 0; j < 3; j++){ 
        		gridExplore[x+i][y+j].setBackground(Color.WHITE);     //explored grid
        	}
        }
    }
    /*
    public void sensorReading(int readingFL, int readingFM, int readingFR, int readingL, int readingR){
    	int currX,currY,x,y;
    
    	x = Global.currFX-1;
    	y = Global.currFY-1;
    	if (readingFL > 20){               //assume value > 20 is an obstacle
    	
    		grid[x][y].setText("1");
    		grid[x][y].setBackground(Color.BLACK);
    	}
    	else{
    		grid[x][y].setText("-1");
    		grid[x][y].setBackground(Color.CYAN);
    	}

    	x = Global.currFX-1;
    	y = Global.currFY;
    	if (readingFM > 20){               //assume value > 20 is an obstacle
			grid[x][y].setText("1");
			grid[x][y].setBackground(Color.BLACK);
		}
    	else{
			grid[x][y].setText("-1");
			grid[x][y].setBackground(Color.CYAN);
		}
    	
    	x = Global.currFX-1;
    	y = Global.currFY+1;
    	if (readingFR > 20){               //assume value > 20 is an obstacle
			grid[x][y].setText("1");
			grid[x][y].setBackground(Color.BLACK);
    	}
    	else{
			grid[x][y].setText("-1");
			grid[x][y].setBackground(Color.CYAN);
    	}
    	
    }
    */
    public void moveForward(){
    	clearRobot();
    	int x = Global.currFX - Global.currCX;
    	int y = Global.currFY - Global.currCY;
    	Global.currCX = Global.currCX + x;
    	Global.currCY = Global.currCY + y;
    	Global.currFX = Global.currFX + x;
    	Global.currFY = Global.currFY + y;
    	
    	paintRobotMap();
    	setRobot();
    }
    
    public void turnRight(){
    	clearRobot();
        int x = Global.currFX-Global.currCX; 
        int y = Global.currFY-Global.currCY;
        
        if(x != 0)
        {
        	if (x < 0)
                {Global.currFX++; Global.currFY++;}
            else
            	{Global.currFX--; Global.currFY--;} 
        }
        else
        {
            if(y < 0)
            	{Global.currFX--; Global.currFY++;}
            else
            	{Global.currFX++; Global.currFY--;}
        }
    
        
        paintRobotMap();
        setRobot();
    }
    
    public void turnLeft(){
    	clearRobot();
    	int x = Global.currFX-Global.currCX; 
    	int y = Global.currFY-Global.currCY;
    	
    	if(x != 0)
    	{
    		if (x < 0)                 //facing upward x=-1,y=0
    			{Global.currFX++; Global.currFY--;}
    		else                       //facing downward
    			{Global.currFX--; Global.currFY++;}
    	}
        else
        {
            if(y < 0)                  //facing left x=0,y=-1
            	{Global.currFX++; Global.currFY++;}
            else                       //facing right x=0,y=1
            	{Global.currFX--; Global.currFY--;}
        }
    	
    	
    	paintRobotMap();
    	setRobot();
    }
    public boolean obstacleInFront(){
    	
    	return false;
    			
    }
	public void explore() {
		final Robot robot=new Robot();
		Thread t = new Thread() {
			public void run() {
				robot.senseEnvironment();
				paintRobotMap();
				while(!(Global.currCX==1&&Global.currCY==14)){
					while(!obstacleInFront()){
					  robot.senseEnvironment();
					  moveForward();
					}
				}
				
				
////				for (int i = 0; i < 5; i++) {
////					robot.senseEnvironment();
////					moveForward();
////					try {
////						sleep(800); // milliseconds
////					} catch (InterruptedException ex) {
////					}
////				}
////				turnRight();
////				for (int i = 0; i < 3; i++) {
////					moveForward();
////					try {
////						sleep(800); // milliseconds
////					} catch (InterruptedException ex) {
////					}
////
////				}
//				

			}
		};
		t.start();
		
		System.out.println("robot map");
		for(int i=0;i<20;i++){
			for (int j=0;j<15;j++){
				
				System.out.print(Global.robotMap[i][j] + ",");
			}
			System.out.println("");
		}

	}
	public void paintRobotMap(){
		for (int i = 0; i < 20; i++){
        	for (int j = 0; j < 15; j++){ 
        		if (Global.exploreMap[i][j]==1){
        			gridExplore[i][j].setBackground(Color.WHITE); 
        		    if (Global.robotMap[i][j]==1)
        		    gridExplore[i][j].setBackground(Color.RED);     //explored grid
        		}
        	}
        }
		
	}
    
    /*public static void main(String[] args) {
    	
        Simulator map = new Simulator("Map");                                 //makes new ButtonGrid with 2 parameters
        map.initialize();
        map.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        map.addComponentsToMap(map.getContentPane());
        map.pack();                                               //sets appropriate size for frame
        map.setVisible(true); 
        
        map.setRobot();
        map.explore();
        //while the middle point of robot is not goal point -> loop
        	//loop: 
            //1)explore and update the map
            //2)if no obstacle -> move forward
            //3)if there is obstacle -> turn
        //terminating condition: middle point of robot == goal point

    }*/
}