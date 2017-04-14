package gumeniuk.privatinfo.PrivatApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ваня on 12.04.2017.
 */

public interface AtmService {
    @GET("infrastructure")
    Call<CashMashines> getData(@Query("json") String json, @Query("atm") String atm, @Query("city") String city);
}
