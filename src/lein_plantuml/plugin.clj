(ns ^{:author "Vladislav Bauer"}
  lein-plantuml.plugin
  (:require [leiningen.compile]
            [lein-plantuml.core :as core]
            [robert.hooke :as hooke]))


; External API: Leiningen hooks

(defn gen-hook [f & args]
  (let [res (apply f args)]
    (core/plantuml (first args))
    res))

(defn activate []
  (hooke/add-hook #'leiningen.compile/compile #'gen-hook))
