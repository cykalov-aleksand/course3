-- liquibase formatted sql

-- changeset acycalov:1
CREATE TABLE notification_task(
    id SERIAL PRIMARY KEY,
    chat_id BIGSERIAL,
    text_notification TEXT,
    date_time_notification TIMESTAMP,
    date_time_departures TIMESTAMP
);