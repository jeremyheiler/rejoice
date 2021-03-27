# Rejoice
An interpretor for a language inspired by Joy.

The purpose of this language is to stick to the Joy philosophy, but not necessarily to be completely backwards compatible. This is a toy research project after all!

For more about Joy, check out the [Joy archive](http://joy-lang.org) for papers, tutorials, and code from Dr Manfred von Thun, the creator of Joy.

## Building the source

There currently are no released packages. 

To build Rejoice, run the following at the project's root directory. 

```
mvn package
````

## Evaluating code

The `eval` command executes the provided program and exits the process.

```
java -jar target/rejoice-{version}.jar eval '1 2 + !'
```

The command has a `--mode` option that selects either the `rejoice` (defualt) or `joy` runtime. If any options are provided, the program must be provided after `--` to indicate that there are no more options to handle.

```
java -jar target/rejoice-{version}.jar eval --mode joy -- '1 2 succ put'
```

## Running a REPL

The `repl` command will start a REPL. Type `Ctl-C` to quit the REPL.

```
java -jar target/rejoice-{version}.jar repl
```

As with the `eval` command, `repl` supports the `--mode` option to select either the `rejoice` (default) or `joy` runtime.

```
java -jar target/rejoice-{version}.jar repl --mode joy
```

Another option is `--eval`, which will execute the provided program immediately after starting the REPL.

The following program will be executed with the `joy` runtime and leave `0` on the stack.

```
java -jar target/rejoice-{version}.jar repl --mode joy --eval '1 pred'
```

## Help

The `help` command will list all available commands. 

## License

This is a pet project. Feel free to play with it, but don't steal it. Thanks :)

Copyright © 2021 Jeremy Heiler.
