package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
//        System.out.println("Введите токен бота");
//        Scanner scnToken = new Scanner(System.in);
//        System.out.println("Введите nikName бота (без знака @)");
//        Scanner scnNik = new Scanner(System.in);




//        Bot bot = new Bot(scnToken.nextLine(),scnNik.nextLine());
        Bot bot = new Bot();

        Main main = new Main();

        main.myregisterBot(bot);


        }
    public   void myregisterBot(Bot bot) throws TelegramApiException {

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }


    }