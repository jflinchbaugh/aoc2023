(ns aoc2023.day-01-test
  (:require [aoc2023.day-01 :refer :all]
            [clojure.test :as t]))

(def input "
1abc2
pqr3stu8vwx
a1b2c3d4e5f
treb7uchet
")

(t/deftest test-part-1
  (t/is (= 142 (part-1 input))))
