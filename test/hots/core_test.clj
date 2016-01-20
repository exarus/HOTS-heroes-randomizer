(ns hots.core-test
  (:require [clojure.test :refer :all]
            [hots.core :refer :all]))

(deftest a-test
  (testing "Heroes DB"
    (is (validate-heroes-db))))
