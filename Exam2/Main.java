import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	private static final int UPDATE = 1;
	private static final int QUERY = 2;
	private static final int MODE = 0;
	private static final int CITY = 1;
	private boolean isMemorizing;
	private int numOfCities;
	private int numOfQueries;
	private int[] distanceTableOrFestiveTable;
	private int[][] queries;
	private ArrayList<ArrayList<Integer>> adjacentCities;

	public Main(){
	}
	
	public void readAndProcessData() {
		Scanner myScanner = new Scanner(System.in);
		numOfCities = myScanner.nextInt();
		numOfQueries = myScanner.nextInt();
		
		distanceTableOrFestiveTable = new int[numOfCities+1];
		Arrays.fill(distanceTableOrFestiveTable, -1);
		adjacentCities = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < numOfCities+1; ++i) {
			adjacentCities.add(new ArrayList<Integer>());
		}
		
		for (int i = 0; i < numOfCities-1; ++i) {
			int first = myScanner.nextInt();
			int second = myScanner.nextInt();
			adjacentCities.get(first).add(second);
			adjacentCities.get(second).add(first);
		}
		
		queries = new int[numOfQueries][2];
		int updateNum = 0, queryNum = 0; 
		for (int i = 0; i < numOfQueries; ++i) {
			queries[i][MODE] = myScanner.nextInt();
			queries[i][CITY] = myScanner.nextInt();
			
			if (queries[i][MODE] == UPDATE) updateNum++;
			else	queryNum++;
		}
		myScanner.close();
		
		//if there are more queries than updates, use distance table
		this.isMemorizing = queryNum >= updateNum;
		
		intializeDistance();
		for (int i = 0; i < numOfQueries; ++i) {
			if (queries[i][MODE] == UPDATE)
				updateDistance(queries[i][CITY]);
			else
				getDistance(queries[i][CITY]);
		}
	}
	
	private void getDistance(int city) {
		if (isMemorizing)
			System.out.println(distanceTableOrFestiveTable[city]);
		else
			System.out.println(bfsGetDistance(city));
	}
	
	private int bfsGetDistance(int city) {
		int dist = 0;
		
		Queue<int[]> myQ = new LinkedList<int[]>();
		myQ.add(new int[]{city, 0, -1});
		while(!myQ.isEmpty()) {
			int[] cd = myQ.poll();
			if (distanceTableOrFestiveTable[cd[0]] == 1){
				dist = cd[1];//found result
				break;
			}
			else {
				for (int i : adjacentCities.get(cd[0])) {
					if (i == cd[2]) continue;//avoid traverse back to parent
					myQ.add(new int[]{i, cd[1]+1, cd[0]});
				}
			}
		}
		return dist;
	}

	private void updateDistance(int city) {
		if (isMemorizing)
			bfsUpdateDistanceTable(0, city);
		else
			distanceTableOrFestiveTable[city] = 1;
	}

	private void intializeDistance() {
		if (isMemorizing)
			bfsUpdateDistanceTable(0, 1);
		else
			distanceTableOrFestiveTable[0] = 1;
	}
	
	private void bfsUpdateDistanceTable(int dist, int city) {
		Queue<int[]> myQ = new LinkedList<int[]>();
		myQ.add(new int[]{city, dist, -1});
		while(!myQ.isEmpty()) {
			int[] cd = myQ.poll();
			if (distanceTableOrFestiveTable[cd[0]] == -1 || distanceTableOrFestiveTable[cd[0]] > cd[1])
			{
				distanceTableOrFestiveTable[cd[0]] = cd[1];
				for (int i : adjacentCities.get(cd[0])) {
					if (i == cd[2]) continue;//avoid traverse back to parent
					myQ.add(new int[]{i, cd[1]+1, cd[0]});
				}
			}
		}
	}

	public static void main(String[] args) {
		Main myMain = new Main();
		myMain.readAndProcessData();
	}

}
