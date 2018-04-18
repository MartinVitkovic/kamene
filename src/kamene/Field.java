package kamene;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;
import java.util.stream.IntStream;

public class Field implements Serializable{
	private final Tile[][] tiles;
	private final int rowCount;
	private final int columnCount;
	private GameState state = GameState.PLAYING;
	private static final Field DEFAULT_FIELD = new Field(4,4);
	private static final String GAME_FILE = System.getProperty("user.home") + System.getProperty("file.separator")
	+ "minesweeper.settings";

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

	public void swapTiles(int r, int c) throws MoveStoneException {
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < columnCount; col++) {
				if (getTile(row, col) instanceof EmptyTile) {
					if ((row + r) >= 0 && (row + r) < rowCount && (col + c) >= 0 && (col + c) < columnCount) {
						tiles[row][col] = new NumberTile(((NumberTile) tiles[row + r][col + c]).getNumber());
						tiles[row + r][col + c] = new EmptyTile();
						return;
					} else {
						throw new MoveStoneException("Nenasiel sa ziaden kamen na posunutie!");
					}
				}
			}
		}
	}

	public boolean isSolved() {
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < columnCount; col++) {
				if (!(tiles[rowCount - 1][columnCount - 1] instanceof EmptyTile)) {
					return false;
				} else if(!((((NumberTile)tiles[row][col]).getNumber()) < (((NumberTile)tiles[row][col+1]).getNumber()))){
					return false;
				}
			}
		}
		state = GameState.SOLVED;
		return true;
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
	
	public void save() {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(GAME_FILE))){
			oos.writeObject(this);
		}catch (IOException ex) {
			System.err.println("ERROR: " + ex.getMessage());
		}
	}
	
	public static Field load() {
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(GAME_FILE))){
			return (Field) ois.readObject();
		} catch (Exception e) {
		e.getStackTrace();
		return DEFAULT_FIELD;
		}
	}


}
