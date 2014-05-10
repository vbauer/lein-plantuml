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

(defn- ffile [fname]
  (if fname
    (io/file (.getAbsolutePath (io/file fname)))))

(defn- create-reader [input output fformat]
  (let [in (ffile input)
        out (ffile output)
        defines (Defines.)
        fmt (FileFormatOption. fformat)
        config []
        charset nil]
    (SourceFileReader. defines in out config charset fmt)))

(defn- process-file [in out fmt]
  (let [reader (create-reader in out fmt)]
    (doseq [image (.getGeneratedImages reader)]
      (println (str image " " (.getDescription image))))))

(defn- process-config [config]
  (let [inputs (glob/glob (nth config 0))
        fmt ((nth config 1 :png) FILE_FORMAT)
        output (nth config 2 nil)]
    (doseq [input inputs]
      (process-file input output fmt))))


; External API

(defn plantuml [project & args]
  (let [configs (:plantuml project)]
    (doseq [config configs]
      (process-config config))))

