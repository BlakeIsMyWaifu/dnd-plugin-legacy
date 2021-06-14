package me.blakeismywaifu.dnd.Util;

public class Util {

	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
		} catch(NumberFormatException | NullPointerException err) {
			return false;
		}
		return true;
	}
}
