package com.homihq.manager.core.dialect;

import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import org.hibernate.dialect.PostgreSQL10Dialect;

import java.sql.Types;

public class PostgresSQL10JsonDialect extends PostgreSQL10Dialect {
    public PostgresSQL10JsonDialect() {
        super();
        this.registerHibernateType(
                Types.OTHER, JsonNodeBinaryType.class.getName()
        );
    }
}
