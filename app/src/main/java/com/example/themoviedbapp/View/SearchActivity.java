package com.example.themoviedbapp.View;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themoviedbapp.Adapter.ChildItemSearchAdapter;
import com.example.themoviedbapp.Model.MoviesModel;
import com.example.themoviedbapp.R;
import com.example.themoviedbapp.Response.MoviesResponse;
import com.example.themoviedbapp.Retrofit.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    EditText editTextSearch;
    Button searchButton;
    RecyclerView searchRecyclerView;
    ProgressBar progressBar;
    List<MoviesModel> moviesModelArrayListSearch;
    ChildItemSearchAdapter searchViewAdapter;
    String queryText;
    ImageView accountSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        init();
        searchMoviesTvShows(queryText);
        searchViewSearch();



        accountSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchActivity.this, "This feature is in Progress!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    public void searchViewSearch() {
        searchButton.setOnClickListener(view -> {
            String qur = editTextSearch.getText().toString();
            if (TextUtils.isEmpty(qur)) {
                editTextSearch.setError("Please enter the query");
            } else {
                moviesModelArrayListSearch.clear();
                searchViewAdapter.notifyDataSetChanged();
                searchMoviesTvShows(qur);
            }
        });
    }

    private void init() {
        editTextSearch = findViewById(R.id.searchEditText);
        String theQuery = MainActivity.getMainEditText();
        editTextSearch.setText(theQuery);

        searchButton = findViewById(R.id.searchButton);
        searchRecyclerView = findViewById(R.id.recyclerViewSearch);
        progressBar = findViewById(R.id.progressBar);
        queryText = MainActivity.getMainEditText();
        moviesModelArrayListSearch = new ArrayList<>();
        searchViewAdapter = new ChildItemSearchAdapter(SearchActivity.this, moviesModelArrayListSearch);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        searchRecyclerView.setLayoutManager(linearLayoutManager);
        searchRecyclerView.setAdapter(searchViewAdapter);
        accountSearch = findViewById(R.id.accountSearch);
    }

    private void searchMoviesTvShows(String query) {
        Call<MoviesResponse> data = RetrofitInstance.getRetrofitInstance().searchMoviesTvShows(query);
        data.enqueue(new Callback<MoviesResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    moviesModelArrayListSearch.addAll(response.body().getResults());
                    searchViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {

            }
        });
    }

}