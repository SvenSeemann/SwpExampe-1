# Nutzerdokumentation für den FVIV Festivalmanager

## Inhaltsverzeichnis

@@TOC@@

## Unterstützte Brwoser
Diese Software ist auf allen gängigen Browsern ausser Firefox und IE funktionsfähig. Bitte achten Sie auf eine aktuelle Version Ihres Browsers. 
##Oberfläche
Die Software ist in 3 Bereiche gegliedert: Mitarbeiter, Planung und Terminal. Die Mitarbeiterbereiche haben eine orangene Farbgebung, die Breiche der Planung sind grün und die Terminalbereiche sind blau. 

Die Seite ist immer in 3 Bereiche gegliedert. 

Im Kopf der Seite finden Sie verschiedene Informationen. Links steht der Titel der Seite. In der Mitte steht der Name des aktuell angewählten Festivals. Rechts sehen Sie als welche Person Sie gerade eingeloggt sind.

Am rechten Rand finden Sie das Menu der Seite. Einige Menupunkte wie "Logout" und "Show Chat" finden Sie überall. Weitere Menupunkte sind Bereichsspezifisch. 

Der dritte große Bereich ist der Bereichsspezifisch gefüllte Inhalt der Seite. 

##Manager
###Mitarbeiter
Dieses Menü bietet eine Übersicht über alle registrierten Mitarbeiter. Weiterhin können durch Ausfüllen des Formularfeldes neue Mitarbeiter registriert werden. Um Mitarbeiter zu löschen geben Sie bitte die ID des entsprechenden Mitarbeiters in das entsprechende Formularfeld und klicken Sie auf "löschen".
###Finanzen
Hier erhalten Sie eine Übersicht über alle Finanzen. 
###Accounts
Mit der Nutzerverwaltung können sie Ihre Mitarbeiter registrieren, mit Zugriffsrechten für das System ausstatten und diese auch wieder entfernen.
Wenn vom Manager Mitarbeiter hinzugefügt werden, dann werden automatisch Accounts mit Vorname.Nachname angelegt.

Die Standardaccounts "Manager", "Boss", "Caterer", "Leader" und "Employee" haben als Standardpasswort: 123 .
###Besucherzahlen
Wählen Sie in dem Drop-Down-Menü das Festival aus, deren Besucheranzahl Sie sehen möchten und klicken Sie auf "Festival laden". Anschließend suchen Sie das Datum aus, an dem Sie die Besucherzahl benötigen. Klicken Sie auf "Besucher abfragen". 

##Festivalleiter
###Terminal
Unter diesem Punkt wählen Sie ein Festival aus, deren Finanzen Sie einsehen wollen und Lager verwalten wollen.

### Finanzen
Hier haben Sie eine Überischt über die Einnahmen und Ausgaben des Caterings auf dem Festival. 
### Stock
Dies ist die Lagerverwaltung des Catering. Hier können Sie den aktuellen Lagerbestand, Einkaufs- und Verkaufspreis einsehen. Außerdem können Sie Artikel nachordern. 

##TicketVerkauf
###Tickets erstellen
Wählen Sie ein Festival aus dem Drop-Down-Menü aus und klicken Sie auf "Festival laden". Unterscheiden Sie nun zwischen einem 1-Tages-Ticket und einem 3-Tages-Ticket. Geben Sie nun den gewünschten Festivaltag und Anzahl an. Klicken Sie nun auf "Tickets erstellen".

###Tickets drucken
Geben Sie in das Auswahlfeld die TicketId des zu drucken Tickets an und drucken Sie das Ticket mittels Klick auf den entsprechenden Button. 

###Tickets prüfen
Suchen Sie aus der Liste das gewünschte Festival aus. Geben Sie die zu prüfende TicketId ein und prüfen Sie das Ticket. 

##Festival Terminal  *(Scheiss name- geht das nicht besser?)*
### Übersicht der Festivals
Hier finden Sie eine Übersicht der bestehenden Festivals. Weiterhin können Sie hier auch neue Festivals anlegen. Dies geschieht durch Aussuchen einer Location und Ausfüllen des Formular. Klicken Sie anschließend auf "Festival erstellen". 
### Festivals bearbeiten
Hier haben Sie die Möglichkeit bestehende Festivals zu bearbeiten. Sie können den Geländeplan erstellen und bearbeiten (mehr dazu im Punkt Geländeplanung). Weiterhin können Sie die Anzahl der Angestellten einstellen und deren Lohn. (Achten Sie auf eventuelle Gesetze in Ihrem Land, kann sein, dass das Finanzamt sonst vor der Türe steht ;)  ).

