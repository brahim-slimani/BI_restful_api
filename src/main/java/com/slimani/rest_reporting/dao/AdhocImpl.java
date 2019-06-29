package com.slimani.rest_reporting.dao;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.slimani.rest_reporting.param.ConnectionDW;
import com.slimani.rest_reporting.param.DataConverter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdhocImpl implements AdhocRepository{

    Connection connectionManager = ConnectionDW.getConnection();
    Statement stm = null;
    ResultSet rsl = null;


    @Override
    public ArrayNode adhocQuery(String query){

        ArrayNode data = null;

        try {
            stm = connectionManager.createStatement();
            rsl = stm.executeQuery(query);
            data = DataConverter.convertToNode(DataConverter.convertToJSON(rsl));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;

    }


}
