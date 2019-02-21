#!/bin/bash

function exist_command() {
	[ ! $(which $1) ] && echo "$1 is needed to generate rpm from deb package" && exit 1  
}

exist_command alien
exist_command rpmbuild

filename=$1
basename=$(basename -- $1)
package="${file%.*}"

echo $filename
echo $basename
echo $package

alien -g --to-rpm $file
rpmbuild --buildroot $basename/$package --nodeps -bb $package.spec
