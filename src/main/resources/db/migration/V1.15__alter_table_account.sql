ALTER TABLE account
    ADD COLUMN current_balance double DEFAULT 0,
    ADD COLUMN debt double DEFAULT 0;