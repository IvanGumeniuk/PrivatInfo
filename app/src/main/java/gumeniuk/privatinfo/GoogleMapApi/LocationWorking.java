package gumeniuk.privatinfo.GoogleMapApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ваня on 14.04.2017.
 */

public interface LocationWorking {
    @GET("json")
    Call<TownRequest> getData(@Query("latlng") String latlng,@Query("language") String language);
}
