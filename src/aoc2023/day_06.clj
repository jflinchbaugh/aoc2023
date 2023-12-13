(ns aoc2023.day-06
  (:require [aoc2023.core :refer :all]
            [clojure.string :as str]))

(def input (str/trim "
Time:        61     70     90     66
Distance:   643   1184   1362   1041
"))

(defn distance [t a]
  (* a (- t a)))

(def races [[61 643]
            [70 1184]
            [90 1362]
            [66 1041]])

(defn part-1 []
  (->>
   races
   (map
    (fn [[t r]]
      (->>
       t
       inc
       range
       (map (partial distance t))
       (filter (partial < r))
       count)))
   (reduce * 1)))

(defn part-2 []
  (->>
   (let [t 61709066
         r 643118413621041])
   (->>
    t
    inc
    range
    (map (partial distance t))
    (filter (partial < r))
    count)))

(comment

  (part-1)
  ;; => 293046

  (part-2)
  ;; => 35150181

;
  )
