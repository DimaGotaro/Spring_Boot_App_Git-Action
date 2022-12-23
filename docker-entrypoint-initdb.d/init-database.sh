#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER postgres;
    CREATE DATABASE kacher;
    GRANT ALL PRIVILEGES ON DATABASE kacher TO postgres;
    CREATE DATABASE public;
    GRANT ALL PRIVILEGES ON DATABASE public TO postgres;
EOSQL