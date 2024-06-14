package com.aluracursos.literalura.service;

import com.aluracursos.literalura.model.BookData;
import com.aluracursos.literalura.model.ResultData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class DataConversor implements IDataConversor {
    private ObjectMapper objectMapper = new ObjectMapper();
    private ApiCommunicator apiCommunicator = new ApiCommunicator();
    private final String URL_BASE = "https://gutendex.com/books/?";

    //Le pide a la API que traiga un libro según su nombre
    public ResultData fetchBookByTitle(String searchedBook) {
        var url = URL_BASE + "search=" + searchedBook.replace(" ", "%20");
        var json = callApi(url);

        return getData(json, ResultData.class);
    }

    //Le pide a la API los 10 libros más descargados
    public List<BookData> fetchTopTenBooks(String searchCondition) {
        var url = URL_BASE + searchCondition;
        List<BookData> results = new ArrayList<>();
        var json = callApi(url);

        //Parsear el json y convertirlo en una lisa de BookData
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode resultsNode = rootNode.get("results");
            if (resultsNode.isArray()) {
                var limit = 0;
                for (JsonNode resultNode : resultsNode) {
                    if (limit >= 10) {
                        break;
                    }
                    BookData resultData = objectMapper.treeToValue(resultNode, BookData.class);
                    results.add(resultData);
                    limit++;
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    //Se comunica con la API
    public String callApi(String url) {
        return apiCommunicator.getData(url);
    }

    //Transforma los datos que vienes de la API para filtrar solo los necesarios
    @Override
    public <T> T getData(String json, Class<T> tClass) {
        try {
            return objectMapper.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al procesar el JSON: " + e.getMessage(), e);
        }
    }
}
