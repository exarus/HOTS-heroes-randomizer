(ns hots.core
  (:require [hots.db :as db]
            [json-path :as json-path]
            [clojure.set :refer :all]
            [clojure.data.json :as json]
            [clojure.java.io :as io]))

(defn validate-heroes-db
  []
  (let [heroes-txt (-> "heroes.json" io/resource io/file slurp)
        heroes-json (json/read-str heroes-txt :key-fn keyword)
        names (set (json-path/at-path "$[*].name" heroes-json))]
    (every? (fn [heroes]
              (every? (fn [hero] (contains? names hero))
                      heroes))
            (vals db/heroes-map))))

(defn- get-heroes-for
  [owner free-pick]
  (vec (union (owner db/heroes-map)
              (if free-pick db/week-heroes))))

(defn- random-hero
  [owner free-pick & {:keys [role type]}]
  (let [owner-allowed-heroes
        (if (or (some? role) (some? type))
          (let [all-heroes-json (-> "heroes.json" io/resource io/file slurp)
                all-heroes (json/read-str all-heroes-json :key-fn keyword)
                by-role-and-type (fn [e] (and (or (nil? role) (= role (:role e)))
                                              (or (nil? type) (= type (:type e)))))
                allowed-heroes (set (map :name
                                         (filter by-role-and-type all-heroes)))]
            (filter (fn [h] (contains? allowed-heroes h))
                    (get-heroes-for owner free-pick)))
          (get-heroes-for owner free-pick))]
    (if (not-empty owner-allowed-heroes)
      (rand-nth owner-allowed-heroes)
      nil)))

(defn- random-team
  [owners free-pick & {:keys [set-up]}]
  (let [heroes (map (fn [o s] (random-hero o free-pick :role (:role s) :type (:type s)))
                    owners set-up)]
    (if (every? some? heroes)
      (if (apply distinct? heroes)
        (zipmap owners heroes)
        (random-team owners free-pick :set-up set-up))
      (random-team (shuffle owners) free-pick :set-up set-up))))

(defn- valid-members-count?
  [members]
  (and (<= 2 (count members) 10) (even? (count members))))

(defn- get-team-compositions-with-size
  [team-size]
  (filter #(= (count %) team-size) db/set-ups))

(defn plain-random-teams
  "Plain random teams"
  [members & {:keys [free-pick]}]
  (if (valid-members-count? members)
    (let [team-size (/ (count members) 2)]
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

(defn mirror-set-up-random-teams
  "Random teams with mirror set-ups."
  [members & {:keys [free-pick]}]
  (if (valid-members-count? members)
    (let [members (shuffle (vec members))
          team-size (/ (count members) 2)
		  team-red (subvec members 0 team-size)
		  team-blue (subvec members team-size)
          set-up (rand-composition-for-teams team-red team-blue free-pick)]
      [(random-team team-red  free-pick :set-up set-up)
       (random-team team-blue free-pick :set-up set-up)])
    (throw (IllegalArgumentException. "There should be 2, 4, 6, 8 or 10 members."))))

(defn -main
  [& args]
  (let [teams (mirror-random-teams (difference db/gamers #{}) :free-pick true)]
    (println (str "Team 1: " (first teams)))
    (println (str "Team 2: " (second teams)))))
