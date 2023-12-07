package gachon.chan.term1;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Check_attendance3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_attendance3);
        setTitle("출석체크");



    }
    public void click1(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        switch (v.getId()){

            case R.id.button7:
                intent.setData(Uri.parse("https://www.inflearn.com/course/web2-python#reviews"));
                startActivity(intent);
                break;

            case R.id.button20:
                Intent mai = new Intent(Check_attendance3Activity.this, main_activityActivity.class);
                startActivity(mai);
                break;

        }
    }






}