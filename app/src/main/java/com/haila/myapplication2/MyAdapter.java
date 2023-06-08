package com.haila.myapplication2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Post> postArrayList;

    public MyAdapter(Context context, ArrayList<Post> postArrayList) {
        this.context = context;
        this.postArrayList = postArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.post_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        Post post = postArrayList.get(position);

        holder.textView1.setText(post.post_text);
        holder.textView3.setText(post.userID);
        //holder.textView2.setText(post.post_date);
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView1, textView2, textView3;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.textView1);
            textView3 = itemView.findViewById(R.id.textViewUser3);
            //textView2 = itemView.findViewById(R.id.textView2);
        }
    }
}
