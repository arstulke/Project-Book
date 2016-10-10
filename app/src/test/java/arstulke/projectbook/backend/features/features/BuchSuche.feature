#language:de
Funktionalität: Buch suchen anhand der ISBN
  Szenariogrundriss: Suche mit valider ISBN
    Wenn ich ein Buch mit der ISBN "<ISBN>" suche
    Dann bekomme ich das Buch mit folgenden Merkmalen angezeigt:
      |Title                                                                              |Autor                               |Verlag             |Erscheinungsdatum|
      |The Cucumber for Java Book|Seb Rose; Matt Wynne; Aslak Hellesoy|O'Reilly UK Ltd.|2015    |

    Beispiele: valide ISBN-Nummern
      |     ISBN         |
      | 9781941222294    |
      | 978 1941 222 294 |
      | 978-1941-222-294 |
      | 1941222294       |
      | 194 122 229 4    |

  Szenariogrundriss: Suche Buch mit invalider ISBN
    Wenn ich ein Buch mit der ISBN "<ISBN>" suche
    Dann bekomme ich folgende Ausgabe "ISBN ist inkorrekt. Bitte überprüfe das Format der ISBN"

    Beispiele: invalide ISBN-Nummern
      |    ISBN        |
      | X781941222294  |
      | 941222294      |
      | 97819412222234 |

  Szenario: Suche mit unbekannter ISBN
    Wenn ich ein Buch mit der ISBN "7781941222294" suche
    Dann bekomme ich folgende Ausgabe "Das Buch konnte nicht gefunden werden oder die ISBN ist inkorrekt."
