package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.service.BotService;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final BotService botService;
    private final TelegramBot telegramBot;

    @Autowired
    public TelegramBotUpdatesListener(BotService botService, TelegramBot telegramBot) {
        this.botService = botService;
        this.telegramBot = telegramBot;
    }

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.debug("Processing update: {}", update);
            String messageText;
            Long chatId = update.message().chat().id();
            if (update.message() != null && update.message().text() != null) {
                messageText = update.message().text();
                if (messageText.startsWith("/start")) {
                    botService.sendingMessage(chatId, "Привет! Я твой бот.");
                }else {
                    botService.processingMessages(chatId, messageText);
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
