/*
 * Copyright 2021 Marc Liberatore.
 */

package sequencer;

import java.util.List;
import java.util.ArrayList;

public class Assembler {

	ArrayList<Fragment> fragments;

	/**
	 * Creates a new Assembler containing a list of fragments.
	 * 
	 * The list is copied into this assembler so that the original list will not be
	 * modified by the actions of this assembler.
	 * 
	 * @param fragments
	 */
	public Assembler(List<Fragment> fragments) {
		// Copy the array into a new list
		this.fragments = new ArrayList<Fragment>(fragments);
	}

	/**
	 * Returns the current list of fragments this assembler contains.
	 * 
	 * @return the current list of fragments
	 */
	public List<Fragment> getFragments() {
		return this.fragments;
	}

	/**
	 * Attempts to perform a single assembly, returning true iff an assembly was
	 * performed.
	 * 
	 * This method chooses the best assembly possible, that is, it merges the two
	 * fragments with the largest overlap, breaking ties between merged fragments by
	 * choosing the shorter merged fragment.
	 * 
	 * Merges must have an overlap of at least 1.
	 * 
	 * After merging two fragments into a new fragment, the new fragment is inserted
	 * into the list of fragments in this assembler, and the two original fragments
	 * are removed from the list.
	 * 
	 * @return true iff an assembly was performed
	 */
	public boolean assembleOnce() {

		// Store the largest overlap, and the indexes of the Fragments for which it is
		// largest for
		int largestOverlap = 0;
		int f1Index = 0;
		int f2Index = 0;

		for (int i = 0; i < fragments.size(); i++) {
			for (int j = 0; j < fragments.size(); j++) {
				// As long as the fragments aren't the same object
				if (i != j) {
					int overlap = fragments.get(i).calculateOverlap(fragments.get(j));
					if (overlap > 1 && overlap > largestOverlap) {
						largestOverlap = overlap;
						f1Index = i;
						f2Index = j;
					}
				}
			}
		}

		if (largestOverlap == 0) {
			return false;
		}

		Fragment f1 = fragments.get(f1Index);
		Fragment f2 = fragments.get(f2Index);

		Fragment merged = f1.mergedWith(f2);
		fragments.add(merged);
		fragments.remove(f1);
		fragments.remove(f2);

		return true;
	}

	/**
	 * Repeatedly assembles fragments until no more assembly can occur.
	 */
	public void assembleAll() {
		while (assembleOnce()) {
			/*
			 * Keep assembling, do nothing. This works because the function is mutating the
			 * List until it returns false, at which point the loop will stop.
			 */
		}
	}
}
