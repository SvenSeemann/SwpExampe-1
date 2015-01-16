# Nutzerdokumentation für den FVIV Festivalmanager

## Inhaltsverzeichnis

@@TOC@@

##Oberfläche
Die Software ist in 3 Bereiche gegliedert: Mitarbeiter, Planung und Terminal. Die Mitarbeiterbereiche haben eine orangene Farbgebung, die Breiche der Planung sind grün und die Terminalbereiche sind blau. 

Die Seite ist immer in 3 Bereiche gegliedert. 

Im Kopf der Seite finden Sie verschiedene Informationen. Links steht der Titel der Seite. In der Mitte steht der Name des aktuell angewählten Festivals. Rechts sehen Sie als welche Person Sie gerade eingeloggt sind. 

am rechten Rand finden Sie das Menu der Seite. Einige Menupunkte wie "Logout" und "Show Chat" finden Sie überall. Weitere Menupunkte sind Bereichsspezifisch. 

Der dritte große Bereich ist der Bereichsspezifisch gefüllte Inhalt der Seite. 

## Die Manager Rolle
Der Manager ist die wichtigste und zentralste Rolle der gesamten Nutzerstruktur.
Der Manager kann die Finanzen einsehen und verwalten.
###Mitarbeiter
Zum Anlegen neuer Mitarbeiter füllen Sie das Formular zum Hinzufügen eines neuen Mitarbeiter aus. Wählen Sie im Drop-Down-Menü anschließend die Art des Useraccounts aus. Klicken Sie nun auf den Speichern-Button.  

Um einen Mitarbeiter zu löschen geben Sie die gewünschte Mitarbeiter-ID ein und klicken Sie auf" löschen". 

###Finanzen
Diese Oberfläche bietet Ihnen eine komplette Übersicht der Finanzen. 

###Accounts
Wenn vom Manager Mitarbeiter hinzugefügt werden, dann werden automatisch Accounts mit Vorname.Nachname angelegt.
Diesen können Sie bearbeiten, indem Sie in das Feld dne Namen des Account eingeben und auf "Account bearbeiten" klicken. 
Sie können Ihre Mitarbeiter registrieren, mit Zugriffsrechten für das System ausstatten und diese auch wieder entfernen.
Die Standardaccounts "Manager", "Boss", "Caterer", "Leader" und "Employee" haben als Standardpasswort: 123 .
###Besucherzahlen
Wählen Sie das Festival aus, deren Besucherzahlen Sie abfragen möchten, und klicken Sie auf "Festival laden". Wählen Sie anschließend das gewünschte Datum und fragen Sie die Anzahl der Besucher ab. 

##Festival Terminal (scheiss name)
Hier legen Sie neue Festivals an und bearbeiten bestehende Festivals. 

### Neues Festival anlegen
Wählen Sie zuerst eine Location aus. Tragen Sie Start- und Enddatum, den Names des Festivals, die Künstler und Kartenpreis ein und erstellen ein neues Festival durch KLick auf den Button. 

###Festival bearbeiten
In diesem Nenü können Sie den Geländeplan erstellen und bearbeiten (mehr dazu im Punkt Geländeplanung). Zudem können Sie Angestellte hinzufügen und deren Gehälter bestimmen- bitte auf Mindestlohn achten, sonst kommt das Finanzamt :P.

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
##Finanzen
###Mitarbeiter
 Für Mitarbeiter werden bei der Festivalerstellung Löhne pro Stunde festgelegt. bei der Finanzkalkulation wird von einem 8 Stunden arbeitstag pro Mitarbeiter ausgegangen
## Nutzerverwaltung

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

## Tickets
Die Ticketverwaltung ermöglicht es Ihnen Tickets anzufordern, auszustellen und zu drucken.

###Tickets erstellen
Wählen Sie sich zuerst das gewünschte Festival aus. Klicken sie anschließend den "Laden" Button. 
Anschließend können Sie zwischen einem 1-Tages-Ticket und einem 3-Tages-Ticket wählen. Suchen Sie über das Drop-Down-Menü nun den gewünschten Festivaltag aus. Geben Sie nun die gewünschte Anzahl der zu erstellenden Tickets an. Mit Klick auf "Ticket erstellen" werden nun Ihre Tickets erstellt. 

### Tickets drucken
Sie können Tickets drucken, indem Sie die gewünschte TicketId in das Feld eingeben und anschließend auf "Ticket drucken" klicken. 

###Ticket prüfen
Sie können Tickets prüfen indem Sie aus der Liste der verfügbaren Festivals das gewünschte auswählen, anschließend die ID des zu prüfenden Tickets eingeben und anschließend das Ticket prüfen.  
