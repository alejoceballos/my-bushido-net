package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V1_0_2__Create_document_tables extends BaseJavaMigration {

    private static final String DDL_DOCUMENT_STMT = """
            CREATE TABLE document (
              id          UUID         NOT NULL
            , contact_id  UUID
            , type        VARCHAR(255) NOT NULL
            , number      VARCHAR(255) NOT NULL
            , description VARCHAR(255)
            , country     VARCHAR(56)  NOT NULL
            , CONSTRAINT document_pk
                PRIMARY KEY (id)
            , CONSTRAINT document_uk
                UNIQUE (contact_id, type)
            , CONSTRAINT document_contact_fk
                FOREIGN KEY (contact_id)
                    REFERENCES contact(id))
            """;

    private static final String DDL_DOCUMENT_AUD_STMT = """
            CREATE TABLE document_aud (
              id                  UUID    NOT NULL
            , rev                 INTEGER NOT NULL
            , revtype             SMALLINT
            , type        VARCHAR(255)
            , number      VARCHAR(255)
            , description VARCHAR(255)
            , country     VARCHAR(56)
            , CONSTRAINT document_aud_pk
                PRIMARY KEY (id, rev)
            , CONSTRAINT document_aud_rev_fk
                FOREIGN KEY (rev)
                    REFERENCES audit_revision_info(id))
            """;

    private static final String DDL_CONTACT_DOCUMENT_AUD_STMT = """
            CREATE TABLE contact_document_aud (
              id         UUID    NOT NULL
            , contact_id UUID    NOT NULL
            , rev        INTEGER NOT NULL
            , revtype    SMALLINT)
            """;

    @Override
    public void migrate(Context context) throws Exception {
        try (final var statement = context.getConnection().createStatement()) {
            statement.execute(DDL_DOCUMENT_STMT);
            statement.execute(DDL_DOCUMENT_AUD_STMT);
            statement.execute(DDL_CONTACT_DOCUMENT_AUD_STMT);
        }
    }

}
