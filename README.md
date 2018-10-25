# HOTS-heroes-randomizer

App was created to randomize hero picks for [Heroes of the Storm](https://heroesofthestorm.com) custom games. No longer can be used on the Lost Cavern map.

## Usage

1. Fill the db.clj with proper `heroes-map` for your friends
```clojure
{:John  #{"Artanis" "Butcher"}
 :Wick #{"Anub'arak" "Brightwing"}}
```

2. From the root directory run command:
- for Windows: `lein run`
- for macOS and Linux: `./lein run`

3. Progam will show the suggested setup.
  * If you like the hero setup **type anything**, for example: `cool` and press *Enter*, key.
  * Else, if you **don't like** hero setup, then just don't type anything and press *Enter*.
  
## Technologies

App is created using Clojure.

## License

This application is no way affiliated with, associated with or endorsed by Blizzard Entertainment, Inc.

Distributed under the GNU General Public License v3.0.
