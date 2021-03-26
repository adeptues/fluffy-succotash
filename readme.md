This uses spring boot and java 8 running on port 8080.

to build do

mvn clean package

then run with

java -jar target/xdesign.jar 

the api can be used via curl such as the following

curl "http://localhost:8080/api/munro?category=MUN&minHeight=940&maxHeight=1000&sortOrder=HEIGHTASC,NAMEASC&limit=10"