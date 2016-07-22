package peopleinteractive.placesnow;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by williamcheng on 7/21/16.
 */
public class CommentAdapter extends ArrayAdapter<Comment> {
    public CommentAdapter(Context context, int resource, List<Comment> comments) {
        super(context, resource, comments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.image_comment_list, null);
        }
        final Comment c = getItem(position);
        if (c != null) {
            ImageView iv = (ImageView) v.findViewById(R.id.imageView);
            TextView body = (TextView) v.findViewById(R.id.body);
            TextView timeStamp = (TextView) v.findViewById(R.id.time_stamp);
            TextView score = (TextView) v.findViewById(R.id.score);
            Button upVote = (Button) v.findViewById(R.id.up_vote);
            Button downVote = (Button) v.findViewById(R.id.down_vote);

            if (iv != null) {
                iv.setImageBitmap(c.getPic());
            }
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
                        c.changeToggleUp();

                        if (c.getToggleUp() && c.getToggleDown()) {
                            c.addToScore();
                            c.addToScore();
                            c.changeToggleDown();
                            //highlight up
                            //unhighlight down
                        } else if (c.getToggleUp() && !c.getToggleDown()) {
                            c.addToScore();
                        } else if (!c.getToggleUp() && !c.getToggleDown()) {
                            c.minusToScore();
                        }
                        notifyDataSetChanged();

                    }
                });
                downVote.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        c.changeToggleDown();
                        if (c.getToggleDown() && c.getToggleUp()) {
                            c.minusToScore();
                            c.minusToScore();
                            c.changeToggleUp();
                            //highlight toggleDown
                            //unhighlight toggleUp

                        } else if (c.getToggleDown() && !c.getToggleUp()) {
                            c.minusToScore();
                            //highlight toggleDown
                        } else if (!c.getToggleDown() && !c.getToggleUp()) {
                            c.addToScore();
                            //unhighlight toggleDown
                        }
                        notifyDataSetChanged();
                    }
                });
            }
        }
        return v;
    }
}
