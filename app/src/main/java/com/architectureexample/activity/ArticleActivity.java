package com.architectureexample.activity;

import android.os.Bundle;
import android.view.View;

import com.architectureexample.R;
import com.architectureexample.adapter.ArticleAdapter;
import com.architectureexample.databinding.ArticleActivityBinding;
import com.architectureexample.model.Article;
import com.architectureexample.viewModel.ArticleViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ArticleActivity extends AppCompatActivity {

    ArticleActivityBinding binding;
    ArticleAdapter articleAdapter;
    ArrayList<Article> list;
    ArticleViewModel articleViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.article_activity);

        initView();
        getArticles();
    }

    private void initView() {

        binding.listView.setLayoutManager( new LinearLayoutManager(this));
        binding.listView.setHasFixedSize(true);

        articleAdapter = new ArticleAdapter(list,this);
        binding.listView.setAdapter(articleAdapter);

        articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);

    }

    private void getArticles (){
         articleViewModel.getArticleResponseLiveData().observe(this,articleResponse -> {

             if (articleResponse != null) {

                 binding.progressCircularMovieArticle.setVisibility(View.GONE);
                 List<Article> articles = articleResponse.getArticles();
                 list.addAll(articles);
                 articleAdapter.notifyDataSetChanged();
             }
         });


    }
}
