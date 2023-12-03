(ns aoc2023.day-02
  (:require [aoc2023.core :refer :all]
            [clojure.string :as str]))

(defn- parse-balls [s]
  (let [spl (str/split s #" ")]
    [(-> spl second keyword) (-> spl first parse-long)]))

(defn- split-counts [s]
  (->>
   (str/split s #", ")
   (map parse-balls)
   (into {})))

(defn- split-games [s]
  (->>
   (str/split s #"; ")
   (map split-counts)))

(defn- parse-line [line]
  (let [spl (str/split line #": ")]
    [(-> spl first (str/split #" ") second parse-long)
     (mapcat split-games (rest spl))]))

(defn- total-balls [[game-num balls]]
  [game-num (apply merge-with + balls)])

(defn- too-many? [red green blue [_ hands]]
  (->>
   hands
   (some
    (fn [balls]
      (or
       (< red (or (:red balls) 0))
       (< green (or (:green balls) 0))
       (< blue (or (:blue balls) 0)))))))

(defn part-1 [lines]
  (->> lines
       (map parse-line)
       (remove (partial too-many? 12 13 14))
       (map first)
       (reduce +)))

(comment

  (->>
   "src/aoc2023/day_02.txt"
   file->lines
   part-1)
  ;; => 2105

  nil)
