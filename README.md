lein-plantuml
=============

A Leiningen plugin for generating UML diagrams using PluntUML

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
- Glob pattern - it is a pattern based on wildcard characters. See [Glob](http://en.wikipedia.org/wiki/Glob_(programming)) for more details.
- File format - it is a image file format (optional parameter).
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

Run:

```
lein plantuml
```

This will generate UML image files in the corresponding directories.
