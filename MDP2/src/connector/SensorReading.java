package connector;

import Global.*;

public class SensorReading {



	// private int SL,SR,FL,FM,FR; // how far is the obstacles , in grids
	// private int SL_B,SR_B,FL_B,FM_B,FR_B; // if there is obstacles in front,
	// == 0
	private char ori = ' '; // orientation => U, D, L, R
    private int FL,FM,FR,SL,SR;
	public SensorReading() {
		ori = 'U';
	}

	public String senseEnvironment(){
		// robot will return the 5 readings from sensor 
		checkOri();
		senseSL();
		senseSR();
		senseFL();
		senseFR();
		senseFM();
		return FL+","+FM+","+FR+","+SL+','+SR+',';
		
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
		if (Global.currFX - Global.currCX == -1) {
			ori = 'L';
			return 'L';
		} else
			return ' ';
	}

	public void senseSL() {
		switch (ori) {
		case 'U':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCY - i - 2 < 0){
					SL=i;
					break; // is it right? so as not to less than 0
				}
				if (Global.realMap[Global.currCX][Global.currCY - i - 2] == 1) {
					SL=i;
					break;
				}
			}
			SL=-1;
			break;
		case 'D':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCY + i + 2 > 14){
					SL=i;
					break; // is it right? so as not to less than 0
				}

				if (Global.realMap[Global.currCX][Global.currCY + i + 2] == 1) {
					SL=i;
					break;
				}
				
			}
			SL=-1;
			break;
		case 'R':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCX - i - 2 < 0){
					SL=i;
				    break; // is it right? so as not to less than 0
			    }
				if (Global.realMap[Global.currCX - i - 2][Global.currCY ] == 1) {
					SL=i;
					break;
				}
				
			}
			SL=-1;
			break;
		case 'L':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCX + i + 2 > 19){
					SL=i;
				    break; // is it right? so as not to less than 0
			    }
				if (Global.realMap[Global.currCX + i + 2][Global.currCY ] == 1) {
					SL=i;
					break;
				}
			}
			SL=-1;
			break;
		}

	}
	public void senseSR() {
		switch (ori) {
		case 'D':
			SR=-1;
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCY - i - 2 < 0){
					SR=i;
				    break; // is it right? so as not to less than 0
			    }
				if (Global.realMap[Global.currCX][Global.currCY - i - 2] == 1) {
					SR=i;
					break;
				}
			}
			break;
		case 'U':
			SR=-1;
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCY + i + 2 > 14){
					SR=i;
				    break; // is it right? so as not to less than 0
			    }
				if (Global.realMap[Global.currCX][Global.currCY + i + 2] == 1) {
					SR=i;
					break;
				}
			}
			
			break;
		case 'L':
			SR=-1;
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCX - i - 2 < 0){
					SR=i;
				    break; // is it right? so as not to less than 0
			    }
				if (Global.realMap[Global.currCX - i - 2][Global.currCY ] == 1) {
					SR=i;
					break;
				}
			}
			
			break;
		case 'R':
			SR=-1;
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCX + i + 2 > 19){
					SR=i;
				    break; // is it right? so as not to less than 0
			    }
				if (Global.realMap[Global.currCX + i + 2][Global.currCY ] == 1) {
					SR=i;
					break;
				}
			}
			
			break;
		}

	}
	public void senseFR() {
		switch (ori) {
		case 'D':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCX + i + 2 > 19){
					FR=i;
					break;
				}
				if (Global.realMap[Global.currCX+i+2][Global.currCY - 1] == 1) {
					FR=i;
					break;
				}
			}
			FR=-1;
			break;
		case 'U':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCX - i - 2 < 0){
					FR=i;
					break;
				}
				if (Global.realMap[Global.currCX-i-2][Global.currCY + 1] == 1) {
					FR=i;
					break;
				}
			}
			FR=-1;
			break;
		case 'L':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCY - i - 2 < 0){
					FR=i;
					break;
				}
				if (Global.realMap[Global.currCX-1][Global.currCY - i - 2] == 1) {
					FR=i;
					break;
				}
			}
			FR=-1;
			break;
		case 'R':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCY + i + 2 > 14){
					FR=i;
					break;
				}
				if (Global.realMap[Global.currCX+1][Global.currCY + i + 2] == 1) {
					FR=i;
					break;
				}
			}
			FR=-1;
			break;
		}

	}
	public void senseFL() {
		switch (ori) {
		case 'D':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCX + i + 2 > 19){
					FL=i;
					break;
				}
				if (Global.realMap[Global.currCX+i+2][Global.currCY + 1] == 1) {
					FL=i;
					break;
				}
			}
			FL=-1;
			break;
		case 'U':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCX - i - 2 < 0){
					FL=i;
					break;
				}
				if (Global.realMap[Global.currCX-i-2][Global.currCY - 1] == 1) {
					FL=i;
					break;
				}
			}
			FL=-1;
			break;
		case 'L':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCY - i - 2 < 0){
					FL=i;
					break;
				}
				if (Global.realMap[Global.currCX+1][Global.currCY - i - 2] == 1) {
					FL=i;
					break;
				}
			}
			FL=-1;
			break;
		case 'R':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCY + i + 2 > 14){
					FL=i;
					break;
				}
				if (Global.realMap[Global.currCX-1][Global.currCY + i + 2] == 1) {
					FL=i;
					break;
				}
			}
			FL=-1;
			break;
		}

	}
	public void senseFM() {
		switch (ori) {
		case 'D':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCX + i + 2 > 19){
					FM=i;
					break;
				}
				if (Global.realMap[Global.currCX+i+2][Global.currCY] == 1) {
					FM=i;
					break;
				}
			}
			FM=-1;
			break;
		case 'U':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCX - i - 2 < 0){
					FM=i;
					break;
				}
				if (Global.realMap[Global.currCX-i-2][Global.currCY ] == 1) {
					FM=i;
					break;
				}
			}
			FM=-1;
			break;
		case 'L':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCY - i - 2 < 0){
					FM=i;
					break;
				}
				if (Global.realMap[Global.currCX][Global.currCY - i - 2] == 1) {
					FM=i;
					break;
				}
			}
			FM=-1;
			break;
		case 'R':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCY + i + 2 > 14){
					FM=i;
					break;
				}
				if (Global.realMap[Global.currCX][Global.currCY + i + 2] == 1) {
					FM=i;
					break;
				}
			}
			FM=-1;
			break;
		}
		

	}

}
