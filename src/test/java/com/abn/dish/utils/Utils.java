package com.abn.dish.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

public final class Utils {

    public static <T> List<T> loadData(Class<T> clazz, String filePath) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        mapper.setDateFormat(df);
        JavaType type = mapper.getTypeFactory().
                constructCollectionType(List.class, clazz);
        List<T> list = new ArrayList<>();
        InputStream is = Utils.class.getClassLoader().getResourceAsStream(filePath);
        try {
            list = mapper.readValue(is, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}

