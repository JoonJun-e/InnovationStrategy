package gachon.chan.term1;


import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

public class Check_attendance2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_attendance2);
        setTitle("주차 선택");
    }

    public void click500(View view) {
        Intent Check_attendance2 = new Intent(Check_attendance2Activity.this, UploadActivity.class);
        Toast.makeText(this, "이미지를 업로드하세요", Toast.LENGTH_LONG).show();
        startActivity(Check_attendance2);
    }
    public void click1(View view) {
        Intent Check_attendance2 = new Intent(Check_attendance2Activity.this,main_activityActivity.class);
        startActivity(Check_attendance2);
    }
    public void click2(View view) {
        Intent Check_attendance2 = new Intent(Check_attendance2Activity.this,Check_attendance1Activity.class);
        startActivity(Check_attendance2);
    }



    public void click4(View v){
        Intent intent4 = new Intent(Intent.ACTION_VIEW);
        switch (v.getId()){


        }
    }
}