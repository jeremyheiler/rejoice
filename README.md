# Rejoice
An interpretor for a language inspired by Joy.

The purpose of this language is to stick to the Joy philosophy, but not necessarily to be completely backwards compatible. This is a toy research project after all!

For more about Joy, check out the [Joy archive](http://joy-lang.org) for papers, tutorials, and code from Dr Manfred von Thun, the creator of Joy.

## Build and Run

There currently are no released packages. 

To run Rejoice, checkout the code and run the following at the project's root: 

```
mvn package
````

To start a REPL, use the `repl` command.

```
java -jar target/rejoice-{version}.jar repl
```

To evaluate code and exit, use the `eval` command.

```
java -jar target/rejoice-{version}.jar eval "1 2 +"
```

Run the command `help` to see all the available commands. 

## License

This is a pet project. Feel free to play with it, but don't steal it. Thanks :)

Copyright © 2021 Jeremy Heiler.
