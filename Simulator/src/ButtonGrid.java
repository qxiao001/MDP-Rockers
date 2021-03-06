import javax.swing.JFrame; //imports JFrame library
import javax.swing.JButton; //imports JButton library

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout; //imports GridLayout library
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
public class ButtonGrid implements ActionListener{
 
        JFrame frame=new JFrame(); //creates frame
        JButton[][] grid; //names the grid of buttons
 
        public ButtonGrid(int width, int length){ //constructor
                frame.setLayout(new GridLayout(width,length)); //set layout
                grid=new JButton[width][length]; //allocate the size of grid
                
                        for(int x=0; x<width; x++){
                        	for(int y=0; y<length; y++){
                       
                                grid[x][y]=new JButton("("+x+","+y+")"); //creates new button 
                                grid[x][y].setPreferredSize(new Dimension(30, 30));
                                grid[x][y].addActionListener(this);
                                grid[x][y].setBackground(Color.WHITE);
                                frame.add(grid[x][y]); //adds button to grid
                        }
                }
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack(); //sets appropriate size for frame
                frame.setVisible(true); //makes frame visible
        }
        
        public void actionPerformed(ActionEvent e) {
        	
            JButton button = (JButton)e.getSource();
            
            /*int x = button.getX();
            int y = button.getY();
            xlabel.setText("x: " + Integer.toString(x));
            ylabel.setText("y: " + Integer.toString(y));*/
            
           
            //set obstacle
            if (button.getBackground() == Color.WHITE){
            	button.setBackground(Color.BLACK);
            	button.setText("1");
            }
            
            //remove obstacle
            else if (button.getBackground() == Color.BLACK){
            	button.setBackground(Color.WHITE);
            	button.setText("0");
            }
        }
         
    //    robot color is Pink
    //    X0X
    //    XXX
    //    XXX   
        
        public void setRobot(int centerX,int centerY, int frontX, int frontY){
        	int x; int y;
        	
            
       /*     x=topLeftX;
            y=topLeftY;
        	grid[x][y].setBackground(Color.YELLOW); y++;
        	grid[frontX][frontY].setBackground(Color.PINK);   y++;
        	grid[x][y].setBackground(Color.YELLOW); x++; 
        	grid[x][y].setBackground(Color.YELLOW); y--;
        	grid[x][y].setBackground(Color.YELLOW); y--;
        	grid[x][y].setBackground(Color.YELLOW); x++;
        	grid[x][y].setBackground(Color.YELLOW); y++;
        	grid[x][y].setBackground(Color.YELLOW); y++;
        	grid[x][y].setBackground(Color.YELLOW);
        */
        	
        	
        }

     /*   public static void main(String[] args) {
                ButtonGrid buttonGrid = new ButtonGrid(20,15);//makes new ButtonGrid with 2 parameters
                buttonGrid.setRobot(3,3,3,4);
        }*/
}