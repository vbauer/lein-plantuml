(ns ^{:author "Vladislav Bauer"}
  leiningen.plantuml
  (:import (net.sourceforge.plantuml.preproc Defines)
           (net.sourceforge.plantuml FileFormat
                                     FileFormatOption
                                     GeneratedImage
                                     ISourceFileReader
                                     SourceFileReader
                                     Option))
  (:require [leiningen.core.main :as main]
            [leiningen.compile]
            [robert.hooke :as hooke]
            [clojure.java.io :as io]
            [org.satta.glob :as glob]
            [clojure.string :as string]))


; Internal API: Common

(defn- log [& messages] (println (apply str messages)))

(defn- abs-file [fname]
  (if fname
    (doto
      (io/file (.getAbsolutePath (io/file fname)))
      (.mkdirs))))


; Internal API: Constants

(def ^:private DEF_SOURCES "src/plantuml/*.puml")
(def ^:private DEF_FILE_FORMAT :png)

(def ^:private FILE_FORMAT
  {:eps FileFormat/EPS
   :eps:txt FileFormat/EPS_TEXT
   :svg FileFormat/SVG
   :png FileFormat/PNG
   :pdf FileFormat/PDF
   :txt FileFormat/ATXT
   :utxt FileFormat/UTXT
   :html FileFormat/HTML
   :html5 FileFormat/HTML5})


; Internal API: Configurations

(defn- file-format [k]
  (let [fmt (keyword (name k))
        f (fmt FILE_FORMAT)]
    (if (nil? f) (log "Bad file format: " k))
    f))

(defn- read-configs [project & args]
  (let [includes (or (:plantuml project) [])
        sources (concat includes (vec args))]
    (remove empty? sources)))


; Internal API: Renderer

(defn- ^ISourceFileReader create-reader [input output fformat]
  (let [in (abs-file input)
        out (abs-file output)
        defines (Defines.)
        fmt (FileFormatOption. fformat)
        config []
        charset nil]
    (SourceFileReader. defines in out config charset fmt)))

(defn- process-file [in out fmt]
  (try
    (let [reader (create-reader in out fmt)
          images (.getGeneratedImages reader)]
      (doseq [^GeneratedImage image images]
        (log "Processed file: " (.getDescription image))))
    (catch Throwable t
      (log "Can not render file " in " with file format " fmt))))

(defn- process-config [config]
  (let [inputs (glob/glob (nth config 0 DEF_SOURCES))
        fmt (file-format (nth config 1 DEF_FILE_FORMAT))
        output (nth config 2 nil)]
    (doseq [input inputs]
      (process-file input output fmt))))

(defn- proc [project & args]
  (let [configs (read-configs project)]
    (doseq [conf configs]
      (process-config conf))))


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
  (proc project args))


; External API: Leiningen hooks

(defn gen-hook [f & args]
  (let [res (apply f args)]
    (proc (first args))
    res))

(defn activate []
  (hooke/add-hook #'leiningen.compile/compile #'gen-hook))
