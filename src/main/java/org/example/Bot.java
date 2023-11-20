package org.example;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.User;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class Bot extends TelegramLongPollingBot {

    //    boolean dobavitNovogoUsera = false;
//
//    static Map<Long, String> baseUsers = new HashMap<>();
//
//    public Bot() {
//        ReadOnWrite.loadBaseUsers("C:/userBasesMapa.txt", baseUsers);
//    }
    Map<Long, TelegramUser> usersMap = new HashMap<>();

    @Override
    public String getBotUsername() {
        return "SportsBot10_bot"; // Метод, который возвращает username бота.
    }

    @Override
    public String getBotToken() {
        return "6493956819:AAHbHe4wsGPvvD97SfEyLJK5k2jJJZgIzZ0"; // Метод, который возвращает token бота.
    }

    // Метод для обработки команды "Старт" и отправки сообщения с кнопкой
    private void handleStartCommand(Message message) {
        long chatId = message.getChatId();
        if (!usersMap.containsKey(chatId)) {
            usersMap.put(chatId, new TelegramUser(chatId));
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Статус: Не активен.\nДля активации свяжитесь со своим менеджером.");

            // Создаем клавиатуру
            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();

            // Создаем кнопку
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText("Activate");
            inlineKeyboardButton.setUrl("https://t.me/vladimirai2023");

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
        System.out.println("onUpdateReceivedonUpdateReceivedonUpdateReceived");

//        System.out.println("--------------------------------------------- " + update.hasMessage());


        if (update.hasMessage()) {
            Message message = update.getMessage();
            String buttonText = message.getText();
            Long chatId = message.getChatId();

//            System.out.println("--------------------------------------------- " + buttonText);


            if (buttonText.equals("/start")) { //todo добаввить топливо 0 и цикл перовый
                System.out.println("SSSSSSSSSSSSSSSSSSSSStart");
                handleStartCommand(message);
                startActivationTimer(chatId);
            } else if (buttonText.equals("activate")) {
                System.out.println("Akkkkkkkkkkkktivate");
            }

        }
    }


//        if (update.hasMessage()) {


//            if (dobavitNovogoUsera) {
//
//                dobavitNovogoUsera = false;
//                Message message = update.getMessage(); //@todo добавить лоику если уже кто то из активированых что то пришлет перед новым ползоватлем
//                Long chatId = message.getChatId(); // Получаем chat ID пользователя
//                // Дальше можно использовать chatId для отправки сообщения обратно пользователю или для других действий.
//
//                // Пример вывода chat ID в консоль:
////                System.out.println("Chat ID: " + chatId);
//
//                User user = message.getFrom();
//                String username = user.getUserName(); // Получаем юзернейм пользователя
//
//                String firstName = user.getFirstName(); // Получение имени пользователя
//                String lastName = user.getLastName(); // Получение фамилии пользователя
//
//
//                String value = ""; // Инициализируем значение
//
//                if (username != null) {
//                    value += "@" + username;
//                }
//                if (firstName != null) {
//                    value += " " + firstName;
//                }
//                if (lastName != null) {
//                    value += " " + lastName;
//                }
//
//                String currentDate = LocalDateTime.now().toString();
//                value += " " + currentDate;
//
//
//                System.out.println("Dobavka v mapu");
//                baseUsers.put(chatId, value); // Добавляем в хеш-карту
//
//
//                System.out.println("Содержимое Map:");
//                for (Map.Entry<Long, String> entry : baseUsers.entrySet()) {
//                    Long key = entry.getKey();
//                    String val = entry.getValue();
//                    System.out.println("Key: " + key + ", Value: " + val);
//                }
//
//
//                // Пример вывода значения из хеш-карты:
//                System.out.println("User Info: " + baseUsers.get(chatId));
//
//
//                // Открытие файлового потока для чтения
//
/////////////////////////////////////////////////////////////////////////////////
//                if (!ReadOnWrite.checkChatIdExists("C:/userBasesMapa.txt", chatId)) {
//                    try (FileWriter writer = new FileWriter("C:/userBasesMapa.txt", true)) {
//                        writer.write(chatId + " " + value + "\n");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        // Обработка ошибки записи файла
//                    }
//                }
//
//
//                /////////////////////////////////////////////////////////////////////////////////////
//
//
//                sendOk(chatId);
//
//            } else {
//                sendError(update.getMessage().getChatId());
//                System.out.println("Попытка доступа при закрытой заслонке ");
//            }
//
//            ////
//            Message message = update.getMessage();
//            String text = message.getText();
//
//            if (text.contains("Delates")) {
//                // @todo логика удаления пользователя
//            } else if (text.equals("Vhodi")) {
//
//                dobavitNovogoUsera = true;
//            } else if (text.equals("S")) { // @todo Метод для отладки
//                sendArrayDataToAll(new String[]{"data1", "data2", "data3"});
//            }
//
//
//        }
//    }


//    // Метод для отправки массива строк пользователю
//    private void sendArrayData(Long chatId, String[] dataArray) {
//
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setText(String.join("\n", dataArray));
//        sendMessage.setChatId(chatId);
//        try {
//            execute(sendMessage); // Синхронное выполнение для простоты
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Data sent successfully to chatId: " + chatId);
//    }


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

    public void sendError(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Ошика доступа обратитесь к Администраторам @GOLDGAME77777 @BakharevDen");
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
        }, 60 * 1000); // Задержка в 60 000 миллисекунд (1 минута)
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


}




































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
