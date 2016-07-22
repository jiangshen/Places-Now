package peopleinteractive.placesnow;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import android.widget.EditText;

import android.widget.Button;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import java.util.ArrayList;

public class Info extends AppCompatActivity {

    List<Comment> commentArray = new ArrayList<Comment>();

    private CommentAdapter commentAdapter;

    private String currPlaceName;
    private double currLat;
    private double currLng;
    private boolean toggleUp;
    private boolean toggleDown;

    private TextView locationName;

    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
    private ImageView iv;
    private Comment c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        toggleUp = false;
        toggleDown = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CLICKED FAB", "clicked");
                displayPopup();
            }
        });

        Firebase.setAndroidContext(this);

        Intent intent = getIntent();
        currPlaceName = intent.getStringExtra("PlaceName");
        currLat = intent.getDoubleExtra("Lat", 0.0);
        currLng = intent.getDoubleExtra("Lng", 0.0);

        onlineUpdate();

        locationName = (TextView) findViewById(R.id.locationName);
        locationName.setText(currPlaceName);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        populateListView();
    }

    private void populateListView() {
        ListView comments = (ListView) findViewById(R.id.commentListView);
        
        commentAdapter = new CommentAdapter(this, R.layout.image_comment_list, commentArray);
        comments.setAdapter(commentAdapter);

    }

    private void onlineUpdate() {
        DataManager.addLocation(currPlaceName);
    }

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    private void displayPopup() {
        Log.d("popup", "trying to display");

        final EditText editText = new EditText(Info.this);

        AlertDialog.Builder builder = new AlertDialog.Builder(Info.this, R.style.DialogStyle);
        LayoutInflater inflater = Info.this.getLayoutInflater();

        builder.setTitle("Add A Comment")
                .setView(editText)
        .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        }).setNeutralButton("Add a Picture and Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String comment = editText.getText().toString();

                DataManager.addComment(currPlaceName, new Comment(comment));

                c = new Comment(comment);

                commentArray.add(c);
                commentAdapter.notifyDataSetChanged();

                populateListView();
                Log.d("COMMENT", comment);
                Log.d("all comments", commentArray.toString());

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
                dialog.cancel();
            }})
        .setNegativeButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String comment = editText.getText().toString();

                DataManager.addComment(currPlaceName, new Comment(comment));

                commentArray.add(new Comment(comment));
                commentAdapter.notifyDataSetChanged();

                populateListView();
                Log.d("COMMENT", comment);
                Log.d("all comments", commentArray.toString());
                dialog.cancel();
            }
        })
        .show();
    }

    private void exitPopup(View view) {
        startActivity(new Intent(this, Info.class));
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);

        if (resultCode!= RESULT_CANCELED) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && data != null)
            {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                c.setPic(imageBitmap);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
