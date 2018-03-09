package com.example.android.newsapi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.newsapi.Controller.DetailsActivity;
import com.example.android.newsapi.models.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emmanuel on 3/9/2018.
 */

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int LOADED = 0;
    private static final int LOADING = 1;

    private List<Item> itemList;
    private Context context;

    private boolean isLoadingAdded = false;

    public PaginationAdapter(Context context) {
        this.context = context;
        itemList = new ArrayList<>();
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view;
        switch (viewType) {
            case LOADED:
                view = inflater.inflate(R.layout.repository_row, parent, false);
                return new ViewHolder(view);
            case LOADING:
                view = inflater.inflate(R.layout.item_progress, parent, false);
                return new ViewHolder(view);

        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final Item item = itemList.get(position); // items

        switch (getItemViewType(position)) {
            case LOADED:
                final ViewHolder movieVH = (ViewHolder) holder;
                movieVH.title.setText(item.getTitle());
                movieVH.author.setText(item.getAuthor());
                Picasso.with(movieVH.articleImage.getContext())
                        .load(item.getUrlToImage())
                        .into( movieVH.articleImage);

                movieVH.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openDetailsActivity(item);
                    }
                });
                break;

            case LOADING:
//                Do nothing
                break;
        }

    }

    private void openDetailsActivity(Item item) {
        startDetailsActivtiy(item);
    }




    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Item item = itemList.get(position);

        if (position == itemList.size() - 1 && isLoadingAdded) {
            return LOADING;
        } else {
                return LOADED;

        }

    }


    /*
 adapter  Helpers

    */
    public void add(Item r) {
        itemList.add(r);
        notifyItemInserted(itemList.size() - 1);
    }

    public void addAll(List<Item> results) {
        for (Item result : results) {
            add(result);
        }
    }

    public void remove(Item r) {
        int position = itemList.indexOf(r);
        if (position > -1) {
            itemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Item());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = itemList.size() - 1;
        Item result = getItem(position);

        if (result != null) {
            itemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    //start details activity
    private void startDetailsActivtiy(Item item) {
        Intent myIntent = new Intent(context, DetailsActivity.class);
        myIntent.putExtra("article_name", item.getTitle());
        myIntent.putExtra("article_author", item.getAuthor());
        myIntent.putExtra("article_details", item.getDescription());
        myIntent.putExtra("article_picture", item.getUrlToImage());
        context.startActivity(myIntent);
    }

    public Item getItem(int position) {
        return itemList.get(position);
    }


    /**
     * data content ViewHolder
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, author;
        private ImageView articleImage;

        private CardView cardView;
        private ProgressBar mProgress;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title_tv_tag);
            author = (TextView) itemView.findViewById(R.id.article_author_tv);
            articleImage = (ImageView) itemView.findViewById(R.id.article_image);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

        }
    }

}
