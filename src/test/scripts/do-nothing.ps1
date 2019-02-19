$i=1
$max=10
echo "[0/$max.INFO] Starting doing nothing"
while ($i -le $max) {
	echo "[$i/$max.INFO] Doing nothing"
    (date).ToString()
	sleep -Seconds 1
    $i++
}
echo "[$max/$max.INFO] Finish"