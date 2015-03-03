package MapUI;

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
         gridPanel.setPreferredSize(new Dimension(430,604));
         grid = new JButton[20][15];      
         
         for(int x = 0; x < 20; x++){
         	for(int y = 0; y < 15; y++){
       
                grid[x][y] = new JButton();                              //creates new button, text can be removed 
                grid[x][y].setName(x + "," + y);
                grid[x][y].setBorder(BorderFactory.createRaisedBevelBorder());
                grid[x][y].setBackground(Color.WHITE);
                grid[x][y].addActionListener(this);
                gridPanel.add(grid[x][y]);         
         	}
         }
         
    	 JButton btnSave = new JButton("Save");
		 btnSave.setName("Save");
		 btnSave.setBackground(Color.WHITE);
		 btnSave.setAlignmentX(RIGHT_ALIGNMENT);
		 btnSave.addActionListener(this);
		 /***For Loading Map ***/
		 btnObs1 = new JButton("Obs1");
		 btnObs1.setName("Obs1");
		 btnObs1.setBackground(Color.WHITE);
		 btnObs1.setAlignmentX(LEFT_ALIGNMENT);
		 btnObs1.addActionListener(this);
		 
		 btnObs2 = new JButton("Obs2");
		 btnObs2.setName("Obs2");
		 btnObs2.setBackground(Color.WHITE);
		 btnObs2.setAlignmentX(LEFT_ALIGNMENT);
		 btnObs2.addActionListener(this);
		 
		 btnObs3 = new JButton("Obs3");
		 btnObs3.setName("Obs3");
		 btnObs3.setBackground(Color.WHITE);
		 btnObs3.setAlignmentX(LEFT_ALIGNMENT);
		 btnObs3.addActionListener(this);
		 
		 btnObs4 = new JButton("Obs4");
		 btnObs4.setName("Obs4");
		 btnObs4.setBackground(Color.WHITE);
		 btnObs4.setAlignmentX(LEFT_ALIGNMENT);
		 btnObs4.addActionListener(this);
		 
		 
		 btnObs5 = new JButton("Obs5");
		 btnObs5.setName("Obs5");
		 btnObs5.setBackground(Color.WHITE);
		 btnObs5.setAlignmentX(LEFT_ALIGNMENT);
		 btnObs5.addActionListener(this);
		 
		 savePanel.add(btnObs1,BorderLayout.WEST);
		 savePanel.add(btnObs2,BorderLayout.WEST);
		 savePanel.add(btnObs3,BorderLayout.WEST);
		 savePanel.add(btnObs4,BorderLayout.WEST);
		 savePanel.add(btnObs5,BorderLayout.WEST);
		 /***For Loading Map ***/
		 savePanel.add(btnSave,BorderLayout.WEST);
		 savePanel.setPreferredSize(new Dimension(460,40));
		 
         pane.add(gridPanel,BorderLayout.PAGE_START);
         pane.add(savePanel,BorderLayout.PAGE_END);
         pane.setPreferredSize(new Dimension(460,670));
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
        }
        else if(e.getSource()==btnObs1)
        {
        	obs.initObs1();
        	try {
				saveMap();
			} catch (IOException e1) {e1.printStackTrace();}
        }
        else if(e.getSource()==btnObs2)
        {
        	obs.initObs2();
        	try {
				saveMap();
			} catch (IOException e1) {e1.printStackTrace();}
        }
        else if(e.getSource()==btnObs3)
        {
        	obs.initObs3();
        	try {
				saveMap();
			} catch (IOException e1) {e1.printStackTrace();}
        }
        else if(e.getSource()==btnObs4)
        {
        	obs.initObs4();
        	try {
				saveMap();
			} catch (IOException e1) {e1.printStackTrace();}
        }
        else if(e.getSource()==btnObs5)
        {
        	obs.initObs5();
        	try {
				saveMap();
			} catch (IOException e1) {e1.printStackTrace();}
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
    	
    	Simulator simulator = new Simulator("Simulator");               //makes new ButtonGrid with 2 parameters
        simulator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        simulator.addComponentsToMap(simulator.getContentPane());
        simulator.pack();                                               //sets appropriate size for frame
        simulator.setVisible(true);
        
        
     }
    
 
}