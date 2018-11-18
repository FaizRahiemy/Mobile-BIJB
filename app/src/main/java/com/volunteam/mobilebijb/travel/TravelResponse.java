package com.volunteam.mobilebijb.travel;

import java.util.List;

/**
 * Created by iqbal on 5/10/2018.
 */

public class TravelResponse {
    private String status_code;
    private List <ResultTravel> result_post;
    private String status;

    public void setStatusCode(String statusCode){
        this.status_code = statusCode;
    }

    public String getStatusCode(){
        return status_code;
    }

    public void setResultPost(List <ResultTravel> resultTravel){
        this.result_post = resultTravel;
    }

    public List <ResultTravel> getResultPost(){
        return result_post;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
