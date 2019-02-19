#!/bin/bash
i=1
max=10
echo "[0/$max.INFO] Starting doing nothing"
while [ $i -le $max ]
do
	echo "[$i/$max.INFO] Doing nothing"
	whoami
	sleep 1s
	i=$(expr $i + 1)
done
echo echo "[$max/$max.INFO] Finish"