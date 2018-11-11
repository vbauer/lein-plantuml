(ns ^{:author "Vladislav Bauer"}
  lein-plantuml.t-core
  (:import (net.sourceforge.plantuml FileFormat))
  (:use [clojure.java.io :only [as-file]]
        [clojure.test :only [deftest is]])
  (:require [me.raynes.fs :as fs]
            [lein-plantuml.core :as core]))


; Configurations

(def ^:private DEF_OUTPUT "test-out")


; Helper functions

(defn- clear-output[]
  (fs/delete-dir DEF_OUTPUT))

(defn- check-formats [& names]
  (let [formats (distinct (map core/file-format names))
        eq (= 1 (count formats))]
    (if eq
      (first formats)
      (throw (Exception. "Incorrect file-format function")))))

(defn- check-process
  ([u] (let [file (str "example/doc/" u)
             formats (vals core/FILE_FORMAT)]
         (every? #(check-process file %) formats)))
  ([u f] (try
           (do
             (clear-output)
             (core/process-file u DEF_OUTPUT f))
           (finally (clear-output)))))


; Tests

(deftest check-available-file-formats
  (is (= (check-formats nil :none) nil))
  (is (= (check-formats :eps :EPS "eps" "EPS") FileFormat/EPS))
  (is (= (check-formats :eps:txt "eps:txt") FileFormat/EPS_TEXT))
  (is (= (check-formats :png "png") FileFormat/PNG))
  (is (= (check-formats :txt "txt") FileFormat/ATXT))
  (is (= (check-formats :utxt "utxt") FileFormat/UTXT))
  (is (= (check-formats :svg "SVG") FileFormat/SVG))
  (is (= (check-formats :pdf "PDF") FileFormat/PDF))
  ; TODO: The following formats are very unstable:
  ;(check-formats :html) => FileFormat/HTML
  ;(check-formats :html5) => FileFormat/HTML5
)

(deftest check-source-list
  (let [read-conf core/read-configs]
    (is (= (empty? (read-conf nil)) true))
    (is (= (empty? (read-conf {})) true))
    (is (= (empty? (read-conf {:plantuml []})) true))
    (is (= (empty? (read-conf {:plantuml ["test.uml"]})) false))
    (is (= (empty? (read-conf {} "test.uml")) false))
    (is (= (empty? (read-conf {} ["test.uml"])) false))
    (is (= (empty? (read-conf {:plantuml ["test.uml"]} ["test.uml"])) false))))

(deftest check-plantuml-processor
  (is (= (check-process "example-01.puml") true))
  (is (= (check-process "example-02.puml") true))
  (is (= (check-process "example-03.puml") true))
  (is (= (check-process "example-04.puml") true)))
