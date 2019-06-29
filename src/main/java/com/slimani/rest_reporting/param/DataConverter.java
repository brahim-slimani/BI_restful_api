package com.slimani.rest_reporting.param;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;

public class DataConverter {

    public static JSONArray convertToJSON(ResultSet resultSet)
            throws Exception {
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            int total_column = resultSet.getMetaData().getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i = 0; i < total_column; i++) {
                obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
                        .toLowerCase(), resultSet.getObject(i + 1));
            }
            jsonArray.put(obj);
        }
        return jsonArray;
    }

    public static ArrayNode convertToNode(JSONArray jsonArray) throws Exception{

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode data = (ArrayNode) objectMapper.readTree(String.valueOf(jsonArray));

        return data;
    }
}
