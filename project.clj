(defproject lein-plantuml "0.1.20-SNAPSHOT"
  :description "A Leiningen plugin for generating UML diagrams using PluntUML."
  :url "https://github.com/vbauer/lein-plantuml"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.apache.xmlgraphics/batik-rasterizer "1.8"]
                 [net.sourceforge.plantuml/plantuml "8033"]
                 [me.raynes/fs "1.4.6" :exclusions [org.clojure/clojure]]]

  :profiles {

    :prod {:plugins [[lein-release "1.0.6" :exclusions [org.clojure/clojure]]]
           :global-vars {*warn-on-reflection* true}
           :scm {:name "git"
                 :url "https://github.com/vbauer/lein-plantuml"}
           :lein-release {:deploy-via :clojars
                          :scm :git}}
  }

  :checksum :warn
  :pedantic? :abort
  :eval-in-leiningen true
  :local-repo-classpath true
  :warn-on-reflection false)
