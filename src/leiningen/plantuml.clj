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

(defn joine [& data]
  (string/join "\r\n" data))


; Internal API: States

(def ^:private FILE_FORMAT
  {:eps FileFormat/EPS
   :eps:txt FileFormat/EPS_TEXT
   :svg FileFormat/SVG
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

(defn- generate-sources [project & args]
  (let [includes (or (:plantuml project) [])
        sources (concat includes (vec args))]
    (remove empty? sources)))


; Internal API: Renderer

(defn- error [msg]
  (println
   (joine
    (str "Can't execute PlanUML: " msg)
    "Check that graphviz is installed."
    "Additional information: https://github.com/vbauer/lein-platnuml"))
  (main/abort))

(defn- ^ISourceFileReader create-reader [input output fformat]
  (let [in (abs-file input)
        out (abs-file output)
        defines (Defines.)
        fmt (FileFormatOption. fformat)
        config []
        charset nil]
    (SourceFileReader. defines in out config charset fmt)))

(defn- process-file [in out fmt]
  (let [reader (create-reader in out fmt)
        images (.getGeneratedImages reader)]
    (doseq [^GeneratedImage image images]
      (println (str image " " (.getDescription image))))))

(defn- process-config [config]
  (try
    (let [inputs (glob/glob (nth config 0))
          fmt (file-format (nth config 1 :png))
          output (nth config 2 nil)]
      (doseq [input inputs]
        (process-file input output fmt)))
    (catch Throwable t
      (error (.getMessage t)))))

(defn- proc [project & args]
  (let [configs (generate-sources project)]
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
