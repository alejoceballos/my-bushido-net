package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;

import static com.momo2x.mbdn.contacts.constant.DefaultValueConstants.*;

public class V1_0_4__Initial_contact_data extends BaseJavaMigration {

    private static final String DML_INSERT_CONTACT_STMT = """
            INSERT INTO contact (id, birth_date, photo)
            VALUES(?, ?, ?)
            """;

    private static final String DML_INSERT_PERSON_STMT = """
            INSERT INTO person (id, first_name, middle_name, last_name)
            VALUES(?, ?, ?, ?)
            """;

    @Override
    public void migrate(Context context) throws Exception {
        try (final var statement = context.getConnection().prepareStatement(DML_INSERT_CONTACT_STMT)) {
            final var image = ResourceUtils.getFile(ADMIN_PHOTO_FILE);

            try (final var inputStream = new FileInputStream(image)) {
                statement.setObject(1, ADMIN_ID);
                statement.setDate(2, new java.sql.Date(ADMIN_BIRTH_DATE.getTime()));
                statement.setBinaryStream(3, inputStream, (int) image.length());

                statement.execute();
            }
        }

        try (final var statement = context.getConnection().prepareStatement(DML_INSERT_PERSON_STMT)) {
            final var image = ResourceUtils.getFile(ADMIN_PHOTO_FILE);

            try (final var inputStream = new FileInputStream(image)) {
                statement.setObject(1, ADMIN_ID);
                statement.setString(2, ADMIN_FIRST_NAME);
                statement.setString(3, ADMIN_MIDDLE_NAME);
                statement.setString(4, ADMIN_LAST_NAME);

                statement.execute();
            }
        }
    }

}
