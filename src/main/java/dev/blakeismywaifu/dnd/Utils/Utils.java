package dev.blakeismywaifu.dnd.Utils;

public class Utils {

	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
		} catch(NumberFormatException | NullPointerException err) {
			return false;
		}
		return true;
	}
}
