-- liquibase formatted sql

-- changeset acycalov:1
CREATE TABLE notification_task(
    Id SERIAL PRIMARY KEY,
    Chat_Id BIGSERIAL,
    Text_Notification TEXT,
    Date_Time_Notification TIMESTAMP,
    Date_Time_Departures TIMESTAMP
);