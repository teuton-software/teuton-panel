# Teuton Panel

Graphical front-end for [TEUTON](https://github.com/dvarrui/teuton).

![logo](logo.png)

## Developers

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
* A `teuton-panel_x.y.z_amd64.deb` package file on Debian based GNU/Linux. 
* A `teuton-panel_x.y.z_amd64.rpm` package file on RedHat based GNU/Linux [future releases]
* A `teuton-panel_x.y.z_amd64.exe` installer file on Windows.