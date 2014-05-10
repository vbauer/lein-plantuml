(ns leiningen.plantuml
  (:import (net.sourceforge.plantuml.preproc Defines)
           (net.sourceforge.plantuml FileFormat
                                     SourceFileReader
                                     FileFormatOption
                                     Option))
  (:require [clojure.java.io :as io]
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


; Internal API

(defn- abs-file [fname]
  (if fname
    (io/file (.getAbsolutePath (io/file fname)))))

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

(defn- file-format [k]
  (let [fmt (if (instance? String k) (keyword k) k)]
    (fmt FILE_FORMAT)))

(defn- process-config [config]
  (let [inputs (glob/glob (nth config 0))
        fmt (file-format (nth config 1 :png))
        output (nth config 2 nil)]
    (doseq [input inputs]
      (process-file input output fmt))))


; External API

(defn plantuml [project & argv]
  (let [plantuml (or (:plantuml project) [])
        args (if argv (vec argv) [])
        even1arg (>= 1 (count args))
        configs (if even1arg plantuml (conj plantuml args))]
    (doseq [config configs]
      (process-config config))))

