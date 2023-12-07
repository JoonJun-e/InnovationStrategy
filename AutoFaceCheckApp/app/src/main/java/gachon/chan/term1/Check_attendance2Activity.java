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

    public void onClick134(View view) {
        Intent Check_attendance2 = new Intent(Check_attendance2Activity.this, Check_attendance3Activity.class);
        Toast.makeText(this, "혁신전략 출결현황을 확인해보세요", Toast.LENGTH_LONG).show();
        startActivity(Check_attendance2);
    }
    public void click1(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        switch (v.getId()){
            case R.id.button7:
                Intent mai = new Intent(this, main_activityActivity.class);
                startActivity(mai);
                break;
            case R.id.button34:
                Intent bmai = new Intent(this, main_activityActivity.class);
                startActivity(bmai);
                break;
            case R.id.button134:
                Intent mail = new Intent(this, Check_attendance3Activity.class);
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