
public class RobotReal {

	// private int SL,SR,FL,FM,FR; // how far is the obstacles , in grids
	// private int SL_B,SR_B,FL_B,FM_B,FR_B; // if there is obstacles in front,
	// == 0
	private char ori = ' '; // orientation => U, D, L, R
	private int F1, F2, F3, L, R;

	public RobotReal() {
		ori = 'U';
	}

	public void senseEnvironment() {
		// robot will update 5 readings to the explore map and robot map
		// checkOri();
		senseSL();
		senseSR();
		senseFL();
		senseFR();
		senseFM();

	}

	public void getReadings(String str) {
		// 5,4,3,2,1 F1 F2 F3 L R 1,2,3 FROM LEFT TO RIGHT
		String[] parts = str.split(",");
		F1 = Integer.parseInt(parts[0]);
		F2 = Integer.parseInt(parts[1]);
		F3 = Integer.parseInt(parts[2]);
		L = Integer.parseInt(parts[3]);
		R = Integer.parseInt(parts[4]);
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
			for (int i = 0; i < L; i++) {
				if (Global.currCY - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - 1][Global.currCY - i - 2] = 1;
			}
			Global.robotMap[Global.currCX - 1][Global.currCY - L - 2] = 1;
			break;
		case 'D':
			for (int i = 0; i < L; i++) {
				if (Global.currCY + i + 2 > 14)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + 1][Global.currCY + i + 2] = 1;
			}
			Global.robotMap[Global.currCX + 1][Global.currCY + L + 2] = 1;
			break;
		case 'R':
			for (int i = 0; i < L; i++) {
				if (Global.currCX - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - i - 2][Global.currCY + 1] = 1;
			}
			Global.robotMap[Global.currCX - L - 2][Global.currCY + 1] = 1;
			break;
		case 'L':
			for (int i = 0; i < L; i++) {
				if (Global.currCX + i + 2 > 19)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + i + 2][Global.currCY - 1] = 1;
			}
			Global.robotMap[Global.currCX + L + 2][Global.currCY - 1] = 1;
			break;
		}

	}

	public void senseSR() {
		switch (ori) {
		case 'D':
			for (int i = 0; i < R; i++) {
				if (Global.currCY - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - 1][Global.currCY - i - 2] = 1;
			}
			Global.robotMap[Global.currCX - 1][Global.currCY - R - 2] = 1;
			break;
		case 'U':
			for (int i = 0; i < R; i++) {
				if (Global.currCY + i + 2 > 14)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + 1][Global.currCY + i + 2] = 1;
			}
			Global.robotMap[Global.currCX + 1][Global.currCY + R + 2] = 1;

			break;
		case 'L':
			for (int i = 0; i < R; i++) {
				if (Global.currCX - R < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - i - 2][Global.currCY + 1] = 1;
			}
			Global.robotMap[Global.currCX - R - 2][Global.currCY + 1] = 1;

			break;
		case 'R':
			for (int i = 0; i < R; i++) {
				if (Global.currCX + i + 2 > 19)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + i + 2][Global.currCY - 1] = 1;
			}
			Global.robotMap[Global.currCX + R + 2][Global.currCY - 1] = 1;

			break;
		}

	}

	public void senseFR() {
		switch (ori) {
		case 'D':
			for (int i = 0; i < F3; i++) {
				if (Global.currCX + i + 2 > 19)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + i + 2][Global.currCY - 1] = 1;
			}
			Global.robotMap[Global.currCX + F3 + 2][Global.currCY - 1] = 1;

			break;
		case 'U':
			for (int i = 0; i < F3; i++) {
				if (Global.currCX - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - i - 2][Global.currCY + 1] = 1;
			}
			Global.robotMap[Global.currCX - F3 - 2][Global.currCY + 1] = 1;

			break;
		case 'L':
			for (int i = 0; i < F3; i++) {
				if (Global.currCY - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - 1][Global.currCY - i - 2] = 1;
			}
			Global.robotMap[Global.currCX - 1][Global.currCY - F3 - 2] = 1;

			break;
		case 'R':
			for (int i = 0; i < F3; i++) {
				if (Global.currCY + i + 2 > 14)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + 1][Global.currCY + i + 2] = 1;
			}
			Global.robotMap[Global.currCX + 1][Global.currCY + F3 + 2] = 1;

			break;
		}

	}

	public void senseFL() {
		switch (ori) {
		case 'D':
			for (int i = 0; i < F1; i++) {
				if (Global.currCX + i + 2 > 19)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + i + 2][Global.currCY + 1] = 1;
			}
			Global.robotMap[Global.currCX + F1 + 2][Global.currCY + 1] = 1;

			break;
		case 'U':
			for (int i = 0; i < F1; i++) {
				if (Global.currCX - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - i - 2][Global.currCY - 1] = 1;
			}
			Global.robotMap[Global.currCX - F1 - 2][Global.currCY - 1] = 1;

			break;
		case 'L':
			for (int i = 0; i < F1; i++) {
				if (Global.currCY - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + 1][Global.currCY - i - 2] = 1;
			}
			Global.robotMap[Global.currCX + 1][Global.currCY - F1 - 2] = 1;

			break;
		case 'R':
			for (int i = 0; i < F1; i++) {
				if (Global.currCY + i + 2 > 14)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - 1][Global.currCY + i + 2] = 1;
			}
			Global.robotMap[Global.currCX - 1][Global.currCY + F1 + 2] = 1;

			break;
		}

	}

	public void senseFM() {
		switch (ori) {
		case 'D':
			for (int i = 0; i < F2; i++) {
				if (Global.currCX + i + 2 > 19)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX + i + 2][Global.currCY] = 1;
			}
			Global.robotMap[Global.currCX + F2 + 2][Global.currCY] = 1;

			break;
		case 'U':
			for (int i = 0; i < F2; i++) {
				if (Global.currCX - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX - i - 2][Global.currCY] = 1;
			}
			Global.robotMap[Global.currCX - F2 - 2][Global.currCY] = 1;

			break;
		case 'L':
			for (int i = 0; i < F2; i++) {
				if (Global.currCY - i - 2 < 0)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX][Global.currCY - i - 2] = 1;
			}
			Global.robotMap[Global.currCX][Global.currCY - F2 - 2] = 1;

			break;
		case 'R':
			for (int i = 0; i < F2; i++) {
				if (Global.currCY + i + 2 > 14)
					break; // is it right? so as not to less than 0
				Global.exploreMap[Global.currCX][Global.currCY + i + 2] = 1;

				Global.robotMap[Global.currCX][Global.currCY + F2 + 2] = 1;

				break;
			}

		}

	}
}
