(ns aoc2023.day-09
  (:require [aoc2023.core :refer :all]
            [clojure.string :as str]))

(defn- parse [s]
  (map parse-long (str/split s #" ")))

(defn- solve [lst]
  (->>
    lst
    (partition 2 1)
    (map (fn [[x y]] (- y x)))))

(defn- find-next-value [lst]
  (->> lst
    (iterate solve)
    (take-while (complement (partial every? zero?)))
    (map last)
    (reduce +)))

(defn part-1 []
  (->>
    "src/aoc2023/day_09.txt"
    file->lines
    (map parse)
    (map find-next-value)
    (reduce +)))


(comment
  (part-1)
  ;; => 1882395907

  nil)
