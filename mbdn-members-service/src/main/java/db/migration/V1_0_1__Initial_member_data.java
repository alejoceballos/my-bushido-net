package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;

import static com.momo2x.mbdn.members.constant.DefaultValueConstants.ADMIN_BIRTH_DATE;
import static com.momo2x.mbdn.members.constant.DefaultValueConstants.ADMIN_ID;
import static com.momo2x.mbdn.members.constant.DefaultValueConstants.ADMIN_NAME;
import static com.momo2x.mbdn.members.constant.DefaultValueConstants.ADMIN_NICKNAME;
import static com.momo2x.mbdn.members.constant.DefaultValueConstants.AVATAR_FILE;

public class V1_0_1__Initial_member_data extends BaseJavaMigration {

    private static final String DML_STMT = """
            INSERT INTO member (id, nickname, avatar, contact_ref_id, name, birth_date)
            VALUES(?, ?, ?, ?, ?, ?)
            """;

    @Override
    public void migrate(Context context) throws Exception {
        try (final var statement = context.getConnection().prepareStatement(DML_STMT)) {
            final var image = ResourceUtils.getFile(AVATAR_FILE);

            try (final var inputStream = new FileInputStream(image)) {
                statement.setBinaryStream(3, inputStream, (int) image.length());
                statement.setObject(1, ADMIN_ID);
                statement.setString(2, ADMIN_NICKNAME);
                statement.setObject(4, ADMIN_ID);
                statement.setString(5, ADMIN_NAME);
                statement.setDate(6, new java.sql.Date(ADMIN_BIRTH_DATE.getTime()));

                statement.execute();
            }
        }
    }

}
