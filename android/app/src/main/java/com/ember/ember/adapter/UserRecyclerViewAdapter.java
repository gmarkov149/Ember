package com.ember.ember.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ember.ember.R;
import com.ember.ember.fragment.UserListFragment.OnListFragmentInteractionListener;
import com.ember.ember.model.UserDetails;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * {@link RecyclerView.Adapter} that can display a {@link UserDetails} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder> {

    private final List<UserDetails> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final boolean isMatched;

    public UserRecyclerViewAdapter(List<UserDetails> items,
                                   OnListFragmentInteractionListener listener, boolean isMatched) {
        mValues = items;
        mListener = listener;
        this.isMatched = isMatched;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        UserDetails currentUser = mValues.get(position);
        Bitmap profilePic = mValues.get(position).getProfilePic();
        if (profilePic == null) {
            holder.thumbnail.setImageResource(R.drawable.round_account_circle_black_48);
        }
        else {
            holder.thumbnail.setImageBitmap(profilePic);
        }
        holder.name.setText(currentUser.getName());
        if (isMatched) {
            holder.match.setImageResource(R.drawable.ic_baseline_message_24px);
        }
        holder.match.setOnClickListener((View v) -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                if (isMatched) {
                    mListener.onListChatFragmentInteraction(holder.mItem);
                }
                else {
                    mListener.onListButtonFragmentInteraction(holder.mItem);
                }
            }
        });

        holder.mView.setOnClickListener((View v) ->  {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onListFragmentInteraction(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView thumbnail;
        public final TextView name;
        public final FloatingActionButton match;
        public UserDetails mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            thumbnail = view.findViewById(R.id.thumbnail);
            name = view.findViewById(R.id.name);
            match = view.findViewById(R.id.match);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
