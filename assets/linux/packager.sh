#!/bin/bash
SCRIPTPATH="$( cd "$(dirname "$0")" ; pwd -P )"
JARFILE=$1
DEST=$2
STARTUP=$SCRIPTPATH/startup.sh
cat $STARTUP $JARFILE > $DEST && chmod +x $DEST