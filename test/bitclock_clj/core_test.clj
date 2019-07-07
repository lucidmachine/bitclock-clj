(ns bitclock-clj.core-test
  (:require [clojure.test :refer :all]
            [bitclock-clj.core :refer :all]
            [java-time :as jt]))

(def testtime (jt/local-time "12:34:56"))
(def testbittime [[0 0 0 1]
                  [0 0 1 0]
                  [0 0 1 1]
                  [0 1 0 0]
                  [0 1 0 1]
                  [0 1 1 0]])

(deftest parse-int-test
  (testing "Parses the given numeric string into an Integer"
    (is (= 1 (parse-int "1")))))

(deftest time-str-test
  (testing "Gets the time as a string in the format 'HHmmss'"
    (is (= "123456" (time-str testtime))))
  (testing "Uses 24-hour time format"
    (is (= "235959" (time-str (jt/local-time "23:59:59"))))))

(deftest time-digits-test
  (testing "Gets the time as a seq of digits."
    (is (= '(1 2 3 4 5 6) (time-digits testtime)))))

(deftest bit--test
  (testing "Get a bit representing whether the place value is active in the given digit."
    (testing "when active"
      (is (= 1 (bit 8 9))))
    (testing "when inactive"
      (is (= 0 (bit 4 3))))))

(deftest bit-digit-test
  (testing "Get a vector of Bits representing the given digit"
    (is (= [0 0 0 0] (bit-digit 0)))
    (is (= [0 0 0 1] (bit-digit 1)))
    (is (= [0 0 1 0] (bit-digit 2)))
    (is (= [0 0 1 1] (bit-digit 3)))
    (is (= [0 1 0 0] (bit-digit 4)))
    (is (= [0 1 0 1] (bit-digit 5)))
    (is (= [0 1 1 0] (bit-digit 6)))
    (is (= [0 1 1 1] (bit-digit 7)))
    (is (= [1 0 0 0] (bit-digit 8)))
    (is (= [1 0 0 1] (bit-digit 9)))))

(deftest bit-time-test
  (testing "Get a vector of BitDigits representing the time"
    (testing "given a specific time"
      (is (= testbittime
             (bit-time testtime))))))

(deftest transpose-test
  (testing "Transpose a matrix"
    (is (= [[1 3]
            [2 4]]
           (transpose [[1 2]
                       [3 4]])))))

(deftest fmt-bit-time-test
  (testing "Format a BitTime"
    (is (= "0 0 0 0 0 0\n0 0 0 1 1 1\n0 1 1 0 0 1\n1 0 1 0 1 0"
           (fmt-bit-time testbittime)))))
