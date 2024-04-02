### Swagger
http://localhost:8080/swagger-ui/index.html

### Requires Neo4j:

```
docker pull neo4j

docker run \        
--publish=7474:7474 --publish=7687:7687 \
--volume=$HOME/neo4j/data:/data \
neo4j
```
