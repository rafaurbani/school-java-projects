import java.util.Comparator;

public class Sort implements Comparator<Character> {
	public int compare(Character o1, Character o2) {
		return o2.compareTo(o1);
	}
}
