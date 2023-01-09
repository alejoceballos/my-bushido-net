package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V1_0_3__Create_phone_number_tables extends BaseJavaMigration {

    private static final String DDL_PHONE_NUMBER_STMT = """
            CREATE TABLE phone_number (
              id           UUID         NOT NULL
            , contact_id   UUID
            , country_code VARCHAR(3)
            , area_code    VARCHAR(3)
            , number       VARCHAR(255) NOT NULL
            , main         BOOLEAN      NOT NULL DEFAULT FALSE
            , CONSTRAINT phone_number_pk
                PRIMARY KEY (id)
            , CONSTRAINT phone_number_uk
                UNIQUE (contact_id, number)
            , CONSTRAINT phone_number_contact_fk
                FOREIGN KEY (contact_id)
                    REFERENCES contact(id))
            """;

    private static final String DDL_PHONE_NUMBER_AUD_STMT = """
            CREATE TABLE phone_number_aud (
              id                  UUID    NOT NULL
            , rev                 INTEGER NOT NULL
            , revtype             SMALLINT
            , country_code VARCHAR(3)
            , area_code    VARCHAR(3)
            , number       VARCHAR(255)
            , main         BOOLEAN
            , CONSTRAINT phone_number_aud_pk
                PRIMARY KEY (id, rev)
            , CONSTRAINT phone_number_aud_rev_fk
                FOREIGN KEY (rev)
                    REFERENCES audit_revision_info(id))
            """;


    private static final String DDL_CONTACT_PHONE_NUMBER_AUD_STMT = """
            CREATE TABLE contact_phone_number_aud (
              id         UUID    NOT NULL
            , contact_id UUID    NOT NULL
            , rev        INTEGER NOT NULL
            , revtype    SMALLINT)
            """;


    @Override
    public void migrate(Context context) throws Exception {
        try (final var statement = context.getConnection().createStatement()) {
            statement.execute(DDL_PHONE_NUMBER_STMT);
            statement.execute(DDL_PHONE_NUMBER_AUD_STMT);
            statement.execute(DDL_CONTACT_PHONE_NUMBER_AUD_STMT);
        }
    }

}
