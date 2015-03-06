package MapUI;

import java.awt.Color;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

import dataStructure.Node;
import dataStructure.pQueueComperator;
import dataStructure.pathCoor;
import Global.Global;
import mapValues.*;



public class FSP {
	static int currFindingX=Global.startX;static int currFindingY=Global.startY;
	static boolean found=false;
	static int calX=0,calY=0;
	static Comparator<Node> comparator = new pQueueComperator();
	static PriorityQueue<Node> q= new PriorityQueue<Node>(10,comparator);
	static Stack<Node> path=new Stack<Node>();
	public FSP(){
		initalizesemSPMap();
		heurValue.initialize();
		gValue astar=new gValue(Global.robotMap);
		initalizeNodes();
	}
	public void findPath()
	{
		findShortPath2();
		printPath();
		makeFPMap();
	}
	//for later error checking
	public boolean sameStartPoint()
	{
		return ((currFindingX==Global.currCX)&&(currFindingY==Global.currCY));
	}
	public void calculateIncrement()
	{
		if(currFindingX>Global.goalX){calX=-1;}else{calX=1;}
		if(currFindingY>Global.goalY){calY=-1;}else{calY=1;}
	}
	public boolean withinBoundary(int x, int y)
	{
		boolean result=false;
		if(x>1 && x<18){
			if(y>1 && y<12) result=true;
		}
		return result;
	}
	public void findShortPath()
	{
		Node cNode,nNode1,nNode2;int ccX;int ccY;
		//Put the starting point to queue
		//System.out.println("The curr point ("+currFindingX+","+currFindingY+")");
		q.add(Global.pathNode[currFindingX][currFindingY]);
		//with the curFindingX , with the curFindingY you either -1 out 
			//everytime before you enqueue you need to check whether got turning
			//this is done by changeNodeCost(x,y);
			//now will only test with simple heuristic
		int count=0;
		do
		{
			
			cNode=q.poll();
			ccX=cNode.getPosition()[0];ccY=cNode.getPosition()[1];
			if (count<10)
				System.out.println("Current processing("+ccX+","+ccY+")");
			
			count++;
			
			
			if( withinBoundary(ccX,ccY) || (ccX!=1 && ccY!=13))
			//if((ccX!=Global.goalX && ccY!=Global.goalY)|| withinBoundary(ccX,ccY))
			{
				boolean prossed=false;
					nNode1=Global.pathNode[ccX-1][ccY];
					nNode2=Global.pathNode[ccX][ccY+1];
					//made changes to 2 nodes if they don't have previous yet
					if(nNode1.getPrevious()[0]==-7 && nNode1.getPrevious()[1]==-7)
					{
		
						Global.pathNode[ccX-1][ccY].setPrevious(new int[]{ccX,ccY});
						Global.pathNode[ccX-1][ccY].addCost(cNode.getCost()+Global.pathNode[ccX-1][ccY].getCost());
						if(q.contains(Global.pathNode[ccX-1][ccY])){q.remove(Global.pathNode[ccX-1][ccY]);}
						q.add(Global.pathNode[ccX-1][ccY]);prossed=true;
					}
					else
					{
						//compare the previous cost if this one is lower change its previous change
						int currCostOfThisNode=Global.pathNode[ccX-1][ccY].getCost();
						int costNeedtoCompare=Global.pathNode[ccX-1][ccY].getG()+Global.pathNode[ccX-1][ccY].getH()+cNode.getCost();
						//if the value is smaller then change the previous and cost
						//just for first version don't add in the equal cost
						if(currCostOfThisNode>costNeedtoCompare) //|| currCostOfThisNode==costNeedtoCompare )
						{
							Global.pathNode[ccX-1][ccY].addCost(costNeedtoCompare);
							Global.pathNode[ccX-1][ccY].setPrevious(new int[]{ccX,ccY});
							if(q.contains(Global.pathNode[ccX-1][ccY])){q.remove(Global.pathNode[ccX-1][ccY]);}
							q.add(Global.pathNode[ccX-1][ccY]);prossed=true;
						}
					}
					
					if(nNode2.getPrevious()[0]==-7 && nNode2.getPrevious()[1]==-7)
					{
						Global.pathNode[ccX][ccY+1].setPrevious(new int[]{ccX,ccY});
						Global.pathNode[ccX][ccY+1].addCost(Global.pathNode[ccX][ccY+1].getCost()+cNode.getCost());
						if(q.contains(Global.pathNode[ccX][ccY+1])){q.remove(Global.pathNode[ccX][ccY+1]);}
						q.add(Global.pathNode[ccX][ccY+1]);	prossed=true;
					}
					else
					{
						//compare the previous cost if this one is lower change its previous change
						int currCostOfThisNode=Global.pathNode[ccX][ccY+1].getCost();
						int costNeedtoCompare=Global.pathNode[ccX][ccY+1].getG()+Global.pathNode[ccX][ccY+1].getH()+cNode.getCost();
						//if the value is smaller then change the previous and cost
						//just for first version don't add in the equal cost
						if(currCostOfThisNode>costNeedtoCompare) //|| currCostOfThisNode==costNeedtoCompare)
						{
							//compare the previouscost if this one is lower change its previous
							Global.pathNode[ccX][ccY+1].addCost(costNeedtoCompare);
							Global.pathNode[ccX][ccY+1].setPrevious(new int[]{ccX,ccY});
							if(q.contains(Global.pathNode[ccX][ccY+1])){q.remove(Global.pathNode[ccX][ccY+1]);}
							q.add(Global.pathNode[ccX][ccY+1]);	prossed=true;
						}
					}
					if(prossed){
						//if(path.contains(cNode))
						path.add(cNode);
					}		
					
				}
				//found the goal already
				else
				{
					found=true;
					compareThesurrounding();
					
				}
			
			
			
		}	while((!found) || (!q.isEmpty()));	
		
	}
	public void findShortPath2()
	{
		Node cNode,nNode1,nNode2;int ccX;int ccY;
		//Put the starting point to queue
		//System.out.println("The curr point ("+currFindingX+","+currFindingY+")");
		q.add(Global.pathNode[currFindingX][currFindingY]);
		//with the curFindingX , with the curFindingY you either -1 out 
			//everytime before you enqueue you need to check whether got turning
			//this is done by changeNodeCost(x,y);
			//now will only test with simple heuristic
		int count=0;
		do
		{
			
			cNode=q.poll();
			ccX=cNode.getPosition()[0];ccY=cNode.getPosition()[1];
			
			
			
			if( withinBoundary(ccX,ccY) || (ccX!=1 && ccY!=13))
			//if((ccX!=Global.goalX && ccY!=Global.goalY)|| withinBoundary(ccX,ccY))
			{
				boolean prossed=false;
					nNode1=Global.pathNode[ccX-1][ccY];
					nNode2=Global.pathNode[ccX][ccY+1];
					if(nNode2.getPrevious()[0]==-7 && nNode2.getPrevious()[1]==-7)
					{
						Global.pathNode[ccX][ccY+1].setPrevious(new int[]{ccX,ccY});
						Global.pathNode[ccX][ccY+1].addCost(Global.pathNode[ccX][ccY+1].getCost()+cNode.getCost());
						if(q.contains(Global.pathNode[ccX][ccY+1])){q.remove(Global.pathNode[ccX][ccY+1]);}
						q.add(Global.pathNode[ccX][ccY+1]);	prossed=true;
					}
					else
					{
						//compare the previous cost if this one is lower change its previous change
						int currCostOfThisNode=Global.pathNode[ccX][ccY+1].getCost();
						int costNeedtoCompare=Global.pathNode[ccX][ccY+1].getG()+Global.pathNode[ccX][ccY+1].getH()+cNode.getCost();
						//if the value is smaller then change the previous and cost
						//just for first version don't add in the equal cost
						if(currCostOfThisNode>costNeedtoCompare || currCostOfThisNode==costNeedtoCompare)
						{
							//compare the previouscost if this one is lower change its previous
							Global.pathNode[ccX][ccY+1].addCost(costNeedtoCompare);
							Global.pathNode[ccX][ccY+1].setPrevious(new int[]{ccX,ccY});
							if(q.contains(Global.pathNode[ccX][ccY+1])){q.remove(Global.pathNode[ccX][ccY+1]);}
							q.add(Global.pathNode[ccX][ccY+1]);	prossed=true;
						}
					}
					if(prossed){
						//if(path.contains(cNode))
						path.add(cNode);
					}		
					
					//made changes to 2 nodes if they don't have previous yet
					if(nNode1.getPrevious()[0]==-7 && nNode1.getPrevious()[1]==-7)
					{
		
						Global.pathNode[ccX-1][ccY].setPrevious(new int[]{ccX,ccY});
						Global.pathNode[ccX-1][ccY].addCost(cNode.getCost()+Global.pathNode[ccX-1][ccY].getCost());
						if(q.contains(Global.pathNode[ccX-1][ccY])){q.remove(Global.pathNode[ccX-1][ccY]);}
						q.add(Global.pathNode[ccX-1][ccY]);prossed=true;
					}
					else
					{
						//compare the previous cost if this one is lower change its previous change
						int currCostOfThisNode=Global.pathNode[ccX-1][ccY].getCost();
						int costNeedtoCompare=Global.pathNode[ccX-1][ccY].getG()+Global.pathNode[ccX-1][ccY].getH()+cNode.getCost();
						//if the value is smaller then change the previous and cost
						//just for first version don't add in the equal cost
						if(currCostOfThisNode>costNeedtoCompare || currCostOfThisNode==costNeedtoCompare )
						{
							Global.pathNode[ccX-1][ccY].addCost(costNeedtoCompare);
							Global.pathNode[ccX-1][ccY].setPrevious(new int[]{ccX,ccY});
							if(q.contains(Global.pathNode[ccX-1][ccY])){q.remove(Global.pathNode[ccX-1][ccY]);}
							q.add(Global.pathNode[ccX-1][ccY]);prossed=true;
						}
					}
					
					
				}
				//found the goal already
				else
				{
					found=true;
					compareThesurrounding();
					
				}
			
			
			
		}	while((!found) || (!q.isEmpty()));	
		
	}
	private void compareThesurrounding() {
		int x=Global.goalX, y=Global.goalY;
		// TODO Auto-generated method stub
		if(Global.pathNode[x][y-1].getPrevious()[0]!=-7)
		{
			if(Global.pathNode[x+1][y].getPrevious()[0]!=-7)
			{
				if(Global.pathNode[x][y-1].getCost() < Global.pathNode[x+1][y].getCost())
				{
					Global.pathNode[x][y].setPrevious(new int[]{Global.pathNode[x][y-1].getPosition()[0],Global.pathNode[x][y-1].getPosition()[1]});
				}
				else
				{
					Global.pathNode[x][y].setPrevious(new int[]{Global.pathNode[x+1][y].getPosition()[0],Global.pathNode[x+1][y].getPosition()[1]});
				}
			}
		}
	}
	/*public void printGeneralPath()
	{
		int count=0;
		Node n;
		System.out.println("This is how the path is founded.");
		while(!path.isEmpty() && count<200)
		{
			n=path.pop();
			System.out.print("("+n.getPosition()[0]+","+n.getPosition()[1]+")");
			count++;
			if(count%10==0){System.out.println();}
			
		}
		System.out.println();
	}*/
	
