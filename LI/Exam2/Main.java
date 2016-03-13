import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	private static final int NOVALUE = -1;
	private static final int UPDATE = 1;
	private static final int CITY = 0;
	private static final int DIST = 1;
	private static final int PARENT = 2;
	private int numOfCities;
	private int numOfQueries;
	private int[] distanceTable;
	private ArrayList<ArrayList<Integer>> adjacentCities;

	public Main(){
	}
	
	public void readAndProcessData() {
		Scanner myScanner = new Scanner(System.in);
		numOfCities = myScanner.nextInt();
		numOfQueries = myScanner.nextInt();
		
		distanceTable = new int[numOfCities+1];
		Arrays.fill(distanceTable, NOVALUE);
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
		
		intializeDistance();
		
		for (int i = 0; i < numOfQueries; ++i) {
			int mode = myScanner.nextInt();
			int city = myScanner.nextInt();
			
			if (mode == UPDATE)
				updateDistance(city);
			else
				getDistance(city);

		}
		myScanner.close();
	}
	
	private void getDistance(int city) {
		System.out.println(distanceTable[city]);
	}

	private void updateDistance(int city) {
		bfsUpdateDistanceTable(0, city);	
	}

	private void intializeDistance() {
		bfsUpdateDistanceTable(0, 1);
	}
	
	private void bfsUpdateDistanceTable(int dist, int city) {
		Queue<int[]> myQ = new LinkedList<int[]>();
		myQ.add(new int[]{city, dist, -1});
		while(!myQ.isEmpty()) {
			int[] cityDistParent = myQ.poll();
			if (distanceTable[cityDistParent[CITY]] == NOVALUE || distanceTable[cityDistParent[CITY]] > cityDistParent[DIST])
			{
				distanceTable[cityDistParent[CITY]] = cityDistParent[DIST];
				for (int neighbour : adjacentCities.get(cityDistParent[CITY])) {
					if (neighbour == cityDistParent[PARENT]) continue;//avoid traverse back to parent
					myQ.add(new int[]{neighbour, cityDistParent[DIST]+1, cityDistParent[CITY]});
				}
			}
		}
	}

	public static void main(String[] args) {
		Main myMain = new Main();
		myMain.readAndProcessData();
	}

}