### Mitarbeiter 
 Für Mitarbeiter werden bei der Festivalerstellung Löhne pro Stunde festgelegt. Bei der Finanzkalkulation wird von einem 8 Stunden Arbeitstag pro Mitarbeiter ausgegangen.

## Catering
Die Catering Oberfläche bietet Ihnen Auswahl- und Bestellfunktionen für Speisen und Getränke.

Im Menü finden Sie neben den "Logout" und "Show Chat" Button die Punkte "Festival auswählen", "Meals" und "Drinks".
Unter dem Punkt "Festival auswählen" können Sie zwischen den verfügbaren Festivals auswählen. Im Punkt "Meals" können Sie die verfügbaren Speisen auswählen, bei "Drinks" die verfügbaren Getränke.

Links finden Sie eine Liste mit den gerade ausgewählten Speisen und Getränken. Mit dem Button "Cancel" brechen Sie die aktuelle Aktion ab und alle ausgewählten Artikel werden gelöscht. bei Klick auf "Confirm", zahlt der Kunde, die gewählten Artikel werden aus dem Lagerbestand herausgerechnet und die Einnahmen in die Finanzstatistiken eingetragen. 
  
## Geländeplanung
Der Geländeplaner erlaubt es Ihnen das Festivallayout bequem und grafisch zu planen und zu speichern.

###Erstellen eines Objektes
Nachdem Sie sich eine Location ausgewählt haben, wird Ihnen ein leeres Festivalgelände vorgegeben. 

Rechts im Menu finden Sie die verwendbaren Objekte, geordnet nach Objekttypen. Mit Klick auf den Typ erweitert sich das Menu um die Objekte dieses Typs.

Klicken Sie nun auf das Objekt, dass Sie erstellen möchten. Nun erscheint oben links in der Ecke des Areal das gewünschte Objekt. 

Sollte das Objekt zu groß für das Gelände sein, wird Ihnen dies mitgeteilt und das zu große Objekt nicht erstellt. 

###Bewegen eines Objektes auf dem Gelände
Wenn Sie ein Objekt auf dem Gelände bewegen möchten, können Sie dies ganz einfach per Drag-and-Drop machen. 

### Das Kontext-Menu
Wenn Sie auf ein Objekt mit der rechten Maustaste klicken, erscheint das Kontextmenu. Hier können Sie Informationen über die Höhe, Breite, und Name des Objektes erfahren. 

Weiterhin haben Sie die Mögichkeit per Klick auf die jeweiligen Buttons das Objekt zu drehen und zu löschen.

## Kommunikation
Für die Kommunikation stehen Ihnen ein Chatsystem zur Verfügung. Personen mit den entsprechenden Berechtigungen sind in der Lage anderen Nutzern via des Chats Nachrichten zu schreiben.

Dazu öffnen Sie zunächst ihr Chatfenster, indem Sie auf <button>Chat Öffnen</button> klicken.

In der Liste der möglichen Empfänger können Sie nun durch klicken ihren gewünschten Empfänger auswählen.

Daraufhin erscheint ein Eingabefeld
<div style="border:1px solid #CCCCCC;border-radius:5px;box-shadow:0 0 5px #999999;padding:10px 15px;float:left;"><div>**Empfänger**</div><textarea style="box-shadow:0 0 5px #999999 inset;margin:10px 0" rows=3 cols=34>Ihre persönliche Nachricht hier!</textarea><button style="display:block;margin:10px 0;">Absenden</button>
</div>
<div style="clear:both;"></div>
In welchem Sie Ihre Nachricht eingeben können. Ein Klick auf den <button>Absenden</button> Button versendet Ihre Nachricht.

Sollten Sie den falschen Empfänger ausgewählt haben, können Sie diesen mit einem Klick auf den korrekten Empfänger ändern.

*Bitte Beachten: In der aktuellen Version löscht das Auswählen eines neuen Empfängers Ihre Nachricht*

## Besucherterminal
Das Besucherterminal bietet eine sichere Umgebung die die Besucher Ihres Festivals benutzen können um sich zu orientieren.

Dazu gehören ein Spiel- und Geländeplan.