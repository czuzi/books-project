# Vizsgaremek

## Leírás

A projektem az olvasmány élmények nyomonkövetésére kinál megoldást. Egy felhasználó létrehozhat maganak különböző polcokat amire könyveket helyezhet el. A könyveket polcoktól és felhasználóktól függetlenül lehet létrehozni.

---

## Felépítés

### User entitás

A `User` entitás a következő attribútumokkal rendelkezik:

- `id` (generalt)
- `username` (nem lehet null, minimum 6 karakter, maximum 16)
- `email`(nem lehet null, jakarta email validacio)
- `password`(passay validacio)

A `User` és a `Shelf` entitások között kétirányú, 1-n kapcsolat van.

Végpontok:

| HTTP metódus | Végpont             | Leírás                                                                                                                                                     |
| ------------ | ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------- |
| GET          | `"/api/users"`      | lekérdezi az összes entitást, requestParam parameterkent optionalt var, ha nem ures, akkor a megadott string alapjan adja vissza az azt tartalmazo userket |
| GET          | `"/api/users/{id}"` | lekérdez egy entitást `id` alapján                                                                                                                         |
| POST         | `"/api/users/"`     | létrehoz egy felhasználót                                                                                                                                  |
| PUT          | `"/api/users/{id}"` | módosítja a megadott id-val rendelkező felhasználót                                                                                                        |
| DELETE       | `"/api/users/{id}"` | törli a megadott id-val rendelkező felhasználót                                                                                                            |

---

### Book entitás

A `Book` entitás a következő attribútumokkal rendelkezik:

- `id`
- `author`
- `title`
- `isbn`
- `numberOfPages`
- `yearOfPublish`
- `genre`

Végpontok:

| HTTP metódus | Végpont             | Leírás                                                                  |
| ------------ | ------------------- | ----------------------------------------------------------------------- |
| GET          | `"/api/books"`      | lekérdezi az összes entitást, opcionálisan cím szerint is lehet keresni |
| GET          | `"/api/books/{id}"` | lekérdez egy entitást `id` alapján                                      |
| POST         | `"/api/books/"`     | létrehoz egy felhasználót                                               |
| PUT          | `"/api/books/{id}"` | módosítja a megadott id-val rendelkező felhasználót                     |
| DELETE       | `"/api/books/{id}"` | törli a megadott id-val rendelkező felhasználót                         |

### Shelf entitás

A `Shelf` entitás a következő attribútumokkal rendelkezik:

- `id`
- `shelfName`
- `user`
- `shelvedBooks`

A `Shelf` és a `User` entitások között kétirányú, n-1 kapcsolat van.

Végpontok:

| HTTP metódus | Végpont                              | Leírás                                                                  |
| ------------ | ------------------------------------ | ----------------------------------------------------------------------- |
| GET          | `"/api/books/{bookId}/shelves"`      | lekérdezi az összes entitást, opcionálisan cím szerint is lehet keresni |
| GET          | `"/api/books/{bookId}/shelves/{id}"` | lekérdez egy entitást `id` alapján                                      |
| POST         | `"/api/books/{bookId}/shelves/"`     | létrehoz egy felhasználót                                               |
| PUT          | `"/api/books/{bookId}/shelves/{id}"` | módosítja a megadott id-val rendelkező felhasználót                     |
| DELETE       | `"/api/books/{bookId}/shelves/{id}"` | törli a megadott id-val rendelkező felhasználót                         |

### ShelvedBook entitás

A `Book` entitás a következő attribútumokkal rendelkezik:

- `id`
- `book`
- `readDate`
- `addDate`
- `shelf`

A `Shelf` és a `ShelvedBook` entitások között kétirányú, 1-n kapcsolat van.
A `ShelvedBook` és a `Book` entitások között egyirányú, n-1 kapcsolat van.

Végpontok:

| HTTP metódus | Végpont                                                    | Leírás                                                                  |
| ------------ | ---------------------------------------------------------- | ----------------------------------------------------------------------- |
| GET          | `"/api/books/{bookId}/shelves/{shelfId}/shelvedbooks"`     | lekérdezi az összes entitást, opcionálisan cím szerint is lehet keresni |
| GET          | `"/api/books/{bookId}/shelves{shelfId}/shelvedbooks/{id}"` | lekérdez egy entitást `id` alapján                                      |
| POST         | `"/api/books/{bookId}/shelves{shelfId}/shelvedbooks/"`     | létrehoz egy felhasználót                                               |
| PUT          | `"/api/books/{bookId}/shelves{shelfId}/shelvedbooks/{id}"` | módosítja a megadott id-val rendelkező felhasználót                     |
| DELETE       | `"/api/books/{bookId}/shelves{shelfId}/shelvedbooks/{id}"` | törli a megadott id-val rendelkező felhasználót                         |

---

## Technológiai részletek

Itt le tudjátok írni, hogy háromrétű, MariaDb, SwaggerUI, Repository, Service, Controller, Docker, Nem kell hogy hosszú legyen.

---
