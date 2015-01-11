(defproject example "0.1.0-SNAPSHOT"
  :description "An example of usage lein-plantuml plugin"
  :url "https://github.com/vbauer/lein-plantuml"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  ; List of plugins
  :plugins [[lein-plantuml "0.1.14"]]

  ; List of hooks
  ; It's used for running lein-plantuml during compile phase
  :hooks [lein-plantuml.plugin]

  ; lein-plantuml configuration
  :plantuml [["doc/*.puml" :png "target/doc"]])
