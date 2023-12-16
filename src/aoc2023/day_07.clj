(ns aoc2023.day-07
  (:require [aoc2023.core :refer :all]
            [clojure.string :as str]))

(defn- card-value-1 [c]
  (get {\T 10 \J 11 \Q 12 \K 13 \A 14} c (parse-long (str c))))

(defn- parse-line [card-value s]
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

(defn- compare-hands-1 [[[g1 g2 g3 g4 g5] [c1 c2 c3 c4 c5] bet]]
  [(count g1) (count g2) (count g3) (count g4) (count g5)
   c1 c2 c3 c4 c5])

(defn part-1 []
  (->>
    "src/aoc2023/day_07.txt"
    file->lines
    (mapv (partial parse-line card-value-1))
    (sort-by compare-hands-1)
    (map (fn [index [_ _ bet]] [(inc index) bet]) (range))
    (reduce (fn [a [i b]] (+ a (* i b))) 0)))

(defn- card-value-2 [c]
  (get {\T 10 \J 0 \Q 12 \K 13 \A 14} c (parse-long (str c))))

(defn- compare-hands-2 [[groups [c1 c2 c3 c4 c5] bet]]
  (let [g0 (first (filter (comp #{(card-value-2 \J)} first) groups))
        joker-count (count g0)
        [g1 g2 g3 g4 g5] (remove (comp #{(card-value-2 \J)} first) groups)]
    [(+ joker-count (count g1)) (count g2) (count g3) (count g4) (count g5)
     c1 c2 c3 c4 c5]))

(defn part-2 []
  (->>
    "src/aoc2023/day_07.txt"
    file->lines
    (mapv (partial parse-line card-value-2))
    (sort-by compare-hands-2)
    (map (fn [index [_ _ bet]] [(inc index) bet]) (range))
    (reduce (fn [a [i b]] (+ a (* i b))) 0)))

(comment

  (part-1)
  ;; => 251287184

  (part-2)
  ;; => 250757288

  nil)
