(ns ^{:author "Vladislav Bauer"}
  lein-plantuml.t-core
  (:import (net.sourceforge.plantuml FileFormat))
  (:use [midje.sweet :only [fact]]
        [midje.util :only [testable-privates]]
        [clojure.java.io :only [as-file]])
  (:require [lein-plantuml.core]
            [me.raynes.fs :as fs]))


; Configurations

(def ^:private DEF_OUTPUT "test-out")

(testable-privates
  lein-plantuml.core
    file-format
    read-configs
    process-file
    FILE_FORMAT)


; Helper functions

(defn- clear-output[]
  (fs/delete-dir DEF_OUTPUT))

(defn- check-formats [& names]
  (let [formats (distinct (map file-format names))
        eq (= 1 (count formats))]
    (if eq
      (first formats)
      (throw (Exception. "Incorrect file-format function")))))

(defn- check-process
  ([u] (let [file (str "example/doc/" u)
             formats (vals (var-get FILE_FORMAT))]
         (every? #(check-process file %) formats)))
  ([u f] (try
           (do
             (clear-output)
             (process-file u DEF_OUTPUT f))
           (finally (clear-output)))))


; Tests

(fact "Check available file formats"
  (check-formats nil :none)             => nil
  (check-formats :eps :EPS "eps" "EPS") => FileFormat/EPS
  (check-formats :eps:txt "eps:txt")    => FileFormat/EPS_TEXT
  (check-formats :png "png")            => FileFormat/PNG
  (check-formats :txt "txt")            => FileFormat/ATXT
  (check-formats :utxt "utxt")          => FileFormat/UTXT
  ; TODO: The following formats are very unstable:
  ;(check-formats :svg) => FileFormat/SVG
  ;(check-formats :pdf) => FileFormat/PDF
  ;(check-formats :html) => FileFormat/HTML
  ;(check-formats :html5) => FileFormat/HTML5
)

(fact "Check source list"
  (empty? (read-configs nil)) => true
  (empty? (read-configs {})) => true
  (empty? (read-configs {:plantuml []})) => true
  (empty? (read-configs {:plantuml ["test.uml"]})) => false
  (empty? (read-configs {} "test.uml")) => false
  (empty? (read-configs {} ["test.uml"])) => false
  (empty? (read-configs {:plantuml ["test.uml"]} ["test.uml"])) => false)

(fact "Check PlantUML processor"
  (check-process "example-01.puml") => true
  (check-process "example-02.puml") => true
  (check-process "example-03.puml") => true
  (check-process "example-04.puml") => true)
