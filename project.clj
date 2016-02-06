(defproject hots "0.1.0-SNAPSHOT"
  :description "Library designed to randomize hero picks for HOTS custom games"
  :url "http://us.battle.net/heroes/en/"
  :license {:name "GNU General Public License v3.0"
            :url  "http://www.gnu.org/licenses/gpl-3.0.en.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/data.json "0.2.6"]
                 [clj-http "2.0.1"]
                 [cheshire "5.5.0"]
                 [org.clojure/math.combinatorics "0.1.1"]
                 [org.clojure/tools.trace "0.7.5"]]
  :main ^:skip-aot hots.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
