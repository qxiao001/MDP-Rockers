package mapValues;

import Global.Global;

public class gValue {
	int[][] whatRobotSent;
	public gValue(int[][] theMap)
	{
		whatRobotSent=theMap;
		calculateGValue();
		printGValue();
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
					Global.heuValue[i][j]=1500;
				}
				else{
						//appoint obstacle so value = 3000
						if(whatRobotSent[i][j]==1)
						{
							Global.heuValue[i][j]=3000;
						}
						//check the surrounding
						else
						{
							//it is near obs so set it to value =1500
							if(nearObs(i, j))
							{
								Global.heuValue[i][j]=1500;
							}
						}
				}
			}
		}
		//end of calculating obstacle
		//Now Calculate the turning point
		for (int i=0;i<20;i++)
		{
			for(int j=0;j<15;j++)
			{
				
			}
		}
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
					if(whatRobotSent[i][j]==1)
						{near=true;break;}
				}
				if(near){break;}
			}
		}
		return near;
	};

}
