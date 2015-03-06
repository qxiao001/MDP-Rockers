package connector;

public class fadeString {
	public static int count=0;
	public fadeString(){}
	public static String getString(){
		// 5,4,3,2,1   F1 F2 F3 L R   1,2,3 FROM LEFT TO RIGHT
		switch(count)
		{
		case  0:
			count++;
			return "-1,2,-1,0,-1";
		case 1:
			count++;
			return "-1,2,-1,1,-1";
		case 2:
			count++;
			return "-1,-1,-1,0,-1";
		case 3:
			count++;
			return "-1,9,-1,7,-1";
		case 4:
			count++;
			return "-1,9,-1,7,-1";
		case 5:
			count++;
			return"-1,9,9,7,9";
		case 6:
			count++;
			return"-1,9,-1,7,-1";
		case 7:
			count++;
			return"-1,8,-1,8,-1";
		
		}
		return null;
	}

}
