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

    > `lein run`

3. Progam will show the suggested setup.
  * If you like the hero setup **type anything**, for example: `cool` and press *Enter*, key.
  * Else, if you **don't like** hero setup, then just don't type anything and press *Enter*.

## License

This application is no way affiliated with, associated with or endorsed by Blizzard Entertainment, Inc.

Copyright © 2016

Distributed under the GNU General Public License v3.0.
