package com.example.themoviedbapp.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.themoviedbapp.Model.MoviesModel;
import com.example.themoviedbapp.R;

import java.util.ArrayList;
import java.util.List;

public class RecommendedItemAdapter extends RecyclerView.Adapter<RecommendedItemAdapter.ViewHolder> {

    private Context context;
    private List<MoviesModel> castMovieModelList;

    public RecommendedItemAdapter(Context context, List<MoviesModel> castMovieModelList) {
        this.context = context;
        this.castMovieModelList = castMovieModelList;
    }

    @NonNull
    @Override
    public RecommendedItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedItemAdapter.ViewHolder holder, int position) {

        Glide.with(context.getApplicationContext()).load(castMovieModelList.get(position).getBackdrop_path()).into(holder.recomImage);

        holder.recomName.setText(castMovieModelList.get(position).getTitle());

        float vote = castMovieModelList.get(position).getVote_average() * 10;
        int vote_final = Math.round(vote);

        String votePercentage = String.valueOf(vote_final);

        holder.recomPercent.setText(votePercentage+"%");

//        Toast.makeText(context.getApplicationContext(), "" + castMovieModelList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
//        Log.d("tagg", "onBindViewHolder: "+castMovieModelList.get(position).getTitle());


    }

    @Override
    public int getItemCount() {
        return castMovieModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView recomImage;
        TextView recomName,recomPercent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recomImage = itemView.findViewById(R.id.recomImage);
            recomName = itemView.findViewById(R.id.recomName);
            recomPercent = itemView.findViewById(R.id.recomPercent);
        }
    }
}
