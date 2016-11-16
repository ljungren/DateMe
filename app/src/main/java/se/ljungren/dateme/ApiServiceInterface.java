package se.ljungren.dateme;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import se.ljungren.dateme.models.User;

/**
 * Created by joakimljungren on 2016-11-16.
 */

public interface ApiServiceInterface {
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    @GET("users/:{id}/match")
    Call<User> getUser(@Path("id") String username);

    @GET("users")
    Call<List<User>> getUserList();

    @POST("users")
    Call<User> createUser(@Body User user);
}