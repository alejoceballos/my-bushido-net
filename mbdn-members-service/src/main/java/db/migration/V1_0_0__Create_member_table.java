package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V1_0_0__Create_member_table extends BaseJavaMigration {

    private static final String DDL_MEMBER_STMT = """
            CREATE TABLE IF NOT EXISTS member (
              id             UUID         NOT NULL
            , nickname       VARCHAR(20) NOT NULL
            , avatar         BYTEA
            , contact_ref_id UUID
            , name           VARCHAR(255)
            , phone          VARCHAR(15)
            , address        VARCHAR(1000)
            , email          VARCHAR(320)
            , birth_date     DATE
            , CONSTRAINT member_pk PRIMARY KEY (id))
            """;

    private static final String DDL_AUDIT_STMT = """
            CREATE TABLE IF NOT EXISTS member_aud (
              id             UUID         NOT NULL
            , rev            INTEGER      NOT NULL
            , revtype        SMALLINT
            , nickname       VARCHAR(20)
            , avatar         BYTEA
            , contact_ref_id UUID
            , name           VARCHAR(255)
            , phone          VARCHAR(15)
            , address        VARCHAR(1000)
            , email          VARCHAR(320)
            , birth_date     DATE
            , CONSTRAINT member_aud_pk PRIMARY KEY (id, rev)
            , CONSTRAINT member_aud_rev_fk FOREIGN KEY (rev) 
                REFERENCES audit_revision_info(id))
            """;

    @Override
    public void migrate(Context context) throws Exception {
        try (final var statement = context.getConnection().createStatement()) {
            statement.execute(DDL_MEMBER_STMT);
            statement.execute(DDL_AUDIT_STMT);
        }
    }

}
