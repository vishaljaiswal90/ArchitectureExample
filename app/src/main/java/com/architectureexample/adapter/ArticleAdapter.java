package com.architectureexample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.architectureexample.R;
import com.architectureexample.databinding.ArticleItemBinding;
import com.architectureexample.model.Article;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder>{

    List<Article> list =  new ArrayList<>();
    Context context;
    public ArticleAdapter(List<Article> list,Context context) {
        this.list = list;
        this.context =  context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ArticleItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.article_item,
                parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.binding.textTitle.setText(list.get(position).getTitle());
        holder.binding.textDescription.setText(list.get(position).getDescription());
        holder.binding.textAuthorPublish.setText(list.get(position).getAuthor()
                +" | Publish at" + list.get(position).getPublishedAt());
        Glide.with(context)
                .load(list.get(position).getUrlToImage())
                .into(holder.binding.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ArticleItemBinding binding;
        public MyViewHolder(@NonNull ArticleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
