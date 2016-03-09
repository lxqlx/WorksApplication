import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	private boolean useTable;
	private int numCity;
	private int numQuery;
	private int[] distance;
	private int[][] queries;
	private ArrayList<ArrayList<Integer>> adjList;

	public Main(){
	}
	
	public void run() {
		Scanner myScanner = new Scanner(System.in);
		numCity = myScanner.nextInt();
		numQuery = myScanner.nextInt();
		distance = new int[numCity+1];
		Arrays.fill(distance, -1);
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
			if (queries[i][0] == 1) updateNum++;
			else	queryNum++;
			queries[i][1] = myScanner.nextInt();
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
			System.out.println(distance[city]);
		else{

			System.out.println(bfsGetDistance(city));
		}
	}
	private int bfsGetDistance(int city) {
		int dist = 0;
		
		boolean[] visited = new boolean[this.numCity+1];
		Queue<int[]> myQ = new LinkedList<int[]>();
		myQ.add(new int[]{city, 0});
		while(!myQ.isEmpty()) {
			int[] cd = myQ.poll();
			if (distance[cd[0]] == 1){
				dist = cd[1];
				break;
			}
			else {
				visited[cd[0]] = true;
				for (int i : adjList.get(cd[0])) {
					if (visited[i]) continue;
					myQ.add(new int[]{i, cd[1]+1});
				}
			}
		}
		return dist;
	}

	private void updateDistance(int city) {
		if (useTable)
			bfs(0, city);
		else
			distance[city] = 1;
	}

	private void intializeDistance() {
		if (useTable)
			bfs(0, 1);
		else
			distance[0] = 1;
	}
//	
//	private void dfs(int dist, int city) {
//		if (distance[city] != -1 && distance[city] <= dist) return;
//		distance[city] = dist;
//		for (int i : adjList.get(city)) {
//			dfs(dist+1, i);
//		}
//	}
	private void bfs(int dist, int city) {
		boolean[] visited = new boolean[this.numCity+1];
		if (distance[city] != -1 && distance[city] <= dist) return;
		Queue<int[]> myQ = new LinkedList<int[]>();
		myQ.add(new int[]{city, dist});
		while(!myQ.isEmpty()) {
			int[] cd = myQ.poll();
			if (distance[cd[0]] == -1 || distance[cd[0]] > cd[1])
			{
				distance[cd[0]] = cd[1];
				visited[cd[0]] = true;
				for (int i : adjList.get(cd[0])) {
					if (visited[i]) continue;
					myQ.add(new int[]{i, cd[1]+1});
				}
			}
		}
	}

	public static void main(String[] args) {
		Main myMain = new Main();
		myMain.run();
	}

}
