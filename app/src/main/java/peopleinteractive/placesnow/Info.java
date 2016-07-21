package peopleinteractive.placesnow;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import java.util.ArrayList;

public class Info extends AppCompatActivity {

    private String currPlaceName;
    private double currLat;
    private double currLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        currPlaceName = intent.getStringExtra("PlaceName");
        currLat = intent.getDoubleExtra("Lat", 0.0);
        currLng = intent.getDoubleExtra("Lng", 0.0);

        Log.d("YO", currPlaceName);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView comments = (ListView) findViewById(R.id.commentListView);
        List<Comment> commentArray = new ArrayList<Comment>();
        commentArray.add(new Comment("hahahahaha"));
        CommentAdapter commentAdapter = new CommentAdapter(this, R.layout.comment_list, commentArray);
        comments.setAdapter(commentAdapter);
    }
}
