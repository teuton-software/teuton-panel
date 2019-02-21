#!/bin/bash

function exist_command() {
	[ ! $(which $1) ] && echo "$1 is needed to generate rpm from deb package" && exit 1  
}

exist_command alien
exist_command rpmbuild

filename=$1
basename=$(basename -- $1)
package="${file%.*}"
alien -g --to-rpm $file
cd $basename/$package
rpmbuild --buildroot $(pwd) --nodeps -bb $package.spec
