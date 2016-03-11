import java.util.Arrays;
import java.util.Scanner;

public class Main {
	private int numOfRows, numOfCols;
	private int[][] mapColRrow;//store data
	private long[] maxScoreOfCurrentColumn;//store maxScore for each row of current column
	private long[] tempScoreOfCurrentColumn;//store intermediate score for each row of current column
	private long maxScore;
	
	public Main() {
		readAndInitializeData();
		processData();
	}
	
	public long getMaxScore() {
		return this.maxScore;
	}
	
	private void processData() {
		Arrays.fill(maxScoreOfCurrentColumn, 0L);
		
		for (int i = 0; i < numOfCols; ++i) {
			//Generate temporary Score of current column(without going up/down): tempScoreOfCurrentColumn[j] = maxScoreOfCurrentColumn[j](previous column) + mapColRrow[i][j]
			for (int j = 0; j < numOfRows; ++j) {
				tempScoreOfCurrentColumn[j] = maxScoreOfCurrentColumn[j] == -1 || mapColRrow[i][j] == -1 ? -1 : maxScoreOfCurrentColumn[j] + mapColRrow[i][j];
				maxScoreOfCurrentColumn[j] = tempScoreOfCurrentColumn[j];
			}
			this.calculateMax(i, mapColRrow[i][0] != -1 && mapColRrow[i][numOfRows-1] != -1);
            //for (long score : colMax) System.out.print(score+" ");
            //System.out.println();
		}
		//calculate max score
		for (long score : maxScoreOfCurrentColumn) {
			maxScore = Math.max(maxScore, score);
		}
	}
	
	private void calculateMax(int colIndex, boolean teleportAvailable) {
		for (int i = 0; i < numOfRows; ++i) {
			//update maxScore of current column by going up
			long score = tempScoreOfCurrentColumn[i];
			if (score == -1) continue;
			
			for (int j = i+numOfRows-1; j >= i; --j) {
				if (!teleportAvailable && j <= numOfRows) break;//no teleport
				int index = j%numOfRows;
				
				if (j == numOfRows-1) score = 0;//teleport, reset score
				
				if (mapColRrow[colIndex][index] != -1)
			    {
					score += mapColRrow[colIndex][index];
					maxScoreOfCurrentColumn[index] = Math.max(maxScoreOfCurrentColumn[index], score);
			    }
				else
					break;
			}
			//update maxScore of current column by going down
			score = tempScoreOfCurrentColumn[i];
			for (int j = i+1; j < i+numOfRows; ++j) {
				if (!teleportAvailable && j >= numOfRows) break;//no teleport
				int index = j%numOfRows;
				
				if (j == numOfRows) score = 0;//teleport, reset score
				
				if (mapColRrow[colIndex][index] != -1)
			    {
					score += mapColRrow[colIndex][index];
					maxScoreOfCurrentColumn[index] = Math.max(maxScoreOfCurrentColumn[index], score);
			    }
				else
					break;
			}
		}
	}

	private void readAndInitializeData() {
		Scanner myScanner = new Scanner(System.in);
		this.numOfRows = myScanner.nextInt();
		this.numOfCols = myScanner.nextInt();
		this.mapColRrow = new int[numOfCols][numOfRows];
		this.maxScoreOfCurrentColumn = new long[numOfRows];
		this.tempScoreOfCurrentColumn = new long[numOfRows];
		this.maxScore = -1L;
		
		for(int n = 0; n < numOfRows; ++n) {
			for(int m = 0; m < numOfCols; ++m) {
				mapColRrow[m][n] = myScanner.nextInt();
			}
		}
		myScanner.close();
	}

	public static void main(String[] args) {
		Main myMain = new Main();
		System.out.println(myMain.getMaxScore());
	}

}
