package gumeniuk.privatinfo.PrivatApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ваня on 14.04.2017.
 */

public interface TsoService {
    @GET("infrastructure")
    Call<CashMashines> getData(@Query("json") String json, @Query("tso") String tso, @Query("city") String city);
}
