(ns aoc2023.day-05-test
  (:require [aoc2023.day-05 :refer :all]
            [clojure.test :as t]))

(t/deftest test-make-mapping-fn
  (t/are
   [expected dest src length seed]
   (= expected ((make-mapping-fn [dest src length]) seed))
    0   0 0 0   0
    1   0 0 0   1
    1   1 0 0   1
    1   1 0 1   0
    1   1 0 1   1
    10  2 3 2  10
    1   2 3 2   1
    3   2 3 2   4))
