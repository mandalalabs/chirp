package com.mandalalabs.chirp.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mandalalabs.chirp.R;
import com.mandalalabs.chirp.fragment.OnListFragmentInteractionListener;
import com.mandalalabs.chirp.utils.Constants;
import com.mandalalabs.chirp.utils.Contact;
import com.parse.ParseObject;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ParseObject} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class NeighborsListAdapter extends RecyclerView.Adapter<NeighborsListAdapter.ViewHolder> {

    Context context;
    List<ParseObject> neighbors;
    private final OnListFragmentInteractionListener mListener;

    public NeighborsListAdapter(List<ParseObject> neighbors, OnListFragmentInteractionListener listener) {
        this.neighbors = neighbors;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View neighborsView = inflater.inflate(R.layout.fragment_neighbors, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(neighborsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ParseObject neighbor = neighbors.get(position);

        Contact contact = getRandomContact();
        holder.ivNeighborPic.setImageResource(contact.getThumbnailDrawable());
//        Glide.with(context)
//                .load("http://some.profile.pic/url")
//                .asBitmap()
//                .centerCrop()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(holder.ivNeighborPic);
        holder.ivNeighborPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Constants.LOG_TAG, "Neighbor profile pic clicked at position: " + position);
//                Intent intent = new Intent(context, ProfileActivity.class);
//                intent.putExtra(Constants.keyScreenName, tweet.getUser().getHandle());
//                context.startActivity(intent);
            }
        });
        holder.tvNeighborName.setText(contact.getName());  // neighbor.get("firstName") != null ? neighbor.get("firstName").toString() : "Next door neighbor");
    }

    // Returns a random contact
    public Contact getRandomContact() {

        Resources resources = context.getResources();

        TypedArray contactNames = resources.obtainTypedArray(R.array.contact_names);
        int name = (int) (Math.random() * contactNames.length());

        TypedArray contactThumbnails = resources.obtainTypedArray(R.array.contact_thumbnails);
        int thumbnail = (int) (Math.random() * contactThumbnails.length());

        TypedArray contactNumbers = resources.obtainTypedArray(R.array.contact_numbers);
        int number = (int) (Math.random() * contactNumbers.length());

        return new Contact(contactNames.getString(name), contactThumbnails.getResourceId(thumbnail, R.drawable.contact_one), contactNumbers.getString(number));
    }

    @Override
    public int getItemCount() {
        return neighbors == null ? 0 : neighbors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivNeighborPic;
        TextView tvNeighborName;

        public ViewHolder(View convertView) {
            super(convertView);

            ivNeighborPic = (ImageView) convertView.findViewById(R.id.ivNeighborPic);
            ivNeighborPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(Constants.LOG_TAG, "I know that profile pic is clicked.");
                }
            });
            tvNeighborName = (TextView) convertView.findViewById(R.id.tvNeighborName);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvNeighborName + "'";
        }
    }
}
