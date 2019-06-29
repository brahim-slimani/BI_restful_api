package com.slimani.rest_reporting.dao;


import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.stereotype.Repository;


@Repository
public interface AdhocRepository {

    ArrayNode adhocQuery(String query);

}
