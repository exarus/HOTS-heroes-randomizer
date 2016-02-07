(ns hots.core-test
  (:require [hots.db :as db]
            [clojure.set :refer :all]
            [clojure.test :refer :all]
            [hots.core :refer :all]))

(defn validate-heroes [heroes]
  (let [all-heroes-names (set (map :name all-heroes-data))]
        (every? #(contains? all-heroes-names %) heroes)))

(deftest heroes-db-test
  (testing "If all hero names are valid."
    (is (validate-heroes (reduce union (vals db/heroes-map))))))

(deftest free-rotation-heroes-test
  (testing "If all free rotation heroes are valid."
    (is (validate-heroes free-rotation-heroes))))
