package gumeniuk.privatinfo.ATM;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ваня on 12.04.2017.
 */

public interface AtmService {
    @GET("/infrastructure")
    Call<CashMashines> getData( @Query("address") String address, @Query("city") String city);
    //"Accept: application/json"
}
