package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

/**
 * Create first version tables and its audit tables. The audit tables must reference the revision table.
 */
public class V1_0_0__Create_contact_tables extends BaseJavaMigration {

    private static final String DDL_CONTACT_STMT = """
            CREATE TABLE contact (
              id                  UUID NOT NULL
            , email               VARCHAR(320)
            , birth_date          DATE
            , reference_contact   VARCHAR(1000)
            , photo               BYTEA
            , street              VARCHAR(85)
            , street_number       VARCHAR(10)
            , address_complement1 VARCHAR(255)
            , address_complement2 VARCHAR(255)
            , city                VARCHAR(176)
            , country             VARCHAR(56)
            , zip_code            VARCHAR(11)
            , CONSTRAINT contact_pk
                PRIMARY KEY (id))
            """;

    private static final String DDL_CONTACT_AUD_STMT = """
            CREATE TABLE contact_aud (
              id                  UUID    NOT NULL
            , rev                 INTEGER NOT NULL
            , revtype             SMALLINT
            , email               VARCHAR(320)
            , birth_date          DATE
            , reference_contact   VARCHAR(1000)
            , photo               BYTEA
            , street              VARCHAR(85)
            , street_number       VARCHAR(10)
            , address_complement1 VARCHAR(255)
            , address_complement2 VARCHAR(255)
            , city                VARCHAR(176)
            , country             VARCHAR(56)
            , zip_code            VARCHAR(11)
            , CONSTRAINT contact_aud_pk
                PRIMARY KEY (id, rev)
            , CONSTRAINT contact_aud_rev_fk
                FOREIGN KEY (rev)
                    REFERENCES audit_revision_info(id))
            """;

    @Override
    public void migrate(Context context) throws Exception {
        try (final var statement = context.getConnection().createStatement()) {
            statement.execute(DDL_CONTACT_STMT);
            statement.execute(DDL_CONTACT_AUD_STMT);
        }
    }

}
