// MainActivity.java
package gachon.chan.term1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_STUDENT_ID = "student_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        // 로그인 버튼 클릭 시
        Button loginButton = findViewById(R.id.button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText studentIdEditText = findViewById(R.id.idText);
                String studentId = studentIdEditText.getText().toString().trim();

                if (!studentId.isEmpty()) {
                    // 학번을 포함하여 UploadActivity 시작
                    Intent uploadIntent = new Intent(MainActivity.this, UploadActivity.class);
                    uploadIntent.putExtra(EXTRA_STUDENT_ID, studentId);
                    startActivity(uploadIntent);
                } else {
                    Toast.makeText(MainActivity.this, "학번을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
