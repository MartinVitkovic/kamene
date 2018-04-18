package kamene;

public class MainStone {
	private UserInterface ui;
	private long startMillis = System.currentTimeMillis();
	private static MainStone mainStone;
	//private Field field;

	public MainStone() {
		mainStone = this;
		ui = new ConsoleUI();
		//Field plaingField = new Field(field.getRowCount(), field.getColumnCount());
		Field field = new Field(4, 4);
		ui.newGame(field);
	}

	public static void main(String[] args) {
		new MainStone();
	}

	public static MainStone getInstance() {
		if (mainStone == null) {
			mainStone = new MainStone();
		}
		return mainStone;
	}

	public int getPlayingTime() {
		return ((int) (System.currentTimeMillis() - startMillis) / 1000);
	}

//	public Field getField() {
//		return field;
//	}
//
//	public void setField(Field field) {
//		this.field = field;
//	}

}
