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
            (map parse-long card))))))

(defn- score [[card-num i-have winning]]
  [card-num
   (long
    (Math/pow
     2
     (dec
      (count
       (set/intersection
        (set i-have)
        (set winning))))))])

(defn part-1 []
  (->> "src/aoc2023/day_04.txt"
       file->lines
       (map parse-line)
       (map score)
       (map second)
       (reduce +)))

(defn- add-initial-count [[n m w]]
  [n 1 m w])

(defn- score-2 [[card-num i-have winning]]
  [card-num
   (count
    (set/intersection
     (set i-have)
     (set winning)))])

(defn- multiplier [multipliers card]
  (get multipliers card 1))

(defn- inc-multiplier [c m]
  (+ c (or m 1)))

(defn- increase-multipliers [multipliers [card winnings]]
  (reduce
   (fn [m c]
     (update
      m
      c
      (partial
       inc-multiplier
       (multiplier multipliers card))))
   multipliers
   (map
    (partial + 1 card)
    (range winnings))))

(defn part-2 []
  (let [scores (->> "src/aoc2023/day_04.txt"
                 file->lines
                 (map parse-line)
                 (map score-2))
        size (count scores)
        multipliers (->>
          scores
          (reduce increase-multipliers {}))]
    (reduce
      (fn [a c] (+ a (multiplier multipliers c)))
      0
      (map inc (range size)))))

(comment

  (inc-multiplier 3)

  (update {} 2 inc-multiplier)

  (part-1)
  ;; => 21088

  (part-2)
  ;; => 6874754

  nil)
