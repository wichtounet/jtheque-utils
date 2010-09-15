# JTheque Utils #

This library provide some utility classes to make easier the development of Java projects. 

The last stable version is the 1.1.5 version. The current development version is the 1.2.0 version. 

## Building ##

You need a Java 6 (or newer) environment and Maven 2.0.9 (or newer) installed. You also need to have installed
this library into your maven repository :
[jtheque-unit-utils](http://github.com/wichtounet/jtheque-unit-utils "jtheque-unit-utils")

You should now be able to do a full build of `jtheque-unit-utils`:

    $ git clone git://github.com/wichtounet/jtheque-utils.git
    $ cd jtheque-utils
    $ mvn clean install

To use this library in your projects, add the following to the `dependencies` section of your `pom.xml`:

    <dependency>
       <groupId>org.jtheque</groupId>
       <artifactId>org.jtheque.utils</artifactId>
       <version>1.1.5</version>
    </dependency>

## Troubleshooting ##

Please consider using [Github issues tracker](http://github.com/wichtounet/jtheque-utils/issues) to submit bug reports or feature requests.

## License ##

See `LICENSE` for details.