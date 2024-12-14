## Grammatik
- Terminalsymbole: eof, ident, "@", "."
- Nonterminalsymbole: EMail, Address, Domain
- Startsymbol: EMail
- EBNF:
  - EMail   = Address "@" Domain
  - Address = ident ["." ident]
  - Domain  = ident "." ident {"." ident}
  - ident   = letter {digit | letter}

First(Email) = ident
Follow(Email) = eof

First(Address) = ident
Follow(Address) = "@"

First(Domain) = ident
Follow(Domain) = eof

