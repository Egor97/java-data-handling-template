package com.epam.izh.rd.online.service;

import com.sun.xml.internal.ws.api.ha.StickyFeature;

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

        try {
            FileInputStream info = new FileInputStream("/home/egor/IdeaProjects/java-data-handling-template/src/main/resources/sensitive_data.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(info, "UTF-8");
            BufferedReader infoReader = new BufferedReader(inputStreamReader, 200);

            int i;

            while((i=infoReader.read())!= -1){
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

        try {
            FileInputStream fileInputStream = new FileInputStream("/home/egor/IdeaProjects/java-data-handling-template/src/main/resources/sensitive_data.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader, 200);

            int i;

            while((i=bufferedReader.read()) != -1) {
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newValue.trim();
    }
}
