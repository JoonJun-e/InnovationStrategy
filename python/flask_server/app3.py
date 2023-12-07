from flask import Flask, request, jsonify
import io
from PIL import Image
import os
from werkzeug.utils import secure_filename
import face_recognition as fr
import cv2

app_flask = Flask(__name__)

UPLOAD_FOLDER = 'C:\\Users\\user\\Desktop\\flask_server\\static'
ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg', 'gif'}

app_flask.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER


def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS


@app_flask.route('/upload', methods=['GET', 'POST'])
def total_image():
    try:
        if 'image' not in request.files:
            return jsonify({'error': 'No image part'}), 400

        file = request.files['image']

        if file.filename == '':
            return jsonify({'error': 'No selected file'}), 400

        if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            filepath = os.path.join(app_flask.config['UPLOAD_FOLDER'], filename)
            file.save(filepath)

            image = Image.open(io.BytesIO(file.read()))
            image = fr.load_image_file(image)
            face_locations = fr.face_locations(image)

            return jsonify({'total_faces': len(face_locations), 'filename': filename, 'filepath': filepath})
        else:
            return jsonify({'error': 'Invalid file type'}), 400

    except Exception as e:
        return jsonify({'error': str(e)}), 500


def image_distance(): ##return boolean, face_distance
    try:
        cyber_image = fr.load_image_file('/content/min.jpg') 
        cyber_image = cv2.cvtColor(cyber_image,cv2.COLOR_BGR2RGB)  #RGB로 변환


        image_data = request.files['image'].read()
        image = Image.open(io.BytesIO(image_data))
        image = fr.load_image_file(image)
        imgTest = cv2.cvtColor(image,cv2.COLOR_BGR2RGB) #Tset 이미지 RGB로 변환

            #얼굴의 위치 같게 만들기
        faceLoc = fr.face_locations(cyber_image)[0]
        encodeElon = fr.face_encodings(cyber_image)[0]   #감지할 얼굴 인코딩, 첫번째 요소만 가져오기
        cv2.rectangle(cyber_image,(faceLoc[3],faceLoc[0]), (faceLoc[1],faceLoc[2]),(255,0,255),2) #우리가 얼굴을 감지한 위치를 확인하기 위해 사각형을 이미지에 그림

        faceLocTest = fr.face_locations(imgTest)[0]
        encodeTest = fr.face_encodings(imgTest)[0]   #Test이미지에 대한 첫번째 요소만 가져오기
        cv2.rectangle(imgTest,(faceLocTest[3],faceLocTest[0]), (faceLocTest[1],faceLocTest[2]),(255,0,255),2) #Test 이미지에 사각형 이미지

        results = fr.compare_faces([encodeElon],encodeTest) #인코딩 이미지와 Test 이미지 간의 비교하기
        faceDis = fr.face_distance([encodeElon],encodeTest) #이미지 유사성 알기, (==> 얼굴 간의 오차 느낌인거 같음)

        if faceDis[0] > 0.3:
            total = False
        else:
            total = True

        return total
    
    except Exception as e:
        return jsonify({'error': str(e)}), 500


if __name__ == '__main__':
    app_flask.run(host='192.9.67.107', port=5000)
