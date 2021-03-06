
//package simulator;

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
 
public class Map extends JFrame {
	
	 static final int WIDTH = 20;
	 static final int LENGTH = 15;
     int currCX = 18, currCY = 1, currFX = 17, currFY = 1;
     //static boolean blocked = false;
     Container pane = new Container();;
     JPanel gridPanel = new JPanel();
     JPanel savePanel = new JPanel();
     JPanel explorePanel = new JPanel();
     JButton[][] grid,gridExplore;
     int[][] realMap = new int[20][15];                              //store values of each grid
     int[][] robotMap = new int[20][15];                             //store values of each grid during exploration
    
     
     //Map constructor
     public Map(String name){ 
    	 super(name);
    	 setPreferredSize(new Dimension(1000,650));
     }
     
     public void addComponentsToMap(Container pane){
    	
    	 gridPanel.setLayout(new GridLayout(WIDTH,LENGTH));              //set layout
         gridPanel.setPreferredSize(new Dimension(450,604));
         grid = new JButton[WIDTH][LENGTH];                              //allocate the size of grid
         explorePanel.setLayout(new GridLayout(WIDTH,LENGTH));
         explorePanel.setPreferredSize(new Dimension(450,604));
         gridExplore = new JButton[WIDTH][LENGTH];
         
         for(int x = 0; x < WIDTH; x++){
         	for(int y = 0; y < LENGTH; y++){
       
                grid[x][y] = new JButton();                              //creates new button, text can be removed 
                grid[x][y].setName(x + "," + y);
                grid[x][y].setBorder(BorderFactory.createRaisedBevelBorder());
                grid[x][y].setBackground(Color.WHITE);
                grid[x][y].addActionListener(new  ActionListener() {
                    public void actionPerformed( ActionEvent evt) {
                    	actionPerformObstacle(evt);
                    }
                });
                gridPanel.add(grid[x][y]);                               //adds button to grid 
                
                gridExplore[x][y] = new JButton();                             //creates new button, text can be removed 
                gridExplore[x][y].setName(x + "," + y);
                gridExplore[x][y].setBorder(BorderFactory.createRaisedBevelBorder());
                gridExplore[x][y].setBackground(Color.CYAN);
                explorePanel.add(gridExplore[x][y]); 
         	}
         }
         
    	 JButton btnSave = new JButton("Save");
		 btnSave.setName("Save");
		 btnSave.setBackground(Color.WHITE);
		 btnSave.setAlignmentX(CENTER_ALIGNMENT);
		 btnSave.addActionListener(new  ActionListener() {
	            public void actionPerformed( ActionEvent evt) {
	                actionPerformSave(evt);
	            }
	        });
		 
		 JButton btnExplore = new JButton("Explore");
		 btnExplore.setName("Explore");
		 btnExplore.setBackground(Color.WHITE);
		 btnExplore.setAlignmentX(CENTER_ALIGNMENT);
		 btnExplore.addActionListener(new  ActionListener() {
	            public void actionPerformed( ActionEvent evt) {
	                actionPerformExplore(evt);
	            }
	        });
		 
		 savePanel.add(btnSave);
		 savePanel.add(btnExplore);
		 savePanel.setPreferredSize(new Dimension(360,25));
		 savePanel.setLayout(new BoxLayout(savePanel, BoxLayout.Y_AXIS));
    	 
         pane.add(gridPanel,BorderLayout.WEST);
         pane.add(savePanel,BorderLayout.CENTER);
         pane.add(explorePanel,BorderLayout.EAST);
         pane.setPreferredSize(new Dimension(460,670));
    }
     
        
    //Action listener. Do something when any of the button is clicked
    public void actionPerformObstacle(ActionEvent e) {
        JButton button = (JButton)e.getSource();
        String name = button.getName();
        
       
	        int index = name.indexOf(",");
	        int x = Integer.parseInt(name.substring(0, index));
	        int y = Integer.parseInt(name.substring(index+1));
	        
	        if (button.getBackground() == Color.WHITE){                //set obstacle
	        	button.setBackground(Color.BLACK);
	        	realMap[x][y] = 1;
	        }
	        else if (button.getBackground() == Color.BLACK){           //remove obstacle
	        	button.setBackground(Color.WHITE);
	        	realMap[x][y] = -1;                                     
	        }
        
    }
    public void actionPerformExplore(ActionEvent e){
    	JButton button = (JButton)e.getSource();
        String name = button.getName();
        
     
    	explore();
    }
    public void actionPerformSave(ActionEvent e){
    	JButton button = (JButton)e.getSource();
        String name = button.getName();
        
   
      
    	saveMap();
    }
      
