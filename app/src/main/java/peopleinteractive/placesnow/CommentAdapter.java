package peopleinteractive.placesnow;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by williamcheng on 7/21/16.
 */
public class CommentAdapter extends ArrayAdapter<Comment> {
    private boolean toggleUp;
    private boolean toggleDown;
    public CommentAdapter(Context context, int resource, List<Comment> comments) {
        super(context, resource, comments);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.comment_list, null);
        }
        final Comment c = getItem(position);
        if (c != null) {
            toggleUp = false;
            toggleDown = false;
            TextView body = (TextView) v.findViewById(R.id.body);
            TextView timeStamp = (TextView) v.findViewById(R.id.time_stamp);
            TextView score = (TextView) v.findViewById(R.id.score);
            Button upVote = (Button) v.findViewById(R.id.up_vote);
            Button downVote = (Button) v.findViewById(R.id.down_vote);
            if (body != null) {
                body.setText(c.getInfo());
            }
            if (timeStamp != null) {
                timeStamp.setText(c.getTime());
            }
            if (score != null) {
                score.setText(Integer.toString(c.getScore()));
            }
            if (upVote != null) {
                upVote.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        toggleUp = !toggleUp;

                        if (toggleUp && toggleDown) {
                            c.addToScore();
                            c.addToScore();
                            toggleDown = false;
                            //highlight up
                            //unhighlight down
                        } else if (toggleUp && !toggleDown) {
                            c.addToScore();
                        } else if (!toggleUp && !toggleDown) {
                            c.minusToScore();
                        }
                        notifyDataSetChanged();
                        Log.d("SCORE", Integer.toString(c.getScore()));
                        Log.d("TOGGLEUP", Boolean.toString(toggleUp));
                        Log.d("TOGGLEDOWN", Boolean.toString(toggleDown));

                    }
                });
                downVote.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        toggleDown = !toggleDown;
                        if (toggleDown && toggleUp) {
                            c.minusToScore();
                            c.minusToScore();
                            toggleUp = false;
                            //highlight toggleDown
                            //unhighlight toggleUp

                        } else if (toggleDown && !toggleUp) {
                            c.minusToScore();
                            //highlight toggleDown
                        } else if (!toggleDown && !toggleUp) {
                            c.addToScore();
                            //unhighlight toggleDown
                        }
                        notifyDataSetChanged();
                        Log.d("SCORE", Integer.toString(c.getScore()));
                        Log.d("TOGGLEUP", Boolean.toString(toggleUp));
                        Log.d("TOGGLEDOWN", Boolean.toString(toggleDown));
                    }
                });
            }
        }
        return v;
    }
}
