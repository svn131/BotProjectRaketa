package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите токен бота");
        String token = scanner.nextLine();

        System.out.println("Введите nikName бота (без знака @)");
        String botName = scanner.nextLine();

        System.out.println("Введите nikName Менеджера в формате https://t.me/nikname");
        String managerNik = scanner.nextLine();

        Bot bot = new Bot(token, botName, managerNik);

        Main main = new Main();
        main.myregisterBot(bot);
    }

    public void myregisterBot(Bot bot) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }
}
