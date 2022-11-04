#!/bin/bash

echo "Starting scheduled job which fetches METAR data for subscribed airports every 2 minutes."
while true
do
	echo "Press [CTRL+C] to stop..."
	curl http://localhost:8080/subscriptions | grep -oP 'icaoCode":"\K[A-Z0-9]{4}' |
	while read -r line
	do
		echo Processing airport with ICAO code: "$line"...
		METAR_DATA="$(curl "https://tgftp.nws.noaa.gov/data/observations/metar/stations/$line.TXT")"
		echo Fetched METAR data: $METAR_DATA
		POST_DATA="{\"data\":\"$METAR_DATA\"}"
		curl --data "$POST_DATA" http://localhost:8080/airport/$line/METAR -H "Content-Type: application/json" -v
		sleep 0.5
	done
	sleep 120
done
