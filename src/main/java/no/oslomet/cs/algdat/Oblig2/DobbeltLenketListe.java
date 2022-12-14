package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.*;


public class DobbeltLenketListe<T> implements Liste<T> {

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

        // Sjekker om verdien som det søkes etter og fjernes er null
        if (verdi == null) {
            return false;
        }

        // Starter letingen etter noden som skal fjernes fra hode
        Node<T> node = hode;

        // Hvis verdien er lik hodeverdien...
        if (verdi.equals(hode.verdi)) {

            // ... og hvis lista kun inneholder én node...
            if (antall == 1) {

                // ... så fjernes denne ved at både hode og hale peker på null
                hode = hale = null;
            }

            // Ellers fjernes denne ved at hodepekeren flyttes én til høyre og den tidligere hodeverdien nulles
           else {
                // Hode peker på ny node
                hode = hode.neste;
                hode.forrige = null;
            }
        }

        // Hvis verdi er samme som haleverdien
        else if (verdi.equals(hale.verdi)) {

            // Hale-pekeren flyttes en bakover. Hale sin neste peker på null.
            hale = hale.forrige;
            hale.neste = null;
        }

        // Hvis hverken hode eller hale er lik verdien, så løpes det gjennom lista med en for-løkke
        else {
            for (int i = 1; i < antall; i++) {

                if (verdi.equals(node.verdi)) {

                    // Legger nodens neste og forrige inn i variablene a og c
                    Node<T> a = node.forrige;
                    Node<T> c = node.neste;

                    // Kobler fra noden som skal fjernes, og kobler nodene på hver side til hverandre.
                    a.neste = c;
                    c.forrige = a;
                    node.forrige = null;
                    node.neste = null;

                    // Hopper ut av for-løkka
                    break;
                }

                // Går videre til neste node
                else if (i != antall-1) {
                    node = node.neste;
                }

                // Returnerer false hvis verdien ikke finnes i lista
                else {
                    return false;
                }
            }
        }

        // Reduserer antallet noder, men øker endringer, og returnerer true.
        antall--;
        endringer++;
        return true;

    } // fjern(T verdi)

    @Override
    public T fjern(int indeks) {

        // Sjekker om indeksen som er oppgitt er lovlig
        indeksKontroll(indeks, false);

        // Initialiserer variabel som lagrer verdien til noden som skal fjernes
        T verdiTilFjernetNode;

        // Starter letingen etter noden som skal fjernes fra hode
        Node<T> node = hode;

        // Hvis indeksen er null
        if (indeks == 0) {

            // Hvis lista kun inneholder én node, så fjernes denne ved at både hode og hale peker på null
            if (antall == 1) {

                // Legger verdien til noden som skal fjernes inn i variabelen verdiTilFjernetNode
                verdiTilFjernetNode = hode.verdi;
                hode = hale = null;
            }

            // Hvis listen inneholder flere enn én node, så fjernes aktuelle node ved å endre pekerne
            else {

                // Legger verdien til noden som skal fjernes inn i variabelen verdiTilFjernetNode
                verdiTilFjernetNode = hode.verdi;

                // Hode peker på ny node
                hode = hode.neste;
                hode.forrige = null;
            }
        }

        // Hvis indeksen er lik halen
        else if (indeks == antall-1) {

            // Legger verdien til noden som skal fjernes inn i variabelen verdiTilFjernetNode
            verdiTilFjernetNode = hale.verdi;

            // Hale-pekeren flyttes en bakover. Hale sin neste peker på null.
            hale = hale.forrige;
            hale.neste = null;
        }

        else {

            for (int i = 0; i < indeks; i++) {
                node = node.neste;
            }

            // Legger verdien til noden som skal fjernes inn i variabelen verdiTilFjernetNode
            verdiTilFjernetNode = node.verdi;

            // Legger de aktuelle nodene inn i variablene a, b og c
            Node<T> a = node.forrige;
            Node<T> b = node;
            Node<T> c = node.neste;

            // Kobler fra noden som skal fjernes, og kobler nodene på hver side til hverandre.
            a.neste = c;
            c.forrige = a;
            b.forrige = null;
            b.neste = null;
        }

        // Reduserer antallet noder, men øker endringer, og returnerer verdien til noden som fjernes.
        antall--;
        endringer++;
        return verdiTilFjernetNode;

    } // T fjern(int indeks)

    @Override
    public void nullstill() {

        for (int i = antall-1; i >= 0; i--) {
            fjern(i);
        }
    }

    @Override
    public String toString() {

        // Mye av løsningen i denne metoden er direkte inspirert av kompendiet, under oppgaver til avsnitt 3.3.2.

        // Benytter StringBuilder for å akkumulere tegn/verdier til en hel streng.
        StringBuilder tegn = new StringBuilder();

        // Legger til første klammeparantes, ettersom tegnene/verdiene skal samles mellom to klammeparanteser
        tegn.append('[');

        // Hvis listen ikke er tom...
        if (!tom()) {

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
        if (!tom()) {

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
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {

        // Sjekker om indeksen som er oppgitt er lovlig
        indeksKontroll(indeks, false);

        return new DobbeltLenketListeIterator(indeks);
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

            // Sjekker om indeksen som er oppgitt er lovlig
            indeksKontroll(indeks, false);

            // Starter letingen etter noden
            Node<T> node = hode;

            if (indeks != 0) {
                for (int i = 0; i < indeks; i++) {
                    node = node.neste;
                }
            }

            denne = new Node<>(node.verdi);

            // Resten er som den ferdigkodede konstruktøren over
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {

            // Deler av koden er hentet fra kompendiet, programkode 3.2.5 e).

            // Sjekker om iteratorendringer er lik endringer
            if (iteratorendringer != endringer) {
                throw new ConcurrentModificationException("Iteratorendringer og endringer samsvarer ikke");
            }

            // Sjekker om det er flere igjen i listen
            if (!hasNext()) {
                throw new NoSuchElementException("Det er ikke flere igjen i lista.");
            }

            fjernOK = true;

            // Tar vare på nodeverdien og flytter den til neste node
            T denneVerdi = denne.verdi;
            denne = denne.neste;

            // Returnerer verdien
            return denneVerdi;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

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


