package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

/**
 * Change main table to reference the new one.
 */
public class V1_1_2__Alter_member_avatar_column extends BaseJavaMigration {

    private static final String DDL_MEMBER_STMT = """
            ALTER TABLE member
              ADD COLUMN avatar_id UUID
            , DROP COLUMN avatar
            , ADD CONSTRAINT member_avatar_fk FOREIGN KEY(avatar_id) 
                REFERENCES member_avatar(id)
            """;


    private static final String DDL_AUDIT_STMT = """
            ALTER TABLE member_aud
              ADD COLUMN avatar_id UUID
            , DROP COLUMN avatar
            """;

    @Override
    public void migrate(Context context) throws Exception {
        try (final var statement = context.getConnection().createStatement()) {
            statement.execute(DDL_MEMBER_STMT);
            statement.execute(DDL_AUDIT_STMT);
        }
    }

}
