lein-plantuml
=============

A Leiningen plugin for generating UML diagrams using PluntUML.

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

Include the following plugin in your project.clj file or your global profile:

```clojure
(defproject your-project-here "version"
 ...
 :plugins [[lein-plantuml "0.1.0"]]
 ...)
```

Configuration
=============

To configure PluntUML generator you need to setup :plantuml option as a list of triplets:
["glob pattern" <file format> <output directory>]

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
