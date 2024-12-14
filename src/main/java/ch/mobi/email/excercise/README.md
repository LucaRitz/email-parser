# EMail-Parser

Der EMail-Parser soll einen Input-String in Form einer EMail-Adresse
zu einem Objekt konvertieren, wenn die EMail-Adresse
gültig ist. Ansonsen soll eine "InvalidEmailException" geworfen werden.

## Definiere eine Grammatik für eine EMail-Adresse:
- Sie besteht aus einem Adress- und einem Domainteil, die von einem "@" getrennt werden.
- Der Adress- wie auch der Domainteil bestehen aus einer Liste von Namen (ident), welche
  durch Punkte getrennt sind.
- Der Adressteil muss einen Namen beinhalten und darf höchstens noch einen zweiten optionalen Namensteil
  aufweisen.
- Der Domainteil muss aus mindestens zwei Namen bestehen, darf aber beliebig viele weitere haben.
- Beispiel: ```frau.muster@mobi.ch```

Definiere folgende Punkte für die Grammatik:
- Terminalsymbole
- Nonterminalsymbole
- Startsymbol
- EBNF

## Schreibe den Scanner (Tokenizer) für die Grammatik
- Gehe dabei nach dem TDD-Prinzip (Test-Driven-Development) vor. 
Schreibe zuerst die Scanner-Klasse und die öffentliche Methode "next", welche ein Token "UNKNOWN" zurückgibt.
Schreibe danach die zugehörige Testklasse, welche alle möglichen Fälle abdeckt. 
Die Tests sollten allesamt scheitern. Danach kannst du den Scanner implementieren.
Am Ende müssen die Tests erfüllt werden.

## Schreibe den Parser für die Grammatik
- Gehe auch hier nach dem TDD-Prinzip vor. Mocke den Scanner und gib für deinen Test vordefinierte Tokens beim Aufruf
der Methode ```next``` zurück.
- Das Resultat der "parse"-Funtion soll ein Objekt mit folgenden Eigenschaften sein:
```
Email {
    address: {
        parts: List<String>
    },
    domain: {
        parts: List<String>
    }
}
```
- Wenn die EMail-Adresse ungültig ist, soll eine ```InvalidEmailException``` geworfen werden.

## Schreibe einen Combined-Test
Um den Scanner zusammen mit dem Parser zu testen, kannst du zusätzlich noch einen CombinedTest schreiben.
Definiere dazu einen Eingabestring einer EMail-Adresse und das erwartete resultieren Objekt.
Prüfe, ob der Scanner und der Parser das gewünschte Resultat liefern.
