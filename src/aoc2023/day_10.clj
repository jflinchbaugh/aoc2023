(ns aoc2023.day-10
  (:require [aoc2023.core :refer :all]))

(defn- parse-connector [row col c]
  [[row col]
   (case c
     \| [[(dec row) col] [(inc row) col] c]
     \- [[row (dec col)] [row (inc col)] c]
     \F [[row (inc col)] [(inc row) col] c]
     \7 [[row (dec col)] [(inc row) col] c]
     \L [[(dec row) col] [row (inc col)] c]
     \J [[(dec row) col] [row (dec col)] c]
     [])])

(defn part-1 []
  (let [start [58 51]
        tunnels (->>
                  "src/aoc2023/day_10.txt"
                  file->lines
                  (mapcat
                    (fn [row cols]
                      (map (partial parse-connector row) (range) cols))
                    (range))
                  (into {}))
        first-step (->>
                     tunnels
                     (filter
                       (fn [[_ steps]]
                         ((set steps) start)))
                     first
                     first)
        path (->>
          [first-step start]
          (iterate
            (fn [[current prev]]
              [(->>
                 current
                 tunnels
                 (filter (partial not= prev))
                 first) current]))
          (map first)
          (take-while (complement nil?)))]
    (->
      path
      count
      (/ 2)
      )))

(comment

  (part-1)
  ;; => 6942


  nil)
