(ns ^{:author "Vladislav Bauer"}
  leiningen.plantuml
  (:import (net.sourceforge.plantuml.preproc Defines)
           (net.sourceforge.plantuml FileFormat
                                     SourceFileReader
                                     FileFormatOption
                                     Option))
  (:require [leiningen.core.main :as main]
            [clojure.java.io :as io]
            [org.satta.glob :as glob]))


; States

(def ^:private FILE_FORMAT
  {:xmi FileFormat/XMI_STANDARD
   :xmi:argo FileFormat/XMI_ARGO
   :xmi:start FileFormat/XMI_STAR
   :eps FileFormat/EPS
   :eps:txt FileFormat/EPS_TEXT
   :svg FileFormat/SVG
   :txt FileFormat/ATXT
   :utxt FileFormat/UTXT
   :png FileFormat/PNG
   :pdf FileFormat/PDF})


; Internal API: Configurations

(defn- abs-file [fname]
  (if fname
    (doto
      (io/file (.getAbsolutePath (io/file fname)))
      (.mkdirs))))

(defn- file-format [k]
  (let [fmt (if (instance? String k) (keyword k) k)]
    (fmt FILE_FORMAT)))

(defn- generate-sources [project argv]
  (let [sources (or (:plantuml project) [])
       args [(vec argv)]]
    (remove empty? (concat sources args))))


; Internal API: Renderer

(defn- create-reader [input output fformat]
  (let [in (abs-file input)
        out (abs-file output)
        defines (Defines.)
        fmt (FileFormatOption. fformat)
        config []
        charset nil]
    (SourceFileReader. defines in out config charset fmt)))

(defn- process-file [in out fmt]
  (let [reader (create-reader in out fmt)]
    (doseq [image (.getGeneratedImages reader)]
      (println (str image " " (.getDescription image))))))

(defn- proc [config]
  (try
    (let [inputs (glob/glob (nth config 0))
          fmt (file-format (nth config 1 :png))
          output (nth config 2 nil)]
      (doseq [input inputs]
        (process-file input output fmt)))
    (catch Throwable t
      (println
       (joine (str "Can't execute PlanUML: " (.getMessage t))
              "Check that graphviz is installed."
              "Additional information: https://github.com/vbauer/lein-platnuml"))
      (main/abort))))


; External API: Leiningen tasks

(defn plantuml
  "Generate UML diagrams using PluntUML.

  Available file formats:
    - xmi, xmi:argo, xmi:start - XML Metadata Interchange format
    - eps, eps:txt - Encapsulated PostScript format
    - svg - Scalable Vector Graphics format
    - text, utext - Text file format
    - png - Portable Network Graphics format
    - pdf - Portable Document Format

  Usage:
    lein plantuml <source folder> [<file format>] [<output folder>]"

  [project & args]
  (let [configs (generate-sources project args)]
    (doseq [conf configs]
      (proc conf))))
