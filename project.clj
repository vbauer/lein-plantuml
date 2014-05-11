(defproject lein-plantuml "0.1.2-SNAPSHOT"
  :description "A Leiningen plugin for generating UML diagrams using PluntUML."
  :url "https://github.com/vbauer/lein-plantuml"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[net.sourceforge.plantuml/plantuml "7997"]
                 [clj-glob "1.0.0"]]

  :plugins [[lein-release "1.0.5"]
            [lein-kibit "0.0.8" :exclusions [org.clojure/clojure]]]

  :eval-in-leiningen true
  :pedantic? :abort

  :local-repo-classpath true
  :lein-release {:deploy-via :clojars
                 :scm :git})
