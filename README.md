## Compile Java client

```
$ cd client/java
$ mvn compile exec:java
```

## Compile services

```
$ cd supplier
$ javac *.java && jar cf Supplier.jar *.class
```