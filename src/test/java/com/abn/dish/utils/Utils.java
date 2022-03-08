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
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Utils {

	public static <T> List<T> loadData(Class<T> classs) {
		String content = "[{\r\n"
				+ "  \"nameOfDish\": \"Bread Omlette\",\r\n"
				+ "  \"creationDateTime\": \"2016-05-28T17:39:44.937\",\r\n"
				+ "  \"suitableFor\": 1,\r\n"
				+ "  \"instructions\": \"Break egg and Toast bread\",\r\n"
				+ "  \"ingredients\": [\r\n"
				+ "\r\n"
				+ "{\r\n"
				+ " \"name\": \"Egg\"\r\n"
				+ "}\r\n"
				+ ",\r\n"
				+ "{\r\n"
				+ " \"name\": \"Bread\"\r\n"
				+ "}\r\n"
				+ "  ],\r\n"
				+ "  \"id\": 1,\r\n"
				+ "  \"veg\": false\r\n"
				+ "},\r\n"
				+ "\r\n"
				+ "{\r\n"
				+ "  \"nameOfDish\": \"Soup\",\r\n"
				+ "  \"creationDateTime\": \"2016-05-28T17:39:44.937\",\r\n"
				+ "  \"suitableFor\": 1,\r\n"
				+ "  \"instructions\": \"Boil water add vegetables\",\r\n"
				+ "  \"ingredients\": [\r\n"
				+ "\r\n"
				+ "{\r\n"
				+ " \"name\": \"Water\"\r\n"
				+ "}\r\n"
				+ ",\r\n"
				+ "{\r\n"
				+ " \"name\": \"Carrot\"\r\n"
				+ "}\r\n"
				+ ",\r\n"
				+ "{\r\n"
				+ " \"name\": \"Salt\"\r\n"
				+ "}\r\n"
				+ "  ],\r\n"
				+ "  \"id\": 2,\r\n"
				+ "  \"veg\": false\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "]";


		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");

		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));

		mapper.findAndRegisterModules();

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(df);
		objectMapper.registerModule(new JavaTimeModule())
		.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		JavaType type = objectMapper.getTypeFactory().
				constructCollectionType(List.class, classs);
		List<T> list = new ArrayList<>();
		try {
			list = objectMapper.readValue(content, type);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

}
