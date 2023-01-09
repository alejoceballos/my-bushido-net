package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V1_0_1__Create_person_and_company_tables extends BaseJavaMigration {

    // Person table & audit

    private static final String DDL_PERSON_STMT = """
            CREATE TABLE person (
              id          UUID        NOT NULL
            , first_name  VARCHAR(32) NOT NULL
            , middle_name VARCHAR(255)
            , last_name   VARCHAR(35) NOT NULL
            , CONSTRAINT person_pk
                PRIMARY KEY (id)
            , CONSTRAINT person_contact_fk
                FOREIGN KEY (id)
                    REFERENCES contact(id))
            """;

    private static final String DDL_PERSON_AUD_STMT = """
            CREATE TABLE person_aud (
              id          UUID    NOT NULL
            , rev         INTEGER NOT NULL
            , first_name  VARCHAR(32)
            , middle_name VARCHAR(255)
            , last_name   VARCHAR(35)
            , CONSTRAINT person_aud_pk
                PRIMARY KEY (id, rev)
            , CONSTRAINT person_aud_rev_fk
                FOREIGN KEY (rev)
                    REFERENCES audit_revision_info(id))
            """;

    // Company table & audit

    private static final String DDL_COMPANY_STMT = """
            CREATE TABLE company (
              id           UUID         NOT NULL
            , company_name VARCHAR(255) NOT NULL
            , trading_name VARCHAR(255) NOT NULL
            , CONSTRAINT company_pk
                PRIMARY KEY (id)
            , CONSTRAINT company_fk
                FOREIGN KEY (id)
                    REFERENCES contact(id))
            """;

    private static final String DDL_COMPANY_AUD_STMT = """
            CREATE TABLE company_aud (
              id           UUID         NOT NULL
            , rev          INTEGER      NOT NULL
            , company_name VARCHAR(255)
            , trading_name VARCHAR(255)
            , CONSTRAINT company_aud_pk
                PRIMARY KEY (rev, id)
            , CONSTRAINT company_aud_fk
                FOREIGN KEY (id)
                    REFERENCES contact(id))
            """;

    @Override
    public void migrate(Context context) throws Exception {
        try (final var statement = context.getConnection().createStatement()) {
            statement.execute(DDL_PERSON_STMT);
            statement.execute(DDL_PERSON_AUD_STMT);

            statement.execute(DDL_COMPANY_STMT);
            statement.execute(DDL_COMPANY_AUD_STMT);
        }
    }

}
