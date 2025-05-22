package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.sky.telegrambot.model.NotificationTask;

import java.util.List;

public interface TelegramRepository extends JpaRepository<NotificationTask,Long> {
    @Query(value = "SELECT * FROM notification_task WHERE date_time_notification LIKE %:dateTimeNotification%", nativeQuery = true)
    List<NotificationTask> getFindTime(@Param("dateTimeNotification") String dateTimeNotification);
   }
