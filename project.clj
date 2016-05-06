(defproject lein-plantuml "0.1.21"
  :description "A Leiningen plugin for generating UML diagrams using PluntUML."
  :url "https://github.com/vbauer/lein-plantuml"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.apache.xmlgraphics/batik-rasterizer "1.8"]
                 [net.sourceforge.plantuml/plantuml "8039"]
                 [me.raynes/fs "1.4.6" :exclusions [org.clojure/clojure]]]

  :checksum :warn
  :pedantic? :abort
  :eval-in-leiningen true
  :local-repo-classpath true
  :warn-on-reflection false)
