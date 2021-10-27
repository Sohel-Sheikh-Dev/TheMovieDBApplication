package com.example.themoviedbapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.themoviedbapp.Model.ReviewsModel;
import com.example.themoviedbapp.R;

import java.util.List;

public class ReviewItemAdapter extends RecyclerView.Adapter<ReviewItemAdapter.ViewHolder> {

    private Context context;
    private final List<ReviewsModel> reviewsModelList;

    public ReviewItemAdapter(Context context, List<ReviewsModel> reviewsModelList) {
        this.context = context;
        this.reviewsModelList = reviewsModelList;
    }

    @NonNull
    @Override
    public ReviewItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewItemAdapter.ViewHolder holder, int position) {
        holder.authorName.setText("A review by " + reviewsModelList.get(position).getAuthor());

        String ava = (reviewsModelList.get(position).getAuthorDetails().getAvatarPath());

        String uni = "https://cdn-icons.flaticon.com/png/512/1144/premium/1144760.png?token=exp=1635333630~hmac=1cb120e3ffa39d2004740925184f13a0";

        String otherAvatar = "https://www.themoviedb.org/t/p/w150_and_h150_face";

        if (ava != null) {
            String ava1 = ava.substring(1);
            Glide.with(context.getApplicationContext()).load(ava1).into(holder.avatar);
        }
        if(ava == null){
            Glide.with(context.getApplicationContext()).load(uni).into(holder.avatar);
        }
        if(ava != null && !(ava.startsWith("/http"))){
            Glide.with(context.getApplicationContext()).load(otherAvatar+ava).into(holder.avatar);
        }


    }

    @Override
    public int getItemCount() {
        return reviewsModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView avatar;
        TextView authorName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            authorName = itemView.findViewById(R.id.authorName);
            avatar = itemView.findViewById(R.id.avatar);

        }
    }
}
