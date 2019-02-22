package com.beacon.zohaib.beacon.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beacon.zohaib.beacon.R;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.vision.CameraSource;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;


import kotlin.Pair;




public class ObjectDetect extends AppCompatActivity implements SurfaceHolder.Callback, TextToSpeech.OnInitListener {


    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private GestureDetectorCompat mDetector;
    private TextToSpeech repeatTTS;
    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    private final String tag = "VideoServer";

    CameraSource cm;


    Camera.PictureCallback rawCallback;
    Camera.ShutterCallback shutterCallback;
    Camera.PictureCallback jpegCallback;
    Button start, stop, capture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_detect);


        mDetector = new GestureDetectorCompat(this, new MyGestureListener(this));
        repeatTTS = new TextToSpeech(this, this);



        verifyStoragePermissions(this);



       // start = (Button)findViewById(R.id.startDetectionBtn2);

        //start.setOnClickListener(new Button.OnClickListener()
//        {
//            public void onClick(View arg0) {
//                start_camera();
//            }
//        });
//
//
//        stop = (Button)findViewById(R.id.stopDetectionBtn2);
//        capture = (Button) findViewById(R.id.caputrebtn2);
//
//        stop.setOnClickListener(new Button.OnClickListener()
//        {
//            public void onClick(View arg0) {
//                stop_camera();
//            }
//        });
//        capture.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                captureImage();
//            }
//        });
//


        surfaceView = (SurfaceView)findViewById(R.id.surface_view2);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        rawCallback = new Camera.PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                Log.d("Log", "onPictureTaken - raw");
            }
        };

        /** Handles data for jpeg picture */
        shutterCallback = new Camera.ShutterCallback() {
            public void onShutter() {
                Log.i("Log", "onShutter'd");
            }
        };
        jpegCallback = new Camera.PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                FileOutputStream outStream = null;
                try {
                    outStream = new FileOutputStream(String.format(
                            Environment.getExternalStorageDirectory().getAbsolutePath() + "/myPic.jpg", System.currentTimeMillis()));
                    outStream.write(data);
                    outStream.close();
                    Log.d("Log", "onPictureTaken - wrote bytes: " + data.length);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
                Log.d("Log", "onPictureTaken - jpeg");
            }
        };


    }


    private void captureImage() {
        // TODO Auto-generated method stub
        camera.takePicture(shutterCallback, rawCallback, jpegCallback);
    }

    private void start_camera()
    {
        try{
            camera = Camera.open();
        }catch(RuntimeException e){
            Log.e(tag, "init_camera: " + e);
            return;
        }
        Camera.Parameters param;
        param = camera.getParameters();
        //modify parameter
        param.setPreviewFrameRate(20);
        param.setPreviewSize(176, 144);
        camera.setParameters(param);
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            //camera.takePicture(shutter, raw, jpeg)
        } catch (Exception e) {
            Log.e(tag, "init_camera: " + e);
            return;
        }
    }




    private void stop_camera()
    {

        camera.stopPreview();
        camera.release();

        String _path=Environment.getExternalStorageDirectory().getAbsolutePath() + "/myPic.jpg";
        Bitmap bitmap = null;
        File f = new File(_path);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        try {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteStream);


        String base64Data = Base64.encodeToString(byteStream.toByteArray(), Base64.URL_SAFE);

        recognizeObjects(base64Data);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }



    private void recognizeObjects(String base64Data) {
        final String requestURL = "https://vision.googleapis.com/v1/images:annotate?key=" +
                getResources().getString(R.string.mykey);

        try {
            JSONArray features = new JSONArray();
            JSONObject feature = new JSONObject();
            feature.put("type", "LABEL_DETECTION");
            features.put(feature);

            JSONObject imageContent = new JSONObject();
            imageContent.put("content", base64Data);

            JSONArray requests = new JSONArray();
            JSONObject request = new JSONObject();
            request.put("image", imageContent);
            request.put("features", features);
            requests.put(request);

            JSONObject postData = new JSONObject();
            postData.put("requests", requests);

            String body = postData.toString();

            Fuel.post(requestURL)
                    .header(new Pair<String, Object>("content-length", body.length()),
                            new Pair<String, Object>("content-type", "application/json"))
                    .body(body.getBytes()).responseString(new Handler<String>() {
                @Override
                public void success(@NotNull Request request, @NotNull com.github.kittinunf.fuel.core.Response response, String data) {
                    try {
                        JSONArray labels = new JSONObject(data).getJSONArray("responses").getJSONObject(0)
                                .getJSONArray("labelAnnotations");

                        String results = "";

                        for(int i=0;i<labels.length();i++) {
                            results = results + labels.getJSONObject(i).getString("description") + "\n";
                        }

                        repeatTTS.speak("Your surrounding consist of "+results,
                                TextToSpeech.QUEUE_FLUSH, null);
                        Log.d("mytags",results);
                      //  ((TextView)findViewById(R.id.resultsText)).setText(results);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void failure(@NotNull Request request, @NotNull com.github.kittinunf.fuel.core.Response response,
                                    @NotNull FuelError fuelError) {

                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onInit(int i) {

    }


    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        int count=0;
        Context context;
        public MyGestureListener(Context context)
        {
            this.context=context;
        }
        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG, "onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2,
                               float velocityX, float velocityY) {

            if (e1.getY() - e2.getY() > 150) {
                Toast.makeText(context,"up",Toast.LENGTH_SHORT).show();
                if(count==0) {
                    repeatTTS.speak("swipe right to capture image",
                            TextToSpeech.QUEUE_FLUSH, null);
                    start_camera();
                    count++;

                    return true;
                }

                repeatTTS.speak("Try again and swipe right to capture image",
                        TextToSpeech.QUEUE_FLUSH, null);
                return true;
            }
            if (e2.getY() - e1.getY() > 150) {

                Toast.makeText(context,"down",Toast.LENGTH_SHORT).show();
                if(count==2) {
                    repeatTTS.speak("Please wait",
                            TextToSpeech.QUEUE_FLUSH, null);
                    stop_camera();
                    count=0;
                }
                return true;
            }
            if (e1.getX() - e2.getX() > 0) {

                Toast.makeText(context,"left",Toast.LENGTH_SHORT).show();
                return true;
            }
            if (e2.getX() - e1.getX() > 0) {
                repeatTTS.speak("swipe down to know about the surrounding objects",
                        TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(context,"right",Toast.LENGTH_SHORT).show();
                if(count==1) {
                    captureImage();
                    count++;
                }
                return true;

            }
            return true;
        }
    }



}
