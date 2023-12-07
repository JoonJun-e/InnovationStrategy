package gachon.chan.term1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        setTitle("AutoCheck");
    }

    public void onclick100 (View v) {
        Toast.makeText(this, "출석체크를 진행하세요", Toast.LENGTH_LONG).show();
        Intent mai = new Intent(MainActivity.this, main_activityActivity.class);
        startActivity(mai);

    }

}
