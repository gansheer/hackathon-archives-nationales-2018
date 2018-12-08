curl -XPUT 'http://localhost:9200/persons'  -H "Accept: application/json"
curl -XPUT 'http://localhost:9200/persons/_mapping/_doc' -H "Accept: application/json" -H "Content-Type: application/json" -d @mapping-es-person.json
