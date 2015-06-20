package com.lookapp.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Created by Giorgi on 6/20/2015.
 */
public class GsonUtils {

    private static Gson GSON;// = createGson(false);

    static {
        GSON = createSimpleGson();
    }

    public static Gson createSimpleGson() {
        return new GsonBuilder()./*.setPrettyPrinting().*/create();
    }

    /**
     * Get reusable pre-configured {@link Gson} instance
     *
     * @return Gson instance
     */
    public static Gson getGson() {
        return GSON;
    }

    /**
     * Convert object to json
     *
     * @param object
     * @return json string
     */
    public static String toJson(final Object object) {
        return GSON.toJson(object);
    }

    /**
     * Convert string to given type
     *
     * @param json
     * @param type
     * @return instance of type
     */
    public static <V> V fromJson(String json, Class<V> type) {
        return GSON.fromJson(json, type);
    }

    /**
     * Convert string to given type
     *
     * @param json
     * @param type
     * @return instance of type
     */
    public static <V> V fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }

    /**
     * Convert content of reader to given type
     *
     * @param reader
     * @param type
     * @return instance of type
     */
    public static <V> V fromJson(Reader reader, Class<V> type) {
        return GSON.fromJson(reader, type);
    }

    /**
     * Convert content of reader to given type
     *
     * @param reader
     * @param type
     * @return instance of type
     */
    public static <V> V fromJson(Reader reader, Type type) {
        return GSON.fromJson(reader, type);
    }
}