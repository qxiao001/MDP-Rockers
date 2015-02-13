
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
 
public class Map extends JFrame implements ActionListener{
	
	 static final int WIDTH = 20;
	 static final int LENGTH = 15;
  //   int currCX = 18, currCY = 1, currFX = 17, currFY = 1;
    
     Container pane = new Container();;
     JPanel gridPanel = new JPanel();
     JPanel savePanel = new JPanel();
     JButton[][] grid;
 //    protected int[][] robotMap = new int[20][15];                             //store values of each grid during exploration
 //    protected static int[][] realMap = new int[20][15];
     
     
     //Map constructor
     public Map(String name){ 
    	 super(name);
    	 setPreferredSize(new Dimension(460,700));
     }
     
     public void addComponentsToMap(Container pane){
    	
    	 gridPanel.setLayout(new GridLayout(WIDTH,LENGTH));              //set layout
         gridPanel.setPreferredSize(new Dimension(430,604));
         grid = new JButton[WIDTH][LENGTH];      
         
         for(int x = 0; x < WIDTH; x++){
         	for(int y = 0; y < LENGTH; y++){
       
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
        	saveMap();
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
      
    public void initialize(){
    	
    	for(int x = 0; x < WIDTH; x++){
          	for(int y = 0; y < LENGTH; y++){
          		Global.realMap[x][y] = 0;                                     //no obstacle
          		Global.robotMap[x][y] = 0;                                     //unexplored, unknown
          	}
         }
    	
    }
    public void saveMap() {
    	
    	for(int x = 0; x < WIDTH; x++){
         	for(int y = 0; y < LENGTH; y++){
         			System.out.print(Global.realMap[x][y] + ",");
         	}
         	System.out.println(" ");
        }
    	
    	Simulator simulator = new Simulator("Simulator");               //makes new ButtonGrid with 2 parameters
     //   simulator.initialize();
        simulator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        simulator.addComponentsToMap(simulator.getContentPane());
        simulator.pack();                                               //sets appropriate size for frame
        simulator.setVisible(true);
        
        System.out.println("initial robot map ");
        for(int x = 0; x < WIDTH; x++){
         	for(int y = 0; y < LENGTH; y++){
         			System.out.print(Global.robotMap[x][y] + ",");
         	}
         	System.out.println(" ");
        }
     }
    
 
}