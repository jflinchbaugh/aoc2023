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

(defn- expand-seed [[start length]]
  [start (+ start length -1)])

(defn- expand-seeds [seeds]
  (->>
    seeds
    (partition 2)
    (map expand-seed)))

(defn- expand-mapping [[dest src length]]
  [[src (+ src length -1)] [dest (+ dest length -1)]])

(defn- intersects? [[s1 e1] [s2 e2]]
  (or
    (<= s1 s2 e1)
    (<= s2 s1 e2)))

(defn part-2 []
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
        seeds (expand-seeds (ffirst input))
        mappings (->>
                   input
                   rest
                   (map (fn [m]
                          (->>
                            m
                            (map expand-mapping)
                            (sort-by (fn [[[ss se] [ds de]]] ds)))))
                   reverse)]
    (->> mappings
      first
      first
      first
      )
    ))

(comment
  (part-1)
  ;; => 289863851
  ;; => 289863851


  (part-2)


  nil)
