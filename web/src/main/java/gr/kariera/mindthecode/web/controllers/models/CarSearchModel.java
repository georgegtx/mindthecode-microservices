package gr.kariera.mindthecode.web.controllers.models;

import org.springframework.stereotype.Component;

@Component
public class CarSearchModel {
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    private String query;

    public CarSearchModel(){}
    public CarSearchModel(String query) {
        this.query = query;
    }

}