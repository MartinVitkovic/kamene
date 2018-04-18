package kamene;

public class ConsoleUI implements UserInterface{

	private Field field;

	public void newGame(Field field) {
		this.field = field;

		//while(true) {
			update();

			if (field.getState() == GameState.SOLVED) {
				System.out.println("Hra vyhrata!");
				System.exit(0);
			}
		//} 
	}

	public void update() {
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int col = 0; col < field.getColumnCount(); col++) {
				Tile tile = field.getTile(row, col);
				if (tile instanceof NumberTile) {
					System.out.print(((NumberTile) tile).getNumber() + " ");
				} else if(tile instanceof EmptyTile){
					System.out.print("- ");
				}
			}
			System.out.println();
		}
	}
}
