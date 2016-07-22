package peopleinteractive.placesnow;

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
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private ImageView iv;

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
        
        commentAdapter = new CommentAdapter(this, R.layout.comment_list, commentArray);
        comments.setAdapter(commentAdapter);

    }

    private void onlineUpdate() {
        DataManager.addLocation(currPlaceName);
    }

    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type){

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

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
        }).setNeutralButton("Add Picture", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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

        Bitmap bp = (Bitmap) data.getExtras().get("data");
        iv.setImageBitmap(bp);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
