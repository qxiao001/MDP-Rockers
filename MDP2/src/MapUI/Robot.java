package MapUI;
   
import java.io.IOException;

import connector.fadeString;
import Global.*;
import Obstacle.Descriptor;
import connector.*;

public class Robot {

	// private int SL,SR,FL,FM,FR; // how far is the obstacles , in grids
	// private int SL_B,SR_B,FL_B,FM_B,FR_B; // if there is obstacles in front,
	// == 0
	private char ori = ' '; // orientation => U, D, L, R
	private int F1, F2, F3, L, R;
	private SensorReading sensorReading;
	//privat int loop=3;
	//for whether to set obs in the robot map

	public Robot() {
		ori = 'U';
		sensorReading=new SensorReading();
	}

	public void senseEnvironment() throws IOException {
		// robot will update 5 readings to the explore map and robot map
		checkOri();

//		
//		String str=Global.c.myReceive();
//		System.out.println("Recevied from rpi(we do nth):"+str);
		
//		String str1=Global.c.myReceive(); // open when testing with rpi
//		System.out.println("Recevied from rpi(pass this string to process)]"+ ":"+str1);
//		String str1 = fadeString.getString(); // our fade string
		String str1 = sensorReading.senseEnvironment();
		
		getReadings(str1);
		senseSL();
		senseSR();
		senseFL();
		senseFR();
		senseFM();
//		Descriptor.generateExploreMap();
//		Descriptor.generateObstacleMap();
		Simulator.paintRobotMap();

	}

	public void getReadings(String str) {
		// 5,4,3,2,1   F1 F2 F3 L R   1,2,3 FROM LEFT TO RIGHT
		try{
		    System.out.println(str);
			String[] parts = str.split(",");
			F1 = Integer.parseInt(parts[0]);
			F2 = Integer.parseInt(parts[1]);
			F3 = Integer.parseInt(parts[2]);
			L = Integer.parseInt(parts[3]);
			R = Integer.parseInt(parts[4]);
			
		}
		catch(Exception e){
			System.out.println("There is an error in getReading");
//			try {
//				senseEnvironment();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				System.out.println("The error is quite serious XP");
//			}
			//
		}
		System.out.println("F1 "+F1);
		System.out.println("F2 "+F2);
		System.out.println("F3 "+F3);
		System.out.println("L "+L);
		System.out.println("R "+R);
		//System.out.println(F1);
		//if (F1==-1)F1=0;if(F2==-1)F2=0;	if(F3==-1)F3=0;if(L==-1)L=0;if(R==-1)R=0;
	}

	public char checkOri() {
		if (Global.currFX - Global.currCX == 1) {
			ori = 'D';
			return 'D';
		}
		if (Global.currFX - Global.currCX == -1) {
			ori = 'U';
			return 'U';
		}
		if (Global.currFY - Global.currCY == 1) {
			ori = 'R';
			return 'R';
		}
		if (Global.currFY - Global.currCY == -1) {
			ori = 'L';
			return 'L';
		} else
			return ' ';
	}

