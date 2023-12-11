(ns aoc2023.day-05
  (:require [aoc2023.core :refer :all]
            [clojure.string :as str]))

(defn- split-line [s]
  (map parse-long (str/split s #" ")))

(defn make-mapping-fn [[dest src length]]
  (let [end (+ src length -1)]
    (fn [x]
      (if (<= src x end)
        (+ dest (- x src))
        x))))

(defn apply-mappings [seed stage]
  (->> stage
    (map (fn [f] (f seed)))
    (remove #{seed})
    (cons seed)
    last))

(defn part-1 []
  (let [input
        (->>
          "src/aoc2023/day_05.txt"
          file->lines
          (map (fn [s] (str/replace s #"seeds: " "")))
          (remove (partial re-matches #".*map:"))
          (map split-line)
          (partition-by #{[nil]})
          (remove #{[[nil]]})
          )
        seeds (ffirst input)
        mappings (->>
                   input
                   rest
                   (map (fn [d] (map make-mapping-fn d))))]
    (->> seeds
      (map
        (fn [seed]
          (reduce apply-mappings seed mappings)))
      (apply min))
    ))

(comment
  (part-1)
  ;; => 289863851

  nil)
