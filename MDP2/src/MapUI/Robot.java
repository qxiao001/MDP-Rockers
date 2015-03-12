package MapUI;

import java.io.IOException;
import java.util.StringTokenizer;

import connector.fadeString;
import Global.*;
import Obstacle.Descriptor;
import connector.*;

public class Robot {

	// private int SL,SR,FL,FM,FR; // how far is the obstacles , in grids
	// private int SL_B,SR_B,FL_B,FM_B,FR_B; // if there is obstacles in front,
	// == 0
	private char ori = ' '; // orientation => U, D, L, R
	private int FL, FM, FR, L, R;
	private SensorReading sensorReading;
	int count = 0;
	private boolean noError = true;
	//private Timer t=new Timer(5000);
	private boolean readyToRead=false; 
	// privat int loop=3;
	// for whether to set obs in the robot map

	public Robot() {
		ori = 'U';
		sensorReading = new SensorReading();
	}

	public void readWithoutComma() { //in this read the string send got no ,
		String str = "";
		boolean notOk = true;//int sendalready=0;
		StringTokenizer st = null;
		String checkString="";
		
			while(!readyToRead){
				try {
					checkString = Global.c.myReceive();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(checkString.charAt(0)=='O' && checkString.charAt(1)=='K') readyToRead=true;
				
			}
		
			while (notOk ) {
				try {
				
					String str1 = Global.c.myReceive();
					str=str1.trim();
					FL = (int)(str.charAt(0));
					FM = (int)(str.charAt(1));
					FR = (int)(str.charAt(2));
					L = (int)(str.charAt(3));
					R = (int)(str.charAt(4));
					notOk=false;
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					notOk=true;
					e.printStackTrace();
				}
			}
			//System.out.println("Recevied from rpi(we process this):" + str);
			
			//System.out.print("FrontLeft = " + FL + ", ");
			//System.out.print("FrontMiddle = " + FM + ", ");
			//System.out.print("FrontRight =  " + FR + ", ");
			//System.out.print("Left =  " + L + ", ");
			//System.out.println("Right = " + R);
			str=null;
			
			readyToRead=false;
	//	t.reset();
}

	public void read() {
		String str = "";
		boolean notOk = true;int sendalready=0;
		StringTokenizer st = null;
		String checkString="";
		while(!readyToRead){
			try {
				checkString = Global.c.myReceive();
				System.out.println("I get the string before processing: "+checkString);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(checkString.charAt(0)=='O' && checkString.charAt(1)=='K') 
			{
				readyToRead=true;
			}

		}
		while (notOk ) {
			try {
			
				String str1 = Global.c.myReceive();
				str=str1.trim();
				
				String ans = str.split(",")[0] + ","+str.split(",")[1] + "," + str.split(",")[2] + "," + str.split(",")[3] + ","+str.split(",")[4];
				st = new StringTokenizer(ans, ",\n");
				if (st.countTokens() == 5)
					{notOk = false;}
				else
				{
					//System.out.println("There is error in reading string: "	+ str);
					//System.out.println("The st count is "+st.countTokens());
					Global.c.mySend("F000/");
					System.out.println("I have sent robot to make no movement F000");
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Recevied from rpi(we process this):" + str);
		FL = Integer.parseInt(st.nextToken());
		FM = Integer.parseInt(st.nextToken());
		FR = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		//System.out.print("FrontLeft = " + FL + ", ");
		//System.out.print("FrontMiddle = " + FM + ", ");
		//System.out.print("FrontRight =  " + FR + ", ");
		//System.out.print("Left =  " + L + ", ");
		//System.out.println("Right = " + R);
		str=null;
		//sendalready=0;
		
		readyToRead=false;
	//	t.reset();
	}

	public void senseEnvironment() throws IOException {

		// robot will update 5 readings to the explore map and robot map
		checkOri();
		if (Global.realRun == true) {
			read();
			//readWithoutComma();
		} else {
			String str1 = sensorReading.senseEnvironment();
			//String str1 = fadeString.getString();
			getReadings(str1);
		}
		// String str1 = fadeString.getString(); // our fade string
		// String str1 = sensorReading.senseEnvironment();

		senseSL1();
		senseSR1();
		senseFL1();
		senseFR1();
		senseFM1();
		// Descriptor.generateExploreMap();
		// Descriptor.generateObstacleMap();
		Simulator.paintRobotMap();

	}

	public void getReadings(String str) {
		// 5,4,3,2,1 F1 F2 F3 L R 1,2,3 FROM LEFT TO RIGHT
		try {
			//System.out.println(str);
			String[] parts = str.split(",");
			FL = Integer.parseInt(parts[0]);
			FM = Integer.parseInt(parts[1]);
			FR = Integer.parseInt(parts[2]);
			L = Integer.parseInt(parts[3]);
			R = Integer.parseInt(parts[4]);

		} catch (Exception e) {
			System.out.println("There is an error in getReading");
			noError = false;
			count = 0;
			/*
			 * try { senseEnvironment(); } catch (IOException e1) { // TODO
			 * Auto-generated catch block
			 * System.out.println("The error is quite serious XP"); }
			 */
			//
		}
		//System.out.println("F1 " + FL);
		//System.out.println("F2 " + FM);
		//System.out.println("F3 " + FR);
		//System.out.println("L " + L);
		//System.out.println("R " + R);
		// System.out.println(F1);
		// if (F1==-1)F1=0;if(F2==-1)F2=0;
		// if(F3==-1)F3=0;if(L==-1)L=0;if(R==-1)R=0;
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
		int clean = 1;
		//System.out.println("The left sensor vlaue is: " + L + ".");
		if (L == -1 || L >= 3) // no obs to set
		{
			L = 2;
			clean = 0;
		}
		switch (ori) {
		case 'U':
			for (int i = 0; i <= L; i++) {
				if ((Global.currCY - i - 2) < 0)
					break;
				Global.exploreMap[Global.currCX][Global.currCY - i - 2] = 1;
			}
			// System.out.println("The out of bound index is" +
			// (Global.currCY-L-2));
			if ((Global.currCY - L - 2) >= 0)
				Global.robotMap[Global.currCX][Global.currCY - L - 2] = clean;
			break;
		case 'D':
			for (int i = 0; i <= L; i++) {
				if (Global.currCY + i + 2 > 14)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX][Global.currCY + i + 2] = 1;
			}
			if (((Global.currCY + L + 2) < 15))
				Global.robotMap[Global.currCX][Global.currCY + L + 2] = clean;
			break;
		case 'R':
			for (int i = 0; i <= L; i++) {
				if (Global.currCX - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - i - 2][Global.currCY] = 1;
			}
			if ((Global.currCX - L - 2 >= 0))
				Global.robotMap[Global.currCX - L - 2][Global.currCY] = clean;
			break;
		case 'L':
			for (int i = 0; i <= L; i++) {
				if (Global.currCX + i + 2 > 19)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + i + 2][Global.currCY] = 1;
			}
			if ((Global.currCX + L + 2 < 20))
				Global.robotMap[Global.currCX + L + 2][Global.currCY] = clean;
			break;
		}

	}

	public void senseSR() {
		int clean = 1;
		//System.out.println("The right sensor vlaue is: " + R + ".");
		if (R == -1 || R >= 3) {
			R = 2;
			clean = 0;
		}
		switch (ori) {
		case 'D':
			for (int i = 0; i <= R; i++) {
				if (Global.currCY - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX][Global.currCY - i - 2] = 1;
			}
			if ((Global.currCY - R - 2 > 0))
				Global.robotMap[Global.currCX][Global.currCY - R - 2] = clean;
			break;
		case 'U':
			for (int i = 0; i <= R; i++) {
				if (Global.currCY + i + 2 > 14)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX][Global.currCY + i + 2] = 1;
			}
			if ((Global.currCY + R + 2 < 15))
				Global.robotMap[Global.currCX][Global.currCY + R + 2] = clean;
			break;
		case 'L':
			for (int i = 0; i <= R; i++) {
				if (Global.currCX - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - i - 2][Global.currCY] = 1;
			}
			if ((Global.currCX - R - 2 > 0))
				Global.robotMap[Global.currCX - R - 2][Global.currCY] = clean;
			break;
		case 'R':
			for (int i = 0; i <= R; i++) {
				if (Global.currCX + i + 2 > 19)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + i + 2][Global.currCY] = 1;
			}
			if ((Global.currCX + R + 2 < 20))
				Global.robotMap[Global.currCX + R + 2][Global.currCY] = clean;
			break;
		}

	}

	public void senseFR() {
		int clean = 1;
		//System.out.println("The right front (f3) sensor vlaue is: " + FR + ".");
		if (FR == -1 || FR >= 3) {
			FR = 2;
			clean = 0;
		}
		switch (ori) {
		case 'D':
			for (int i = 0; i <= FR; i++) {
				if (Global.currCX + i + 2 > 19)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + i + 2][Global.currCY - 1] = 1;
			}
			if ((Global.currCX + FR + 2 < 20))
				Global.robotMap[Global.currCX + FR + 2][Global.currCY - 1] = clean;
			break;
		case 'U':
			for (int i = 0; i <= FR; i++) {
				if (Global.currCX - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - i - 2][Global.currCY + 1] = 1;
			}
			if ((Global.currCX - FR - 2 > 0))
				Global.robotMap[Global.currCX - FR - 2][Global.currCY + 1] = clean;
			break;
		case 'L':
			for (int i = 0; i <= FR; i++) {
				if (Global.currCY - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - 1][Global.currCY - i - 2] = 1;
			}
			if ((Global.currCY - FR - 2 > 0))
				Global.robotMap[Global.currCX - 1][Global.currCY - FR - 2] = clean;

			break;
		case 'R':
			for (int i = 0; i <= FR; i++) {
				if (Global.currCY + i + 2 > 14)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + 1][Global.currCY + i + 2] = 1;
			}
			if ((Global.currCY + FR + 2 < 15))
				Global.robotMap[Global.currCX + 1][Global.currCY + FR + 2] = clean;

			break;
		}

	}

	public void senseFL() {
		int clean = 1;
		//System.out.println("The left front (f1) sensor vlaue is: " + FL + ".");
		if (FL == -1 || FL >= 3) {
			FL = 2;
			clean = 0;
		}
		switch (ori) {
		case 'D':
			for (int i = 0; i <= FL; i++) {
				if (Global.currCX + i + 2 > 19)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + i + 2][Global.currCY + 1] = 1;
			}
			if ((Global.currCX + FL + 2 < 20))
				Global.robotMap[Global.currCX + FL + 2][Global.currCY + 1] = clean;

			break;
		case 'U':
			for (int i = 0; i <= FL; i++) {
				if (Global.currCX - i - 2 < 0)
					break;
				// is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - i - 2][Global.currCY - 1] = 1;
			}
			if ((Global.currCX - FL - 2 > 0))
				Global.robotMap[Global.currCX - FL - 2][Global.currCY - 1] = clean;

			break;
		case 'L':
			for (int i = 0; i <= FL; i++) {
				if (Global.currCY - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + 1][Global.currCY - i - 2] = 1;
			}
			if ((Global.currCY - FL - 2 > 0))
				Global.robotMap[Global.currCX + 1][Global.currCY - FL - 2] = clean;
			break;
		case 'R':
			for (int i = 0; i <= FL; i++) {
				if (Global.currCY + i + 2 > 14)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - 1][Global.currCY + i + 2] = 1;
			}
			if ((Global.currCY + FL + 2 < 15))
				Global.robotMap[Global.currCX - 1][Global.currCY + FL + 2] = clean;

			break;
		}

	}

	public void senseFM() {
		int clean = 1;
		//System.out.println("The middle front (f2) sensor vlaue is: " + FM + ".");
		if (FM == -1 || FM >= 3) {FM = 2;clean = 0;} 
		switch (ori) {
		case 'D':
			for (int i = 0; i <= FM; i++) {
				if (Global.currCX + i + 2 > 19)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + i + 2][Global.currCY] = 1;
			}
			
			if ((Global.currCX + FM + 2 < 20))
				Global.robotMap[Global.currCX + FM + 2][Global.currCY] = clean;

			break;
		case 'U':
			//System.out.println("F2 IS :" + FM);
			for (int i = 0; i <= FM; i++) {
				if (Global.currCX - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - i - 2][Global.currCY] = 1;
			}
			if ((Global.currCX - FM - 2 > 0)) {
				//System.out.println("Facing up clean value is  : " + clean);
				Global.robotMap[Global.currCX - FM - 2][Global.currCY] = clean;
			}
			break;
		case 'L':
			for (int i = 0; i <= FM; i++) {
				if (Global.currCY - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX][Global.currCY - i - 2] = 1;
			}
			if ((Global.currCY - FM - 2 > 0))
				Global.robotMap[Global.currCX][Global.currCY - FM - 2] = clean;

			break;
		case 'R':
			for (int i = 0; i <= FM; i++) {
				if (Global.currCY + i + 2 > 14)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX][Global.currCY + i + 2] = 1;
			}
			if ((Global.currCY + FM + 2 < 15))
				Global.robotMap[Global.currCX][Global.currCY + FM + 2] = clean;

			break;
		}

	}

	public void senseSL1() {
		int clean=1; 
		//System.out.println("The left sensor vlaue is: "+ L +".");
		int actualReading=L;
		if(L==-1 || L>=3) //no obs to set
			{L=2;}
		int currValue;
		switch (ori) {
		
		case 'U':
			for(int i=0;i<=L;i++){
				if((Global.currCY-i-2)<0) 
					break;
				Global.exploreMap[Global.currCX][Global.currCY - i - 2] = 1;
			}
			//System.out.println("The out of bound index is" + (Global.currCY-L-2));
			for(int i=0;i<=L;i++){
				if(i==actualReading) //for when the obs is 2,1,0 grid away
				{	if((Global.currCY - i -2)>=0 )//checking out of bound or not
					{  //since you sense as 
						currValue=Global.robotMap[Global.currCX][Global.currCY - i - 2];
							Global.robotMap[Global.currCX][Global.currCY - i - 2] = currValue+1;

					}
					break;
				}
				else
				{
					if((Global.currCY - i -2)>=0 )
					{
						currValue=Global.robotMap[Global.currCX][Global.currCY - i - 2];
						if(currValue>0)
							Global.robotMap[Global.currCX][Global.currCY - i - 2] = currValue-1;
					}
				}
			}
			
			break;
		case 'D':
			for (int i = 0; i <= L; i++) {
				if (Global.currCY + i + 2 > 14)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX][Global.currCY + i + 2] = 1;
			}
			//for robotmap
			for(int i=0;i<=L;i++){
				if(i==actualReading) //for when the obs is 2,1,0 grid away
				{	if((Global.currCY + i +2)<15 )//checking out of bound or not
					{  //since you sense as 
						currValue=Global.robotMap[Global.currCX][Global.currCY + i + 2];
							Global.robotMap[Global.currCX][Global.currCY + i + 2] = currValue+1;

					}
					break;
				}
				else
				{
					if((Global.currCY + i +2)<15 )
					{
						currValue=Global.robotMap[Global.currCX][Global.currCY + i + 2];
						if(currValue>0)
							Global.robotMap[Global.currCX][Global.currCY + i + 2] = currValue-1;
					}
				}
			}
			break;
		case 'R':
			for (int i = 0; i <= L; i++) {
				if (Global.currCX - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - i - 2][Global.currCY ] = 1;
			}
			//for robotmap
			for(int i=0;i<=L;i++){
				if(i==actualReading) //for when the obs is 2,1,0 grid away
				{	if((Global.currCX - i - 2)>=0 )//checking out of bound or not
					{  //since you sense as 
						currValue=Global.robotMap[Global.currCX-i-2][Global.currCY ];
							Global.robotMap[Global.currCX-i-2][Global.currCY] = currValue+1;

					}
					break;
				}
				else
				{
					if((Global.currCX - i - 2)>=0 )
					{
						currValue=Global.robotMap[Global.currCX-i-2][Global.currCY ];
						if(currValue>0)
							Global.robotMap[Global.currCX-i-2][Global.currCY ] = currValue-1;
					}
				}
			}
			break;
		case 'L':
			for (int i = 0; i <= L; i++) {
				if (Global.currCX + i + 2 > 19)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + i + 2][Global.currCY ] = 1;
			}
			//for robotmap
			for(int i=0;i<=L;i++){
				if(i==actualReading) //for when the obs is 2,1,0 grid away
				{	if((Global.currCX + i + 2)<20 )//checking out of bound or not
					{  //since you sense as 
						currValue=Global.robotMap[Global.currCX+i+2][Global.currCY ];
							Global.robotMap[Global.currCX+i+2][Global.currCY] = currValue+1;

					}
					break;
				}
				else
				{
					if((Global.currCX + i + 2)<20 )
					{
						currValue=Global.robotMap[Global.currCX+i+2][Global.currCY ];
						if(currValue>0)
							Global.robotMap[Global.currCX+i+2][Global.currCY ] = currValue-1;
					}
				}
			}
			
			break;
		}
	}
	public void senseSR1() {
		int clean = 1;
		//System.out.println("The right sensor vlaue is: " + R + ".");
		int actualReading=R;int currValue;
		if (R == -1 || R >= 3) {R = 2;}
		switch (ori) {
		case 'D':
			for (int i = 0; i <= R; i++) {
				if (Global.currCY - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX][Global.currCY - i - 2] = 1;
			}
			//for robotmap
			for(int i=0;i<=R;i++){
				if(i==actualReading) //for when the obs is 2,1,0 grid away
				{	if((Global.currCY - i -2)>=0 )//checking out of bound or not
					{  //since you sense as 
						currValue=Global.robotMap[Global.currCX][Global.currCY - i - 2];
							Global.robotMap[Global.currCX][Global.currCY - i - 2] = currValue+1;

					}
					break;
				}
				else
				{
					if((Global.currCY - i -2)>=0 )
					{
						currValue=Global.robotMap[Global.currCX][Global.currCY - i - 2];
						if(currValue>0)
							Global.robotMap[Global.currCX][Global.currCY - i - 2] = currValue-1;
					}
				}
			}
			
			break;
		case 'U':
			for (int i = 0; i <= R; i++) {
				if (Global.currCY + i + 2 > 14)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX][Global.currCY + i + 2] = 1;
			}
			//for robotmap
			for(int i=0;i<=R;i++){
				if(i==actualReading) //for when the obs is 2,1,0 grid away
				{	if((Global.currCY + i +2)<15 )//checking out of bound or not
					{  //since you sense as 
						currValue=Global.robotMap[Global.currCX][Global.currCY + i + 2];
						Global.robotMap[Global.currCX][Global.currCY + i + 2] = currValue+1;

					}
					break;
				}
				else
				{
					if((Global.currCY + i +2)<15 )
					{
						currValue=Global.robotMap[Global.currCX][Global.currCY + i + 2];
						if(currValue>0)
							Global.robotMap[Global.currCX][Global.currCY + i + 2] = currValue-1;
					}
				}
			}
			
			break;
		case 'L':
			for (int i = 0; i <= R; i++) {
				if (Global.currCX - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - i - 2][Global.currCY] = 1;
			}
			
			//for robotmap
			for(int i=0;i<=R;i++){
				if(i==actualReading) //for when the obs is 2,1,0 grid away
				{	if((Global.currCX - i - 2)>=0 )//checking out of bound or not
					{  //since you sense as 
						currValue=Global.robotMap[Global.currCX-i-2][Global.currCY];
						Global.robotMap[Global.currCX-i-2][Global.currCY] = currValue+1;

					}
					break;
				}
				else
				{
					if((Global.currCX - i - 2)>=0 )
					{
						currValue=Global.robotMap[Global.currCX-i-2][Global.currCY];
						if(currValue>0)
							Global.robotMap[Global.currCX-i-2][Global.currCY] = currValue-1;
					}
				}
			}
			

			break;
		case 'R':
			for (int i = 0; i <= R; i++) {
				if (Global.currCX + i + 2 > 19)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + i + 2][Global.currCY] = 1;
			}
			
			//for robotmap
			for(int i=0;i<=R;i++){
				if(i==actualReading) //for when the obs is 2,1,0 grid away
				{	if((Global.currCX + i + 2)<20 )//checking out of bound or not
					{  //since you sense as 
						currValue=Global.robotMap[Global.currCX+i+2][Global.currCY];
						Global.robotMap[Global.currCX+i+2][Global.currCY]= currValue+1;

					}
					break;
				}
				else
				{
					if((Global.currCX + i + 2)<20 )
					{
						currValue=Global.robotMap[Global.currCX+i+2][Global.currCY];
						if(currValue>0)
							Global.robotMap[Global.currCX+i+2][Global.currCY]= currValue-1;
					}
				}
			}
			
			break;
		}

	}
	public void senseFL1() {
		
		//System.out.println("The left front (f1) sensor vlaue is: " + FL + ".");
		int actualReading=FL;int currValue;
		if (FL == -1 || FL >= 3) {
			FL = 2;		}
		switch (ori) {
		case 'D':
			for (int i = 0; i <= FL; i++) {
				if (Global.currCX + i + 2 > 19)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + i + 2][Global.currCY + 1] = 1;
			}
			
			//for robotmap
			for(int i=0;i<=FL;i++){
				if(i==actualReading) //for when the obs is 2,1,0 grid away
				{	if((Global.currCX + i + 2)<20 )//checking out of bound or not
					{  //since you sense as 
						currValue=Global.robotMap[Global.currCX+i+2][Global.currCY+1 ];
							Global.robotMap[Global.currCX+i+2][Global.currCY+1] = currValue+1;

					}
					break;
				}
				else
				{
					if((Global.currCX + i + 2)<20 )
					{
						currValue=Global.robotMap[Global.currCX+i+2][Global.currCY+1 ];
						if(currValue>0)
							Global.robotMap[Global.currCX+i+2][Global.currCY+1 ] = currValue-1;
					}
				}
			}
			break;
		case 'U':
			for (int i = 0; i <= FL; i++) {
				if (Global.currCX - i - 2 < 0)
					break;
				// is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - i - 2][Global.currCY - 1] = 1;
			}
			
			//for robotmap
			for(int i=0;i<=FL;i++){
				if(i==actualReading) //for when the obs is 2,1,0 grid away
				{	if((Global.currCX - i - 2) >0)//checking out of bound or not
					{  //since you sense as 
						currValue=Global.robotMap[Global.currCX-i-2][Global.currCY-1 ];
						Global.robotMap[Global.currCX-i-2][Global.currCY-1 ] = currValue+1;

					}
					break;
				}
				else
				{
					if((Global.currCX - i - 2) >0 )
					{
						currValue=Global.robotMap[Global.currCX-i-2][Global.currCY-1 ];
						if(currValue>0)
							Global.robotMap[Global.currCX-i-2][Global.currCY-1 ] = currValue-1;
					}
				}
			}

			break;
		case 'L':
			for (int i = 0; i <= FL; i++) {
				if (Global.currCY - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + 1][Global.currCY - i - 2] = 1;
			}
			//for robotmap
			for(int i=0;i<=FL;i++){
				if(i==actualReading) //for when the obs is 2,1,0 grid away
				{	if((Global.currCY - i - 2) >0)//checking out of bound or not
					{  //since you sense as 
						currValue=Global.robotMap[Global.currCX+1][Global.currCY-i-2];
						Global.robotMap[Global.currCX+1][Global.currCY-i-2] = currValue+1;

					}
					break;
				}
				else
				{
					if((Global.currCY - i - 2) >0 )
					{
						currValue=Global.robotMap[Global.currCX+1][Global.currCY-i-2];
						if(currValue>0)
							Global.robotMap[Global.currCX+1][Global.currCY-i-2] = currValue-1;
					}
				}
			}
			
			break;
		case 'R':
			for (int i = 0; i <= FL; i++) {
				if (Global.currCY + i + 2 > 14)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - 1][Global.currCY + i + 2] = 1;
			}
			//for robotmap
			for(int i=0;i<=FL;i++){
				if(i==actualReading) //for when the obs is 2,1,0 grid away
				{	if((Global.currCY + i + 2) <15)//checking out of bound or not
					{  //since you sense as 
						currValue=Global.robotMap[Global.currCX-1][Global.currCY+i+2];
						Global.robotMap[Global.currCX-1][Global.currCY+i+2] = currValue+1;

					}
					break;
				}
				else
				{
					if((Global.currCY + i + 2) <15 )
					{
						currValue=Global.robotMap[Global.currCX-1][Global.currCY+i+2];
						if(currValue>0)
							Global.robotMap[Global.currCX-1][Global.currCY+i+2] = currValue-1;
					}
				}
			}
			
			break;
		}

	}	
	public void senseFM1() {
			
			int actualReading=FM;
			int currValue;
			//System.out.println("The middle front (f2) sensor vlaue is: " + FM + ".");
			if (FM == -1 || FM >= 3) {FM = 2;} 
			switch (ori) {
			case 'D':
				for (int i = 0; i <= FM; i++) {
					if (Global.currCX + i + 2 > 19)
						break; // is it right? so as not to less than 0
					Global.exploreMap[Global.currCX + i + 2][Global.currCY] = 1;
				}
				//for robotmap
				for(int i=0;i<=FM;i++){
					if(i==actualReading) //for when the obs is 2,1,0 grid away
					{	if((Global.currCX + i + 2 < 20))//checking out of bound or not
						{  //since you sense as 
							currValue=Global.robotMap[Global.currCX+i+2][Global.currCY ];
							Global.robotMap[Global.currCX+i+2][Global.currCY ] = currValue+1;
	
						}
						break;
					}
					else
					{
						if((Global.currCX + i + 2 < 20) )
						{
							currValue=Global.robotMap[Global.currCX+i+2][Global.currCY ];
							if(currValue>0)
								Global.robotMap[Global.currCX+i+2][Global.currCY ] = currValue-1;
						}
					}
				}
				
	
				break;
			case 'U':
				//System.out.println("F2 IS :" + FM);
				for (int i = 0; i <= FM; i++) {
					if (Global.currCX - i - 2 < 0)
						break; // is it right? so as not to less than 0
					Global.exploreMap[Global.currCX - i - 2][Global.currCY] = 1;
				}
				//for robotmap
				for(int i=0;i<=FM;i++){
					if(i==actualReading) //for when the obs is 2,1,0 grid away
					{	if((Global.currCX - i - 2) >0)//checking out of bound or not
						{  //since you sense as 
							currValue=Global.robotMap[Global.currCX-i-2][Global.currCY ];
							Global.robotMap[Global.currCX-i-2][Global.currCY ] = currValue+1;
	
						}
						break;
					}
					else
					{
						if((Global.currCX - i - 2) >0 )
						{
							currValue=Global.robotMap[Global.currCX-i-2][Global.currCY ];
							if(currValue>0)
								Global.robotMap[Global.currCX-i-2][Global.currCY ] = currValue-1;
						}
					}
				}
				
				break;
			case 'L':
				for (int i = 0; i <= FM; i++) {
					if (Global.currCY - i - 2 < 0)
						break; // is it right? so as not to less than 0
					Global.exploreMap[Global.currCX][Global.currCY - i - 2] = 1;
				}
				//for robotmap
				for(int i=0;i<=FM;i++){
					if(i==actualReading) //for when the obs is 2,1,0 grid away
					{	if((Global.currCY - i - 2) >0)//checking out of bound or not
						{  //since you sense as 
							currValue=Global.robotMap[Global.currCX][Global.currCY-i-2];
							Global.robotMap[Global.currCX][Global.currCY-i-2] = currValue+1;
	
						}
						break;
					}
					else
					{
						if((Global.currCY - i - 2) >0 )
						{
							currValue=Global.robotMap[Global.currCX][Global.currCY-i-2];
							if(currValue>0)
								Global.robotMap[Global.currCX][Global.currCY-i-2] = currValue-1;
						}
					}
				}
				break;
			case 'R':
				for (int i = 0; i <= FM; i++) {
					if (Global.currCY + i + 2 > 14)
						break; // is it right? so as not to less than 0
					Global.exploreMap[Global.currCX][Global.currCY + i + 2] = 1;
				}
				//for robotmap
				for(int i=0;i<=FM;i++){
					if(i==actualReading) //for when the obs is 2,1,0 grid away
					{	if((Global.currCY + i + 2) <15)//checking out of bound or not
						{  //since you sense as 
							currValue=Global.robotMap[Global.currCX][Global.currCY+i+2];
							Global.robotMap[Global.currCX][Global.currCY+i+2] = currValue+1;
	
						}
						break;
					}
					else
					{
						if((Global.currCY + i + 2) <15 )
						{
							currValue=Global.robotMap[Global.currCX][Global.currCY+i+2];
							if(currValue>0)
								Global.robotMap[Global.currCX][Global.currCY+i+2] = currValue-1;
						}
					}
				}
				
	
				break;
			}
	
		}
	public void senseFR1() {
		
		int actualReading=FR;
		int currValue;
		//System.out.println("The right front (f3) sensor vlaue is: " + FR + ".");
		if (FR == -1 || FR >= 3) {
			FR = 2;
			
		}
		switch (ori) {
		case 'D':
			for (int i = 0; i <= FR; i++) {
				if (Global.currCX + i + 2 > 19)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + i + 2][Global.currCY - 1] = 1;
			}
			//for robotmap
			for(int i=0;i<=FR;i++){
				if(i==actualReading) //for when the obs is 2,1,0 grid away
				{	if((Global.currCX + i + 2 < 20))//checking out of bound or not
					{  //since you sense as 
						currValue=Global.robotMap[Global.currCX+i+2][Global.currCY-1 ];
						Global.robotMap[Global.currCX+i+2][Global.currCY-1 ] = currValue+1;

					}
					break;
				}
				else
				{
					if((Global.currCX + i + 2 < 20) )
					{
						currValue=Global.robotMap[Global.currCX+i+2][Global.currCY-1 ];
						if(currValue>0)
							Global.robotMap[Global.currCX+i+2][Global.currCY-1 ] = currValue-1;
					}
				}
			}
			
			break;
		case 'U':
			for (int i = 0; i <= FR; i++) {
				if (Global.currCX - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - i - 2][Global.currCY + 1] = 1;
			}
			//for robotmap
			for(int i=0;i<=FR;i++){
				if(i==actualReading) //for when the obs is 2,1,0 grid away
				{	if((Global.currCX - i - 2) >0)//checking out of bound or not
					{  //since you sense as 
						currValue=Global.robotMap[Global.currCX-i-2][Global.currCY+1 ];
						Global.robotMap[Global.currCX-i-2][Global.currCY +1] = currValue+1;

					}
					break;
				}
				else
				{
					if((Global.currCX - i - 2) >0 )
					{
						currValue=Global.robotMap[Global.currCX-i-2][Global.currCY+1 ];
						if(currValue>0)
							Global.robotMap[Global.currCX-i-2][Global.currCY+1 ] = currValue-1;
					}
				}
			}
			
			break;
		case 'L':
			for (int i = 0; i <= FR; i++) {
				if (Global.currCY - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - 1][Global.currCY - i - 2] = 1;
			}
			//for robotmap
			for(int i=0;i<=FR;i++){
				if(i==actualReading) //for when the obs is 2,1,0 grid away
				{	if((Global.currCY - i - 2) >0)//checking out of bound or not
					{  //since you sense as 
						currValue=Global.robotMap[Global.currCX-1][Global.currCY-i-2];
						Global.robotMap[Global.currCX-1][Global.currCY-i-2] = currValue+1;

					}
					break;
				}
				else
				{
					if((Global.currCY - i - 2) >0 )
					{
						currValue=Global.robotMap[Global.currCX-1][Global.currCY-i-2];
						if(currValue>0)
							Global.robotMap[Global.currCX-1][Global.currCY-i-2] = currValue-1;
					}
				}
			}
			

			break;
		case 'R':
			for (int i = 0; i <= FR; i++) {
				if (Global.currCY + i + 2 > 14)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + 1][Global.currCY + i + 2] = 1;
			}
			//for robotmap
			for(int i=0;i<=FR;i++){
				if(i==actualReading) //for when the obs is 2,1,0 grid away
				{	if((Global.currCY + i + 2) <15)//checking out of bound or not
					{  //since you sense as 
						currValue=Global.robotMap[Global.currCX+1][Global.currCY+i+2];
						Global.robotMap[Global.currCX+1][Global.currCY+i+2] = currValue+1;

					}
					break;
				}
				else
				{
					if((Global.currCY + i + 2) <15 )
					{
						currValue=Global.robotMap[Global.currCX+1][Global.currCY+i+2];
						if(currValue>0)
							Global.robotMap[Global.currCX+1][Global.currCY+i+2] = currValue-1;
					}
				}
			}
			
		

			break;
		}

	}

		
	}
