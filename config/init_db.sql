CREATE TABLE resume
(
    uuid      CHAR(36) NOT NULL
        CONSTRAINT resume_pk
            PRIMARY KEY,
    full_name TEXT     NOT NULL
);

alter table resume
    owner to postgres;

CREATE TABLE IF NOT EXISTS contact
(
    id          SERIAL   NOT NULL
        CONSTRAINT contact_pk
            PRIMARY KEY,
    resume_uuid CHAR(36) NOT NULL
        CONSTRAINT contact_resume_uuid_fk
            REFERENCES resume
            ON UPDATE RESTRICT ON DELETE CASCADE,
    contact_type        TEXT     NOT NULL,
    value       TEXT     NOT NULL
);

CREATE UNIQUE INDEX contact_uuid_type_index
    ON contact (resume_uuid, contact_type);

CREATE TABLE IF NOT EXISTS section
(
    id          SERIAL   NOT NULL PRIMARY KEY,
    resume_uuid CHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    section_type        TEXT     NOT NULL,
    content     TEXT     NOT NULL

);

CREATE UNIQUE INDEX section_uuid_type_index
    ON section (resume_uuid, section_type);

