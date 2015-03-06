package Obstacle;

import Global.*;

import java.lang.Math;

public class Descriptor {

	public Descriptor() {

	}

	public static void generateExploreMap() {
		// int biCount=301;
		// int sum=(int) (Math.pow(2,303)+Math.pow(2,302)+2+1);
		String temp, hex;
		// int count = 0;
		System.out.println("This is the explore map: ");
		System.out.println("11");
		temp = "11";
		hex = "";

		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 15; j++) {
				if (Global.exploreMap[i][j] == 1) {
					System.out.print(1);
					// sum+=Math.pow(2,biCount);
					temp = temp + "1";

				}
				if (Global.exploreMap[i][j] == 0) {
					System.out.print(0);
					temp = temp + "0";
				}
				if (temp.length() == 16) {
					// hex=hex+Integer.toHexString(Integer.parseInt(temp));
					hex = hex + Long.toHexString(Long.parseLong(temp, 2));
					// System.out.println(count + ": the Hex is : " + hex);
					temp = ""; // restart temp to count to 16 bits
					// count++;
				}
				// biCount--;
			}
			System.out.println();
		}
		System.out.println("11");
		temp = temp + "11";
		hex = hex + Long.toHexString(Long.parseLong(temp, 2));
		// count++;

		System.out.println("Explore map: the Hex value is : " + hex);
		// System.out.println("the counter is : " + count);
	}

	public static void generateObstacleMap() {

		String temp, hex;

		temp = "";
		hex = "";
		System.out.println("This is the obstacle map: ");
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 15; j++) {
				if (Global.exploreMap[i][j] == 1) {
					if (Global.robotMap[i][j] >= 1) {
						System.out.print(1);
						temp = temp + "1";

					}
					if (Global.robotMap[i][j] == 0) {
						System.out.print(0);
						temp = temp + "0";
					}
					if (temp.length() == 4) {

						hex = hex + Long.toHexString(Long.parseLong(temp, 2));

						temp = ""; // restart temp to count to 16 bits

					}
				}

			}
			System.out.println();
		}
	    if (temp.length()>0)
		    hex = hex + Long.toHexString(Long.parseLong(temp, 2));

		System.out.println("Ostabcle map : the Hex value is : " + hex);

	}

//	public static void main(String[] args) {
//		for (int i = 0; i < 20; i++)
//			for (int j = 0; j < 15; j++)
//				Global.exploreMap[i][j] = 1;
//		generateExploreMap();
//		generateObstacleMap();
//		// System.out.println("TRY : the Hex is : " +
//		// Long.toHexString(Long.parseLong("11111111111111", 2)));
//	}

}