	public void printPath()
	{
		int prex=Global.goalX,prey=Global.goalY,x,y;
		//Global.fsPaint.add(new pathCoor(prex,prey));
		
		do
		{
			//System.out.print("("+prex+","+prey+")");
			Global.fsPaint.add(new pathCoor(prex,prey));
			x=Global.pathNode[prex][prey].getPrevious()[0];y=Global.pathNode[prex][prey].getPrevious()[1];
			prex=x;prey=y;
			
			//sSystem.out.print("("+x+","+y+")");
			
		}while(!((prex==Global.startX) && (prey==Global.startY)));
		//System.out.print("("+prex+","+prey+")");
		Global.fsPaint.add(new pathCoor(Global.startX,Global.startY));
		//System.out.print("("+prex+","+prey+")");
		//System.out.println();
	}
	public void makeFPMap()
	{
		int prex=Global.goalX,prey=Global.goalY,x,y;
		while(!((prex==Global.startX) && (prey==Global.startY)))	
		{
			
			x=Global.pathNode[prex][prey].getPrevious()[0];y=Global.pathNode[prex][prey].getPrevious()[1];
			prex=x;prey=y;
			Global.sempFPMap[prex][prey]=1;
		}
	}
	public static void changeNodeCost(Node[] nodesToConsider,int startNodeIndex, char firstOri)
	{
		//check the node move with orientation
	}
	
