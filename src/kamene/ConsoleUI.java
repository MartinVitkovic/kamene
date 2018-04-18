package kamene;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI implements UserInterface {

	private Field field;
	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	MainStone mainStone = MainStone.getInstance();

	private String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	public void newGame(Field field) {
		this.field = field;

		while (true) {
			update();
			consoleInput();

			if (field.getState() == GameState.SOLVED) {
				System.out.println("Hra vyhrata!");
				System.out.println("Hraci cas: " + mainStone.getPlayingTime() + "s");
				System.exit(0);
			}
		}
	}

	public void update() {
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int col = 0; col < field.getColumnCount(); col++) {
				Tile tile = field.getTile(row, col);
				if (tile instanceof NumberTile) {
					if (((NumberTile) tile).getNumber() < 10) {
						System.out.print("0" + ((NumberTile) tile).getNumber() + " ");
					} else {
						System.out.print(((NumberTile) tile).getNumber() + " ");
					}
				} else if (tile instanceof EmptyTile) {
					System.out.print("-- ");
				}
			}
			System.out.println();
		}
		System.out.println("Aktualny hraci cas: " + mainStone.getPlayingTime() + "s");
		System.out.println("Zadaj prikaz:");
	}

	public void consoleInput() {

		String input = readLine().trim().toLowerCase();
		try {
			handleInput(input);
		} catch (WrongFormatException ex) {
			System.err.println(ex.getMessage());
		} catch (MoveStoneException exc) {
			System.err.println(exc.getMessage());
		}

	}

	private void handleInput(String input) throws kamene.WrongFormatException, MoveStoneException {
		Pattern pattern = Pattern.compile("(/?)([a-z]+)");

		Matcher matcher = pattern.matcher(input);

		if (matcher.matches()) {
			String command = matcher.group(2);

			if (command.equals("exit")) {
				System.out.println("Hraci cas: " + mainStone.getPlayingTime() + "s");
				System.exit(0);
			} else if (command.equals("new")) {
				new MainStone();
			} else if (command.equals("w") || command.equals("up")) {
				field.swapTiles(1, 0);
			} else if (input.equals("a") || command.equals("left")) {
				field.swapTiles(0, 1);
			} else if (command.equals("s") || command.equals("down")) {
				field.swapTiles(-1, 0);
			} else if (command.equals("d") || command.equals("right")) {
				field.swapTiles(0, -1);
			} else {
				throw new WrongFormatException("Zadal si zly vstup");
			}
		} else {
			throw new WrongFormatException("Zadal si zly vstup");
		}
	}

}