    public void initialize(){
    	
    	for(int x = 0; x < WIDTH; x++){
          	for(int y = 0; y < LENGTH; y++){
          		realMap[x][y] = -1;                                     //no obstacle
          		robotMap[x][y] = 0;                                     //unexplored, unknown
          	}
         }
    	
    }
    public void saveMap() {
    	
    	int count = 0;
    	
    	for(int x = 0; x < WIDTH; x++){
         	for(int y = 0; y < LENGTH; y++){
         		if (realMap[x][y] != -1)
         			System.out.print(" " + realMap[x][y] + ",");
         		else
         			System.out.print(realMap[x][y] + ",");
         	}
         	System.out.println(" ");
        }
    	
    	
    //	setRobot();
  
    	
     }
    
    //    robot color is Pink
    //    X0X
    //    XXX
    //    XXX   
        
    //Display robot
    public void setRobot(){
 
    	int x; int y;
        x = currCX - 1;
        y = currCY - 1;
        
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
               gridExplore[x+i][y+j].setBackground(Color.YELLOW);         //color of robot
            }
        }
    	gridExplore[currFX][currFY].setBackground(Color.PINK);   	      //indicate front of robot
    	
    }
    
    public void clearRobot(){
    	int x; int y;
        x = currCX - 1;
        y = currCY - 1;
     
        for (int i = 0; i < 3; i++){
        	for (int j = 0; j < 3; j++){ 
        		gridExplore[x+i][y+j].setBackground(Color.WHITE);     //explored grid
        	}
        }
    }
    /*
    public void sensorReading(int readingFL, int readingFM, int readingFR, int readingL, int readingR){
    	int currX,currY,x,y;
    
    	x = currFX-1;
    	y = currFY-1;
    	if (readingFL > 20){               //assume value > 20 is an obstacle
    	
    		grid[x][y].setText("1");
    		grid[x][y].setBackground(Color.BLACK);
    	}
    	else{
    		grid[x][y].setText("-1");
    		grid[x][y].setBackground(Color.CYAN);
    	}

    	x = currFX-1;
    	y = currFY;
    	if (readingFM > 20){               //assume value > 20 is an obstacle
			grid[x][y].setText("1");
			grid[x][y].setBackground(Color.BLACK);
		}
    	else{
			grid[x][y].setText("-1");
			grid[x][y].setBackground(Color.CYAN);
		}
    	
    	x = currFX-1;
    	y = currFY+1;
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
    	int x = currFX - currCX;
    	int y = currFY - currCY;
    	currCX = currCX + x;
    	currCY = currCY + y;
    	currFX = currFX + x;
    	currFY = currFY + y;
    	setRobot();
    	System.out.println("move forward");
    }
    
    public void turnRight(){
    	clearRobot();
        int x = currFX-currCX; 
        int y = currFY-currCY;
        
        if(x != 0)
        {
        	if (x < 0)
                {currFX++; currFY++;}
            else
            	{currFX--; currFY--;} 
        }
        else
        {
            if(y < 0)
            	{currFX--; currFY++;}
            else
            	{currFX++; currFY--;}
        }
    
        setRobot();
        System.out.println("turn right");
    }
    
    public void turnLeft(){
    	clearRobot();
    	int x = currFX-currCX; 
    	int y = currFY-currCY;
    	
    	if(x != 0)
    	{
    		if (x < 0)                 //facing upward x=-1,y=0
    			{currFX++; currFY--;}
    		else                       //facing downward
    			{currFX--; currFY++;}
    	}
        else
        {
            if(y < 0)                  //facing left x=0,y=-1
            	{currFX++; currFY++;}
            else                       //facing right x=0,y=1
            	{currFX--; currFY--;}
        }
    	
    	setRobot();
    	System.out.println("turn left");
    }

    public void explore(){
    	int step = 3;
    	int counter;
        for(int i = 0; i < step; i++){
        	moveForward();
        	for (counter=0; counter<1000000; counter++){}
        	System.out.println("first finish ");
        }
        
       
        for(int i = 0; i < step; i++){
        	turnRight();
        	for (counter=0; counter<1000000; counter++){}
        	System.out.println("second finish ");
        	         
        }
         
        
        for(int i = 0; i < step; i++){
        	turnLeft();
        	for (counter=0; counter<1000000; counter++){}
        	System.out.println("third finish ");
        	
        }
        
    }
    
   
}