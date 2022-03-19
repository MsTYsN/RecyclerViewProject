package com.mestaoui.rvproject.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mestaoui.rvproject.R;
import com.mestaoui.rvproject.beans.Actor;
import com.mestaoui.rvproject.service.ActorService;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ActorViewHolder> implements Filterable {
    private static final String TAG = "ActorAdapter";
    private List<Actor> actors;
    private List<Actor> actorsFilter;
    private LayoutInflater inflater;
    private Context context;
    private NewFilter mfilter;

    public ActorAdapter(Context context, List<Actor> actors) {
        this.context = context;
        this.actors = actors;
        this.actorsFilter = new ArrayList<>();
        this.actorsFilter.addAll(actors);
        this.inflater = LayoutInflater.from(context);
        mfilter = new NewFilter(this);
    }


    @NonNull
    @Override
    public ActorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.actor_item, parent, false);
        return new ActorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorViewHolder holder, int position) {
        holder.fullName.setText(actorsFilter.get(position).getFullName());
        holder.idA.setText(actorsFilter.get(position).getId()+"");
        holder.rating.setRating(actorsFilter.get(position).getStar());
        Glide
                .with(context)
                .load(Uri.parse(actorsFilter.get(position).getImg()))
                .centerCrop()
                .apply(new RequestOptions().override(120, 120))
                .into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popup = LayoutInflater.from(context).inflate(R.layout.actor_star_item, null,
                        false);
                final CircleImageView img = popup.findViewById(R.id.imageE);
                final RatingBar bar = popup.findViewById(R.id.ratingE);
                final TextView idA = popup.findViewById(R.id.idAE);
                final TextView fullName = popup.findViewById(R.id.fullNameE);
                Bitmap bitmap =
                        ((BitmapDrawable)((CircleImageView)v.findViewById(R.id.image)).getDrawable()).getBitmap();
                img.setImageBitmap(bitmap);
                bar.setRating(((RatingBar)v.findViewById(R.id.rating)).getRating());
                idA.setText(((TextView)v.findViewById(R.id.idA)).getText().toString());
                fullName.setText(((TextView)v.findViewById(R.id.fullName)).getText().toString());
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Notez : ")
                        .setMessage("Donner une note entre 1 et 5 :")
                        .setView(popup)
                        .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                float s = bar.getRating();
                                int ids = Integer.parseInt(idA.getText().toString());
                                Actor actor = ActorService.getInstance().findById(ids);
                                actor.setStar(s);
                                ActorService.getInstance().update(actor);
                                notifyItemChanged(holder.getAdapterPosition());
                            }
                        })
                        .setNegativeButton("Annuler", null)
                        .create();
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return actorsFilter.size();
    }

    public Actor removeActorAt(int position) {
        return actorsFilter.remove(position);
    }

    @Override
    public Filter getFilter() {
        return mfilter;
    }

    public class ActorViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView fullName,idA;
        RatingBar rating;
        public ActorViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            fullName = itemView.findViewById(R.id.fullName);
            idA = itemView.findViewById(R.id.idA);
            rating = itemView.findViewById(R.id.rating);
        }
    }

    public class NewFilter extends Filter {
        public RecyclerView.Adapter mAdapter;
        public NewFilter(RecyclerView.Adapter mAdapter) {
            super();
            this.mAdapter = mAdapter;
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            actorsFilter.clear();
            final FilterResults results = new FilterResults();
            if (charSequence.length() == 0) {
                actorsFilter.addAll(actors);
            } else {
                final String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Actor a : actors) {
                    if (a.getFullName().toLowerCase().startsWith(filterPattern)) {
                        actorsFilter.add(a);
                    }
                }
            }
            results.values = actorsFilter;
            results.count = actorsFilter.size();
            return results;
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            actorsFilter = (List<Actor>) filterResults.values;
            this.mAdapter.notifyDataSetChanged();
        }
    }

}
