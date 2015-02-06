
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

public class Simulator {

	public static void main(String[] args) {
		
		    	
		        Map map = new Map("Map");                                 //makes new ButtonGrid with 2 parameters
		        map.initialize();
		        map.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        map.addComponentsToMap(map.getContentPane());
		        map.pack();                                               //sets appropriate size for frame
		        map.setVisible(true); 
		       
		        
		        //while the middle point of robot is not goal point -> loop
		        	//loop: 
		            //1)explore and update the map
		            //2)if no obstacle -> move forward
		            //3)if there is obstacle -> turn
		        //terminating condition: middle point of robot == goal point

		 
		// TODO Auto-generated method stub

	}

}
