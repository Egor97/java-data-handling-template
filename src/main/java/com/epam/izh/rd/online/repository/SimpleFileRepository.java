package com.epam.izh.rd.online.repository;

import java.io.*;
import java.net.URL;

public class SimpleFileRepository implements FileRepository {

    private long value = 0;
    private long value2 = 0;

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        File dir = new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + path);

        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isFile()) {
                        value++;
                    } else if (files[i].isDirectory()) {
                        countFilesInDirectory(path + File.separator + files[i].getName());
                    }
                }
            }
        }

        return value;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        File dir = new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + path);

        if (dir.exists() && dir.isDirectory()) {
            if (dir.getName().equals(path)) {
                value2++;
            }

            File[] files = dir.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        value2++;
                        countDirsInDirectory(path + File.separator + files[i].getName());
                    }
                }
            }
        }

        return value2;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        File dir = new File(from);

        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".txt")) {
                    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(from)));
                         BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(to)))) {
                            int i;

                            while ((i = bufferedReader.read()) != -1) {
                                bufferedWriter.write((char) i);
                            }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(path);

        if (resource != null) {
            File file  = new File(resource.getPath());

            try (FileWriter fileWriter = new FileWriter(file.getPath() + File.separator + name)) {
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return false;
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        File dir = new File("src" + File.separator + "main" + File.separator + "resources");

        if (dir.exists() && dir.isDirectory()) {
            File file = new File(dir + File.separator, fileName);

            if (file.exists() && file.canRead()) {
                try (FileReader fileReader = new FileReader(file)) {
                    StringBuilder valueForSend = new StringBuilder();
                    int i;

                    while ((i = fileReader.read()) != -1) {
                        valueForSend.append((char) i);
                    }

                    return valueForSend.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
