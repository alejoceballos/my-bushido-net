package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V1_1_3__Update_avatar_reference extends BaseJavaMigration {

    private static final String DML_MEMBER_UPDATE = """
            UPDATE member
            SET avatar_id = id
            """;

    private static final String DML_AUD_UPDATE = """
            UPDATE member_aud
            SET avatar_id = id
            """;

    @Override
    public void migrate(Context context) throws Exception {
        try (final var updateStmt = context.getConnection().createStatement()) {
            updateStmt.executeUpdate(DML_MEMBER_UPDATE);
            updateStmt.executeUpdate(DML_AUD_UPDATE);
        }
    }

}
