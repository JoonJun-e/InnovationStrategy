package gachon.chan.term1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class main_activityActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setTitle("오늘은 12월 12일 화요일입니다.");
    }

    public void onclick2(View v){
        Intent check_attendance_statue = new Intent(main_activityActivity.this, check_weeklyActivity.class);
        Toast.makeText(this, "주차별 출결현황을 확인해보세요", Toast.LENGTH_LONG).show();
        startActivity(check_attendance_statue);
    }
    public void onclick3(View v){
        Intent clang = new Intent(main_activityActivity.this, check_attendance_statueActivity.class);
        Toast.makeText(this, "학생별 출결 현황을 확인해보세요", Toast.LENGTH_LONG).show();
        startActivity(clang);
    }
    public void onclick1(View v){
        Intent Check_attendance1 = new Intent(main_activityActivity.this, Check_attendance1Activity.class);
        Toast.makeText(this, "출석체크 할 과목을 선택해주세요", Toast.LENGTH_LONG).show();
        startActivity(Check_attendance1);
    }

}

