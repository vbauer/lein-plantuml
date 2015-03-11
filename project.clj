(defproject lein-plantuml "0.1.16-SNAPSHOT"
  :description "A Leiningen plugin for generating UML diagrams using PluntUML."
  :url "https://github.com/vbauer/lein-plantuml"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.apache.xmlgraphics/batik-rasterizer "1.7"]
                 [net.sourceforge.plantuml/plantuml "8020"]
                 [clj-glob "1.0.0" :exclusions [org.clojure/clojure]]]

  :plugins [[jonase/eastwood "0.2.1" :exclusions [org.clojure/clojure]]
            [lein-kibit "0.0.8" :exclusions [org.clojure/clojure]]
            [lein-bikeshed "0.2.0" :exclusions [org.clojure/clojure]]
            [lein-ancient "0.6.5"]]

  :profiles {

    :dev {:dependencies [[midje "1.6.3" :exclusions [org.clojure/clojure joda-time]]
                         [me.raynes/fs "1.4.6" :exclusions [org.clojure/clojure]]]
          ; Don't use the latest version: https://github.com/marick/lein-midje/issues/47
          :plugins [[lein-midje "3.1.1"]]}

    :prod {:plugins [[lein-release "1.0.6" :exclusions [org.clojure/clojure]]]
           :global-vars {*warn-on-reflection* true}
           :scm {:name "git"
                 :url "https://github.com/vbauer/lein-plantuml"}
           :lein-release {:deploy-via :clojars
                          :scm :git}}
  }

  :pedantic? :abort
  :eval-in-leiningen true
  :local-repo-classpath true)
