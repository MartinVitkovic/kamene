package kamene;

import java.util.Random;
import java.util.stream.IntStream;

public class Field {
	private final Tile[][] tiles;
	private final int rowCount;
	private final int columnCount;
	private GameState state = GameState.PLAYING;

	public Field(int rowCount, int columnCount) {
		super();
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		tiles = new Tile[rowCount][columnCount];

		generate();
	}

	private void generate() {
		generateNumber();
		generateEmpty();
	}

	private void generateNumber() {
		Random random = new Random();
		int[] data = IntStream.iterate(1, i -> i + 1).limit(rowCount * columnCount - 1).toArray();
		int numberCount = data.length;
		int j = 0;

		while (numberCount > 0) {
			int row = random.nextInt(rowCount);
			int column = random.nextInt(columnCount);

			if (getTile(row, column) == null) {
				tiles[row][column] = new NumberTile(data[j]);
				numberCount--;
				j++;
			}
		}
	}

	private void generateEmpty() {
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < columnCount; col++) {
				if (getTile(row, col) == null) {
					tiles[row][col] = new EmptyTile();
				}
			}
		}
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	public GameState getState() {
		return state;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

}
