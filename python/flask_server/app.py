from flask import Flask, request, jsonify
import os
from werkzeug.utils import secure_filename
from PIL import Image
import face_recognition as fr
import cv2
import io

app_flask = Flask(__name__)

UPLOAD_FOLDER = 'c:\\Users\\user\\Desktop\\flask_server\\static'
ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg', 'gif'}

app_flask.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER


def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS


@app_flask.route('/upload', methods=['GET','POST'])
def upload_image():
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

            return jsonify({
                'filename': filename,
                'message': 'Upload successful'
            })
        else:
            return jsonify({'error': 'Invalid file type'}), 400

    except Exception as e:
        return jsonify({'error': str(e)}), 500




if __name__ == '__main__':
    app_flask.run(host='172.20.10.12', port=5000, debug=True)
