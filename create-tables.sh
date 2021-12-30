#!/bin/bash

set -e
set -x

docker-compose exec scylla cqlsh -e "DROP KEYSPACE IF EXISTS testing;"
docker-compose exec scylla cqlsh -e "CREATE KEYSPACE testing WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 1};"
docker-compose exec scylla cqlsh -e "CREATE TABLE testing.example (NAME TEXT PRIMARY KEY, SURNAME TEXT);"
docker-compose exec scylla cqlsh -e "DESC SCHEMA;"
