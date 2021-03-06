

public class Robot {

	// private int SL,SR,FL,FM,FR; // how far is the obstacles , in grids
	// private int SL_B,SR_B,FL_B,FM_B,FR_B; // if there is obstacles in front,
	// == 0
	private char ori = ' '; // orientation => U, D, L, R

	public Robot() {
		ori = 'U';
	}

	public void senseEnvironment(){
		// robot will return the 5 readings from sensor 
		checkOri();
		senseSL();
		senseSR();
		senseFL();
		senseFR();
		senseFM();
		
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
				if (Global.currCY - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX-1][Global.currCY - i - 2] = 1;
				if (Global.realMap[Global.currCX-1][Global.currCY - i - 2] == 1) {
					Global.robotMap[Global.currCX-1][Global.currCY - i - 2] = 1;
					break;
				}
			}
			break;
		case 'D':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCY + i + 2 > 14)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX+1][Global.currCY + i + 2] = 1;
				if (Global.realMap[Global.currCX+1][Global.currCY + i + 2] == 1) {
					Global.robotMap[Global.currCX+1][Global.currCY + i + 2] = 1;
					break;
				}
			}
			break;
		case 'R':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCX - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - i - 2][Global.currCY+1] = 1;
				if (Global.realMap[Global.currCX - i - 2][Global.currCY+1 ] == 1) {
					Global.robotMap[Global.currCX - i - 2][Global.currCY+1] = 1;
					break;
				}
			}
			break;
		case 'L':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCX + i + 2 > 19)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + i + 2][Global.currCY-1 ] = 1;
				if (Global.realMap[Global.currCX + i + 2][Global.currCY-1 ] == 1) {
					Global.robotMap[Global.currCX + i + 2][Global.currCY-1] = 1;
					break;
				}
			}
			break;
		}

	}
	public void senseSR() {
		switch (ori) {
		case 'D':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCY - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX-1][Global.currCY - i - 2] = 1;
				if (Global.realMap[Global.currCX-1][Global.currCY - i - 2] == 1) {
					Global.robotMap[Global.currCX-1][Global.currCY - i - 2] = 1;
					break;
				}
			}
			break;
		case 'U':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCY + i + 2 > 14)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX+1][Global.currCY + i + 2] = 1;
				if (Global.realMap[Global.currCX+1][Global.currCY + i + 2] == 1) {
					Global.robotMap[Global.currCX+1][Global.currCY + i + 2] = 1;
					System.out.println("obstable! "+i);
					break;
				
				}
			}
			break;
		case 'L':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCX - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX  - i - 2][Global.currCY+1] = 1;
				if (Global.realMap[Global.currCX - i - 2][Global.currCY+1 ] == 1) {
					Global.robotMap[Global.currCX  - i - 2][Global.currCY+1] = 1;
					break;
				}
			}
			break;
		case 'R':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCX + i + 2 > 19)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + i + 2][Global.currCY-1] = 1;
				if (Global.realMap[Global.currCX + i + 2][Global.currCY-1 ] == 1) {
					Global.robotMap[Global.currCX + i + 2][Global.currCY-1 ] = 1;
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
				if (Global.currCX + i + 2 > 19)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX+i+2][Global.currCY - 1] = 1;
				if (Global.realMap[Global.currCX+i+2][Global.currCY - 1] == 1) {
					Global.robotMap[Global.currCX+i+2][Global.currCY - 1] = 1;
					break;
				}
			}
			break;
		case 'U':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCX - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX-i-2][Global.currCY + 1] = 1;
				if (Global.realMap[Global.currCX-i-2][Global.currCY + 1] == 1) {
					Global.robotMap[Global.currCX-i-2][Global.currCY + 1] = 1;
					break;
				}
			}
			break;
		case 'L':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCY - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX-1][Global.currCY - i - 2] = 1;
				if (Global.realMap[Global.currCX-1][Global.currCY - i - 2] == 1) {
					Global.robotMap[Global.currCX-1][Global.currCY - i - 2] = 1;
					break;
				}
			}
			break;
		case 'R':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCY + i + 2 > 14)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX+1][Global.currCY + i + 2] = 1;
				if (Global.realMap[Global.currCX+1][Global.currCY + i + 2] == 1) {
					Global.robotMap[Global.currCX+1][Global.currCY + i + 2] = 1;
					break;
				}
			}
			break;
		}

	}
	public void senseFL() {
		switch (ori) {
		case 'D':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCX + i + 2 > 19)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX+i+2][Global.currCY + 1] = 1;
				if (Global.realMap[Global.currCX+i+2][Global.currCY + 1] == 1) {
					Global.robotMap[Global.currCX+i+2][Global.currCY + 1] = 1;
					break;
				}
			}
			break;
		case 'U':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCX - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX-i-2][Global.currCY - 1] = 1;
				if (Global.realMap[Global.currCX-i-2][Global.currCY - 1] == 1) {
					Global.robotMap[Global.currCX-i-2][Global.currCY - 1] = 1;
					break;
				}
			}
			break;
		case 'L':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCY - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX+1][Global.currCY - i - 2] = 1;
				if (Global.realMap[Global.currCX+1][Global.currCY - i - 2] == 1) {
					Global.robotMap[Global.currCX+1][Global.currCY - i - 2] = 1;
					break;
				}
			}
			break;
		case 'R':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCY + i + 2 > 14)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX-1][Global.currCY + i + 2] = 1;
				if (Global.realMap[Global.currCX-1][Global.currCY + i + 2] == 1) {
					Global.robotMap[Global.currCX-1][Global.currCY + i + 2] = 1;
					break;
				}
			}
			break;
		}

	}
	public void senseFM() {
		switch (ori) {
		case 'D':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCX + i + 2 > 19)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX+i+2][Global.currCY] = 1;
				if (Global.realMap[Global.currCX+i+2][Global.currCY] == 1) {
					Global.robotMap[Global.currCX+i+2][Global.currCY] = 1;
					break;
				}
			}
			break;
		case 'U':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCX - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX-i-2][Global.currCY] = 1;
				if (Global.realMap[Global.currCX-i-2][Global.currCY ] == 1) {
					Global.robotMap[Global.currCX-i-2][Global.currCY] = 1;
					break;
				}
			}
			break;
		case 'L':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCY - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX][Global.currCY - i - 2] = 1;
				if (Global.realMap[Global.currCX][Global.currCY - i - 2] == 1) {
					Global.robotMap[Global.currCX][Global.currCY - i - 2] = 1;
					break;
				}
			}
			break;
		case 'R':
			for (int i = 0; i < Global.senseRange; i++) {
				if (Global.currCY + i + 2 > 14)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX][Global.currCY + i + 2] = 1;
				if (Global.realMap[Global.currCX][Global.currCY + i + 2] == 1) {
					Global.robotMap[Global.currCX][Global.currCY + i + 2] = 1;
					break;
				}
			}
			break;
		}
		

	}

}
