package mapValues;

import Global.Global;

import java.lang.Math;

public class heurValue {
	public heurValue()	{}
	public static void initialize()
	{
		for(int i=0;i<20;i++)
		{
			for(int j=0;j<15;j++)
			{
					Global.gValue[i][j]=(Math.abs(Global.goalX-i))+(Math.abs(Global.goalY-j));
			}
		}
		//just making sure the goal point is 0
		Global.gValue[Global.goalX][Global.goalY]=0;
	
	}
	public static void printHeurValue()
	{
		for(int i=0;i<20;i++)
		{
			for(int j=0;j<15;j++)
				System.out.print(Global.gValue[i][j]+"|");
			System.out.println();
		}
		
	}
}
 