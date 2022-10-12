package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;


public class DobbeltLenketListe<T> implements Liste<T> {

    public static void main(String[] args) {
        Liste<String> liste1 = new DobbeltLenketListe<>();
        System.out.println(liste1.antall() + " " + liste1.tom());
        // Utskrift: 0 true

        String[] s = {"Ole", null, "Per", "Kari", null};
        Liste<String> liste2 = new DobbeltLenketListe<>(s);
        System.out.println(liste2.antall() + " " + liste2.tom());
        // Utskrift: 3 false
    }

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() { }

    public DobbeltLenketListe(T[] a) {

        // Sjekker om tabellen er tom
        Objects.requireNonNull(a, "Tabellen a er null!");

        // Initialiserer i, som skal brukes i flere steder
        int i = 0;

        // Finner den første indeksen i a som ikke har en verdi null
        for (; i < a.length && a[i] == null; i++) {

            // Hvis det kun er null-verdier i tabellen, så returneres det at tabellen er null.
            if (i == a.length-1) {
                Objects.requireNonNull(a, "Tabellen a er null!");
            }
        }

        // Legger til noder
        if (i < a.length) {

            // Den første noden legges til
            Node<T> node = hode = new Node<>(a[i]);

            // Løper videre med en for-løkke og legger noder etter hverandre
            for (; i < a.length; i++) {

                // Legger kun til node hvis den ikke er null
                if (a[i] != null) {
                    node = node.neste = new Node<>(a[i]);
                    antall++;
                }
            }

            // Legger til halenoden.
            hale = node;
        }
    }

    public Liste<T> subliste(int fra, int til) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int antall() {

        // Returnerer antallet som er telt opp i metoden DobbeltLenketListe(T[] a)
        return antall;
    }

    @Override
    public boolean tom() {

        // Sjekker om listen er tom, hvis ikke returnerer den false.
        if (hode != null) {
            return false;
        }

        // Hvis listen er tom returneres true.
        return true;
    }










    @Override
    public boolean leggInn(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean inneholder(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T hent(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indeksTil(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T fjern(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException();
    }

    public String omvendtString() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new UnsupportedOperationException();
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

} // class DobbeltLenketListe


