package com.mostafabor3e.mychat.Activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mostafabor3e.mychat.R;
import com.mostafabor3e.mychat.model.Poast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.PostHolder> {

    private   Context mcontext;
private List<Poast>poasts;
onclicklisener onclicklisener;
onclickShare onclickShare;

    public AdapterPost(Context mcontext, List<Poast> poasts,
                       AdapterPost.onclicklisener onclicklisener,
                       AdapterPost.onclickShare onclickShare)
    {


        this.mcontext = mcontext;
        this.poasts = poasts;
        this.onclicklisener = onclicklisener;
        this.onclickShare = onclickShare;
    }

    public interface onclicklisener{
        void onclick(View v,int potion);
    }
    public interface onclickShare{
        void onShare(View v,int potion);
    }
    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.row_poast_custom,null,false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostHolder holder, final int position) {
        holder.titlePost.setText(poasts.get(position).getTitle());
        Glide.with(mcontext).load(poasts.get(position).getPostPhoto()).into(holder.postPhoto);
        Glide.with(mcontext).load(poasts.get(position).getUserPhoto()).into(holder.userPostPhoto);
        holder.postPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclicklisener.onclick(v,position);
            }
        });
        holder.noLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.love.setVisibility(View.VISIBLE);
                holder.noLove.setVisibility(View.INVISIBLE);

            }
        });
        holder.love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.noLove.setVisibility(View.VISIBLE);
                holder.love.setVisibility(View.INVISIBLE);

            }
        });
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclicklisener.onclick(v,position);
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                           onclickShare.onShare(v,position);
                }
                });


    }



    @Override
    public int getItemCount() {
        return poasts.size();
    }

    public static class PostHolder extends RecyclerView.ViewHolder {
        CircleImageView userPostPhoto;
        ImageView postPhoto,love,noLove,comment;
        TextView titlePost;
        ImageView  share;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            userPostPhoto=itemView.findViewById(R.id.imageView3);
            postPhoto=itemView.findViewById(R.id.row_iv_post);
            titlePost=itemView.findViewById(R.id.textView_row);
            love=itemView.findViewById(R.id.iv_love_visible);
            noLove=itemView.findViewById(R.id.iv_love_invisible);
            comment=itemView.findViewById(R.id.imageView4);
            share=itemView.findViewById(R.id.imageView5);


        }
    }
}
