(ns ^{:author "Vladislav Bauer"}
  lein-plantuml.t-core
  (:import (net.sourceforge.plantuml FileFormat)
           (org.apache.commons.io FileUtils FilenameUtils))
  (:use [midje.sweet]
        [midje.util :only [testable-privates]]
        [clojure.java.io :only [as-file]])
  (:require [lein-plantuml.core]))


; Configurations

(def ^:private DEF_OUTPUT "test-out")

(testable-privates
  lein-plantuml.core
    file-format
    read-configs
    process-file
    FILE_FORMAT)


; Helper functions

(defn- check-process
  ([u] (let [file (str "example/doc/" u)
             formats (vals (var-get FILE_FORMAT))]
         (every? #(check-process file %) formats)))
  ([u f] (try
           (process-file u DEF_OUTPUT f)
           (finally (FileUtils/deleteDirectory (as-file DEF_OUTPUT))))))


; Tests

(fact "Check available file formats"
  (file-format :none) => nil
  (file-format "eps") => FileFormat/EPS
  (file-format :eps) => FileFormat/EPS
  (file-format :eps:txt) => FileFormat/EPS_TEXT
  (file-format :png) => FileFormat/PNG
  (file-format :txt) => FileFormat/ATXT
  (file-format :utxt) => FileFormat/UTXT
  ; TODO: The following formats are very unstable:
  ;(file-format :svg) => FileFormat/SVG
  ;(file-format :pdf) => FileFormat/PDF
  ;(file-format :html) => FileFormat/HTML
  ;(file-format :html5) => FileFormat/HTML5
)

(fact "Check source list"
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
