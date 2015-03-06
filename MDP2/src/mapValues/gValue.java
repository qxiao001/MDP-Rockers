package mapValues;

import Global.Global;

public class gValue {
	int[][] whatRobotSent;
	public gValue(int[][] theMap)
	{
		whatRobotSent=theMap;
		calculateGValue();
		//printGValue();
		//theMap will be calculated against
	}
	public void calculateGValue()
	{
		
		for(int i=0;i<20;i++)
		{
			for(int j=0;j<15;j++)
			{
				//set all 4 walls to near wall so value =1500
				if(i==0 || j==0 || i==19 || j==14)
				{
					Global.heuValue[i][j]=3500;
				}
				else{
						//appoint obstacle so value = 3000
						if(whatRobotSent[i][j]>0)
						{
							Global.heuValue[i][j]=7000;
						}
						//check the surrounding
						else if(nearObs(i, j))
						{
							//it is near obs so set it to value =1500
							
							{
								Global.heuValue[i][j]=3500;
							}
						}
						else if(j>1 && i>1)
						{
							Global.heuValue[i][j]=(j-1)*20+(i-1)*20;
							
						}
						
						
				}
			}
		}
		
		Global.heuValue[Global.goalX][Global.goalY]=0;
		
	}
	public void printGValue()
	{
		for(int i=0;i<20;i++)
		{
			for(int j=0;j<15;j++)
					System.out.print(Global.heuValue[i][j]+"|");
			System.out.println();
		}
	}
	public boolean nearObs(int row, int column)
	{
		boolean near=false;
		if((row > 0) && (column > 0))
		{
			//check the surrounding;
			for(int i=row-1;i<row+2;i++){
				for(int j=column-1;j<column+2;j++)
				{
					if(whatRobotSent[i][j]>0)
						{near=true;break;}
				}
				if(near){break;}
			}
		}
		return near;
	};

}
