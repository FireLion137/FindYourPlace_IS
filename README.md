# FindYourPlace
<p align="center">
  <img width="200" src="https://github.com/FireLion137/FindYourPlace_IS/assets/55352707/a5daae28-f6a9-4022-b63e-1becb1226da9">
</p>
Repository per il progetto FindYourPlace per l'esame di Ingegneria del Software <br>(Corso di Informatica - UniSA)<br><br>
Per visualizzare il lavoro dedicato alla creazione del modulo di IA, <a href="https://github.com/FireLion137/FindYourPlace_FIA">clicca qui!</a>

# Autori
+ Pietro Esposito - p.esposito62@studenti.unisa.it
+ Alessandro Nacchia - a.nacchia9@studenti.unisa.it
+ Lorenzo Castellano - l.castellano4@studenti.unisa.it
<br>

# Indice
+ [Introduzione](#introduzione)
+ [Prerequisiti](#prerequisiti)
+ [Installazione](#installazione)
    + [Clonazione della Repository](#clonazione-della-repository)
    + [Installazione dell'Applicazione Web](#installazione-dellapplicazione-web)
    + [Installazione del Modulo IA](#installazione-del-modulo-ia)
    + [Installazione del Database](#installazione-del-database)
+ [Test](#test)
+ [Altro](#altro)

## Introduzione
FindYourPlace (FYP) è una piattaforma progettata per supportare gli utenti nella scelta dei luoghi che meglio si adattano al loro stile di vita, offrendo un'esperienza unificata e semplice.<br>
Gli utenti possono ottenere approfondimenti e informazioni dettagliate basate sui parametri inseriti, tutto in un unico ambiente, grazie a un modulo di intelligenza artificiale.

## Prerequisiti
Per installare e eseguire FindYourPlace, assicurati di avere installato i seguenti software:
+ Tomcat 10.1
+ MySQL Workbench
+ Java Virtual Machine (JVM)
+ Python 3.10
+ Maven
+ IDE per Java e Python (IntelliJ IDEA è consigliato)

## Installazione
### Clonazione della Repository
Clona il repository sul tuo computer locale:
```
git clone https://github.com/Pietro1377/FindYourPlace_IS.git
```

### Installazione dell'Applicazione Web
1. Apri il repository clonato nel tuo IDE.
2. Usa Maven per impacchettare l'applicazione. Nel terminale, esegui:
```
mvn clean package -Dmaven.test.skip=true
```
&nbsp;&nbsp;Questo creerà un file `FindYourPlace.war` nella directory `target`.
1. Deploia il file `FindYourPlace.war` in Tomcat posizionandolo nella directory `webapps`.
2. Avvia il server Tomcat eseguendo il file `Tomcat10` nella directory `bin`.

### Installazione del Modulo IA
1. Clona il repository del modulo IA da GitHub.
2. Vai nella directory `dist` all'interno del pacchetto del modulo.
3. bInstalla il modulo usando pip:
```
pip install fyp.ai_pkg-1.0.0-py3-none-any.whl
```

### Installazione del Database
1. Usa MySQL Workbench per impostare lo username su `root` e la password su `password`.
2. Esegui lo script `createDB.sql` per creare il database.
3. Esegui lo script `PopulateDB.sql` per popolare il database con i dati necessari per i test.

### Test
Per eseguire i test e generare un report:
+ Nel tuo IDE, vai al menu Maven.
+ Espandi il progetto e naviga su `FindYourPlace > Plugins > site`.
+ Esegui `site:site` per eseguire tutti i test e generare un report nella directory `target`.
+ Apri `index.html` nella directory `target` per visualizzare i report dettagliati dei test, inclusa la copertura delle branch.

### Altro
Per ulteriori informazioni dettagliate, puoi consultare la documentazione del progetto: [FindYourPlace ProjectDocs](https://github.com/FireLion137/FindYourPlace_IS/tree/main/projectDocs/Prodotto)<br>
Per visualizzare i Reports generati automaticamente da Maven, puoi cliccare [QUI!](https://firelion137.github.io/FindYourPlace_IS/project-reports.html)<br>
