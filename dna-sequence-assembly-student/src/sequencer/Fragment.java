/*
 * Copyright 2021 Marc Liberatore.
 */

package sequencer;

public class Fragment {

	String sequence;

	/**
	 * Creates a new Fragment based upon a String representing a sequence of
	 * nucleotides, containing only the uppercase characters G, C, A and T.
	 * 
	 * @param nucleotides
	 * @throws IllegalArgumentException if invalid characters are in the sequence of
	 *                                  nucleotides
	 */
	public Fragment(String nucleotides) throws IllegalArgumentException {
		/*
		 * Personally I'd rather use regex for this, but I didn't know if we're allowed
		 * to import other classes (Pattern/Matcher, in this case)
		 */
		for (int i = 0; i < nucleotides.length(); i++) {
			if (!(nucleotides.charAt(i) == 'G' || nucleotides.charAt(i) == 'C' || nucleotides.charAt(i) == 'A'
					|| nucleotides.charAt(i) == 'T')) {
				throw new IllegalArgumentException("Invalid characters in sequence of nucleotides.");
			}
		}

		this.sequence = nucleotides;
	}

	/**
	 * Returns the length of this fragment.
	 * 
	 * @return the length of this fragment
	 */
	public int length() {
		return sequence.length();
	}

	/**
	 * Returns a String representation of this fragment, exactly as was passed to
	 * the constructor.
	 * 
	 * @return a String representation of this fragment
	 */
	@Override
	public String toString() {
		return sequence;
	}

	/**
	 * Return true if and only if this fragment contains the same sequence of
	 * nucleotides as another sequence.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (!(o instanceof Fragment)) {
			return false;
		}

		Fragment f = (Fragment) o;

		// Don't unconditionally return false; check that
		// the relevant instances variables in this and f
		// are semantically equal
		return sequence.equals(f.sequence);
	}

	/**
	 * Returns the number of nucleotides of overlap between the end of this fragment
	 * and the start of another fragment, f.
	 * 
	 * The largest overlap is found, for example, CAA and AAG have an overlap of 2,
	 * not 1.
	 * 
	 * @param f the other fragment
	 * @return the number of nucleotides of overlap
	 */
	public int calculateOverlap(Fragment f) {
		int overlap = 0;

		for (int i = 0; i <= f.sequence.length(); i++) {
			if (this.sequence.endsWith(f.sequence.substring(0, i))) {
				overlap = i;
			}
		}

		return overlap;
	}

	/**
	 * Returns a new fragment based upon merging this fragment with another fragment
	 * f.
	 * 
	 * This fragment will be on the left, the other fragment will be on the right;
	 * the fragments will be overlapped as much as possible during the merge.
	 * 
	 * @param f the other fragment
	 * @return a new fragment based upon merging this fragment with another fragment
	 */
	public Fragment mergedWith(Fragment f) {
		int overlap = calculateOverlap(f);

		Fragment merged = new Fragment(this.sequence + f.sequence.substring(overlap));

		return merged;
	}
}
