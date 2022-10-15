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

        String[] s1 = {}, s2 = {"A"}, s3 = {null,"A",null,"B",null};
        DobbeltLenketListe<String> l1 = new DobbeltLenketListe<>(s1);
        DobbeltLenketListe<String> l2 = new DobbeltLenketListe<>(s2);
        DobbeltLenketListe<String> l3 = new DobbeltLenketListe<>(s3);

        System.out.println(l1.toString() + " " + l2.toString()
                + " " + l3.toString() + " " + l1.omvendtString() + " "
                + l2.omvendtString() + " " + l3.omvendtString());

        // Utskrift: [] [A] [A, B] [] [A] [B, A]


        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>();
        System.out.println(liste.toString() + " " + liste.omvendtString());
        for (int i = 1; i <= 3; i++) {
            liste.leggInn(i);
            System.out.println(liste.toString() + " " + liste.omvendtString());
        }

        // Utskrift:
        // [] []
        // [1] [1]
        // [1, 2] [2, 1]
        // [1, 2, 3] [3, 2, 1]


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

        this.hode = null;
        this.hale = null;

        // Sjekker om tabellen er tom
       Objects.requireNonNull(a, "Tabellen a er null!");

        // Løkke som løper gjennom fra slutt til start
        for (int i = a.length-1; i >= 0; i--) {

            // Legger kun til ny node hvis indeks a[i] ikke har verdien null.
            if (a[i] != null) {

                // Legger til en ny node og øker både antallet og endringer med én
                Node<T> nyNode = new Node<>(a[i]);
                antall++;
                endringer++;

                // Hvis listen er tom så legges noden til halen
                if (tom()) {
                    hale = nyNode;
                }

                // Ellers legges de nye nodene til fra hodet
                else {
                    hode.forrige = nyNode;
                }

                // nyNode sin neste tilordnes hode-verdien
                nyNode.neste = hode;

                // hode tilordnes nyNode sin verdi
                hode = nyNode;
            }
        }
    } // DobbeltLenketListe(T[] a)



    public Liste<T> subliste(int fra, int til) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int antall() {

        // Returnerer antallet som er telt opp i metoden DobbeltLenketListe(T[] a)
        return antall;

    } // antall()

    @Override
    public boolean tom() {

        // Sjekker om listen er tom, hvis ikke returnerer den false.
        if (hode != null) {
            return false;
        }

        // Hvis listen er tom returneres true.
        return true;

    }  // tom()

    @Override
    public boolean leggInn(T verdi) {

        // Mye av løsningen i denne metoden er direkte inspirert av kompendiet, under avsnitt 3.3.2.

        // Null-verdier er ikke tillatt
        Objects.requireNonNull(verdi, "Null-verdier er ikke tillatt.");

        // Oppretter ny node med innkommende verdi
        Node<T> nyNode = new Node<>(verdi);

        // Hvis tom liste
        if (antall == 0)  {

            // Både hode og hale peker på den nye noden
            hode = hale = nyNode;
        }

        // Hvis ikke tom liste
        else {

            // hale sin neste tilordnes nyNode, nyNode sin forrige tilordnes hale sin verdi og halepekeren flyttes bakerst
            hale.neste = nyNode;
            nyNode.forrige = hale;
            hale = nyNode;
        }

        // Øker antallet og endringer med én
        antall++;
        endringer++;

        // Returnerer true ved vellykket innlegging av node
        return true;

    } // leggInn(T verdi)

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

        // Mye av løsningen i denne metoden er direkte inspirert av kompendiet, under oppgaver til avsnitt 3.3.2.

        // Benytter StringBuilder for å akkumulere tegn/verdier til en hel streng.
        StringBuilder tegn = new StringBuilder();

        // Legger til første klammeparantes, ettersom tegnene/verdiene skal samles mellom to klammeparanteser
        tegn.append('[');

        // Hvis listen ikke er tom...
        if (tom() == false) {

            // ... så legges først verdien til hodenoden i den lenkede listen til StringBuilder-en.
            tegn.append(hode.verdi);

            // Deretter finner vi verdien til neste node:
            Node<T> node = hode.neste;

            // For så å traversere gjennom resten (hvis det er noe mer) fram til halenoden.
            while (node != null) {

                // Legger til komma (','), mellomrom (' ') og til slutt verdien til den aktuelle noden.
                tegn.append(',');
                tegn.append(' ');
                tegn.append(node.verdi);

                // Går så videre til neste node
                node = node.neste;
            }
        }

        // Til slutt legges den siste klammeparantesen til
        tegn.append(']');

        // Returnerer den ferdige strengen
        return tegn.toString();

    } // toString()

    public String omvendtString() {

        // Mye av løsningen i denne metoden er direkte inspirert av kompendiet, under oppgaver til avsnitt 3.3.2.

        // Benytter StringBuilder for å akkumulere tegn/verdier til en hel streng, fra hale til hodet.
        StringBuilder tegn = new StringBuilder();

        // Legger til første klammeparantes, ettersom tegnene/verdiene skal samles mellom to klammeparanteser
        tegn.append('[');

        // Hvis listen ikke er tom...
        if (tom() == false) {

            // ... så legges først verdien til halenoden i den lenkede listen til StringBuilder-en.
            Node<T> node = hale;
            tegn.append(node.verdi);

            // Deretter finner vi verdien til forrige node:
            node = node.forrige;

            // For så å traversere gjennom resten (hvis det er noe mer) fram til hodenoden.
            while (node != null) {

                // Legger til komma (','), mellomrom (' ') og til slutt verdien til den aktuelle noden.
                tegn.append(',');
                tegn.append(' ');
                tegn.append(node.verdi);

                // Går så videre til noden før
                node = node.forrige;
            }
        }

        // Til slutt legges den siste klammeparantesen til
        tegn.append(']');

        // Returnerer den ferdige strengen
        return tegn.toString();

    } // omvendtString()

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


