lein-plantuml
=============

[PlantUML](http://plantuml.sourceforge.net) is an open-source tool that uses simple textual descriptions to draw UML diagrams. Diagrams are defined using a simple and intuitive language.

[lein-plantuml](https://github.com/vbauer/lein-plantuml) is a Leiningen plugin for generating UML diagrams using PlantUML.


Pre-requirements
================

Install [Graphviz](http://www.graphviz.org) to use lein-plantuml plugin. PlantUML should be working with any version of GraphViz, starting with 2.26.3

* [Mac OS X](http://www.graphviz.org/Download_macos.php):
```bash
brew install graphviz
```
* [Ubuntu](http://www.graphviz.org/Download_linux_ubuntu.php):
```bash
sudo apt-get install graphviz
```
* [Fedora](http://www.graphviz.org/Download_linux_fedora.php):
```bash
yum list available 'graphviz*'
yum install 'graphviz*'
```
* [RHEL or CentOS](http://www.graphviz.org/Download_linux_rhel.php)
* [Solaris](http://www.graphviz.org/Download_solaris.php)
* [Windows](http://www.graphviz.org/Download_windows.php)


Installation
============

To enable lein-plantuml for your project, put the following in the :plugins vector of your project.clj file:

![latest-version](https://clojars.org/lein-plantuml/latest-version.svg)

[![Build Status](https://travis-ci.org/vbauer/lein-plantuml.svg?branch=master)](https://travis-ci.org/vbauer/lein-plantuml)
[![Dependencies Status](http://jarkeeper.com/vbauer/lein-plantuml/status.png)](http://jarkeeper.com/vbauer/lein-plantuml)

Configuration
=============

To configure PlantUML generator, you need to setup :plantuml option as a list of triplets:
"glob pattern" ["file format"] ["output directory"]

Description:
- *Glob pattern* is based on wildcard characters, see [Glob](http://en.wikipedia.org/wiki/Glob_(programming)) for additional information.
- *Files format* is an image file format for output data (optional parameter, default value is :png). You could use both variants (keywords and text values) to configure file format.
- *Output directory* is an optional parameter, files will be generated in the same directory by default.

File formats:
- :eps - Encapsulated PostScript format
- :svg - Scalable Vector Graphics format
- :png - Portable Network Graphics format
- :pdf - Portable Document Format
- :txt, :utxt - Text file format

Example configuration:

```clojure
  :plantuml [["resources/*.puml" :png "target"]
             ["presentation/*.txt" "svg"]]
```


Usage
=====

To generate UML image files using configuration from project.clj, you should use:

```bash
lein plantuml
```

To enable this plugin at compile stage, use the following hook:
```clojure
:hooks [leiningen.plantuml]
```

To run PlantUML generator using command line interface without configuration in project.clj file, you need to use following command:
```bash
lein plantuml <source folder> [<file format>] [<output folder>]
```
By default, generator will use PNG output format and input directory for output files.

To show help for CLI:
```bash
 lein help plantuml
```


Example
=======

To test the plugin, you can create a simple file and run lein-plantuml:
```
@startuml

User -> (Start)
User --> (Use the application) : A small label

:Main Admin: ---> (Use the application) : This is\nyet another\nlabel

@enduml
```
Output UML diagram should look like this:

![uml-example](http://plantuml.sourceforge.net/imgp/usecase_003.png)

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


Thanks to
=========
PlantUML developers for the really great project and adding lein-planuml on the [official site](http://plantuml.sourceforge.net/running.html).


Might also like
===============

* [lein-jshint](https://github.com/vbauer/lein-jshint) - a Leiningen plugin for running javascript code through JSHint
* [lein-jslint](https://github.com/vbauer/lein-jslint) - a Leiningen plugin for running javascript code through JSLint
