package com.kinosoftware.backend.DTO.response;

import java.util.List;

public class MovieApiResponse {
   private List<ResultMovie> results;

    public List<ResultMovie> getResults() {
        return results;
    }

    public void setResults(List<ResultMovie> results) {
        this.results = results;
    }
}
