(ns ^{:author "Vladislav Bauer"}
  lein-plantuml.t-core
  (:import (net.sourceforge.plantuml FileFormat))
  (:use [midje.sweet]
        [midje.util :only [testable-privates]])
  (:require [lein-plantuml.core]))

(testable-privates lein-plantuml.core file-format)


(fact "Check available file formats"
  (file-format "eps") => FileFormat/EPS
  (file-format :eps) => FileFormat/EPS
  (file-format :eps:txt) => FileFormat/EPS_TEXT
  (file-format :svg) => FileFormat/SVG
  (file-format :png) => FileFormat/PNG
  (file-format :pdf) => FileFormat/PDF
  (file-format :txt) => FileFormat/ATXT
  (file-format :utxt) => FileFormat/UTXT
  (file-format :html) => FileFormat/HTML
  (file-format :html5) => FileFormat/HTML5)
