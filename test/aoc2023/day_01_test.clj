(ns aoc2023.day-01-test
  (:require [aoc2023.day-01 :refer :all]
            [clojure.test :as t]))

(def input-1 "
1abc2
pqr3stu8vwx
a1b2c3d4e5f
treb7uchet
")

(t/deftest test-part-1
  (t/is (= 142 (part-1 input-1))))

(def input-2 "
two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen
")

(t/deftest test-part-2
  (t/is (= 281 (part-2 input-2))))
