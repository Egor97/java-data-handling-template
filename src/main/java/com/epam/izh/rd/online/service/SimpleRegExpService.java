package com.epam.izh.rd.online.service;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegExpService implements RegExpService {

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {
        StringBuilder message = new StringBuilder();
        String value = "";

        try (FileReader fileReader = new FileReader("src/main/resources/sensitive_data.txt")) {

            int i;

            while((i=fileReader.read())!= -1){
                message.append((char)i);
            }

            Pattern pattern = Pattern.compile("[\\d]{4}\\s(([\\d]{4}\\s){2})[\\d]{4}\\s");
            Matcher matcher = pattern.matcher(message);

            value = message.toString();

            while(matcher.find()) {
                String stars = matcher.group(1);

                for (int j = 0; j < stars.length(); j++) {
                    if (stars.charAt(j) != ' ') {
                        stars = stars.replace(stars.charAt(j), '*');
                    }
                }


                value = value.replace(matcher.group(1), stars);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return value.trim();
    }

    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        StringBuilder value = new StringBuilder();
        String newValue = "";

        try (FileReader fileReader = new FileReader("src/main/resources/sensitive_data.txt")) {
            int i;

            while((i=fileReader.read()) != -1) {
                value.append((char) i);
            }

            Pattern pattern = Pattern.compile("[$][{][\\w[a-zA-Z]]*[}]");
            Matcher matcher = pattern.matcher(value);

            newValue = new String(value);

            while (matcher.find()) {
                String changePlaceHolder = matcher.group();

                if (changePlaceHolder.equals("${payment_amount}")) {
                    newValue = newValue.replace(changePlaceHolder, String.valueOf((int) paymentAmount));
                } else if (changePlaceHolder.equals("${balance}")) {
                    newValue = newValue.replace(changePlaceHolder, String.valueOf((int) balance));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newValue.trim();
    }
}
