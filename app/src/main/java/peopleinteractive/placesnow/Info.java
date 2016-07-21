package peopleinteractive.placesnow;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
<<<<<<< Updated upstream
import android.widget.EditText;
=======
import android.widget.Button;
>>>>>>> Stashed changes
import android.widget.ListView;
import android.widget.PopupWindow;

import com.firebase.client.Firebase;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import java.util.ArrayList;

public class Info extends AppCompatActivity {

    List<Comment> commentArray = new ArrayList<Comment>();
    private String currPlaceName;
    private double currLat;
    private double currLng;
    private boolean toggleUp;
    private boolean toggleDown;

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

        Log.d("YO", currPlaceName);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView comments = (ListView) findViewById(R.id.commentListView);
        List<Comment> commentArray = new ArrayList<Comment>();
        commentArray.add(new Comment("trolololol"));
        CommentAdapter commentAdapter = new CommentAdapter(this, R.layout.comment_list, commentArray);
        comments.setAdapter(commentAdapter);
        Button upVote = commentAdapter.getUpVote();
        Button downVote = commentAdapter.getDownVote();
        final Comment c = commentAdapter.getComment();
        upVote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!toggleUp) {
                    c.addToScore();
                    toggleUp = true;
                }
                if (toggleUp) {
                    c.minusToScore();
                    toggleUp = false;
                }
                if (toggleDown) {
                    c.addToScore();
                    c.addToScore();
                    toggleUp = true;
                    toggleDown = false;
                }
            }
        });
        downVote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!toggleDown) {
                    c.minusToScore();
                    toggleDown = true;
                }
                if (toggleDown) {
                    c.addToScore();
                    toggleDown = false;
                }
                if (toggleUp) {
                    c.minusToScore();
                    c.minusToScore();
                    toggleDown = true;
                    toggleUp = false;
                }
            }
        });
    }

    private void onlineUpdate() {
        Log.d("YOYO", "STARTED");
        DataManager.addLocation(currPlaceName);
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
        })
        .setNegativeButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String comment = editText.getText().toString();
                commentArray.add(new Comment(comment));
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
}
