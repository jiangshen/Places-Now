package peopleinteractive.placesnow;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
            v = vi.inflate(R.layout.comment_list, null);
        }

        Comment c = getItem(position);

        if (c != null) {
            TextView body = (TextView) v.findViewById(R.id.body);
            TextView timeStamp = (TextView) v.findViewById(R.id.time_stamp);
            TextView score = (TextView) v.findViewById(R.id.score);
            if (body != null) {
                body.setText(c.getInfo());
            }
            if (timeStamp != null) {
                timeStamp.setText(c.getTime());
            }
            if (score != null) {
                score.setText(Integer.toString(c.getScore()));
            }
        }
        return v;
    }
}
