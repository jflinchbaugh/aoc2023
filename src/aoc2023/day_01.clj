(ns aoc2023.day-01
  (:require [aoc2023.core :refer :all]
            [clojure.string :as str]))


(defn part-1 [input]
  (->>
    input
    str->lines
    (filter (complement str/blank?))
    (map #(str/replace % #"[a-zA-Z]*" ""))
    (map (juxt first last))
    (map str/join)
    (map parse-long)
    (reduce + 0))
  )


(comment

  (->> "src/aoc2023/day_01.txt"
    slurp
    part-1)
  ;; => 53651

  )

(defn- tr-number
  "replace a number word at the beginning of a string with its digit"
  [s]
  (-> s
    (str/replace #"^one" "1")
    (str/replace #"^two" "2")
    (str/replace #"^three" "3")
    (str/replace #"^four" "4")
    (str/replace #"^five" "5")
    (str/replace #"^six" "6")
    (str/replace #"^seven" "7")
    (str/replace #"^eight" "8")
    (str/replace #"^nine" "9")))

(defn- digit? [s]
  (re-matches #"^[0123456789]" s))

(defn- tr-digits
  "replace first char of each 'number word'
  with the digit across the entire string"
  [s]
  (->> s
    (iterate rest) ; 'abc' => ('abc' 'bc' 'c')
    (take-while (complement empty?))
    (map (comp first tr-number str/join))
    (str/join)
    ))

(defn part-2 [input]
  (->>
    input
    seq
    tr-digits
    part-1
    ))

(comment

  (->>
    "src/aoc2023/day_01.txt"
    slurp
    part-2)
  ;; => 53894

  )
