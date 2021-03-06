
import java.awt.*;

import javax.swing.*;

public class Main extends JFrame{

	public static void main(String[] args) {
		
		for(int x = 0; x < 20; x++){
	      	for(int y = 0; y < 15; y++){
	      		Global.realMap[x][y] = 0;
	      		Global.robotMap[x][y] = 0;                                     //unexplored, unknown
	      		Global.exploreMap[x][y] = 0;
	      	}
	    }
		
		Map map = new Map("Map");                                 //makes new ButtonGrid with 2 parameters
        map.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        map.addComponentsToMap(map.getContentPane());
        map.pack();                                               //sets appropriate size for frame
        map.setVisible(true);
    }
}
