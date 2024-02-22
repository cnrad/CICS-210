/*
 * Copyright 2021 Marc Liberatore.
 */

package list.exercises;

import java.util.ArrayList;
import java.util.List;

public class ListExercises {

	/**
	 * Counts the number of characters in total across all strings in the supplied
	 * list;
	 * in other words, the sum of the lengths of the all the strings.
	 * 
	 * @param l a non-null list of strings
	 * @return the number of characters
	 */
	public static int countCharacters(List<String> l) {
		int total = 0;

		for (String s : l) {
			total += s.length();
		}

		return total;
	}

	/**
	 * Splits a string into words and returns a list of the words.
	 * If the string is empty, split returns a list containing an empty string.
	 * 
	 * @param s a non-null string of zero or more words
	 * @return a list of words
	 */
	public static List<String> split(String s) {
		List<String> list = new ArrayList<String>();

		for (String str : s.split("\\s+")) {
			list.add(str);
		}

		return list;
	}

	/**
	 * Returns a copy of the list of strings where each string has been
	 * uppercased (as by String.toUpperCase).
	 * 
	 * The original list is unchanged.
	 * 
	 * @param l a non-null list of strings
	 * @return a list of uppercased strings
	 */
	public static List<String> uppercased(List<String> l) {
		List<String> uppercased = new ArrayList<String>();

		for (String s : l) {
			uppercased.add(s.toUpperCase());
		}

		return uppercased;
	}

	/**
	 * Returns true if and only if each string in the supplied list of strings
	 * starts with an uppercase letter. If the list is empty, returns false.
	 * 
	 * @param l a non-null list of strings
	 * @return true iff each string starts with an uppercase letter
	 */
	public static boolean allCapitalizedWords(List<String> l) {
		if (l.size() == 0)
			return false;

		for (String s : l) {
			if (s.length() == 0 || !Character.isUpperCase(s.charAt(0)))
				return false;
		}

		return true;
	}

	/**
	 * Returns a list of strings selected from a supplied list, which contain the
	 * character c.
	 * 
	 * The returned list is in the same order as the original list, but it omits all
	 * strings
	 * that do not contain c.
	 * 
	 * The original list is unmodified.
	 * 
	 * @param l a non-null list of strings
	 * @param c the character to filter on
	 * @return a list of strings containing the character c, selected from l
	 */
	public static List<String> filterContaining(List<String> l, char c) {
		List<String> filtered = new ArrayList<String>();

		for (String s : l) {
			if (s.indexOf(c) != -1)
				filtered.add(s);
		}

		return filtered;
	}

	/**
	 * Inserts a string into a sorted list of strings, maintaining the sorted
	 * property of the list.
	 * 
	 * @param s the string to insert
	 * @param l a non-null, sorted list of strings
	 */
	public static void insertInOrder(String s, List<String> l) {
		if (l.size() == 0) {
			l.add(s);
			return;
		}

		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).compareTo(s) > 0) {
				l.add(i, s);
				return;
			}
		}

		l.add(s);
	}
}
