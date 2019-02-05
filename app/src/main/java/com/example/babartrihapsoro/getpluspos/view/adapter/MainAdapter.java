package com.example.babartrihapsoro.getpluspos.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.babartrihapsoro.getpluspos.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {

    private Context context;
    private int images[];
    private String title[];
    private ItemClickListener mClickListener;

    public MainAdapter(Context context, int[] images, String[] title) {
        this.context = context;
        this.images = images;
        this.title = title;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, null);
        MainHolder mainHolder = new MainHolder(view);
        return mainHolder;
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {
        holder.imgItemView.setImageResource(images[position]);
        holder.txtItemView.setText(title[position]);
    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    public String getItem(int id) {
        return title[id];
    }

    public class MainHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.img_item_view)
        ImageView imgItemView;
        @BindView(R.id.tv_item_view)
        TextView txtItemView;

        public MainHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
