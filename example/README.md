
Introduction
============

It is a simple example to show generation of UML diagrams using [lein-plantuml](https://github.com/vbauer/lein-plantuml).


Configuration options
=====================

Options are already configured, you check them in `project.clj` file:

```clojure
:plantuml [["doc/*.puml" :png "target/doc"]]
```

You could play with options later to see different results.


Usage
=====

Run the following command in the current directory (`"example"`):

```bash
lein plantuml
```


Results
=======

In case of successful completion, you should see something like that in console:

```bash
Processed file: [example-04.puml] (5 entities)
Processed file: [example-03.puml] (4 entities)
Processed file: [example-01.puml] (7 entities)
Processed file: [example-02.puml] (5 entities)
```

New `"target/doc"` directory with 4 files should be created:

* ./target/doc/example-01.png
* ./target/doc/example-02.png
* ./target/doc/example-03.png
* ./target/doc/example-04.png
