package gachon.chan.term1;


import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

public class Check_attendance1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_attendance1);
        setTitle("출석체크");



    }

    public void onClick34(View view) {
        Intent Check_attendance1 = new Intent(Check_attendance1Activity.this, Check_attendance2Activity.class);
        Toast.makeText(this, "출석체크 주차를 선택해주세요", Toast.LENGTH_LONG).show();
        startActivity(Check_attendance1);
    }


    public void click1(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        switch (v.getId()){
            case R.id.button7:
                Intent mai = new Intent(Check_attendance1Activity.this, main_activityActivity.class);
                startActivity(mai);
                break;
            case R.id.button20:
                Intent mails = new Intent(Check_attendance1Activity.this, main_activityActivity.class);
                startActivity(mails);
                break;
            case R.id.button34:
                Intent mail = new Intent(Check_attendance1Activity.this, Check_attendance2Activity.class);
                startActivity(mail);
                break;

        }
    }


    public void click4(View v){
        Intent intent4 = new Intent(Intent.ACTION_VIEW);
        switch (v.getId()){


        }
    }
}