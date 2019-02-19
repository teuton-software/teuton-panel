# Teuton Panel

Graphical front-end for [TEUTON](https://github.com/dvarrui/teuton).

![logo](logo.png)

## For users

[TODO]

## For developers

Requirements for building `teuton-panel`:

* [**OpenJDK**](https://adoptopenjdk.net/) (11.0.2)
* [**Maven**](https://maven.apache.org) (3.5.4+)
* [**Inno Setup**](http://www.jrsoftware.org/isinfo.php) (5+): *to build EXE installer on Windows*
* **rpmbuild**: *to build RPM on GNU/Linux*

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
* A `teuton-panel_x.y.z.deb` package file onGNU/Linux. 
* A `teuton-panel_x.y.z.rpm` package file on GNU/Linux [future release]
* A `teuton-panel_x.y.z.exe` installer file on Windows.

> **x.y.z** is the version number (e.g. 1.2.3).

## Thanks to

* **Javier Valencia Rodríguez**: for testing the app and helping to fix some issues.