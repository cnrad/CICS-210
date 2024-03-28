/*
 * Copyright 2023 Marc Liberatore.
 */

package index;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import comparators.TfIdfComparator;
import documents.DocumentId;

/**
 * A simplified document indexer and search engine.
 * 
 * Documents are added to the engine one-by-one, and uniquely identified by a
 * DocumentId.
 *
 * Documents are internally represented as "terms", which are lowercased
 * versions of each word
 * in the document.
 * 
 * Queries for terms are also made on the lowercased version of the term. Terms
 * are
 * therefore case-insensitive.
 * 
 * Lookups for documents can be done by term, and the most relevant document(s)
 * to a specific term
 * (as computed by tf-idf) can also be retrieved.
 *
 * See:
 * - <https://en.wikipedia.org/wiki/Inverted_index>
 * - <https://en.wikipedia.org/wiki/Search_engine_(computing)>
 * - <https://en.wikipedia.org/wiki/Tf%E2%80%93idf>
 * 
 * @author Marc Liberatore
 *
 */
public class SearchEngine {

	Map<String, Set<DocumentId>> index;
	Map<DocumentId, Map<String, Integer>> termFrequencies;

	/**
	 * Inserts a document into the search engine for later analysis and retrieval.
	 * 
	 * The document is uniquely identified by a documentId; attempts to re-insert
	 * the same
	 * document are ignored.
	 * 
	 * The document is supplied as a Reader; this method stores the document
	 * contents for
	 * later analysis and retrieval.
	 * 
	 * @param documentId
	 * @param reader
	 * @throws IOException iff the reader throws an exception
	 */

	public SearchEngine() {
		index = new HashMap<String, Set<DocumentId>>();
		termFrequencies = new HashMap<DocumentId, Map<String, Integer>>();
	}

	public void addDocument(DocumentId documentId, Reader reader) throws IOException {
		Set<String> terms = new HashSet<String>();

		if (!termFrequencies.containsKey(documentId)) {
			termFrequencies.put(documentId, new HashMap<String, Integer>());
		}

		Scanner s = new Scanner(reader);
		s.useDelimiter("\\W+");

		while (s.hasNext()) {
			String term = s.next().toLowerCase();

			int termCount = termFrequencies.get(documentId).getOrDefault(term, 0);
			termFrequencies.get(documentId).put(term, termCount + 1);

			if (!term.isEmpty()) {
				terms.add(term);
			}
		}

		for (String term : terms) {
			if (!index.containsKey(term)) {
				index.put(term, new HashSet<DocumentId>());
			}

			index.get(term).add(documentId);
		}

		// No memory leaks here!
		s.close();
	}

	/**
	 * Returns the set of DocumentIds contained within the search engine that
	 * contain a given term.
	 * 
	 * @param term
	 * @return the set of DocumentIds that contain a given term
	 */
	public Set<DocumentId> indexLookup(String term) {
		return index.getOrDefault(term.toLowerCase(), new HashSet<DocumentId>());
	}

	/**
	 * Returns the term frequency of a term in a particular document.
	 * 
	 * The term frequency is number of times the term appears in a document.
	 * 
	 * See
	 * 
	 * @param documentId
	 * @param term
	 * @return the term frequency of a term in a particular document
	 * @throws IllegalArgumentException if the documentId has not been added to the
	 *                                  engine
	 */
	public int termFrequency(DocumentId documentId, String term) throws IllegalArgumentException {
		if (!termFrequencies.containsKey(documentId)) {
			throw new IllegalArgumentException("DocumentId has not been added to the engine");
		}
		return termFrequencies.get(documentId).getOrDefault(term.toLowerCase(), 0);
	}

	/**
	 * Returns the inverse document frequency of a term across all documents in the
	 * index.
	 * 
	 * For our purposes, IDF is defined as log ((1 + N) / (1 + M)) where
	 * N is the number of documents in total, and M
	 * is the number of documents where the term appears.
	 * 
	 * @param term
	 * @return the inverse document frequency of term
	 */
	public double inverseDocumentFrequency(String term) {
		int N = termFrequencies.size();
		int M = index.get(term.toLowerCase()) != null ? index.get(term.toLowerCase()).size() : 0;

		return Math.log((double) (1 + N) / (1 + M));
	}

	/**
	 * Returns the tfidf score of a particular term for a particular document.
	 * 
	 * tfidf is the product of term frequency and inverse document frequency for the
	 * given term and document.
	 * 
	 * @param documentId
	 * @param term
	 * @return the tfidf of the the term/document
	 * @throws IllegalArgumentException if the documentId has not been added to the
	 *                                  engine
	 */
	public double tfIdf(DocumentId documentId, String term) throws IllegalArgumentException {
		if (!termFrequencies.containsKey(documentId)) {
			throw new IllegalArgumentException("DocumentId has not been added to the engine");
		}

		return (double) termFrequency(documentId, term.toLowerCase()) * inverseDocumentFrequency(term.toLowerCase());
	}

	/**
	 * Returns a sorted list of documents, most relevant to least relevant, for the
	 * given term.
	 * 
	 * A document with a larger tfidf score is more relevant than a document with a
	 * lower tfidf score.
	 * 
	 * Each document in the returned list must contain the term.
	 * 
	 * @param term
	 * @return a list of documents sorted in descending order by tfidf
	 */
	public List<DocumentId> relevanceLookup(String term) {
		if (index.get(term) == null)
			return new ArrayList<DocumentId>();

		List<DocumentId> docs = new ArrayList<DocumentId>(index.get(term));
		Collections.sort(docs, new TfIdfComparator(this, term));

		return docs;
	}
}
