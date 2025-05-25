package pro.sky.telegrambot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class NotificationTask {
    @javax.persistence.Id
    @GeneratedValue
    private Long Id;
    private Long chatId;
    private String textNotification;
    private LocalDateTime dateTimeNotification;
    private LocalDateTime dateTimeDepartures;
    public NotificationTask(){}

    public long getChatId() {
        return chatId;
    }

    public NotificationTask(Long chatId, String textNotification, LocalDateTime dateTimeNotification, LocalDateTime dateTimeDepartures) {
        this.chatId = chatId;
        this.textNotification = textNotification;
        this.dateTimeNotification = dateTimeNotification;
        this.dateTimeDepartures = dateTimeDepartures;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getTextNotification() {
        return textNotification;
    }

    public void setTextNotification(String textNotification) {
        this.textNotification = textNotification;
    }

    public LocalDateTime getDateTimeNotification() {
        return dateTimeNotification;
    }

    public void setDateTimeNotification(LocalDateTime dateTimeNotification) {
        this.dateTimeNotification = dateTimeNotification;
    }

    public LocalDateTime getDateTimeDepartures() {
        return dateTimeDepartures;
    }

    public void setDateTimeDepartures(LocalDateTime dateTimeDepartures) {
        this.dateTimeDepartures = dateTimeDepartures;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof NotificationTask)) return false;
        NotificationTask that = (NotificationTask) object;
        return chatId == that.chatId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(chatId);
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "Id=" + Id +
                ", chatId=" + chatId +
                ", textNotification='" + textNotification + '\'' +
                ", dateTimeNotification='" + dateTimeNotification + '\'' +
                ", dateTimeDepartures='" + dateTimeDepartures + '\'' +
                '}';
    }
}

