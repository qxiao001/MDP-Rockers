package MapUI;


import javax.swing.BorderFactory;
import javax.swing.JFrame; 
import javax.swing.JButton; 
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import Global.*;
import Obstacle.obsValue;
 
public class Map extends JFrame implements ActionListener{
	
     Container pane = new Container();;
     JPanel gridPanel = new JPanel();
     JPanel savePanel = new JPanel();
     JButton[][] grid;
     obsValue obs=new obsValue();
     JButton btnObs1,btnObs2,btnObs3,btnObs4,btnObs5;
     
     //Map constructor
     public Map(String name){ 
    	 super(name);
    	 setPreferredSize(new Dimension(460,700));
     }
     
     public void addComponentsToMap(Container pane){
    	
    	 gridPanel.setLayout(new GridLayout(20,15));              //set layout
         gridPanel.setPreferredSize(new Dimension(430,610));
         gridPanel.setBackground(new Color(225,225,225));
         grid = new JButton[20][15];      
         
         for(int x = 0; x < 20; x++){
         	for(int y = 0; y < 15; y++){
       
                grid[x][y] = new JButton();                             
                grid[x][y].setName(x + "," + y);
                grid[x][y].setBorder(BorderFactory.createRaisedBevelBorder());
                //grid[x][y].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                //grid[x][y].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
                grid[x][y].setBackground(Color.WHITE);
                grid[x][y].addActionListener(this);
                gridPanel.add(grid[x][y]);         
         	}
         }
         
    	 JButton btnSave = new JButton("Save");
		 btnSave.setName("Save");
		 btnSave.setBorder(BorderFactory.createRaisedBevelBorder());
		 btnSave.setPreferredSize(new Dimension(70,30));
		 btnSave.setBackground(new Color(255,255,255));
		 btnSave.setForeground(Color.BLACK);
		 btnSave.setAlignmentX(RIGHT_ALIGNMENT);
		 btnSave.addActionListener(this);
		 /***For Loading Map ***/
		 btnObs1 = new JButton("Map 1");
		 btnObs1.setName("Obs1");
		 btnObs1.setBorder(BorderFactory.createRaisedBevelBorder());
		 btnObs1.setPreferredSize(new Dimension(60,30));
		 btnObs1.setBackground(new Color(255,255,255));
		 btnObs1.setForeground(Color.BLACK);
		 btnObs1.addActionListener(this);
		 
		 btnObs2 = new JButton("Map 2");
		 btnObs2.setName("Obs2");
		 btnObs2.setBorder(BorderFactory.createRaisedBevelBorder());
		 btnObs2.setPreferredSize(new Dimension(60,30));
		 btnObs2.setBackground(new Color(255,255,255));
		 btnObs2.setForeground(Color.BLACK);
		 btnObs2.addActionListener(this);
		 
		 btnObs3 = new JButton("Map 3");
		 btnObs3.setName("Obs3");
		 btnObs3.setBorder(BorderFactory.createRaisedBevelBorder());
		 btnObs3.setPreferredSize(new Dimension(60,30));
		 btnObs3.setBackground(new Color(255,255,255));
		 btnObs3.setForeground(Color.BLACK);
		 btnObs3.addActionListener(this);
		 
		 btnObs4 = new JButton("Map 4");
		 btnObs4.setName("Obs4");
		 btnObs4.setBorder(BorderFactory.createRaisedBevelBorder());
		 btnObs4.setPreferredSize(new Dimension(60,30));
		 btnObs4.setBackground(new Color(255,255,255));
		 btnObs4.setForeground(Color.BLACK);
		 btnObs4.addActionListener(this);
		 
		 
		 btnObs5 = new JButton("Map 5");
		 btnObs5.setName("Obs5");
		 btnObs5.setBorder(BorderFactory.createRaisedBevelBorder());
		 btnObs5.setPreferredSize(new Dimension(60,30));
		 btnObs5.setBackground(new Color(255,255,255));
		 btnObs5.setForeground(Color.BLACK);
		 btnObs5.addActionListener(this);
		 
		 savePanel.add(btnObs1);
		 savePanel.add(btnObs2);
		 savePanel.add(btnObs3);
		 savePanel.add(btnObs4);
		 savePanel.add(btnObs5);
		 /***For Loading Map ***/
		 savePanel.add(btnSave);
		 savePanel.setPreferredSize(new Dimension(460,40));
		 savePanel.setBackground(new Color(255,255,255));
		 
         pane.add(gridPanel,BorderLayout.PAGE_START);
         pane.add(savePanel,BorderLayout.PAGE_END);
         pane.setPreferredSize(new Dimension(460,670));
         pane.setBackground(new Color(255,255,255));
    }
     
        
    //Action listener. Do something when any of the button is clicked
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton)e.getSource();
        String name = button.getName();
        
        if (name.equals("Save")){
        	try {
				saveMap();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	displaySimulator();
        }
        else if(e.getSource()==btnObs1)
        {
        	//RobotControl.initializeLogicalMap();
        	obs.initObs1();
        	try {
        		displaySimulator();
			} catch (Exception e1) {e1.printStackTrace();}
        }
        else if(e.getSource()==btnObs2)
        {
        	obs.initObs2();
        	try {
        		displaySimulator();
			} catch (Exception e1) {e1.printStackTrace();}
        }
        else if(e.getSource()==btnObs3)
        {
        	obs.initObs3();
        	try {
        		displaySimulator();
			} catch (Exception e1) {e1.printStackTrace();}
        }
        else if(e.getSource()==btnObs4)
        {
        	obs.initObs4();
        	try {
        		displaySimulator();
			} catch (Exception e1) {e1.printStackTrace();}
        }
        else if(e.getSource()==btnObs5)
        {
        	obs.initObs5();
        	try {
				displaySimulator();
			} catch (Exception e1) {e1.printStackTrace();}
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
      
 
 public void saveMap() throws IOException {
    	
    	for(int x = 0; x < 20; x++){
         	for(int y = 0; y < 15; y++){
         			System.out.print(Global.realMap[x][y] + ",");
         	}
         	System.out.println(" ");
        }
     }
    
    public void displaySimulator(){
    	
    	Simulator simulator = new Simulator("Simulator");               //makes new ButtonGrid with 2 parameters
        simulator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        simulator.addComponentsToMap(simulator.getContentPane());
        simulator.pack();                                               //sets appropriate size for frame
        simulator.setVisible(true);
    }
    
 
}