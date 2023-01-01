package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V1_1_0__Create_avatar_table extends BaseJavaMigration {

    private static final String DDL_AVATAR_STMT = """
            CREATE TABLE IF NOT EXISTS member_avatar (
              id          UUID         NOT NULL
            , description VARCHAR(100) NOT NULL
            , image       BYTEA        NOT NULL
            , CONSTRAINT member_avatar_pk PRIMARY KEY (id))
            """;

    private static final String DDL_AUDIT_STMT = """
            CREATE TABLE IF NOT EXISTS member_avatar_aud (
              id          UUID         NOT NULL
            , rev         INTEGER      NOT NULL
            , revtype     SMALLINT
            , description VARCHAR(100)
            , image       BYTEA
            , CONSTRAINT member_avatar_aud_pk PRIMARY KEY (id, rev)
            , CONSTRAINT member_avatar_aud_rev_fk FOREIGN KEY (rev) 
                REFERENCES audit_revision_info(id))
            """;


    @Override
    public void migrate(Context context) throws Exception {
        try (final var statement = context.getConnection().createStatement()) {
            statement.execute(DDL_AVATAR_STMT);
            statement.execute(DDL_AUDIT_STMT);
        }
    }

}
