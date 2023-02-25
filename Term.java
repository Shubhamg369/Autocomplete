import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

// An immutable data type that represents an autocomplete term: a query string
// and an associated real-valued weight.
public class Term implements Comparable<Term> {
    private final String query;
    private final long weight;

    // Construct a term given the associated query string, having weight 0.
    public Term(String query) {
        if (query == null)
            throw new java.lang.NullPointerException();
        this.query = query;
        this.weight = 0;
    }

    // Construct a term given the associated query string and weight.
    public Term(String query, long weight) {
        if (query == null)
            throw new java.lang.NullPointerException();
        if (weight < 0)
            throw new java.lang.IllegalArgumentException();
        this.query = query;
        this.weight = weight;

    }

    // A reverse-weight comparator.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeightOrder();
    }

    // Helper reverse-weight comparator.
    private static class ReverseWeightOrder implements Comparator<Term> {
        public int compare(Term v, Term w) {
            return (int) (w.weight - v.weight);
        }
    }

    // A prefix-order comparator.
    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0)
            throw new java.lang.IllegalArgumentException();
        return new PrefixOrder(r);
    }

    // Helper prefix-order comparator.
    private static class PrefixOrder implements Comparator<Term> {
        int r;

        PrefixOrder(int r) {
            this.r = r;
        }

        public int compare(Term v, Term w) {
            String s1, s2;
            if (v.query.length() > r)
                s1 = v.query.substring(0, r);
            else
                s1 = v.query;
            if (w.query.length() > r)
                s2 = w.query.substring(0, r);
            else
                s2 = w.query;
            return s1.compareTo(s2);
        }
    }

    // Compare this term to that in lexicographic order by query and
    // return a negative, zero, or positive integer based on whether this
    // term is smaller, equal to, or larger than that term.
    public int compareTo(Term that) {
        return this.query.compareTo(that.query);
    }

    // A string representation of this term.
    public String toString() {
        return weight + "\t" + query;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        int k = Integer.parseInt(args[1]);
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        StdOut.printf("Top %d by lexicographic order:\n", k);
        Arrays.sort(terms);
        for (int i = 0; i < k; i++) {
            StdOut.println(terms[i]);
        }
        StdOut.printf("Top %d by reverse-weight order:\n", k);
        Arrays.sort(terms, Term.byReverseWeightOrder());
        for (int i = 0; i < k; i++) {
            StdOut.println(terms[i]);
        }
    }
}

/*************
 * Sample Run
 * ***********
 * kuntals-MacBook-Pro:Project#3 kuntal$ javac-algs4 Term.java
 * kuntals-MacBook-Pro:Project#3 kuntal$ java-algs4 Term.java cities.txt 5
 * Top 5 by lexicographic order:
 * 2200    's Gravenmoer, Netherlands
 * 19190   's-Gravenzande, Netherlands
 * 134520  's-Hertogenbosch, Netherlands
 * 3628    't Hofke, Netherlands
 * 246056  A Coruña, Spain
 * Top 5 by reverse-weight order:
 * 14608512        Shanghai, China
 * 13076300        Buenos Aires, Argentina
 * 12691836        Mumbai, India
 * 12294193        Mexico City, Distrito Federal, Mexico
 * 11624219        Karachi, Pakistan
 * kuntals-MacBook-Pro:Project#3 kuntal$
 */
