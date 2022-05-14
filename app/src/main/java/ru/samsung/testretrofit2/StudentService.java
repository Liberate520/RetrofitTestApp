package ru.samsung.testretrofit2;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface StudentService {
    @GET("/names/players/list")
    public Call<List<Student>> getStudents();
    @POST("/names/players/add")
    public Call<Student> putStudent(@Body Student student);
}
