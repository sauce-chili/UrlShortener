ALTER TABLE url RENAME COLUMN hashId TO hash_id;

ALTER TABLE url DROP COLUMN requested_at;
