package gachon.chan.term1;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import android.webkit.MimeTypeMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;




public class UploadActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri; // 이미지 Uri를 저장할 변수
    private String studentId; // 학번 변수 추가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_image);

        // MainActivity로부터 학번 값 받아오기
        studentId = getIntent().getStringExtra(MainActivity.EXTRA_STUDENT_ID);

        // 이미지 불러오기 버튼 클릭 시
        Button selectImageButton = findViewById(R.id.button9);
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        // 이미지 업로드 버튼 클릭 시
        Button uploadButton = findViewById(R.id.button2);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 이미지 업로드 로직 호출
                handleImageUpload();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData(); // 선택한 이미지의 Uri 저장
        }
    }

    private void handleImageUpload() {
        if (selectedImageUri != null && studentId != null && !studentId.isEmpty()) {
            String fileName = studentId + ".jpg"; // 학번을 파일 이름으로 사용

            try {
                InputStream imageStream = getContentResolver().openInputStream(selectedImageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageData = baos.toByteArray();

                // 이미지 업로드 로직 호출 (fileName 변수 사용)
                uploadImage(imageData, fileName);
            } catch (IOException e) {
                e.printStackTrace();
                showToast("이미지 업로드 실패: " + e.getMessage());
            }
        } else {
            showToast("이미지 또는 학번이 선택되지 않았습니다.");
        }
    }

    // 이미지 업로드 메서드 (파일 이름을 포함하여 업로드)
    private void uploadImage(byte[] imageData, String fileName) {
        String serverUrl = "http://example.com/upload"; // 실제 서버 URL로 변경해야 함

        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        InputStream inputStream = null;

        try {
            URL url = new URL(serverUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            String boundary = "*****";
            String lineEnd = "\r\n";
            String twoHyphens = "--";

            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\"; filename=\"" + fileName + "\"" + lineEnd);
            outputStream.writeBytes(lineEnd);

            outputStream.write(imageData);

            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 서버로부터 응답을 받음
                inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();

                // 서버 응답에 따라 업로드 성공 여부 확인
                String serverResponse = stringBuilder.toString();
                if (serverResponse.equals("Upload successful")) {
                    showToast("이미지 업로드 성공");
                } else {
                    showToast("이미지 업로드 실패: " + serverResponse);
                }
            } else {
                showToast("이미지 업로드 실패: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showToast("이미지 업로드 실패: " + e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(UploadActivity.this, message, Toast.LENGTH_SHORT).show();
    }

}
