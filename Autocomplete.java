import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

// A data type that provides autocomplete functionality for a given set of
// string and weights, using Term and BinarySearchDeluxe.
public class Autocomplete {
    Term[] terms;

    // Initialize the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null)
            throw new java.lang.NullPointerException();
        Arrays.sort(terms);
        this.terms = terms;
    }

    // All terms that start with the given prefix, in descending order of
    // weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null)
            throw new java.lang.NullPointerException();
        if (prefix.length() == 0) {
            Term[] array = new Term[terms.length];
            for (int i = 0; i < terms.length; i++)
                array[i] = terms[i];
            Arrays.sort(array, Term.byReverseWeightOrder());
            return array;
        }
        Term term = new Term(prefix);
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        int i = BinarySearchDeluxe.firstIndexOf(terms, term, prefixOrder);
        int j = BinarySearchDeluxe.lastIndexOf(terms, term, prefixOrder);
        Term[] prefixArray = new Term[j - i + 1];
        for (int y = 0, z = i; z <= j; z++, y++)
            prefixArray[y] = terms[z];
        Arrays.sort(prefixArray, Term.byReverseWeightOrder());
        return prefixArray;
    }

    // The number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null)
            throw new java.lang.NullPointerException();
        Term[] prefixArray = allMatches(prefix);
        return prefixArray.length;
    }

    // Entry point. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println(results[i]);
            }
        }
    }
}

/*************
 * Sample Run
 * ***********
 *kuntals-MacBook-Pro:Project#3 kuntal$ javac-algs4 Autocomplete.java
 * kuntals-MacBook-Pro:Project#3 kuntal$ java-algs4 Autocomplete.java cities.txt 7
 * M
 * 12691836        Mumbai, India
 * 12294193        Mexico City, Distrito Federal, Mexico
 * 10444527        Manila, Philippines
 * 10381222        Moscow, Russia
 * 3730206 Melbourne, Victoria, Australia
 * 3268513 Montréal, Quebec, Canada
 * 3255944 Madrid, Spain
 * Al M
 * 431052  Al Maḩallah al Kubrá, Egypt
 * 420195  Al Manşūrah, Egypt
 * 290802  Al Mubarraz, Saudi Arabia
 * 258132  Al Mukallā, Yemen
 * 227150  Al Minyā, Egypt
 * 128297  Al Manāqil, Sudan
 * 99357   Al Maţarīyah, Egypt
 */
