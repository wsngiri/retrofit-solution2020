package id.putraprima.retrofit.api.services;


import id.putraprima.retrofit.api.models.AppVersion;
import id.putraprima.retrofit.api.models.Envelope;
import id.putraprima.retrofit.api.models.LoginRequest;
import id.putraprima.retrofit.api.models.LoginResponse;
import id.putraprima.retrofit.api.models.RegisterRequest;
import id.putraprima.retrofit.api.models.RegisterResponse;
import id.putraprima.retrofit.api.models.UpdatePasswordRequest;
import id.putraprima.retrofit.api.models.UpdatePasswordResponse;
import id.putraprima.retrofit.api.models.UpdateProfileRequest;
import id.putraprima.retrofit.api.models.UpdateProfileResponse;
import id.putraprima.retrofit.api.models.UserInfo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface{
    @GET("/")
    Call<AppVersion> getAppVersion();

    @POST("/api/auth/login")
    Call<LoginResponse> doLogin(@Body LoginRequest loginRequest);

    @GET("/api/auth/me")
    Call<Envelope<UserInfo>> me();

    @POST("/api/auth/register")
    Call<Envelope<RegisterResponse>> doRegister(@Body RegisterRequest registerRequest);

    @PATCH("/api/account/profile")
    Call<Envelope<UpdateProfileResponse>> doUpateProfile(@Body UpdateProfileRequest updateProfileRequest);

    @PATCH("/api/account/password")
    Call<Envelope<UpdatePasswordResponse>> doUpatePassword(@Body UpdatePasswordRequest updatePasswordRequest);


}
