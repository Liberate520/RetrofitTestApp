package ru.samsung.testretrofit2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edName, edAge;
    TextView tvList;
    Button btSend, btGetList;

    final String URL = "https://retrofittest520.herokuapp.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        edName = findViewById(R.id.ed_name);
        edAge = findViewById(R.id.ed_age);
        tvList = findViewById(R.id.tv_list);
        btSend = findViewById(R.id.bt_send);
        btGetList = findViewById(R.id.bt_get_list);
        btSend.setOnClickListener(this);
        btGetList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_get_list:
                new GetAsyncTask().execute();
                break;
            case R.id.bt_send:
                String name = edName.getText().toString();
                int age = Integer.parseInt(edAge.getText().toString());
                Student student = new Student(name, age);
                new SendAsyncTask().execute(student);
                break;
        }
    }

    class SendAsyncTask extends AsyncTask<Student, Void, Void>{
        @Override
        protected Void doInBackground(Student... students) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            StudentService service = retrofit.create(StudentService.class);
            Call<Student> call = service.putStudent(students[0]);
            try {
                Response<Student> userStudent = call.execute();
                Student student = userStudent.body();
                Log.d("My", "Студент: " + student);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    class GetAsyncTask extends AsyncTask<Void, Void, List<Student>>{
        @Override
        protected List<Student> doInBackground(Void... voids) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            StudentService service = retrofit.create(StudentService.class);
            Call<List<Student>> call = service.getStudents();
            try {
                Response<List<Student>> userStudent = call.execute();
                List<Student> list = userStudent.body();
                return list;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Student> students) {
            String result = "";
            for (Student student: students){
                result += "Студент: " + student + "\n";
            }
            tvList.setText(result);
        }
    }
}