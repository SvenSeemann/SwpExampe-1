# Architekturdokumentation

### Fviv Festivalmanager

erstellt von:
<div style="padding-left:30px">
Praktikumsgruppe swt13w33m  
Hendric Eckelt, Justus Adam, Max Schwarze, Niklas Fallik, Nikolai Kostka
</div>
<p> </p>
<div style="font-size: smaller">
Angepasstes Template angelehnt an das ARC Template für das Softwarepraktikum an der TU Dresden
</div>


### Änderungsübersicht

Version | Datum | Bearbeiter | Beschreibung
--------|-------|------------|-------------
1.0 | 12.01.15 | Nikolai Kostka | Erstellung der Architekturdokumentation
1.1 | 16.01.15 | Justus Adam | Änderungen und Vervollständigung

### Basisdokumente

Dokument | Beschreibung
-|
Pflichtenheft | Pflichtenheft der Version 1.1 mit allen Enthaltenen Diagrammen

# Inhaltsverzeichnis

1. Einfühung und Ziele  
    1. Aufgabenstellung
    * Qualitätsziele
    * Stakeholder
* Randbedingungen
    1. Technische Randbedingungen
    * Konventionen
* Kontextabgrenzung
    1. Externe Schnittstellen
* Lösungsstrategien und Entwurfsentscheidungen
* Konzepte
    1. Persistenz
    * Benutzungsoberfläche
* Glossar

## 1. Einführung und Ziele
Das folgende Dokument hat die Beschreibung und Erläuterung des Aufbaus und der Funktionen der FVIV Festivalmanager Software, geschrieben im Rahmen des SWT Softwareprojektes 2014/15, zum Ziel.
### 1.2. Aufgabenstellung
Der Festivalveranstalter, die „FVIV GmbH“, sieht sich mit ihrem derzeitigen System den wachsenden Aufgaben einer Festival-Organisation und –Durchführung nicht mehr gewachsen und benötigt daher zeitnah ein neues IT-System, das interne Verwaltungsaufgaben, als auch den Kundenkontakt übernimmt. Daher werden sie damit beauftragt, dieses System zu entwerfen und auch umzusetzen.  
 Das System soll unter anderem in der Planungsabteilung eingesetzt werden, in der Festivals angelegt werden. Dazu muss ein Termin festgelegt werden und die passende Location zu diesem Zeitpunkt gebucht werden. Doppelbuchungen dieser Locations sind unter allen Umständen zu vermeiden. Jede Location bietet Platz für eine maximale Anzahl von Besuchern und Bühnen. Außerdem ist jede Location in verschiedene, eindeutig gekennzeichnete Bereiche unterteilt, die wiederum einen Teil der maximalen Besucherzahl fassen können und sich außerdem in Camping-, Park-, Catering- und Stage-Bereiche einordnen. Um dem Planungsteam einen guten Überblick zu verschaffen, ist es zwingend notwendig, dass das Gelände und die Bereiche visualisiert werden können. Sobald die Location gebucht ist, können für dieses Festival Anpassungen durchgeführt werden, wie die Bühnenpositionierung, Toilettenbestückung und Cateringstände, und es muss möglich sein, bestimmte Bereiche zu sperren. Die genannten Gegenstände (Bühnen, usw.) werden von externen Anbietern gemietet.In der nächsten Stufe muss das „Line-Up“ zusammengestellt werden. Dazu müssen Angebote bei verschiedenen Künstlern eingeholt werden. Anschließend werden aus diesen Angeboten Künstler ausgewählt und es muss ein Spielplan für jede Bühne erstellt werden. Neben den genannten Künstlern ist weiteres Personal erforderlich, wie Sicherheitspersonal (mindestens einer pro 100 Besuchern), Bedienungen an den Cateringständen, Bühnentechniker (Anzahl wird durch die Band bestimmt), sowie ein Veranstaltungsleiter. Jede Arbeitskraft wird dabei auf Stundenbasis bezahlt.  
 Zu jedem Zeitpunkt während der Planung muss der Planungsabteilung als auch dem Management eine automatische Kostenaufstellung zur Verfügung gestellt werden können, in der Kosten für Mieten, Gagen, Personal und Sonstigem aufgelistet und aggregiert werden. Schon während der Planungsphase kann ein Event freigegeben werden, und es kann ein Kartenpreis festgelegt werden. Es gibt dabei Camping-Tickets, die das ganze Festival über gültig sind und Tageskarten. Diese Tickets werden bis 3 Tage vor Veranstaltungsbeginn in den Filialen der „FVIV GmbH“ verkauft und danach nur noch an der Abendkasse. Es muss den Verkaufsmitarbeitern möglich sein, bei noch vorhandenen Tickets diese zu verkaufen und auszudrucken. Auf jedes Ticket wird ein Barcode bzw. eine eindeutige Nummer aufgedruckt, mit dem es dem Personal am Festivaleingang möglich ist, das Ticket auf Gültigkeit hin zu überprüfen und zu vermeiden, dass verschiedene Personen das Gelände mit derselben Karte betreten. Eine weitere Nutzergruppe ist das Cateringpersonal, das sich an entsprechenden Terminals am Verkaufsstand mit gültigem Login anmeldet. Dort können Getränke und vorgefertigte Speisen ausgewählt und abgerechnet werden. Dabei wird das Gekaufte vom verfügbaren Lagerbestand abgezogen. Sollte ein gewisser Mindestbestand unterschritten werden erfolgt eine Mittteilung an die Festivalleitung. Der Festivalleiter besitzt ebenfalls ein persönliches Terminal, an dem es ihm möglich ist, das Lager einzusehen und Nachbestellungen zu tätigen. Weiterhin sieht er dort aktuelle Besucherzahlen, Nachrichten von anderen Mitarbeitern, sowie Verkaufszahlen des Caterings und die aktuelle Bühnenbelegung. Überall auf dem Festivalgelände verteilt befinden sich Terminals, die für jeden frei nutzbar sind. Dort sieht man neben einem Plan vom Festivalgelände auch den Spielplan für alle Festivaltage. Der Manager der „FVIV GmbH“ sorgt für die Verteilung der Logins an die Mitarbeiter und kann sehen, welcher Mitarbeiter momentan am System angemeldet ist. Weiterhin soll es ihm möglich sein, betriebswirtschaftliche Daten, wie Umsatz, Ausgaben,... , abzurufen und grafisch zu visualisieren.

### 1.2. Qualitätsziele
Ein Funktionsfähiges Festivalmanagement Programm zu erstellen, das die Aufgaben von Festivalleiter bis zu einem einfachem Caterer erleichtern soll. Ebenso für einfache Festivalgäste, die auf Terminals zugreifen und sich über Auftritte verschiedener Künstler informieren können.

### 1.3. Stakeholder
Rolle | Beschreibung | Ziel/Intention | Kontakt | Bemerkung
-|
Auftraggeber | Kunde | Empfänger und Nutzer der Software | Sven Seemann |
Entwickler | Entwickelt die Software | Entwicklung der Software | Niklas Fallik & Team |

##
