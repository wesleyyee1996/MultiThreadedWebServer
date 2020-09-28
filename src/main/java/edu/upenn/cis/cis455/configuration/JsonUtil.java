package edu.upenn.cis.cis455.configuration;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonUtil {
	private static ObjectMapper _objectMapper = defaultObjectMapper();
	
	private static ObjectMapper defaultObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper;
	}
	
	public static JsonNode parseString(String jsonFile) throws JsonProcessingException, IOException {
		return _objectMapper.readTree(jsonFile);
	}
	
	public static <A> A fromJson(JsonNode node, Class<A> obj) throws JsonProcessingException {
		return _objectMapper.treeToValue(node, obj);
	}
	
	public static String jsonToString(JsonNode node) throws JsonProcessingException {
		return createJson(node);
	}
	
	private static String createJson(Object object) throws JsonProcessingException{
		ObjectWriter objectWriter = _objectMapper.writer();
		return objectWriter.writeValueAsString(object);
	}
}
