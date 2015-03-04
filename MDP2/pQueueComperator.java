package dataStructure;

import java.util.Comparator;


public class pQueueComperator implements Comparator<Node>{

	@Override
	public int compare(Node o1, Node o2) {
		if(o1.getCost()<o2.getCost())
			return -1;
		else return 1;
		
	}
}
