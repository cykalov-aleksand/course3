package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.TelegramRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BotService {
    private final TelegramRepository telegramRepository;
    private final TelegramBot telegramBot;

    @Autowired
    public BotService(TelegramRepository telegramRepository, TelegramBot telegramBot) {
        this.telegramRepository = telegramRepository;
        this.telegramBot = telegramBot;
    }

    private Logger logger = LoggerFactory.getLogger(BotService.class);
//Отправляем сообщение
    public void sendingMessage(Long chatId, String string) {
        SendMessage message = new SendMessage(String.valueOf(chatId), string);
        controlSendingControl(telegramBot.execute(message));
    }

    public void processingMessages(Long chatId, String messageText) {
        Pattern pattern = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        Matcher matcher = pattern.matcher(messageText);
        // сортируем сообщение + дату
        if (matcher.matches()) {
            String data = matcher.group(1);
            String item = matcher.group(3);
            setSql(chatId, item, data, LocalDateTime.now().format(formatter));
            logger.info("Сообщение \" {}  \" отправлено с датой {} ",item, data);
        }else {
            setSql(chatId, messageText, "", LocalDateTime.now().format(formatter));
            logger.info("Сообщение \" {} \" отправлено без даты", messageText);
        }
    }

    @Scheduled(cron = "0/59 * * * * ?")
    public void executeTask() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        List<NotificationTask> listObjectEqualsDate = telegramRepository.getFindTime(LocalDateTime.now()
                .truncatedTo(ChronoUnit.MINUTES).format(formatter));
        //если есть сообщение с текущей датой и временем отправляем боту сообщение
        if (listObjectEqualsDate != null) {
            for (NotificationTask variable : listObjectEqualsDate) {
                sendingMessage(variable.getChatId(), variable.getTextNotification());
            }
        }
    }
    //записываем данные сообщения в базу данных
    private void setSql(Long chatId, String textMessage, String dateTimeNotatification, String dateTimesDepartures) {
        NotificationTask sql = new NotificationTask(chatId, textMessage, dateTimeNotatification, dateTimesDepartures);
        telegramRepository.save(sql);
    }

    private void controlSendingControl(SendResponse sendResponse) {
        if (sendResponse.isOk()) {
            logger.info("Сообщение отправлено");
            return;
        }
        logger.info("Ошибка сообщение не отправлено");
    }
}
