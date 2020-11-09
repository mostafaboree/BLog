package com.mostafabor3e.mychat;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mostafabor3e.mychat.model.Comment;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
List<Comment>comments;
Context mContext;

    public CommentAdapter(List<Comment> comments, Context mContext) {
        this.comments = comments;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.row_custom_comment,null,false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        long data= (long) comments.get(position).getTimeStemp();
        holder.time.setText(getData(data));
        holder.description.setText(comments.get(position).getContent());
        holder.titleComment.setText(comments.get(position).getuName());
        Glide.with(mContext).load(comments.get(position).getuPhoto()).into(holder.userComment);

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    static class CommentHolder extends RecyclerView.ViewHolder {
        CircleImageView userComment;
        TextView titleComment,description,time;
        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            userComment=itemView.findViewById(R.id.iv_user_comment);
            titleComment=itemView.findViewById(R.id.tv_name_comment);
            description=itemView.findViewById(R.id.tv_description_comment);
            time=itemView.findViewById(R.id.tv_time_comment);
        }
    }

    private String getData(long time){
        Calendar calendar=Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String data= DateFormat.format("hh:mm",calendar).toString();


        return data;

    }}
