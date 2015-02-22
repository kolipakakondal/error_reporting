How to contribute to EPP Logging
==============================================

EPP Logging provides a set of logging plugins for the Eclipse IDE. 

Find Out More
-------------

- [Project website](http://www.eclipse.org/epp/)

Build from Source
-----------------

### Building on the Command Line

If you want to build the code from the command line, you will need both [Git](http://www.git-scm.com/downloads) and  [Apache Maven](http://maven.apache.org/download.html), version 3.x.
First clone the Git repository:

    $ git clone http://git.eclipse.org/gitroot/epp/org.eclipse.epp.logging.git

After you have successfully cloned the repository, use Maven to build from scratch:

    $ cd org.eclipse.epp.logging
    $ mvn clean install

That’s it.
After a few minutes wait, you should see a `BUILD SUCCESS`.
(The initial build may take a bit longer, as Maven automatically downloads anything required by the build.)
