# AirportMetarDemo

This project consists of a java implementation of a simple service that allows storing (and retrieving) the METAR data for subscribed airports (based on their ICAO code) into a MySQL database, and a bash script that starts a job which is triggered every two minutes and fetches METAR data for subscribed airports from https://tgftp.nws.noaa.gov/data/observations/metar/stations/. The data can be manipulated via simple HTTP requests towards the following API endpoints:

"/subscriptions" - allows GET, POST (with the request body in the form of {"icaoCode": XXXX}) and DELETE methods

"/airport/{icaoCode}/METAR" - allows GET, POST (with the requesst body in the form of {"data": SOME METAR DATA}) and DELETE methods

Database should be created automatically via Spring & Hibernate, provided that MySQL server is installed.
In order to run the scheduled job, start the JAR service (the repository already contains the ready-to-go JAR file with the service implementation, which was compiled with JAVA 17), and then run the scheduled_job.sh script in bash shell.
