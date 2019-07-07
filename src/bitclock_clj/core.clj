(ns bitclock-clj.core
  (:gen-class)
  (:require [clojure.string :as s]
            [java-time :as jt]))

;; Backend
(defn parse-int
  "Parse a numeric string into an integer."
  [number-string]
  (try (Integer/parseInt number-string)
       (catch Exception e nil)))

(defn time-str
  "Get the time as a string in the format 'hhmmss'."
  [time]
  (jt/format "HHmmss" time))

(defn time-digits
  "Get the time as a seq of digits."
  [time]
  (let [digit-strs (s/split (time-str time) #"")]
    (map parse-int digit-strs)))

(defn bit
  "Get a bit representing whether the given place value is active in the given digit."
  [place-value digit]
  (if (> (bit-and place-value digit) 0)
    1
    0))

(defn bit-digit
  "Get a vector of Bits representing the given digit."
  [digit]
  [(bit 8 digit)
   (bit 4 digit)
   (bit 2 digit)
   (bit 1 digit)])

(defn bit-time
  "Get a vector of BitDigits representing the time."
  ([]
   (bit-time (jt/local-time)))
  ([time]
   (vec (map bit-digit (time-digits time)))))


;; Frontend


(defn transpose
  "Transpose a matrix"
  [matrix]
  (apply mapv vector matrix))

(defn fmt-bit-time
  "Format a BitTime for output"
  [bt]
  (s/join "\n"
          (map #(s/join " " %)
               (transpose bt))))

(defn -main
  "A command-line BitClock implementation in Clojure."
  [& args]
  (println (fmt-bit-time (bit-time))))
