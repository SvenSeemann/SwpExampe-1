# Developer documentation for the FVIV festival manager

## Table of contents

@@TOC@@

## Dependencies and Libraries

This software is built upon and deeply integrated with the <!--relatively good and thankfully--> open source [spring web framework](https://spring.io). <!-- Unfortunately it also depends on a badly written thin software layer, that also describes itself as 'framework', called salespoint-->
As well as the the proprietary [salespoint framework](http://www.st.inf.tu-dresden.de/SalesPoint/v5.0/wiki/index.php/Main_Page) for business applications.

All java dependencies and libraries (including version information) necessary for development work with this application are listed in pom.xml.

This project uses [maven](http://maven.apache.org) for project and dependency mangement and building and [git](http://git-scm.com) for source code management with [GitHub](https://github.com) as remote repository host.

Notable non-java dependencies not listed in pom.xml:
* [jQuery](https://jquery.com) a multi purpose javascript library

These libraries are included in the distributed binaries, as well as the source code repository.

## Structure

The following section should not only be treated as a description of the projects general structure, but also as a guideline for developers to ensure easy navigation in future iterations of the project.

### Directories

The project root only contains configuration files and subdirectories.

* **pom.xml:** maven configuration
* **prototypes/:** application prototypes for new features
* **src/:** source code
* **README.md:** readme file
* **.git/:** vcs data
* **.gitignore:** contains files omitted by the version control system
* **.travis.yml:** ci configuration


### Packages
All source code is structured in packages.

The main project package is `fviv`.

All subcomponents should be organised is separate subpackages to not pollute the global namespace, aka. `fviv.catering`, `fviv.planning` etc.

All Controllers are organised in one single package `fviv.controller`. This avoids potential problems with springs controller discovery and makes it easy to find controllers that are causing problems.

The Main class is located at src/main/java/fviv/Application.java.
