package com.beacon.zohaib.beacon.ui.activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.beacon.zohaib.beacon.R;
import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MentalActivity extends AppCompatActivity {

    private static final String TAG = "###Ali";
    private VrPanoramaView panoWidgetView0 ;
    private VrPanoramaView panoWidgetView1;
    private VrPanoramaView panoWidgetView2 ;
    private Button BT_Video ;
    public boolean loadImageSuccessful;

    private Uri fileUri;

    private VrPanoramaView.Options panoOptions = new VrPanoramaView.Options();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mental);

        panoWidgetView0 = (VrPanoramaView) findViewById(R.id.pano_view0);
        panoWidgetView1 = (VrPanoramaView) findViewById(R.id.pano_view1);
        panoWidgetView2 = (VrPanoramaView) findViewById(R.id.pano_view2);
        loadPhotoSphere();


    }
    @Override
    protected void onNewIntent(Intent intent) {
        Log.i(TAG, this.hashCode() + ".onNewIntent()");
        setIntent(intent);
    }

    @Override
    protected void onPause() {
        panoWidgetView0.pauseRendering();
        panoWidgetView1.pauseRendering();
        panoWidgetView2.pauseRendering();
        super.onPause();
    }

    @Override
    protected void onResume() {
        panoWidgetView0.resumeRendering();
        panoWidgetView1.resumeRendering();
        panoWidgetView2.resumeRendering();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        panoWidgetView0.shutdown();
        panoWidgetView1.shutdown();
        panoWidgetView2.shutdown();
        super.onDestroy();
    }

    private void loadPhotoSphere() {
        VrPanoramaView.Options options0 = new VrPanoramaView.Options();
        VrPanoramaView.Options options1 = new VrPanoramaView.Options();
        VrPanoramaView.Options options2 = new VrPanoramaView.Options();
        InputStream inputStream = null;

        AssetManager assetManager = getAssets();

        try {
            inputStream = assetManager.open("andes.jpg");
            options0.inputType = VrPanoramaView.Options.TYPE_STEREO_OVER_UNDER;
            panoWidgetView0.loadImageFromBitmap(BitmapFactory.decodeStream(inputStream), options0);
            inputStream.close();

            inputStream = assetManager.open("NY.jpeg");
            options1.inputType = VrPanoramaView.Options.TYPE_MONO;
            panoWidgetView1.loadImageFromBitmap(BitmapFactory.decodeStream(inputStream), options1);
            inputStream.close();

            inputStream = assetManager.open("Venice.jpeg");
            options2.inputType = VrPanoramaView.Options.TYPE_MONO;
            panoWidgetView2.loadImageFromBitmap(BitmapFactory.decodeStream(inputStream), options2);
            inputStream.close();

        } catch (IOException e) {
            Log.e("Tuts+", "Exception in loadPhotoSphere: " + e.getMessage() );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menatal_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.id_video:
                Intent I = new Intent(MentalActivity.this,MentalVideoActivity.class);
                startActivity(I);
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
