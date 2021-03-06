package com.paulo.joao.mymovies.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.paulo.joao.mymovies.MainActivity;
import com.paulo.joao.mymovies.MovieDetailsActivity;
import com.paulo.joao.mymovies.model.MyMovie;
import com.paulo.joao.mymovies.R;
import com.paulo.joao.mymovies.util.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Joao Paulo Ribeiro on 07/02/2017.
 */

public class CoverFlowAdapter extends BaseAdapter {

    private List<MyMovie> data;
    private Context context;

    public CoverFlowAdapter(List<MyMovie> movies, Context context) {
        this.data = movies;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_flow_view, null, false);

            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Picasso.with(this.context).load(Utils.formatImageUrlString(data.get(position).getPoster_path())).into(viewHolder.movieImage);

        view.setOnClickListener(onMovieClickListener(position));

        return view;
    }

    private View.OnClickListener onMovieClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);

                dialog.setContentView(R.layout.dialog_movie_info);
                dialog.setCancelable(true);
                dialog.setTitle(R.string.selected_movie_dialog_title);

                Intent intent = new Intent(context, MovieDetailsActivity.class);

                intent.putExtra("movieFoundToDetails", new Gson().toJson(data.get(position)));

                context.startActivity(intent);
            }
        };
    }


    private static class ViewHolder {

        private TextView movieName;
        private ImageView movieImage;

        public ViewHolder(View view) {
            movieName = (TextView) view.findViewById(R.id.name);
            movieImage = (ImageView) view.findViewById(R.id.image);
        }
    }
}
