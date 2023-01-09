package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V0_0_0__Create_audit_rev_info_table extends BaseJavaMigration {

    private static final String DDL_AUDIT_REVISION_INFO_STMT = """
            CREATE TABLE audit_revision_info (
              id        INTEGER NOT NULL
            , timestamp BIGINT  NOT NULL
            , username  VARCHAR(255)
            , CONSTRAINT audit_revision_info_pk PRIMARY KEY (id))
            """;

    private static final String DDL_AUDIT_REVISION_INFO_SEQ_STMT = """
            CREATE SEQUENCE audit_revision_info_seq
            INCREMENT BY 50
            OWNED BY audit_revision_info.id;
            """;

    @Override
    public void migrate(Context context) throws Exception {
        try (final var statement = context.getConnection().createStatement()) {
            statement.execute(DDL_AUDIT_REVISION_INFO_STMT);
            statement.execute(DDL_AUDIT_REVISION_INFO_SEQ_STMT);
        }
    }

}
