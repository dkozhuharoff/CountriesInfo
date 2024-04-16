# About the project

Countries Info is a Java Spring application designed to fetch comprehensive information about countries from an open API.We can get information such as the country name, the capital of the country, the official currency, etc. When we make a GET request, we first check if the country and the information about it exists in our database, we read the response from the database and if not, we pull it from the open API. By successfully running the GET request, the country and the info about it is saved in our database, which helps us prevent downtime in case the open API is not responding.

# Used technologies

The project is written on Java, using the Spring Framework. I've used a NoSQL database - Apache Cassandra. There is also a docker-compose file, which allows you to run the application in the database as a whole.

# Setting up the database

* Exec into the container
* Do cqlsh to login into the database
* Execute the following command:
> create keyspace countries with replication={'class':'SimpleStrategy', 'replication_factor':1};
* Then use countries keyspace by executing this command:
> use countries;
* Create the table by executing this command:
> create table country(name text PRIMARY KEY, info text);

# CI/CD

In the Git repository, three pipelines are established. The Continuous Integration (CI) pipeline executes a Maven build upon each branch and commit. Another pipeline focuses on building a Docker image for every branch, ensuring compatibility with changes made outside the main branch. The final pipeline operates exclusively on the main branch, where changes from other branches are merged. Upon merging, an image is built and pushed to the Docker Hub repository automatically, streamlining the deployment process.
