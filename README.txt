We have built this project using java 1.6

In order to compile this project you need to set vm arguments of the runner of src/astro/Main.java to:
-javaagent:/PATH-TO-REPO/stockrobot/jar/openjpa-all-2.2.0.jar

That also applies to TestSuiteForEverything.java, if you want to run the tests.

If for some reason JPA starts to complain about lockfiles, or something that's not easy to trouble shoot, the easy way is to delete all database files:
!!!This will remove all the content in the database!!!
rm -r stockrobot/astrodatabase.*
rm -r stockrobot/astrotestdatabase.*


