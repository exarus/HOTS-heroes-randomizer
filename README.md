# HOTS-heroes-randomizer

A Clojure library designed to
randomize hero picks for HOTS custom games.

## Usage

1. Fill the db.clj with proper `heroes-map` for your friends

    > `{:Anton  #{"Artanis"
                 "Butcher"}
        :Bratus #{"Anub'arak"
                  "Brightwing"}}`

2. From the root directory run command:

    > `lein repl`

3. In REPL run:

    > `(plain-random-teams db/gamers :free-pick true)`

    or

    > `(mirror-random-teams [:Anton :Konstantine :Ruslan :Vlad :Shiron :Bratus])`

## License

Copyright © 2016

Distributed under the GNU General Public License v3.0.
