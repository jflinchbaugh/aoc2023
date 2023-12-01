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
