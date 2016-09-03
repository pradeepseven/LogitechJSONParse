package example.logitech.pradeep.logitechjsonparse;


import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by antonypradeep on 03/09/16.
 */

//https://s3.amazonaws.com/harmony-recruit/devices.json

public interface RetroRequestInterface {
    @GET("harmony-recruit/devices.json")
    Call<AmazonProductsArray> getDevices();
}
