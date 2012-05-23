We have built this project using java 1.6

In order to compile this project you need to set vm arguments to:
-javaagent:/PATH-TO-REPO/stockrobot/jar/openjpa-all-2.2.0.jar
note that this is not always necessary tho.


If for some reason JPA starts to complain alot you can delete all database files:
!!!This will remove all the content in the database!!!
rm -r stockrobot/astrodatabase.*
rm -r stockrobot/astrotestdatabase.*


