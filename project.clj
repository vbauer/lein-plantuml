(defproject lein-plantuml "0.1.4-SNAPSHOT"
  :description "A Leiningen plugin for generating UML diagrams using PluntUML."
  :url "https://github.com/vbauer/lein-plantuml"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[net.sourceforge.plantuml/plantuml "7999"]
                 [clj-glob "1.0.0" :exclusions [org.clojure/clojure]]]

  :plugins [[lein-release "1.0.5" :exclusions [org.clojure/clojure]]
            [lein-kibit "0.0.8" :exclusions [org.clojure/clojure]]
            [lein-bikeshed "0.1.7" :exclusions [org.clojure/clojure]]
            [lein-ancient "0.5.5"]]

  :eval-in-leiningen true
  :pedantic? :abort
  :global-vars {*warn-on-reflection* true}

  :local-repo-classpath true
  :lein-release {:deploy-via :clojars
                 :scm :git})
