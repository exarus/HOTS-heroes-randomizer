(ns hots.core
  (:require [hots.db :as db]
            [json-path :as json-path]
            [clojure.set :refer :all]
            [clojure.data.json :as json]
            [clojure.java.io :as io]))

(defn validate-heroes-db []
  (let [heroes-txt (slurp (-> "heroes.json" io/resource io/file))
        heroes-json (json/read-str heroes-txt :key-fn keyword)
        names (set (json-path/at-path "$[*].name" heroes-json))]
    (every? (fn [heroes]
              (every? (fn [hero] (contains? names hero))
                      heroes))
            (vals db/heroes-map))))
          
(defn valid-members-count? [members]
  (and (>= (count members) 2) (<= (count members) 10) (even? (count members))))

(defn get-heroes-for [owner free-pick]
  (vec (union (owner db/heroes-map)
              (if free-pick db/week-heroes))))

(defn random-hero [owner free-pick & {:keys [role type]}]
  (let [heroes-txt (slurp (-> "heroes.json" io/resource io/file))
        heroes-json (json/read-str heroes-txt :key-fn keyword)
        allowed-heroes (set (map :name
                                 (filter (fn [e] (and (or (nil? role) (= role (:role e)))
                                                      (or (nil? type) (= type (:type e)))))
                                         heroes-json)))]
    (rand-nth (filter (fn [h] (contains? allowed-heroes h))
                      (get-heroes-for owner free-pick)))))


(defn random-team [owners free-pick & {:keys [set-up]}]
  (let [heroes (if (nil? set-up)
                 (map (fn [o] (rand-nth (get-heroes-for o free-pick))) owners)
                 (map (fn [o s] (random-hero o free-pick (:role s) (:type s))) owners set-up))]
    (if (apply distinct? heroes)
      (zipmap owners heroes)
      (random-team owners free-pick :set-up set-up))))

(defn plain-random-teams [members & {:keys [free-pick]}]
  "Plain random teams"
  (if (valid-members-count? members)
    [(random-team (subvec members 0 (/ (count members) 2)) free-pick)
     (random-team (subvec members (/ (count members) 2)) free-pick)]))

(defn mirror-random-teams [members & {:keys [free-pick]}]
  "Random teams with mirror set-ups."
  (if (valid-members-count? members)
    (let [members (shuffle members)
          team-size (/ (count members) 2)
          set-up (rand-nth (filter (fn [e] (= team-size (count e))) db/set-ups))]
      [(random-team (subvec members 0 team-size) free-pick :set-up set-up)
       (random-team (subvec members team-size) free-pick :set-up set-up)])))

(defn -main
  [& args]
  (let [teams (plain-random-teams [:Anton :Konstantine :Ruslan :Vlad :Shiron :Bratus] :free-pick true)]
    (println (str "Team 1: " (first teams)))
    (println (str "Team 2: " (second teams)))))
