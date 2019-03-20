package com.example.warhammer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.warhammer.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<Post> posts;

    public PostAdapter(Context context, ArrayList<Post> posts) {
        this.mContext = context;
        this.posts = posts;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Post post = this.posts.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.linear_post, null);
        }

        // 3
        final ImageView imageView = (ImageView)convertView.findViewById(R.id.imageview_cover_art);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.textview_book_name);
        final TextView authorTextView = (TextView)convertView.findViewById(R.id.textview_book_author);

        // 4
        Picasso.get().load(post.getPhotoThumb()).into(imageView); //загрузить фото из url в нужный эулумент
        nameTextView.setText(post.getDescription());
//        authorTextView.setText(post.getAlbumId());

        return convertView;

    }

}