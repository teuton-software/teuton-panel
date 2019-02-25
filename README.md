# Teuton Panel

Graphical front-end for [TEUTON](https://github.com/dvarrui/teuton).

![logo](logo.png)

## For users

[TODO]

## For developers

Requirements for building `teuton-panel`:

* [**OpenJDK**](https://adoptopenjdk.net/) (11.0.2)
* [**Maven**](https://maven.apache.org) (3.5.4+)
* [**JavaPackager Maven plugin**](https://github.com/fvarrui/JavaPackager)
* [**Inno Setup**](http://www.jrsoftware.org/isinfo.php) (5+): *to build EXE installer on Windows*
* **alien & rpmbuild**: *to generate RPM on GNU/Linux*

### How to build the application 

Execute next commands in BASH (GNU/Linux) or CMD (Windows):

1. Download source code and change to the project directory:

```bash
git clone https://github.com/fvarrui/teuton-panel.git
cd teuton-panel
```

2. Compile and package the project:

```bash
mvn package
```

It generates:

* A native application in `target/app` directory with a bundled JRE.
* A `teuton-panel_x.y.z.deb` package file on GNU/Linux. 
* A `teuton-panel_x.y.z.rpm` package file on GNU/Linux.
* A `teuton-panel_x.y.z.exe` installer file on Windows.
* A `teuton-panel_x.y.z.dmg` installer file on Mac OS X.

> **x.y.z** is the version number (e.g. 1.2.3).

## Contributors

Thanks to the following contributors, who have provided patches and other assistance:

* **Javier Valencia Rodríguez**