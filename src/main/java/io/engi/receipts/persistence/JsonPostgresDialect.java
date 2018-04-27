package io.engi.receipts.persistence;

import org.hibernate.dialect.PostgreSQL9Dialect;

import java.sql.Types;

public class JsonPostgresDialect extends PostgreSQL9Dialect {

    public JsonPostgresDialect() {

        super();

        this.registerColumnType(Types.JAVA_OBJECT, "json");
    }
}