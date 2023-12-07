(ns aoc2023.day-04
  (:require [aoc2023.core :refer :all]
            [clojure.string :as str]
            [clojure.set :as set]))

(defn- parse-line [s]
  (->>
    (-> s
      (str/replace #"Card *" "")
      (str/split #"[:|]"))
    (map str/trim)
    (map (fn [nums] (str/split nums #" +")))
    (map (fn [card]
           (if (= 1 (count card))
             (parse-long (first card))
             (map parse-long card))))
    ))

(defn- score [[card-num i-have winning]]
  [card-num (long (Math/pow 2 (dec (count (set/intersection (set i-have) (set winning))))))])

(defn part-1 []
  (->> "src/aoc2023/day_04.txt"
    file->lines
    (map parse-line)
    (map score)
    (map second)
    (reduce +)
    ))

(comment

  (part-1)
  ;; => 21088


  nil)
