#!/bin/sh
SCRIPTPATH="$(dirname "$(readlink -f "$0")")"
JAVA=$SCRIPTPATH/jre/bin/java
TEUTONPATH=$SCRIPTPATH/teuton-panel
pkexec env DISPLAY=$DISPLAY XAUTHORITY=$XAUTHORITY "$JAVA -jar $TEUTONPATH $@"
