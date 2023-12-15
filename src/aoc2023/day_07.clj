(ns aoc2023.day-07
  (:require [aoc2023.core :refer :all]
            [clojure.string :as str]))

(defn- card-value [c]
  (get {\T 10 \J 11 \Q 12 \K 13 \A 14} c (parse-long (str c))))

(defn- parse-line [s]
  (let [[_cards bet] (str/split s #" ")
        cards (->>
               _cards
               (mapv card-value))
        card-groups (->>
                     cards
                     sort
                     (partition-by identity)
                     (sort-by (juxt count first))
                     reverse
                     (mapv vec))]
    [card-groups cards (parse-long bet)]))

(defn- compare-hands [[[g1 g2 g3 g4 g5] [c1 c2 c3 c4 c5] bet]]
  [(count g1) (count g2) (count g3) (count g4) (count g5)
   c1 c2 c3 c4 c5])

(defn part-1 []
  (->>
   "src/aoc2023/day_07.txt"
   file->lines
   (mapv parse-line)
   (sort-by compare-hands)
   (map (fn [index [_ _ bet]] [(inc index) bet]) (range))
   (reduce (fn [a [i b]] (+ a (* i b))) 0)))

(comment

  (part-1)
  ;; => 251287184

  nil)
