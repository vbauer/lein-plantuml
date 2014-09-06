(ns ^{:author "Vladislav Bauer"}
  leiningen.plantuml
  (:require [lein-plantuml.core :as core]))


; External API: Leiningen tasks

(defn plantuml
  "Generate UML diagrams using PluntUML.

  Available file formats:
    - eps - Encapsulated PostScript format
    - svg - Scalable Vector Graphics format
    - png - Portable Network Graphics format
    - pdf - Portable Document Format
    - txt, utxt - Text file format
    - html, html5 - HTML documents
    - mjpeg - MJPEG format

  Usage:
    lein plantuml <source folder> [<file format>] [<output folder>]"

  [project & args]
  (core/plantuml project args))
