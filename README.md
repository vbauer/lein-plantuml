lein-plantuml
=============

A Leiningen plugin for generating UML diagrams using [PluntUML](http://plantuml.sourceforge.net).


Pre-requirements
================

Install [Graphviz](http://www.graphviz.org) to use lein-plantuml plugin.

On Ubuntu:
```
sudo port install graphviz
```
On Mac OS X:
```
brew install graphviz
```


Installation
============

To enable lein-plantuml for your project, put the following in the :plugins vector of your project.clj file:

![latest-version](https://clojars.org/lein-plantuml/latest-version.svg)

[![Build Status](https://travis-ci.org/vbauer/lein-plantuml.svg?branch=master)](https://travis-ci.org/vbauer/lein-plantuml)
[![Dependencies Status](http://jarkeeper.com/vbauer/lein-plantuml/status.png)](http://jarkeeper.com/vbauer/lein-plantuml)

Configuration
=============

To configure PluntUML generator you need to setup :plantuml option as a list of triplets:
"glob pattern" ["file format"] ["output directory"]

Description:
- Glob pattern - it is a pattern based on wildcard characters for input files. See [Glob](http://en.wikipedia.org/wiki/Glob_(programming)) for more details.
- Files format - it is a image file format for output data (optional parameter).
- Output directory - is an optional parameter, files will be generated in the same directory by default.

File formats:
- :xmi, :xmi:argo, :xmi:start - XML Metadata Interchange format
- :eps, :eps:txt - Encapsulated PostScript format
- :svg - Scalable Vector Graphics format
- :text, :utext - Text file format
- :png - Portable Network Graphics format
- :pdf - Portable Document Format

Example configuration:

```clojure
  :plantuml [["resources/*.puml" :png "target"]
             ["presentation/*.txt" :svg]]
```


Usage
=====

To generate UML image files in the corresponding directories using configuration section from project.clj use:

```
lein plantuml
```

You could also use command-line parameters to define configuration:
```
lein plantuml "resources/*.puml" png "target/resources"
```

NB: To test plugin, put file "test.puml" in folder "resources":
```
@startuml

User -> (Start)
User --> (Use the application) : A small label

:Main Admin: ---> (Use the application) : This is\nyet another\nlabel

@enduml
```

More examples could be found here:
- [Sequence Diagram](http://plantuml.sourceforge.net/sequence.html)
- [Use Case Diagram](http://plantuml.sourceforge.net/usecase.html)
- [Class Diagram](http://plantuml.sourceforge.net/classes.html)
- [Activity Diagram](http://plantuml.sourceforge.net/activity.html) + [(more)](http://plantuml.sourceforge.net/activity2.html)
- [Component Diagram](http://plantuml.sourceforge.net/component.html)
- [State Diagram](http://plantuml.sourceforge.net/state.html)
- [Object Diagram](http://plantuml.sourceforge.net/objects.html)


License
=======

Copyright © 2014 Vladislav Bauer

Distributed under the Eclipse Public License, the same as Clojure.
