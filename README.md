# elastic-java-api
Elasticsearch JAVA API with custom search parameters

## What is it?
The application basically connects to elasticsearch, creates query according to your custom fields and brings json elements

## How to run
Configure app.properties file under /conf folder and run Main.java

## App properties
```
# ELASTICSEARCH CONFIGURATION
elastic.ip=192.168.10.14
elastic.index=index*
elastic.size=100
elastic.start.time=2016-10-01T00:00:00.000
elastic.end.time=2016-10-02T23:59:59.000
elastic.scroll.enable=on
elastic.scroll.time=5m

# KEY/VALUE PAIR TO SEARCH FOR (KEY/VALUE NUMBERS MUST BE EQUAL AND IN AN ORDER RESPECTIVELY)
query.keys=name,lastname,age
query.values=ali,veli,20

# RESULT FIELD
# (THIS IS BASICALLY THE RESULT YOU ARE LOOKING FOR. MUST MATCH WITH ELASTIC FIELDS. ONLY ONE FIELD ALLOWED)
result.field=birthday
```