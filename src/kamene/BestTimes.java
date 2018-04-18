package kamene;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

public class BestTimes implements Iterable<BestTimes.PlayerTime> {

	private List<PlayerTime> playerTimes = new ArrayList<PlayerTime>();

	public Iterator<PlayerTime> iterator() {
		return playerTimes.iterator();
	}

	public void addPlayerTime(String name, int time) {
		PlayerTime playerTime = new PlayerTime(name, time);
		playerTimes.add(0, playerTime);
	}

	public void reset() {
		playerTimes.clear();
	}

	public String toString() {
		Formatter f = new Formatter();
		String name;
		int time;
		int order;

		for (PlayerTime pt : playerTimes) {
			order = playerTimes.indexOf(pt) + 1;
			name = pt.getName();
			time = pt.getTime();
			f.format("%d. %s: %d", order, name, time);
		}
		return f.toString();
	}

	public static class PlayerTime implements Comparable<PlayerTime> {
		/** Player name. */
		private final String name;

		/** Playing time in seconds. */
		private final int time;

		public String getName() {
			return name;
		}

		public int getTime() {
			return time;
		}

		public PlayerTime(String name, int time) {
			this.name = name;
			this.time = time;
		}

		public int compareTo(PlayerTime o) {
			if (getTime() < o.getTime()) {
				return -1;
			} else if (getTime() == o.getTime()) {
				return 0;
			} else {
				return 1;
			}

		}

	}
}