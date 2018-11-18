package com.volunteam.mobilebijb.travelDetail;

import java.util.List;

/**
 * Created by iqbal on 5/10/2018.
 */

public class TravelDetailResponse {
    private String status_code;
    private List<ResultTravelDetail> result_post;
    private String status;

    public void setStatusCode(String statusCode){
        this.status_code = statusCode;
    }

    public String getStatusCode(){
        return status_code;
    }

    public void setResultPost(List <ResultTravelDetail> resultPost){
        this.result_post = resultPost;
    }

    public List <ResultTravelDetail> getResultPost(){
        return result_post;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
