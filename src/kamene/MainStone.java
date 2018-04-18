package kamene;

public class MainStone {
	private UserInterface ui;

	public MainStone() {
		ui = new ConsoleUI();
		Field field = new Field(4, 4);
		ui.newGame(field);
	}

	public static void main(String[] args) {
		new MainStone();
	}

}
