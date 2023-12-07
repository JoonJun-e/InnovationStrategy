package gachon.chan.term1;

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

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class UploadActivity extends Activity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    private Button btnUpload;
    private ImageView imageView;
    private Bitmap selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_image);

        btnUpload = findViewById(R.id.button2);
        imageView = findViewById(R.id.imageView);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionAndOpenGallery();
            }
        });
    }

    private void checkPermissionAndOpenGallery() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            openGallery();
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            handleImageUpload(selectedImageUri);
        }
    }

    private void handleImageUpload(Uri selectedImageUri) {
        try {
            InputStream imageStream = getContentResolver().openInputStream(selectedImageUri);
            selectedImage = BitmapFactory.decodeStream(imageStream);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageData = baos.toByteArray();

            if (allowedFile(selectedImageUri)) {
                new UploadTask().execute(imageData);
            } else {
                Toast.makeText(this, "Invalid file type", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean allowedFile(Uri uri) {
        // 수정된 부분: ContentResolver를 사용하여 파일 확장자 확인
        ContentResolver contentResolver = getContentResolver();
        String fileExtension = MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri));
        return fileExtension != null && Arrays.asList("png", "jpg", "jpeg", "gif").contains(fileExtension.toLowerCase());
    }

    private class UploadTask extends AsyncTask<byte[], Void, Void> {
        @Override
        protected Void doInBackground(byte[]... params) {
            try {
                byte[] imageData = params[0];
                String boundary = "MyBoundaryString";  // 임의의 boundary 문자열로 수정
                String lineEnd = "\r\n";

                URL url = new URL("http://172.20.10.12:5000/upload");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

                DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                outputStream.writeBytes("--" + boundary + lineEnd);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=\"image.jpg\"" + lineEnd);
                outputStream.writeBytes("Content-Type: image/jpeg" + lineEnd);
                outputStream.writeBytes(lineEnd);
                outputStream.write(imageData);
                outputStream.writeBytes(lineEnd);
                outputStream.writeBytes("--" + boundary + "--" + lineEnd);
                outputStream.flush();
                outputStream.close();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // 업로드 성공
                    showToast("이미지 업로드 성공");
                } else {
                    // 오류 처리
                    showToast("이미지 업로드 실패: " + responseCode);
                }

            } catch (Exception e) {
                e.printStackTrace();
                showToast("이미지 업로드 실패: " + e.getMessage());
            }
            return null;
        }

        private void showToast(String message) {
            Toast.makeText(UploadActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    }
}