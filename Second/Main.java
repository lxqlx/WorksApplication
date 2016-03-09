import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	private boolean useTable;
	private int numCity;
	private int numQuery;
	private int[] disTableOrFestiveTable;
	private int[][] queries;
	private ArrayList<ArrayList<Integer>> adjList;

	public Main(){
	}
	
	public void run() {
		Scanner myScanner = new Scanner(System.in);
		numCity = myScanner.nextInt();
		numQuery = myScanner.nextInt();
		
		disTableOrFestiveTable = new int[numCity+1];
		Arrays.fill(disTableOrFestiveTable, -1);
		adjList = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < numCity+1; ++i) {
			adjList.add(new ArrayList<Integer>());
		}
		
		for (int i = 0; i < numCity-1; ++i) {
			int first = myScanner.nextInt();
			int second = myScanner.nextInt();
			adjList.get(first).add(second);
			adjList.get(second).add(first);
		}
		
		queries = new int[numQuery][2];
		int updateNum = 0, queryNum = 0; 
		for (int i = 0; i < numQuery; ++i) {
			queries[i][0] = myScanner.nextInt();
			queries[i][1] = myScanner.nextInt();
			
			if (queries[i][0] == 1) updateNum++;
			else	queryNum++;
		}
		myScanner.close();
		
		//decide mode
		this.useTable = queryNum >= updateNum;
		
		intializeDistance();
		for (int i = 0; i < numQuery; ++i) {
			if (queries[i][0] == 1)
				updateDistance(queries[i][1]);
			else
				getDistance(queries[i][1]);
		}
	}
	private void getDistance(int city) {
		if (useTable)
			System.out.println(disTableOrFestiveTable[city]);
		else{

			System.out.println(bfsGetDistance(city));
		}
	}
	private int bfsGetDistance(int city) {
		int dist = 0;
		
		Queue<int[]> myQ = new LinkedList<int[]>();
		myQ.add(new int[]{city, 0, -1});
		while(!myQ.isEmpty()) {
			int[] cd = myQ.poll();
			if (disTableOrFestiveTable[cd[0]] == 1){
				dist = cd[1];//found result
				break;
			}
			else {
				for (int i : adjList.get(cd[0])) {
					if (i == cd[2]) continue;//avoid traverse back to parent
					myQ.add(new int[]{i, cd[1]+1});
				}
			}
		}
		return dist;
	}

	private void updateDistance(int city) {
		if (useTable)
			bfsUpdateDistanceTable(0, city);
		else
			disTableOrFestiveTable[city] = 1;
	}

	private void intializeDistance() {
		if (useTable)
			bfsUpdateDistanceTable(0, 1);
		else
			disTableOrFestiveTable[0] = 1;
	}
	
	private void bfsUpdateDistanceTable(int dist, int city) {
		Queue<int[]> myQ = new LinkedList<int[]>();
		myQ.add(new int[]{city, dist, -1});
		while(!myQ.isEmpty()) {
			int[] cd = myQ.poll();
			if (disTableOrFestiveTable[cd[0]] == -1 || disTableOrFestiveTable[cd[0]] > cd[1])
			{
				disTableOrFestiveTable[cd[0]] = cd[1];
				for (int i : adjList.get(cd[0])) {
					if (i == cd[2]) continue;//avoid traverse back to parent
					myQ.add(new int[]{i, cd[1]+1, cd[0]});
				}
			}
		}
	}

	public static void main(String[] args) {
		Main myMain = new Main();
		myMain.run();
	}

}
