package com.example.newsreader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<NewsItem> news;
    private boolean isSmallView;
    private int layoutResource;


    public NewsAdapter(Context context) {
        this.context = context;
        this.news = new ArrayList<>();
        this.isSmallView = false;
        this.layoutResource = R.layout.news_item;
    }

    public void setNews(ArrayList<NewsItem> news) {
        this.news = news;
        notifyDataSetChanged();
    }

    public void setLayout(int layoutResource) {
        this.layoutResource = layoutResource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(viewType, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtTitle.setText(news.get(position).getTitle());
        holder.txtDate.setText(formatDate(news.get(position).getDate()));
        if (layoutResource == R.layout.news_item) {
            holder.txtDescription.setVisibility(View.VISIBLE);
            holder.txtDescription.setText(news.get(position).getDescription());
        } else {
            holder.txtDescription.setVisibility(View.GONE);
        }

        Glide.with(context)
                .load(news.get(position).getImageUrl())
                .into(holder.image);


        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebsiteActivity.class);
                intent.putExtra("url", news.get(position).getLink());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }


    @Override
    public int getItemViewType(int position) {
        return isSmallView ? R.layout.news_item_small : R.layout.news_item;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtDescription, txtDate;
        private ImageView image;
        private CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.title);
            txtDescription = itemView.findViewById(R.id.description);
            txtDate = itemView.findViewById(R.id.date);
            parent = itemView.findViewById(R.id.parent);
            image = itemView.findViewById(R.id.image);
        }

    }

    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm", Locale.getDefault());
        return dateFormat.format(date);
    }
}
