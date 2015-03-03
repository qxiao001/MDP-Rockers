package Obstacle;

import Global.Global;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class obsValue {
	public ArrayList<String> mapDesc= new ArrayList<String>();
	String fName=null;
	public obsValue(){}
	public void initObs1()
	{
		fName="Obs1.txt";
		ReadFromFile(fName);
		makeRealMapOccupied();
	}
	public void initObs2()
	{
		fName="Obs2.txt";
		ReadFromFile(fName);
		makeRealMapOccupied();
	}
	public void initObs3()
	{
		fName="Obs3.txt";
		ReadFromFile(fName);
		makeRealMapOccupied();
	}
	public void initObs4()
	{
		fName="Obs4.txt";
		ReadFromFile(fName);
		makeRealMapOccupied();
	}
	public void initObs5()
	{
		fName="Obs5.txt";
		ReadFromFile(fName);
		makeRealMapOccupied();
	}
	public void ReadFromFile(String fName)
	{
		try
		{
			BufferedReader in=new BufferedReader(new FileReader(fName));
			String s="";
    		while((s=in.readLine())!=null)
    		{
    			mapDesc.add(s);
    			System.out.println(s);
    		}

		}
		catch(Exception e)
		{
			System.out.println("File Reading Error for obstacles!");
		}
	}
	public void makeRealMapOccupied()
	{
		for(int i=0;i<20;i++)
		{
				String str=mapDesc.get(i);
				String[] st = str.split(",");
				for(int j=0;j<15;j++)
					{
						int mapInt=Integer.parseInt(st[j]);
						Global.realMap[i][j]=mapInt;
					}
		}

	}
}


