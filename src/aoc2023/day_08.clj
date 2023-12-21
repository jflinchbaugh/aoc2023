(ns aoc2023.day-08
  (:require [aoc2023.core :refer :all]
            [clojure.string :as str]))

(defn- parse-node [s]
  (let [[root left right] (str/split s #"[ =(,)]+")]
    [root [left right]]))

(defn- walk-to-end [visited nodes [next-step & r-path]]
  (let [current (first visited)]
    (if
     (#{"ZZZ"} current)
      visited
      (recur
       (cons (get-in nodes [current next-step]) visited)
       nodes
       r-path))))

(defn part-1 []
  (let [[path-line _ & node-lines] (->>
                                    "src/aoc2023/day_08.txt"
                                    file->lines)
        path (cycle (map {\L 0 \R 1} path-line))
        nodes (into {} (map parse-node node-lines))]
    (dec (count (walk-to-end ["AAA"] nodes path)))))

(defn end-node? [node-name]
  (str/ends-with? node-name "Z"))

(defn- walk-2x [visited nodes [next-step & r-path]]
  (let [current (first visited)]
    (if
     (= 2 (count (filter end-node? visited)))
      visited
      (recur
       (cons (get-in nodes [current next-step]) visited)
       nodes
       r-path))))

(comment

  (part-1)
  ;; => 14893

  nil)
