package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.util.UUID;

import static com.momo2x.mbdn.members.constant.DefaultValueConstants.ADMIN_ID;
import static com.momo2x.mbdn.members.constant.DefaultValueConstants.AVATAR_DESCRIPTION;

/**
 * Migrate data from already inserted data to the new image holding table.
 */
public class V1_1_1__Migrate_avatar_data extends BaseJavaMigration {

    private static final String DML_SELECT_MEMBER = """
            SELECT id, nickname, avatar FROM member
            """;

    private static final String DML_INSERT_AVATAR = """
            INSERT INTO member_avatar(id, description, image) VALUES (?, ?, ?)
            """;

    private static final String DML_UPDATE_AVATAR = """
            UPDATE member_avatar SET description = ? WHERE id = ?;
            """;

    @Override
    public void migrate(Context context) throws Exception {
        try (final var queryStmt = context.getConnection().createStatement()) {
            final var rs = queryStmt.executeQuery(DML_SELECT_MEMBER);

            try (final var insertStmt = context.getConnection().prepareStatement(DML_INSERT_AVATAR)) {
                while (rs.next()) {
                    insertStmt.setObject(1, rs.getObject("id", UUID.class));
                    insertStmt.setString(2, rs.getString("nickname"));
                    insertStmt.setBytes(3, rs.getBytes("avatar"));
                    insertStmt.addBatch();
                }

                insertStmt.executeBatch();
            }

            try (final var updateStmt = context.getConnection().prepareStatement(DML_UPDATE_AVATAR)) {
                updateStmt.setString(1, AVATAR_DESCRIPTION);
                updateStmt.setObject(2, ADMIN_ID);

                updateStmt.execute();
            }
        }
    }

}
