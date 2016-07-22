package peopleinteractive.placesnow;
import android.content.Context;
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
        }
        return v;
    }
}
