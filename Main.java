import java.util.Arrays;
import java.util.Scanner;

public class Main {
	private int row, col;
	private int[][] col_row;//store data
	private long[] colMax;//store maxScore for each row of current column
	private long[] colTemp;//store intermediate score for each row of current column
	
	public Main() {
		readData();
		processData();
	}
	
	public long getMaxScore() {
		long maxScore = -1;
		for (long score : colMax) {
			maxScore = Math.max(maxScore, score);
		}
		return maxScore;
	}
	
	private void processData() {
		Arrays.fill(colMax, 0L);
		
		for (int i = 0; i < col; ++i) {
			//Generate colTemp in current column: colTemp[j] = colMax[j](previous column) + col_row[i][j]
			for (int j = 0; j < row; ++j) {
				colTemp[j] = colMax[j] == -1 || col_row[i][j] == -1 ? -1 : colMax[j] + col_row[i][j];
				colMax[j] = colTemp[j];
			}
			this.calculateMax(i, col_row[i][0] != -1 && col_row[i][row-1] != -1);
            //for (long score : colMax) System.out.print(score+" ");
            //System.out.println();
		}
	}
	
	private void calculateMax(int colIndex, boolean teleportAvailable) {
		for (int i = 0; i < row; ++i) {
			//up
			long score = colTemp[i];
			if (score == -1) continue;
			
			for (int j = i+row-1; j >= i; --j) {
				if (!teleportAvailable && j <= row) break;//no teleport
				int index = j%row;
				
				if (j == row-1) score = 0;
				
				if (col_row[colIndex][index] != -1)
			    {
					score += col_row[colIndex][index];
					colMax[index] = Math.max(colMax[index], score);
			    }
				else
					break;
			}
			//down
			score = colTemp[i];
			for (int j = i+1; j < i+row; ++j) {
				if (!teleportAvailable && j >= row) break;//no teleport
				int index = j%row;
				
				if (j == row) score = 0;
				
				if (col_row[colIndex][index] != -1)
			    {
					score += col_row[colIndex][index];
					colMax[index] = Math.max(colMax[index], score);
			    }
				else
					break;
			}
		}
	}

	private void readData() {
		Scanner myScanner = new Scanner(System.in);
		this.row = myScanner.nextInt();
		this.col = myScanner.nextInt();
		col_row = new int[col][row];
		colMax = new long[row];
		colTemp = new long[row];
		
		for(int n = 0; n < row; ++n) {
			for(int m = 0; m < col; ++m) {
				col_row[m][n] = myScanner.nextInt();
			}
		}
	}

	public static void main(String[] args) {
		Main myMain = new Main();
		System.out.println(myMain.getMaxScore());
	}

}
