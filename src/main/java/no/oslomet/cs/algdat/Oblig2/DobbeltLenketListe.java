package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.io.InvalidObjectException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
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


        Character[] c = {'A','B','C','D','E','F','G','H','I','J',};
        DobbeltLenketListe<Character> liste3 = new DobbeltLenketListe<>(c);
        System.out.println(liste3.subliste(3,8));  // [D, E, F, G, H]
        System.out.println(liste3.subliste(5,5));  // []
        System.out.println(liste3.subliste(8,liste3.antall()));  // [I, J]
        // System.out.println(liste3.subliste(0,11));  // skal kaste unntak

        liste = new DobbeltLenketListe<>();

        liste.leggInn(0, 4);  // ny verdi i tom liste
        liste.leggInn(0, 2);  // ny verdi legges forrest
        liste.leggInn(2, 6);  // ny verdi legges bakerst
        liste.leggInn(1, 3);  // ny verdi nest forrest
        liste.leggInn(3, 5);  // ny verdi nest bakerst
        liste.leggInn(0, 1);  // ny verdi forrest
        liste.leggInn(6, 7);  // ny verdi legges bakerst
        System.out.println(liste);

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

        // Sjekker om indeksene fra og til er lovlige
        fratilKontroll(antall, fra, til);

        // Oppretter en instans  av  klassen DobbeltLenketListe
        DobbeltLenketListe<T> nySubliste = new DobbeltLenketListe<>();

        // Deklarerer node-variabelen
        Node<T> node;

        // Løkke som løper fra og med indeksen "fra" til indeksen "til".
        for (int i = fra; i < til; i++) {

            // Finner node-verdien til den aktuelle noden
            node = finnNode(i);

            // Legger inn den aktuelle noden i nySubliste
            nySubliste.leggInn(node.verdi);
        }

        // Endringer settes til 0
        endringer = 0;

        // Returnerer sublisten
        return nySubliste;

    } // Liste<T> subliste(int fra, int til)

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

        // Sjekker om verdien som legges inn er null
        Objects.requireNonNull(verdi, "Null-verdi er ulovlig!");

        // Sjekker om indeksen som er oppgitt er lovlig
        indeksKontroll(indeks, true);

        // Oppretter ny node med innkommende verdi
        Node<T> nyNode = new Node<>(verdi);

        // Hvis indeksen er 0 skal ny verdi ligge først
        if (indeks == 0) {

            // Hvis lista på forhånd er tom så peker både hode og hale til samme node
            if (antall == 0) {
                hale = hode = nyNode;
            }

            else {
                // Hode peker på ny node
                hode.forrige = nyNode;
                nyNode.neste = hode;
                hode = nyNode;
            }

        }

        // Hvis indeksen er lik antallet skal noden ligge bakerst
        else if (indeks == antall) {

            // hale sin neste tilordnes nyNode, nyNode sin forrige tilordnes hale sin verdi og halepekeren flyttes bakerst
            hale.neste = nyNode;
            nyNode.forrige = hale;
            hale = nyNode;
        }

        // Ellers løper vi gjennom en løkke og legger noden inn der den oppgitte indeksen tilsier
        else {
            Node<T> nodeForfra = hode;
            for (int i = 1; i < indeks; i++) {
                nodeForfra = nodeForfra.neste;
            }

            Node<T> nodeBakfra = nodeForfra.neste;

            // Noden legges inn i lista og kobles på de andre nodene
            nodeForfra.neste = nyNode;
            nyNode.neste = nodeBakfra;
            nodeBakfra.forrige = nyNode;
            nyNode.forrige = nodeForfra;

        }

        // Øker antallet noder i listen med én, samt øker endringer med én
        endringer++;
        antall++;

    } // leggInn(int indeks, T verdi)

    @Override
    public boolean inneholder(T verdi) {

        // Returnerer false hvis verdien ikke finnes ved å kalle på indeksTil og se om det vi får tilbake er -1
         if (indeksTil(verdi) == -1) return false;

        // Returnerer true hvis verdien finnes
        else return true;

    } // inneholder(T verdi)

    @Override
    public T hent(int indeks) {

        // Koden er hentet fra kompendiet, programkode 3.3.3 b.

        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi) {

        // Koden i denne metoden er hentet fra kompendiet under "Løsningsforslag - oppgaver i Avsnitt 3.3.3"

        // Hvis verdien er null, så returneres -1.
        if (verdi == null) {
            return -1;
        }

        // Ellers starter letingen etter verdien ved hode
        Node<T> node = hode;

        // Løkke som løper gjennom og sjekker verdiene på hver indeks i listen
        for (int indeks = 0; indeks < antall ; indeks++)
        {
            // Sammenlikner nodeverdien med inputverdien og returnerer indeksen hvis verdien finnes
            if (node.verdi.equals(verdi)) {
                return indeks;
            }

            // Går til neste node hvis verdien ennå ikke er funnet
            node = node.neste;
        }

        // Returnerer -1 hvis verdien ikke finnes i listen
        return -1;

    } // indeksTil(T verdi)

    @Override
    public T oppdater(int indeks, T nyverdi) {

        // Koden er i stor grad hentet fra kompendiet, programkode 3.3.3 b.

        Objects.requireNonNull(nyverdi, "Ikke tillatt med null-verdier!");

        indeksKontroll(indeks, false);

        Node<T> node = finnNode(indeks);
        T gammelVerdi = node.verdi;

        node.verdi = nyverdi;
        endringer++;

        return gammelVerdi;
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

    private Node<T> finnNode(int indeks) {

        Node<T> node;

        if (indeks < antall/2) {
            node = hode;
            for (int i = 0; i < indeks; i++) {
                node = node.neste;
            }
        }

        else {
            node = hale;
            for (int i = antall-1; i > indeks; i--) {
                node = node.forrige;
            }
        }
        return node;
    }

    // Kopiert fra kompendiet, Programkode 1.2.3 a.
    private static void fratilKontroll(int antall, int fra, int til)
    {
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > antall(" + antall + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }

} // class DobbeltLenketListe


