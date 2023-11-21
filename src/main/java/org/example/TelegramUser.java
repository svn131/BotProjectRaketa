package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class TelegramUser {
    long id;
    int toplivo = 0;
    int cykl = 1;
    boolean activate = false;


    List<Double> cefy;

    public TelegramUser(long id) {
        this.id = id;
        cefy = generateRandomNumbers();

    }

    public static List<Double> generateRandomNumbers() {
        List<Double> numbers = new ArrayList<>();
        Random random = new Random();

        // Генерация 65 чисел от 1.01 до 1.25
        for (int i = 0; i < 65; i++) {
            numbers.add(1.01 + (1.25 - 1.01) * random.nextDouble());
        }
        // Генерация 20 чисел от 1.26 до 1.95
        for (int i = 0; i < 20; i++) {
            numbers.add(1.26 + (1.95 - 1.26) * random.nextDouble());
        }
        // Генерация 10 чисел от 1.96 до 3.55
        for (int i = 0; i < 10; i++) {
            numbers.add(1.96 + (3.55 - 1.96) * random.nextDouble());
        }
        // Генерация 3 чисел от 3.56 до 10.99
        for (int i = 0; i < 3; i++) {
            numbers.add(3.56 + (10.99 - 3.56) * random.nextDouble());
        }
        // Генерация 2 чисел от 10.99 до 25.55
        for (int i = 0; i < 2; i++) {
            numbers.add(10.99 + (25.55 - 10.99) * random.nextDouble());
        }

        // Перемешивание списка для случайного порядка
        Collections.shuffle(numbers);

        return numbers;
    }

    public Double getCef() {
        Double cef = cefy.get(0);
        cefy.remove(0);
        BigDecimal bd = new BigDecimal(cef).setScale(2, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }

}
