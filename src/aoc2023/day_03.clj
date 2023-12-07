(ns aoc2023.day-03
  (:require [aoc2023.core :refer :all]
            [clojure.string :as str]
            [clojure.set :as set]))

(defn- map-symbol? [c]
  (not (nil? (re-matches #"[^0-9\.]" (str c)))))

(defn- map-number? [c]
  (not (nil? (re-matches #"[0-9]" (str c)))))

(defn- find-symbols [m]
  (filter identity
          (let [h (count m)
                w (count (first m))]
            (for [y (range 0 h)
                  x (range 0 w)]
              (let [line (nth m y)
                    ch (nth line x)]
                (when (map-symbol? ch) [ch x y]))))))

(defn- find-numbers [m]
  (filter identity
          (let [h (count m)
                w (count (first m))]
            (for [y (range 0 h)
                  x (range 0 w)]
              (let [line (nth m y)
                    ch (nth line x)]
                (when (map-number? ch) [ch x y]))))))

(defn- adjacent-positions [[x y]]
  (for [dy [-1 0 1]
        dx [-1 0 1]]
    [(+ x dx) (+ y dy)]))

(defn- mark-gaps [cells]
  (reduce
   (fn
     [acc [d x y]]
     (let [[pd px py] (peek acc)]
       (cond
         (nil? px) [[d x y]]
         (= [(inc px) py] [x y]) (conj acc [d x y])
         :else (conj acc nil [d x y]))))
   []
   cells))

(defn- group-numbers [cells]
  (->>
   cells
   mark-gaps
   (partition-by nil?)
   (remove #{[nil]})))

(defn- expand-adjacent [locs]
  (remove (set locs) (set (mapcat adjacent-positions locs))))

(defn only-location [[_ x y]]
  [x y])

(defn- adjacent-to-symbol? [symbols cells]
  (not
   (empty?
    (set/intersection
     (->> cells
          (map (comp only-location))
          expand-adjacent
          set)
     (set (map only-location symbols))))))

(defn- cells->number [cells]
  (parse-long (str/join (map (comp str first) cells))))

(defn- size? [expected actual]
  (= expected (count actual)))

(defn part-1 [lines]
  (let [symbols (find-symbols lines)
        number-cells (find-numbers lines)
        number-groups (group-numbers number-cells)]
    (->>
     number-groups
     (filter (partial adjacent-to-symbol? symbols))
     (map cells->number)
     (reduce +))))

(defn part-2 [lines]
  (let [symbols (->>
                 lines
                 find-symbols
                 (filter (comp (partial = \*) first)))
        number-cells (find-numbers lines)
        number-groups (group-numbers number-cells)]
    symbols
    (reduce +
      (map (partial reduce *)
        (filter (partial size? 2)
          (for [s symbols]
            (->>
              number-groups
              (filter (partial adjacent-to-symbol? [s]))
              (map cells->number))))))))

(comment
  (->>
   "src/aoc2023/day_03.txt"
   file->lines
   part-1)
  ;; => 556057

  (->>
   "src/aoc2023/day_03.txt"
   file->lines
   part-2)
  ;; => 82824352

  nil)
