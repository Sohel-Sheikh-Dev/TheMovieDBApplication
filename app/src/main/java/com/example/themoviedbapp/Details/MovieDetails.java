package com.example.themoviedbapp.Details;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.themoviedbapp.Adapter.CastItemAdapter;
import com.example.themoviedbapp.Adapter.ChildItemAdapter;
import com.example.themoviedbapp.Adapter.RecommendedItemAdapter;
import com.example.themoviedbapp.Adapter.ReviewItemAdapter;
import com.example.themoviedbapp.Model.CastModel;
import com.example.themoviedbapp.Model.MoviesModel;
import com.example.themoviedbapp.Model.ReviewsModel;
import com.example.themoviedbapp.R;
import com.example.themoviedbapp.Response.MoviesResponse;
import com.example.themoviedbapp.Response.ReviewsResponse;
import com.example.themoviedbapp.Retrofit.RetrofitInstance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import eightbitlab.com.blurview.BlurView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetails extends AppCompatActivity {

//    TextView textviewReleaseDate, tvReleaseRuntime, tvReleaseVoteAverage;


    List<CastModel> castModelList;
    List<MoviesModel> recommendedMoviesList;
    List<ReviewsModel> reviewsModelList;
    RecyclerView mediaRecyclerView, recomRecyclerView, reviewsRecyclerView;
    LinearLayoutManager linearLayoutManagerRecom, linearLayoutManagerCast, linearLayoutManagerReviews;
    private CastItemAdapter castItemAdapter;
    private RecommendedItemAdapter recommendedItemAdapter;
    private ReviewItemAdapter reviewItemAdapter;

    ImageView detailsSearchButton, detailsCloseButton, detailsIV, cardImage;
    EditText detailsSearchET;
    TextView titleTv, dateTv, fullDateTv, tvReleaseRuntime, taglineTv, overviewListTv, statusResultTv, originalLangResultTv, budgetResultTv, revenueResultTv, genreTv;

    ProgressBar detailsRatingProgress;
    TextView detailsRatingPercentage, detailsPercentageSign;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        int i = ChildItemAdapter.mid;
        init();
        getRecomMovies(i);
        getPopMovies1(i);
        getMovieDetails(i);
        getMovieReviews(i);


        detailsSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsSearchButton.setVisibility(View.GONE);
                detailsCloseButton.setVisibility(View.VISIBLE);
                detailsSearchET.setVisibility(View.VISIBLE);
            }
        });

        detailsCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsSearchButton.setVisibility(View.VISIBLE);
                detailsCloseButton.setVisibility(View.GONE);
                detailsSearchET.setVisibility(View.GONE);
            }
        });

    }

    public void init() {
        detailsSearchButton = findViewById(R.id.detailsSearchButton);
        detailsCloseButton = findViewById(R.id.detailsCloseButton);
        detailsSearchET = findViewById(R.id.detailsSearchEditText);
        detailsIV = findViewById(R.id.detailsIV);

        titleTv = findViewById(R.id.titleTV);
        dateTv = findViewById(R.id.dateTV);
        fullDateTv = findViewById(R.id.fullDateTV);
        tvReleaseRuntime = findViewById(R.id.fullTimeTV);
        taglineTv = findViewById(R.id.taglineTV);
        overviewListTv = findViewById(R.id.overviewListTV);
        statusResultTv = findViewById(R.id.statusResultTV);
        originalLangResultTv = findViewById(R.id.originalLangResultTV);
        budgetResultTv = findViewById(R.id.budgetResultTV);
        revenueResultTv = findViewById(R.id.revenueResultTV);
        cardImage = findViewById(R.id.cardImage);
        detailsRatingProgress = findViewById(R.id.detailsRatingProgress);
        detailsRatingPercentage = findViewById(R.id.detailsRatingPercentage);
        detailsPercentageSign = findViewById(R.id.detailsPercentSign);
        genreTv = findViewById(R.id.genreTV);


        castModelList = new ArrayList<>();
        recommendedMoviesList = new ArrayList<>();
        reviewsModelList = new ArrayList<>();

        mediaRecyclerView = findViewById(R.id.topCastRV);
        recomRecyclerView = findViewById(R.id.recommendationsRV);
        reviewsRecyclerView = findViewById(R.id.reviewsRV);

        linearLayoutManagerRecom = new LinearLayoutManager(MovieDetails.this);
        linearLayoutManagerRecom.setOrientation(RecyclerView.HORIZONTAL);

        linearLayoutManagerCast = new LinearLayoutManager(MovieDetails.this);
        linearLayoutManagerCast.setOrientation(RecyclerView.HORIZONTAL);

        linearLayoutManagerReviews = new LinearLayoutManager(MovieDetails.this);
        linearLayoutManagerReviews.setOrientation(RecyclerView.VERTICAL);

        mediaRecyclerView.setLayoutManager(linearLayoutManagerCast);
        recomRecyclerView.setLayoutManager(linearLayoutManagerRecom);
        reviewsRecyclerView.setLayoutManager(linearLayoutManagerReviews);

        recommendedItemAdapter = new RecommendedItemAdapter(getApplicationContext(), recommendedMoviesList);
        recomRecyclerView.setAdapter(recommendedItemAdapter);

        castItemAdapter = new CastItemAdapter(getApplicationContext(), castModelList);
        mediaRecyclerView.setAdapter(castItemAdapter);

        reviewItemAdapter = new ReviewItemAdapter(getApplicationContext(),reviewsModelList);
        reviewsRecyclerView.setAdapter(reviewItemAdapter);



    }

    /*
        private void getMovieTrailer(int id) {

            Call<TrailerResponse> data = RetrofitInstance.getRetrofitInstance().getMoviesTrailer(id);
            data.enqueue(new Callback<TrailerResponse>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(@NonNull Call<TrailerResponse> call, @NonNull Response<TrailerResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        movieTVTrailerModelList.addAll(response.body().getMovieTVTrailerModelList());
                        mediaAdapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onFailure(@NonNull Call<TrailerResponse> call, @NonNull Throwable t) {

                }
            });

        }
     */

    public void getMovieDetails(int id) {

        Call<MoviesModel> data = RetrofitInstance.getRetrofitInstance().getMovieDetails(id);
        data.enqueue(new Callback<MoviesModel>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(@NonNull Call<MoviesModel> call, @NonNull Response<MoviesModel> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Glide.with(MovieDetails.this).load(response.body().getBackdrop_path()).into(detailsIV);
                    titleTv.setText(response.body().getTitle());
                    Toast.makeText(MovieDetails.this, "" + titleTv.getText().toString(), Toast.LENGTH_SHORT).show();
                    String a = response.body().getRelease_date();
                    String[] strings = a.split("-");
                    dateTv.setText(strings[0]);

                    @SuppressLint("SimpleDateFormat") String date_format = ChildItemAdapter.parseDate(a,
                            new SimpleDateFormat("yyyy-MM-dd"),
                            new SimpleDateFormat("dd/MM/yyyy"));
                    fullDateTv.setText(date_format);

                    int b = response.body().getRuntime();

                    int h = b / 60;
                    int m = b % 60;

                    tvReleaseRuntime.setText(String.format("%dh %dm", h, m));

                    taglineTv.setText(response.body().getTagline());

                    overviewListTv.setText(response.body().getOverview());

                    statusResultTv.setText(response.body().getStatus());
                    originalLangResultTv.setText(response.body().getOriginal_language());
                    budgetResultTv.setText(response.body().getBudget());
                    revenueResultTv.setText(response.body().getRevenue());
                    Glide.with(MovieDetails.this).load(response.body().getPoster_path()).into(cardImage);

                    String genreVal = "";

                    for (int i = 0; i < response.body().getGenres().size(); i++) {
                        int val = response.body().getGenres().size();
                        genreVal += response.body().getGenres().get(i).getName();

                        if (i < val - 1) {
                            genreVal += ", ";
                        }
                    }
                    genreTv.setText(genreVal);

                }

            }

            @Override
            public void onFailure(@NonNull Call<MoviesModel> call, @NonNull Throwable t) {
            }
        });

    }

    private void getMovieReviews(int id) {
        Call<ReviewsResponse> data = RetrofitInstance.getRetrofitInstance().getMovieReviews(id);
        data.enqueue(new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    reviewsModelList.addAll(response.body().getResults());
                    reviewItemAdapter.notifyDataSetChanged();

//                  String ava = response.body().getResults().get(1).getAuthorDetails().getAvatarPath();
//
//                    String ava1 = ava.substring(1);
//
//                    Log.d("Dhag", "onResponse: "+ava1);
                }
            }

            @Override
            public void onFailure(Call<ReviewsResponse> call, Throwable t) {
                Log.d("Dhag", "onFail: Call: " + call.toString() + "\nt: " + t.toString());
            }
        });
    }

    private void getRecomMovies(int id) {

        Call<MoviesResponse> data = RetrofitInstance.getRetrofitInstance().getRecommendedMovies(id);
        data.enqueue(new Callback<MoviesResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
//                    progressBar.setVisibility(View.GONE);
                    recommendedMoviesList.addAll(response.body().getResults());
                    recommendedItemAdapter.notifyDataSetChanged();

                    int vote_final = ChildItemAdapter.mpos;

                    String votePercentage = String.valueOf(vote_final);

                    if (vote_final > 69) {

                        detailsRatingPercentage.setText(votePercentage);
                        Drawable drawable = ContextCompat.getDrawable(MovieDetails.this, R.drawable.circle_green);
                        detailsRatingProgress.setProgressDrawable(drawable);

                        detailsRatingProgress.setProgress(vote_final);
                    }

                    if (vote_final < 70) {
                        detailsRatingPercentage.setText(votePercentage);
                        Drawable drawable = ContextCompat.getDrawable(MovieDetails.this, R.drawable.circle_yellow);
                        detailsRatingProgress.setProgressDrawable(drawable);

                        detailsRatingProgress.setProgress(vote_final);

                    }


                    if (vote_final == 0 && votePercentage.equals("0")) {
                        detailsRatingPercentage.setText("NR");
                        detailsPercentageSign.setVisibility(View.GONE);
//                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.circle_grey);
//                ((ViewHolder1) holder).ratingProgress.setProgressDrawable(drawable);

                        detailsRatingProgress.getProgressDrawable().setColorFilter(Color.GRAY, android.graphics.PorterDuff.Mode.SRC_IN);

                        int paddingDp = 1;
                        float density = MovieDetails.this.getResources().getDisplayMetrics().density;
                        int paddingPixel = (int) (paddingDp * density);
                        detailsRatingPercentage.setPadding(paddingPixel, 0, 0, 0);
                        detailsRatingProgress.setProgress(0);
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {

            }
        });
    }


    private void getPopMovies1(int idC) {

        Call<MoviesModel> data = RetrofitInstance.getRetrofitInstance().getCastDetails(idC);
        data.enqueue(new Callback<MoviesModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MoviesModel> call, @NonNull Response<MoviesModel> response) {
                if (response.isSuccessful() && response.body() != null) {
//                    progressBar.setVisibility(View.GONE);
//                    Toast.makeText(MovieDetails.this, ""+response.body().getCredits().getCast().get(0).getName(), Toast.LENGTH_SHORT).show();
//                    castModelList.addAll();
                    castModelList.addAll(response.body().getCredits().getCast());

                    castItemAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesModel> call, @NonNull Throwable t) {

            }
        });
    }


}