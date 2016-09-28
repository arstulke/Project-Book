#language:de
Funktionalität: Buch suchen anhand der ISBN
  Szenario: Suche mit valider ISBN
    Wenn ich ein Buch mit der ISBN "9781941222294" suche
    Dann bekomme ich das Buch mit folgenden Merkmalen angezeigt:
      |Title                                                                              |Autor                               |Verlag             |Erscheinungsdatum|
      |The Cucumber for Java Book: Behaviour-Driven Development for Testers and Developers|Seb Rose; Matt Wynne; Aslak Hellesoy|Pragmatic Bookshelf|February 2015    |

  Szenario: Suche Buch mit invalider ISBN
    Wenn ich ein Buch mit der ISBN "1554643541564564dfghjklö125" suche
    Dann bekomme ich folgende Aufforderung "ISBN ist inkorrekt. Bitte überprüfe das Format der ISBN"

  Szenario: Suche ein Buch mit der ISBN-10
    Wenn ich die ISBN-10 "1941222294" eingebe
    Dann wird die ISBN-10 in die ISBN-13 "9781941222294" umgewandelt

  Szenario: Suche mit unbekannter ISBN
    Wenn ich ein Buch mit der ISBN "0000000000000" suche
    Dann bekomme ich folgende Aufforderung "Das Buch konnte nicht gefunden werden oder die ISBN ist inkorrekt."