	public void senseSL() {
		int clean=1; 
		System.out.println("The left sensor vlaue is: "+ L +".");
		if(L==-1 || L>=3) //no obs to set
			{L=2;clean=0;}
		switch (ori) {
		case 'U':
			for(int i=0;i<=L;i++){
				if((Global.currCY-i-2)<0) 
					break;
				Global.exploreMap[Global.currCX][Global.currCY - i - 2] = 1;
			}
			//System.out.println("The out of bound index is" + (Global.currCY-L-2));
			if((Global.currCY - L -2)>=0 )
				Global.robotMap[Global.currCX ][Global.currCY - L -2] = clean;
			break;
		case 'D':
			for (int i = 0; i <= L; i++) {
				if (Global.currCY + i + 2 > 14)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX][Global.currCY + i + 2] = 1;
			}
			if(((Global.currCY + L + 2) <15))
				Global.robotMap[Global.currCX ][Global.currCY + L + 2] = clean;
			break;
		case 'R':
			for (int i = 0; i <= L; i++) {
				if (Global.currCX - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - i - 2][Global.currCY ] = 1;
			}
			if((Global.currCX - L - 2>=0 ))
				Global.robotMap[Global.currCX - L - 2][Global.currCY ] = clean;
			break;
		case 'L':
			for (int i = 0; i <= L; i++) {
				if (Global.currCX + i + 2 > 19)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + i + 2][Global.currCY ] = 1;
			}
			if((Global.currCX + L + 2 <20))
				Global.robotMap[Global.currCX + L + 2][Global.currCY ] = clean;
			break;
		}
		
	}

	public void senseSR() {
		int clean=1; 
		System.out.println("The right sensor vlaue is: "+ R+".");
		if(R==-1 || R>=3)
			{R=2;clean=0;}
		switch (ori) {
		case 'D':
			for (int i = 0; i <= R; i++) {
				if (Global.currCY - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX ][Global.currCY - i - 2] = 1;
			}
			if((Global.currCY - R - 2 >0 ))
				Global.robotMap[Global.currCX ][Global.currCY - R - 2] = clean;
			break;
		case 'U':
			for (int i = 0; i <= R; i++) {
				if (Global.currCY + i + 2 > 14)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX ][Global.currCY + i + 2] = 1;
			}
			if((Global.currCY + R + 2 <15))
				Global.robotMap[Global.currCX ][Global.currCY + R + 2] = clean;
			break;
		case 'L':
			for (int i = 0; i <= R; i++) {
				if (Global.currCX - i-2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - i - 2][Global.currCY] = 1;
			}
			if((Global.currCX - R - 2>0 ))
				Global.robotMap[Global.currCX - R - 2][Global.currCY ] = clean;
			break;
		case 'R':
			for (int i = 0; i <= R; i++) {
				if (Global.currCX + i + 2 > 19)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + i + 2][Global.currCY ] = 1;
			}
			if((Global.currCX + R + 2<20))
				Global.robotMap[Global.currCX + R + 2][Global.currCY ] = clean;
			break;
		}
		
	}

	public void senseFR() {
		int clean=1; 
		System.out.println("The right front (f3) sensor vlaue is: "+ F3+".");
		if(F3==-1 || F3>=3)
			{F3=2;clean=0;}
		switch (ori) {
		case 'D':
			for (int i = 0; i <= F3; i++) {
				if (Global.currCX + i + 2 > 19)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + i + 2][Global.currCY - 1] = 1;
			}
			if((Global.currCX + F3 + 2<20))
				Global.robotMap[Global.currCX + F3 + 2][Global.currCY - 1] = clean;
			break;
		case 'U':
			for (int i = 0; i <= F3; i++) {
				if (Global.currCX - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - i - 2][Global.currCY + 1] = 1;
			}
			if((Global.currCX - F3 -2 >0 ))
				Global.robotMap[Global.currCX - F3 - 2][Global.currCY + 1] = clean;
			break;
		case 'L':
			for (int i = 0; i <= F3; i++) {
				if (Global.currCY - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - 1][Global.currCY - i - 2] = 1;
			}
			if((Global.currCY - F3 - 2>0 ))
			Global.robotMap[Global.currCX - 1][Global.currCY - F3 - 2] = clean;

			break;
		case 'R':
			for (int i = 0; i <= F3; i++) {
				if (Global.currCY + i + 2 > 14)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + 1][Global.currCY + i + 2] = 1;
			}
			if((Global.currCY + F3 + 2<15))
			Global.robotMap[Global.currCX + 1][Global.currCY + F3 + 2] = clean;

			break;
		}
		
	}

	public void senseFL() {
		int clean=1; 
		System.out.println("The left front (f1) sensor vlaue is: "+ F1+".");
		if(F1==-1 || F1>=3)
			{F1=2;clean=0;}
		switch (ori) {
		case 'D':
			for (int i = 0; i <= F1; i++) {
				if (Global.currCX + i + 2 > 19)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + i + 2][Global.currCY + 1] = 1;
			}
			if((Global.currCX + F1 + 2<20))
			Global.robotMap[Global.currCX + F1 + 2][Global.currCY + 1] = clean;

			break;
		case 'U':
			for (int i = 0; i <= F1; i++) {
				if (Global.currCX - i - 2 < 0  )
					break; 
				// is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - i - 2][Global.currCY - 1] = 1;
			}
			if((Global.currCX - F1 - 2>0 ))
			Global.robotMap[Global.currCX - F1 - 2][Global.currCY - 1] = clean;

			break;
		case 'L':
			for (int i = 0; i <= F1; i++) {
				if (Global.currCY - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + 1][Global.currCY - i - 2] = 1;
			}
			if((Global.currCY - F1 - 2>0 ))
				Global.robotMap[Global.currCX + 1][Global.currCY - F1 - 2] = clean;
			break;
		case 'R':
			for (int i = 0; i <= F1; i++) {
				if (Global.currCY + i + 2 > 14)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - 1][Global.currCY + i + 2] = 1;
			}
			if((Global.currCY + F1 + 2<15))
			Global.robotMap[Global.currCX - 1][Global.currCY + F1 + 2] = clean;

			break;
		}
		
	}

	public void senseFM() {
		int clean=1; 
		System.out.println("The middle front (f2) sensor vlaue is: "+ F2+".");
		if(F2==-1 || F2>=3 )
			{F2=2;clean=0;}
		switch (ori) {
		case 'D':
			for (int i = 0; i <= F2; i++) {
				if (Global.currCX + i + 2 > 19 )
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + i + 2][Global.currCY] = 1;
			}
			if((Global.currCX + F2 + 2<20))
			Global.robotMap[Global.currCX + F2 + 2][Global.currCY] = clean;

			break;
		case 'U':
			System.out.println("F2 IS :" + F2);
			for (int i = 0; i <= F2; i++) {
				if (Global.currCX - i - 2 < 0 )
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - i - 2][Global.currCY] = 1;
			}
			if((Global.currCX - F2 - 2>0 )){
		    System.out.println("Facing up clean value is  : " + clean );
			Global.robotMap[Global.currCX - F2 - 2][Global.currCY] = clean;
			}
			break;
		case 'L':
			for (int i = 0; i <= F2; i++) {
				if (Global.currCY - i - 2 < 0 )
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX][Global.currCY - i - 2] = 1;
			}
			if((Global.currCY - F2 - 2>0 ))
			Global.robotMap[Global.currCX][Global.currCY - F2 - 2] = clean;

			break;
		case 'R':
			for (int i = 0; i <= F2; i++) {
				if (Global.currCY + i + 2 > 14)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX][Global.currCY + i + 2] = 1;
			}
			if((Global.currCY + F2 + 2<15))
				Global.robotMap[Global.currCX][Global.currCY + F2 + 2] = clean;

				break;
			}

		}
		
	
}