	/*public int[] checkDirectionChange()
	{
		int[] startEnd=new int[];
		return 
	}*/
	public static void initalizesemSPMap()
	{
		for (int i=0;i<20;i++)
		{
			for(int j=0;j<15;j++)
			{
				Global.sempFPMap[i][j]=0;
			}
		}
	}
	public static void initalizeNodes()
	{
		Node newNode;
		for (int i=0;i<20;i++)
		{
			for(int j=0;j<15;j++)
			{
				newNode=new Node(Global.gValue[i][j],Global.heuValue[i][j],i,j);
				Global.pathNode[i][j]=newNode;
			}
		}
	}
	public String iLoveToMoveIt()
	{
		StringBuilder sB = new StringBuilder();
		Robot robot=new Robot();int count=0;
		char ori=robot.checkOri();
		//use the center point only
		
		int nowX=Global.currCX,nowY=Global.currCY;
		//System.out.println("The total steps (fsPaint arrary) are "+Global.fsPaint.size()+"************************************************************************");

		/*for(int i=Global.fsPaint.size();i>0;i--)
    	{
    		Global.realsteps.add(Global.fsPaint.get(i-1));
    		
    	}*/
		Collections.reverse(Global.fsPaint);
		for(int i=0;i<Global.fsPaint.size();i++)
    	{
    		System.out.print("("+Global.fsPaint.get(i).getX()+","+Global.fsPaint.get(i).getY()+")");
    		
    	}
		System.out.println("The total steps (realsteps arrary) are "+Global.realsteps.size()+"************************************************************************");
		//System.out.println("The orientation before moving the fastest path :"+ori);
		//check curr ori
		Global.realsteps=Global.fsPaint;
		do
		{
			int moveX=Global.realsteps.get(count).getX();
			int moveY=Global.realsteps.get(count).getY();
			if(ori=='U')
			{
				System.out.println("The step: "+count);
				if(findDifference(nowX,nowY,moveX,moveY)==1)
				{
					sB.append("F010,");
				}
				else if(findDifference(nowX,nowY,moveX,moveY)==-1)
				{
					sB.append("R000,F010,");
					ori='R';				
				}
				
			}
			else if(ori=='R')
			{
				if(findDifference(nowX,nowY,moveX,moveY)==1)
				{
					sB.append("L000,F010,");ori='U';
				}
				else if(findDifference(nowX,nowY,moveX,moveY)==-1)
				{
					sB.append("F010,");
				}
			}
			nowX=moveX;nowY=moveY;
			count++;
		}while(count<Global.realsteps.size());
		
		String finalString = sB.toString();
		//return finalString;
		return finalString;
	}
		
	
	public static int findDifference(int nowX,int nowY,int moveX,int moveY)
	{
		int compareX=nowX-moveX;
		int compareY=nowY-moveY;
		if(compareX!=0)
			return compareX;
		else if(compareY!=0)
			return compareY;
		else
			return 0;
		
		
	}
	public void printNodesCostOnly()
	{
		for (int i=0;i<20;i++)
		{
			for(int j=0;j<15;j++)
			{
				System.out.print(Global.pathNode[i][j].getCost()+"|");
			}
			System.out.println();
		}
	}
	
	
}
