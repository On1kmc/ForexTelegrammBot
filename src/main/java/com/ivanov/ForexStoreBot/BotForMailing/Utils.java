package com.ivanov.ForexStoreBot.BotForMailing;

import java.io.*;
import java.util.*;


public class Utils {

    private static Map<Integer, String> categories;

    private static List<String> messages;

    private static String token;
    public static void initializeCategories() throws IOException {
        categories = new HashMap<>();
        File file = new File("./config/categories.txt");
        Properties props = new Properties();
        props.load(new FileReader(file));
        Iterator<Object> iterator = props.keys().asIterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            categories.put(Integer.valueOf(obj.toString()), props.getProperty(obj.toString()));
        }
    }

    public static void initializeMessages() throws IOException {
        messages = new ArrayList<>();
        File file = new File("./config/messages.txt");
        Properties props = new Properties();
        props.load(new FileReader(file));
        Iterator<Object> iterator = props.keys().asIterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            messages.add(props.getProperty(obj.toString()));
        }
    }

    public static Map<Integer, String> getCategories() {
        return categories;
    }

    public static List<String> getMessages() {
        return messages;
    }

    public static String getLink(String category, String model) {
        return "https://forex-robot.store/" + category + "/" + model;
    }

    public static void addMessages(Integer id, String message) {
        try {
            FileInputStream in = new FileInputStream("./config/messages.txt");
            Properties properties = new Properties();
            properties.load(in);
            in.close();
            properties.setProperty(String.valueOf(id), message);
            FileOutputStream out = new FileOutputStream("./config/messages.txt");
            properties.store(out, "null");
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static String getToken(){
        if (token ==null) {
            File file = new File("./config/token.txt");
            Properties props = new Properties();
            try {
                props.load(new FileReader(file));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            token = props.getProperty("token");
        }
        return token;
    }
}
