# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende student:
* Alexander Hagen Huse, s354599, s354599@oslomet.no

# Arbeidsfordeling

Hele denne obligen har blitt løst av Alexander.

# Oppgavebeskrivelse

Her følger en kort beskrivelse av de ulike oppgaveløsningene. For mer detaljer, se kommentarer i kildekoden.

### Oppgave 1:

Metoden `antall()` returnerer antallet som er telt opp, mens `tom()` sjekker om listen er tom.

I `DobbeltLenketListe(T[] a)` brukes en `for`-løkke som løper fra bakerste posisjon i arrayet og fram til første posisjon,
og som kun legger til en ny node hvis indeksen som mates inn (`a[i]`) ikke har verdien null. Antallet og endringer 
økes med én for hver node som legges til.

### Oppgave 2:

I metoden `toString()` og `omvendtString()` benyttes `StringBuilder` for å akkumulere tegn og verdier til en hel streng. Etter å
ha lagt til klammeparentes `[`, så sjekkes det om listen er tom. Hvis ikke den er tom, så legges hode-/haleverdien til,
for så å bruke en `while`-løkke til å traversere gjennom resten (hvis det er noe mer) med `node.neste`/`node.forrige` 
fram til hale-/hodenoden. Til slutt legges avsluttende klammeparentes `]` på.

For metoden `leggInn(T verdi)` så legges en ny node med innkommende verdi bakerst i lista og returnere `true`.
Hvis tom liste, så settes både hode- og halepeker på den nye noden. Ellers settes tilordnes hale sin neste verdien til 
den nye noden, den nye noden sin forrige tilordnes hale sin verdi og halepekeren flyttes bakerst.

### Oppgave 3:

Den private hjelpemetoden `finnNode(int indeks)` sjekker først om parameteret indeks er mindre enn antall/2,
i så fall letes det etter noden fra hode. Hvis indeksen er lik eller større enn antall/2, så letes det etter noden
fra halen.

Metoden `hent(int indeks)` bruker `finnNode`-metoden, og returnerer den aktuelle nodens verdi.

`oppdater(int indeks, T nyverdi)` finner også den aktuelle noden ved hjelp av parameteren `indeks` som sendes til
metoden `finnNode`. Når noden er funnet lagres den gamle verdien i en variabel, mens nodens verdi oppdateres til
ny verdi ved hjelp av parameteren `T nyverdi`. Endringer økes og gammel verdi returneres.

Metoden `Liste<T> subliste(int fra, int til)` oppretter en instans av klassen `DobbeltLenketListe`. Det benyttes
en `for`-løkke til å hente ut aktuelle verdier fra og med den oppgitte indeksen i `fra`-parameteren og til 
den oppgitte indeksen i `til`-parameteren. Herunder brukes metodene `finnNode()` og `leggInn()`.

### Oppgave 4:
Kildekoden til metoden `indeksTil(T verdi)` er hentet fra kompendiet under "Løsningsforslag - oppgaver i Avsnitt 3.3.3".
Metoden sjekker først om `verdi` er null, og returnerer da `-1`. Ellers starter letingen ved hode. En `for`-løkke
løper gjennom og sammenlikner `verdi`-parameteren med `node.verdi` ved å bruke `equals()`. Finnes ikke verdien
i lista, så returneres `-1`, ellers returneres den første verdien fra venstre som er lik verdien det søkes etter.

`inneholder(T verdi)` returnerer `true` hvis lista inneholder verdien som søkes etter. Dette ved hjelp av overnevte metode.
Ellers returneres `false` hvis `indeksTil` returnerer `-1`.

### Oppgave 5:
Se kommentarer i kildekoden.

### Oppgave 6:

### Oppgave 8:
