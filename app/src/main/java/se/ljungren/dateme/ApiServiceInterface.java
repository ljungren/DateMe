package se.ljungren.dateme;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import se.ljungren.dateme.models.User;

/**
 * Created by joakimljungren on 2016-11-16.
 */

public interface ApiServiceInterface {
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    @GET("users/{id}/match")
    Call<List<User>> getUser(@Path("id") String userId);

    @GET("users")
    Call<List<User>> getUserList();

    @POST("users")
    Call<User> createUser(@Body User user);

    @PUT("users/{id}")
    Call<User> setUser(@Path("id") String userId, @Body User user);

    @DELETE("users/{id}")
    Call<ResponseBody> deleteUser(@Path("id") String userId);
}