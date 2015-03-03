//package MapUI;

// change made by caining
// I AM HANDSOME
// LET'S HAVE FUN
// AFTER HAVE FUN
// RESET 
// i am zinzin
import Global.Global;
import MapUI.Map;

import connector.*;

import java.io.IOException;
import java.net.*;

import javax.swing.JFrame;
public class RobotControl {
	
	//public static Server sc = new Server(Global.port,Global.backlock);
	
	public static String robotSensor;
	//public RobotControl(){}
	
	public static void main(String[] args) throws IOException
	{
		
		initializeLogicalMap();
		System.out.println("Yes it run");
		Map map = new Map("Map");                                 //makes new ButtonGrid with 2 parameters
        map.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        map.addComponentsToMap(map.getContentPane());
        map.pack();                                               //sets appropriate size for frame
        map.setVisible(true);
		System.out.println("here");
        Global.c.mySend(Global.moveForward);
        
		
	}
	public static void initializeLogicalMap()
	{
		for(int x = 0; x < 20; x++){
	      	for(int y = 0; y < 15; y++){
	      		Global.realMap[x][y] = 0;
	      		Global.robotMap[x][y] = 0;                         //unexplored, unknown
	      		Global.exploreMap[x][y] = 0;
	      	}
	    }
		
		//start zone
		for (int i = 17; i < 20; i++){
			for (int j = 0; j < 3; j++){
				Global.exploreMap[i][j] = 1;
				Global.robotMap[i][j] = 0;
			}
		}
	}
}
