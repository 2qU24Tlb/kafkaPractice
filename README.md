# kafkaPractice
## Topic
## Partition
## Broker
    - connect to each other

## Producer:
    - round robin without key
    - level of ack: none, borker, broker+replica
    
## Consumer:
    - read in order within each partitions
    - # of partitions -> # of consumers
    - commit: at most once, a least once

## CLI:
### topic:
create:
```shell script
kafka-topics.sh --bootstrap-server localhost:9092 --topic second-topic --create --partitions 3 --replication-factor 1
```
describe:
```shell script
kafka-topics.sh --bootstrap-server localhost:9092  --topic second-topic --describe
```
delete:
```shell script
kafka-topics.sh --bootstrap-server localhost:9092  --topic second_topic --delete
```

### producer:
produce:
```shell script
kafka-console-producer.sh --broker-list localhost:9092 --topic second-topic
```

### consumer:
consume without group:
```shell script
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic second-topic
```
consume with group:
```shell script
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic second-topic --group 1
```

### consumer group:
list
```shell script
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list
```

describe
```shell script
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group 1
```
