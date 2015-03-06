package dataStructure;

public class Node {
	private int g;
	private int h;
	private int totalcost=0;
	private int[] positionXAndY=new int[2];
	private int[] previous=new int[2];
	//private int previousCost=0;
	private int needToTurn=0;
	
	public Node(int g, int h, int x, int y)
	{
		this.g=g;this.h=h;
		positionXAndY=new int[] {x,y};
		previous=new int[]{-7,-7};
		totalcost=g+h;
		//previousCost=g+h;
	}
	
	public void setG(int value){g=value;}
	public void setH(int value){h=value;}
	public void setPosition(int x, int y){positionXAndY[0]=x;positionXAndY[1]=y;};
	public void setPrevious(int[] s){previous=s;}
	//public void setPreviousCost(int p){previousCost=p;}
	public void setNeedToTurn(int turnDirection){needToTurn=turnDirection;}
	public void addCost(int addCost){totalcost=addCost;}
	public int getG(){return g;}
	public int getH(){return h;}
	public int getCost(){return totalcost;}
	public int[] getPosition(){return positionXAndY;} 
	public int[] getPrevious(){return previous;}
	//public int getPreviousCost(){return previousCost;}
	public int getTurn(){return needToTurn;}
	public String toString()
		{return "Node(position): x= "+positionXAndY[0]+", y="+positionXAndY[1]+"\n"
				+"Node Cost:"+getCost()+" \nthe previous(x,y): "+getPrevious()[0]+","+getPrevious()[1] ;}
	
}
