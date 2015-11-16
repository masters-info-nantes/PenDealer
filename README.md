## Compile Java client

```
$ cd client/java
$ mvn compile exec:java
```

## Compile services

```
$ cd services 

# For each service to get .jar file
$ mvn package 
```

For the shop, do it twice and change the wso2 configuration