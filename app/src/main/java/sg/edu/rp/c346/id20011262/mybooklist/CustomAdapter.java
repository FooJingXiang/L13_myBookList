package sg.edu.rp.c346.id20011262.mybooklist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter{

    Context parent_context;
    int layout_id;
    ArrayList<Book> versionlist;

    public CustomAdapter(Context context, int resource, ArrayList<Book> objects) {

        super(context, resource, objects);
        parent_context = context;
        layout_id = resource;
        versionlist = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(layout_id, parent, false);

        TextView tvTitle = rowView.findViewById(R.id.textViewBookTitle);
        TextView tvCategory = rowView.findViewById(R.id.textViewBookCategory);
        TextView tvAuthor = rowView.findViewById(R.id.textViewBookAuthor);
        TextView tvYear = rowView.findViewById(R.id.textViewBookYear);
        ImageView iv = rowView.findViewById(R.id.imageView);
        RatingBar rb = rowView.findViewById(R.id.ratingBarStars);

        Book currentVersion = versionlist.get(position);

        tvTitle.setText(currentVersion.getTitle());
        tvCategory.setText(currentVersion.getCategory());
        tvAuthor.setText(currentVersion.getAuthor());
        tvYear.setText(currentVersion.getYear()+"");
        rb.setRating(currentVersion.getRatings());

        if(currentVersion.getRatings() >= 4) {
            iv.setVisibility(View.VISIBLE);
        } else {
            iv.setVisibility(View.INVISIBLE);
        }
        return rowView;
    }
}
