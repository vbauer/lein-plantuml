(ns ^{:author "Vladislav Bauer"}
  lein-plantuml.core
  (:import (net.sourceforge.plantuml.preproc Defines)
           (net.sourceforge.plantuml FileFormat
                                     FileFormatOption
                                     GeneratedImage
                                     ISourceFileReader
                                     SourceFileReader
                                     Option))
  (:require [leiningen.core.main :as main]
            [me.raynes.fs :as fs]
            [citizen.os :as os]
            [clojure.string :as string]))


; Internal API: Constants

(def ^:private DEF_SOURCES "src/plantuml/*.puml")
(def ^:private DEF_FILE_FORMAT :png)

(def ^:private FILE_FORMAT
  {:eps FileFormat/EPS
   :eps:txt FileFormat/EPS_TEXT
   :png FileFormat/PNG
   :txt FileFormat/ATXT
   :utxt FileFormat/UTXT
   :svg FileFormat/SVG
   ; TODO: The following formats are very unstable:
   ;:pdf FileFormat/PDF
   ;:html FileFormat/HTML
   ;:html5 FileFormat/HTML5
   })


; Internal API: Common

(defn- log [& messages] (println (string/join messages)))

(defn- abs-file [fname]
  (if fname
    (doto
      (fs/file (.getAbsolutePath (fs/file fname)))
      (.mkdirs))))

(defn- clean-path [p]
  (if os/windows?
    (string/replace p "/" "\\")
    (string/replace p "\\" "/")))


; Internal API: Configurations

(defn- file-format [k]
  (let [fmt (when-not (nil? k)
              (keyword (string/lower-case (name k))))
        f (get FILE_FORMAT fmt)]
    (if (nil? f) (log "Bad file format: " k))
    f))

(defn- read-configs [project & args]
  (let [includes (or (when-not (nil? project) (:plantuml project)) [])
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
        (log "Processed file: " (.getDescription image) " format: " fmt))
      true)
    (catch Throwable t
      (log "Can not render file " in " with file format " fmt)
      false)))

(defn- process-config [config]
  (let [inputs (fs/glob (clean-path (nth config 0 DEF_SOURCES)))
        fmt (file-format (nth config 1 DEF_FILE_FORMAT))
        output (nth config 2 nil)]
    (doseq [input inputs]
      (process-file input output fmt))))


; External API: Runner

(defn plantuml [project & args]
  (let [configs (read-configs project)]
    (doseq [conf configs]
      (process-config conf))))
