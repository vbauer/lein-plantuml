(defproject lein-plantuml "1.0.0-SNAPSHOT"
  :description "A Leiningen plugin for generating UML diagrams using PluntUML."
  :url "https://github.com/vbauer/lein-plantuml"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[net.sourceforge.plantuml/plantuml "7997"]
                 [clj-glob "1.0.0"]]

  :eval-in-leiningen true
  :pedantic? :abort)
