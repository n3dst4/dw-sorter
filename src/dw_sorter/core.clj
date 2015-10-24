(ns dw-sorter.core
  (:gen-class)
  (:require [clojure.java.io :as io]))

(def dw-path "C:\\Users\\ndc\\Dropbox\\dr who\\")

(def dw-folder (io/file dw-path))

(def files (filter #(not (.isDirectory %)) (rest (file-seq dw-folder))))

(defn episode-number [file]
  (def match (re-matches 
              #"\d{4}-\d{2}-\d{2} - (S\d\d E\d\d) - .*" 
              (.getName file)))
  (if (= match nil) 
    nil
    (match 1)))

(def episodes (partition-by episode-number files))

(defn process-episode [s]
  (def this-episode-number (episode-number (nth s 0)))
  (def folder-name (str dw-path this-episode-number "\\"))
  (io/make-parents (str folder-name "x"))
  (doseq [f s]
    (def new-name (str folder-name (.getName f)))
    (println "Moving " (.getAbsolutePath f) " to " new-name)
    (.renameTo (.getAbsoluteFile f) (io/file new-name))))

;(def episodes (take 3 episodes))

(doseq [e episodes]
  (process-episode e))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
