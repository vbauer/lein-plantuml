(defproject example "0.1.0-SNAPSHOT"
  :description "An example of usage plang-uml plugin"
  :url "https://github.com/vbauer/lein-plantuml"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :plugins [[lein-plantuml "0.1.8"]]
  :plantuml [["doc/*.puml" :png "target/doc"]])
