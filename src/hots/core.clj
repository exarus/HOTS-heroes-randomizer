(ns hots.core
  (:gen-class)
  (:require [hots.db :as db]
            [clojure.set :refer :all]
            [clojure.string :as str]
            [clojure.data.json :as json]
            [clj-http.client :as client]
            [clojure.math.combinatorics :as comb]))

(def free-rotation-heroes
  (->>
    (-> (client/get "http://eu.battle.net/heroes/en/")
        (:body)
        (str/split #"window.heroes = ")
        (second)
        (str/split #";")
        (first)
        (json/read-str :key-fn keyword))
    (filter #(:inFreeHeroRotation %))
    (map :name)
    (set)))

(def all-heroes-data
  (:body (client/get "http://heroesjson.com/heroes.json" {:accept :json, :as :json})))

(defn- get-hero-property [hero-name property-name]
  (property-name (some #(if (= hero-name (:name %)) %) all-heroes-data)))

(defn- hero-have-these-role-and-type [hero role type]
  (and (or (nil? role) (= role (get-hero-property hero :role)))
       (or (nil? type) (= type (get-hero-property hero :type)))))

(defn- get-heroes-for [owner & {:keys [free-pick role type without]}]
  (if-not (nil? owner)
    (if (some some? [role type])
      (filter #(hero-have-these-role-and-type % role type)
              (get-heroes-for owner :free-pick free-pick :without without))
      (difference (if db/all-available?
                    (set (map :name all-heroes-data))
                    (union (owner db/heroes-map) (if free-pick free-rotation-heroes)))
                  (union db/banned-heroes without)))))

(defn- random-hero [owner free-pick & {:keys [role type]}]
  (let [owner-allowed-heroes (vec (get-heroes-for owner :free-pick free-pick :role role :type type))]
    (when (not-empty owner-allowed-heroes)
      (rand-nth owner-allowed-heroes))))

(defn- random-team [owners free-pick & {:keys [set-up]}]
  (let [heroes (if (some? set-up)
                 (map (fn [o s] (random-hero o free-pick :role (:role s) :type (:type s)))
                      owners set-up)
                 (map (fn [o] (random-hero o free-pick))
                      owners))]
    (if (every? some? heroes)
      (if (apply distinct? heroes)
        (zipmap owners heroes)
        (random-team owners free-pick :set-up set-up))
      (random-team (shuffle owners) free-pick :set-up set-up))))

(defn- valid-members-count? [members]
  (and (<= 2 (count members) 10) (even? (count members))))

(defn- get-team-compositions-with-size [team-size]
  (filter #(= (count %) team-size) db/set-ups))

(defn plain-random-teams
  "Plain random teams"
  [members & {:keys [free-pick]}]
  (if (valid-members-count? members)
    (let [members (vec members)
          team-size (/ (count members) 2)]
      [(random-team (subvec members 0 team-size) free-pick)
       (random-team (subvec members team-size) free-pick)])
    (throw (IllegalArgumentException. "There should be 2, 4, 6, 8 or 10 members."))))

(defn mirror-random-teams
  "Random teams with mirror set-ups."
  [members & {:keys [free-pick]}]
  (if (valid-members-count? members)
    (let [members (shuffle (vec members))
          team-size (/ (count members) 2)
          set-up (rand-nth (filter (fn [e] (= team-size (count e))) db/set-ups))]
      [(random-team (subvec members 0 team-size) free-pick :set-up set-up)
       (random-team (subvec members team-size) free-pick :set-up set-up)])
    (throw (IllegalArgumentException. "There should be 2, 4, 6, 8 or 10 members."))))

(defn- all-elements-unique? [seq]
  (= (count seq) (count (set seq))))

(defn- find-all-valid-hero-combinations-for-specified-team
  [members-order hero-role-order & [free-pick]]
  {:pre [(= (count members-order) (count hero-role-order)) (all-elements-unique? members-order)]}
  (let [owners-heroes-lists
        (mapv #(get-heroes-for
                %1
                :free-pick free-pick
                :role (:role %2)
                :type (:type %2))
              members-order
              hero-role-order)]
    (filter all-elements-unique?
            (apply comb/cartesian-product owners-heroes-lists))))

(defn- random-heroes-for-team-with-members-combinations
  [team-members team-combination & [free-pick]]
  (if-not (empty? team-members)
    (let [rand-team-members-order (rand-nth team-members)
          rand-teams (find-all-valid-hero-combinations-for-specified-team
                       rand-team-members-order
                       team-combination)]
      (if (empty? rand-teams)
        (random-heroes-for-team-with-members-combinations
          (remove #(= % rand-team-members-order) team-members)
          team-combination
          free-pick)
        (zipmap rand-team-members-order (rand-nth rand-teams))))))

(defn- random-heroes-for-team [team-members team-composition & [free-pick]]
  (random-heroes-for-team-with-members-combinations
    (comb/permutations team-members)
    team-composition
    free-pick))

(defn mirror-set-up-random-teams
  "Random teams with mirror set-ups."
  [members & {:keys [free-pick]}]
  (if (valid-members-count? members)
    (let [members (shuffle (vec members))
          team-size (/ (count members) 2)
          team-red (subvec members 0 team-size)
          team-blue (subvec members team-size)
          set-up (rand-nth (get-team-compositions-with-size team-size))]
      (println "Roles setup: " set-up)
      [(random-heroes-for-team team-red set-up free-pick)
       (random-heroes-for-team team-blue set-up free-pick)])
    (throw (IllegalArgumentException. "There should be 2, 4, 6, 8 or 10 members."))))

(defn -main [& args]
  (let [teams (mirror-set-up-random-teams (difference db/gamers #{:Stepanov :Bratus}) :free-pick true)]
    (if (or (nil? (first teams)) (nil? (second teams)))
      (do (println "Unable to find a team for random setup. Trying again.")
          (-main))
      (do (println "Heroes:")
          (println (vals (first teams)))
          (println "      versus      ")
          (println (vals (second teams)))
          (print "Ok? > ")
          (flush)

          (if (not-empty (read-line))
            (do (println "Teams revealed:")
                (println (first teams))
                (println "      versus      ")
                (println (second teams)))
            (do (println)
                (-main)))))))
