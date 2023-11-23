package org.example;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.*;

public class Bot extends TelegramLongPollingBot {

    String botToken;

    String botUserName;

    String operatorNikName;

    int countLive = 5;


    Map<Long, TelegramUser> usersMap = new HashMap<>();

    List<String> keyList = new ArrayList<>(Arrays.asList("dsdasdwvcvfvf", "Aviator1745", "Aviator2752", "Aviator3769", "Aviator4774", "Aviator5782", "Aviator6791", "Aviator7797"));

    public Bot(String botToken, String botUserName, String operatorNikName) {
        this.botToken = botToken;
        this.botUserName = botUserName;
        this.operatorNikName = operatorNikName;
    }

    @Override
    public String getBotUsername() {
        return botUserName; // Метод, который возвращает username бота.
    }

    @Override
    public String getBotToken() {
        return botToken; // Метод, который возвращает token бота.
    }

    // Метод для обработки команды "Старт" и отправки сообщения с кнопкой
    private void handleStartCommand(Message message) {
        long chatId = message.getChatId();
        if (!usersMap.containsKey(chatId)) {
            usersMap.put(chatId, new TelegramUser(chatId));
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Статус: Не активен❌\nДля активации свяжитесь со своим менеджером.");

            // Создаем клавиатуру
            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();

            // Создаем кнопку
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText("Актиация");
            inlineKeyboardButton.setUrl(operatorNikName);

            rowInline.add(inlineKeyboardButton);

            // Добавляем кнопку в строку и строку в клавиатуру
            rowsInline.add(rowInline);
            markupInline.setKeyboard(rowsInline);

            // Добавляем клавиатуру к сообщению
            sendMessage.setReplyMarkup(markupInline);

            try {
                execute(sendMessage); // Отправляем сообщение
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else { // неверный код активации

            if (usersMap.get(chatId).toplivo > 0) { // если перезашел и сного нажал старт
                radyVnovViddetVas(message);
                return;
            }

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Статус: Не активен❌\nДля активации свяжитесь со своим менеджером.");

            // Создаем клавиатуру
            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();

            // Создаем кнопку
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText("Активация");
            inlineKeyboardButton.setUrl(operatorNikName);

            rowInline.add(inlineKeyboardButton);

            // Добавляем кнопку в строку и строку в клавиатуру
            rowsInline.add(rowInline);
            markupInline.setKeyboard(rowsInline);

            // Добавляем клавиатуру к сообщению
            sendMessage.setReplyMarkup(markupInline);

            try {
                execute(sendMessage); // Отправляем сообщение
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }


        }
    }

    public void onUpdateReceived(Update update) {

        // Проверяем, есть ли callback query в обновлении
        if (update.hasCallbackQuery()) {
            String callData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if ("poluchitCef".equals(callData)) {                                           //todo пытаемся выдать кэф

                TelegramUser igrok = usersMap.get(chatId);
                Message message = update.getMessage();

                if (igrok.toplivo > 0) {
                    igrok.toplivo--;

                    vidatButtonDlyPoluchitCefyclychnyiMetod(chatId, igrok);    //Oтправвка кефа
                } else {
                    noMany(chatId);
                }


            }
        } else if (update.hasMessage()) {
            Message message = update.getMessage();
            String poleText = message.getText();
            Long chatId = message.getChatId();
            TelegramUser igrok = usersMap.get(chatId);


            if (poleText.equals("/start")) {
                handleStartCommand(message);
                startActivationTimer(chatId);

            } else if (poleText.matches("3\\d{5}") && igrok.cykl == 1&&!igrok.activate) {
                // логика новичка
                igrok.activate = true;

                igrok.toplivo = igrok.toplivo + countLive;

                vidatButtonDlyPoluchitCef(message);

            } else if (slovoPodoshlo(chatId, poleText)) { // проверка на правельный ключ
                usersMap.get(chatId).toplivo = usersMap.get(chatId).toplivo + countLive;
                vidatButtonDlyPoluchitCefNewKey(message); //todo доступ возобнавлен новый метод


            }else if(igrok.cykl>1 || igrok.toplivo == 0){
               sendMesageFinishNo(chatId);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                noMany(chatId);
            } else if(poleText.equals("soutprint")) {
                try {
                    Thread.sleep(900000000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }else {
                handleStartCommand(message);
                startActivationTimer(chatId); // повтор старт логики
            }
        }
    }



    private void vidatButtonDlyPoluchitCef(Message message) {
        long chatId = message.getChatId();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Активация прошла успешно✅\n\nДля того что бы запросить сигнал нажмите\nна кнопку - получить кеф\uD83D\uDE80.\nУдачной игры\uD83E\uDD11");

        // Создаем клавиатуру
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("Получить кэф\uD83D\uDE80");
        inlineKeyboardButton.setCallbackData("poluchitCef"); // Установка callback_data


        rowInline.add(inlineKeyboardButton);

        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        sendMessage.setReplyMarkup(markupInline);

        try {
            execute(sendMessage); // Отправляем сообщение
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }



    private void vidatButtonDlyPoluchitCefNewKey(Message message) {
        long chatId = message.getChatId();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Доступ возобновлен✅\n\nДля того что бы запросить сигнал нажмте\n на кнопку - получить кеф\uD83D\uDE80.\nУдачной игры\uD83E\uDD11");

        // Создаем клавиатуру
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("Получить кэф\uD83D\uDE80");
        inlineKeyboardButton.setCallbackData("poluchitCef"); // Установка callback_data
        rowInline.add(inlineKeyboardButton);

        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        sendMessage.setReplyMarkup(markupInline);

        try {
            execute(sendMessage); // Отправляем сообщение
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }


    private void vidatButtonDlyPoluchitCefyclychnyiMetod(Long chatId, TelegramUser igrok) {


        Double cef = igrok.getCef();


        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("ВАШ КОФИЦИЕНТ" + cef);  //Oтправвка кефа


        // Создаем клавиатуру
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("Получить кэф\uD83D\uDE80");
        inlineKeyboardButton.setCallbackData("poluchitCef"); // Установка callback_data
        rowInline.add(inlineKeyboardButton);

        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        sendMessage.setReplyMarkup(markupInline);

        try {
            execute(sendMessage); // Отправляем сообщение
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }


    // Метод для обработки команды "Старт" и отправки сообщения с кнопкой
    private void noMany(Long chatId) {

//        long chatId = message.getChatId();
        if (usersMap.get(chatId).toplivo <= 0) {

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("ОШИБКА\uD83D\uDEA8 \n\n Статус нехватка баланса\n\n<b>Пополните свой баланс и свяжитесь\nсо своим Менеджером.</b>");
            sendMessage.enableHtml(true);

            // Создаем клавиатуру
            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();

            // Создаем кнопку
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText("Связь с менеджером");
            inlineKeyboardButton.setUrl("https://t.me/vladimirai2023");

            rowInline.add(inlineKeyboardButton);

            // Добавляем кнопку в строку и строку в клавиатуру
            rowsInline.add(rowInline);
            markupInline.setKeyboard(rowsInline);

            // Добавляем клавиатуру к сообщению
            sendMessage.setReplyMarkup(markupInline);

            startActivationTimer(chatId); // выводим сообщение через минуту ???
            try {
                execute(sendMessage); // Отправляем сообщение
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


    public boolean slovoPodoshlo(Long chatId, String slovoVveddeno) {
        TelegramUser igrok = usersMap.get(chatId);
        int cykl = igrok.cykl;
        String dolghenBytKey = keyList.get(cykl);


        boolean res = slovoVveddeno.equals(dolghenBytKey);

        if (res) {
            igrok.cykl++; // todo Выдаст тру на ето слово только один раз
        }

        return res;
    }


    private void radyVnovViddetVas(Message message) {
        long chatId = message.getChatId();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Рады видеть Вас вновь\nДля того что бы получить сигнал нажмте на кнопку - получить кеф.");

        // Создаем клавиатуру
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("Получить кэф\uD83D\uDE80");
        inlineKeyboardButton.setCallbackData("poluchitCef"); // Установка callback_data
        rowInline.add(inlineKeyboardButton);

        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        sendMessage.setReplyMarkup(markupInline);

        try {
            execute(sendMessage); // Отправляем сообщение
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }


    //@todo Метод для отправки сообщения пользователю доделать..
    public void sendMessage(Long chatId, String messageText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(messageText);
        sendMessage.setChatId(chatId);
        try {
            executeAsync(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Метод для обработки введенного ID
    private void handleIdInput(Message message) {
        String text = message.getText();
        // Проверка ID на корректность
        if (text.matches("3\\d{5}")) {
            // ID корректен, активация пользователя
            long chatId = message.getChatId();
            TelegramUser user = usersMap.get(chatId);
            // ... логика активации ...
        } else {
            // ID некорректен, сообщение об ошибке
            // ... отправить сообщение об ошибке ...
        }
    }



    public void sendError(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Ошика доступа обратитесь к Администраторам ");
        sendMessage.setChatId(chatId);
        try {
            executeAsync(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private void startActivationTimer(long chatId) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                sendActivationCodeRequest(chatId);
            }
        }, 6 * 1000); // Задержка в 60 000 миллисекунд (1 минута)
//        }, 60 * 1000); // Задержка в 60 000 миллисекунд (1 минута)
    }

    private void sendActivationCodeRequest(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Введите ваш код активации:");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private void sendMesageFinishNo(Long chatId) {



            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Что-то пошло не так\uD83D\uDE14 \n\n Скорее всего вы не связались со своим\nменедером.Напишите менеджеру \n'АКТИВАЦИЯ' и после его разрешения\n попробуйте снова");


            try {
                execute(sendMessage); // Отправляем сообщение
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

    }

}


//    public void sendArrayDataToAll(String[] dataArray) {
////        System.out.println("Delaetsya sendArrayDataToAll");
//
//        List<Long> allowedChatIds = getAllowedChatIds(); // Получаем список разрешенных chatId
//
////        System.out.println("Allowed chatIds: " + allowedChatIds);
//
//        for (Long chatId : allowedChatIds) {
////            System.out.println("Sending data to chatId: " + chatId);
//            sendArrayData(chatId, dataArray); // Отправляем массив строк каждому пользователю по chatId
//        }
//    }


//    // Метод для получения списка всех разрешенных chatId
//    private static List<Long> getAllowedChatIds() {
////        System.out.println("getAllowedChatIds");
//        List<Long> allowedChatIds = new ArrayList<>(baseUsers.keySet());
//        return allowedChatIds;
//    }
//
//
//    public void sendOk(Long chatId) {
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setText("Вы успешно подписаны на бота");
//        sendMessage.setChatId(chatId);
//        try {
//            executeAsync(sendMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }

//    InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
//    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
//    List<InlineKeyboardButton> rowInline = new ArrayList<>();
//
//    InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
//inlineKeyboardButton.setText("Активация");
//        inlineKeyboardButton.setCallbackData("activate"); // Установка callback_data
//        rowInline.add(inlineKeyboardButton);
//
//        rowsInline.add(rowInline);
//        markupInline.setKeyboard(rowsInline);
//        sendMessage.setReplyMarkup(markupInline);


//
//    @Override
//    public void onUpdateReceived(Update update) {
//        if (update.hasCallbackQuery()) {
//            // Получаем данные callback_query
//            String callData = update.getCallbackQuery().getData();
//            long chatId = update.getCallbackQuery().getMessage().getChatId();
//
//            if ("poluchitCef".equals(callData)) {
//                // Пользователь нажал на кнопку "Получить кеф"
//                // Обрабатываем нажатие
//                handlePoluchitCef(chatId);
//            }
//        } else if (update.hasMessage()) {
//            Message message = update.getMessage();
//            // ... Обработка других сообщений ...
//        }
//    }
//
//    private void handlePoluchitCef(long chatId) {
//        // Логика для выдачи кефа пользователю
//        // Например, проверка, достаточно ли у пользователя топлива и т.д.
//        // ...
//    }



// кнокп ина весь экран снизу
//    public void vidatButtonDlyPoluchitCef(Message message) {
//        long chatId = message.getChatId();
//
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(chatId);
//        sendMessage.setText("Активация прошла успешно✅\n\nДля того что бы запросить сигнал нажмите на кнопку - получить кэф\uD83D\uDE80.\nУдачной игры\uD83E\uDD11");
//
//        // Создаем клавиатуру
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        List<KeyboardRow> keyboard = new ArrayList<>();
//        KeyboardRow row = new KeyboardRow();
//
//        // Создаем кнопку
//        KeyboardButton keyboardButton = new KeyboardButton("11Получить кэф\uD83D\uDE80");
//
//        row.add(keyboardButton);
//        keyboard.add(row);
//
//        replyKeyboardMarkup.setKeyboard(keyboard);
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//
//        try {
//            execute(sendMessage); // Отправляем сообщение
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }



//регулировка по ширене
//    private void vidatButtonDlyPoluchitCef(Message message) {
//        long chatId = message.getChatId();
//
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(chatId);
//        sendMessage.setText("Активация прошла успешно✅\n\nДля того что бы запросить сигнал нажмите\u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B \u200B на кнопку - получить кеф\uD83D\uDE80.\nУдачной игры\uD83E\uDD11");
//
//        // Создаем клавиатуру
//        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
//        List<InlineKeyboardButton> rowInline = new ArrayList<>();
//
//        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
//        inlineKeyboardButton.setText("Получить кэф\uD83D\uDE80");
//        inlineKeyboardButton.setCallbackData("poluchitCef"); // Установка callback_data
//
//
//        rowInline.add(inlineKeyboardButton);
//
//        rowsInline.add(rowInline);
//        markupInline.setKeyboard(rowsInline);
//        sendMessage.setReplyMarkup(markupInline);
//
//        try {
//            execute(sendMessage); // Отправляем сообщение
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//
//
//    }