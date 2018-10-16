package com.ember.ember.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ember.ember.R;
import com.ember.ember.model.Chat;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ChatRecycler extends RecyclerView.Adapter<ChatRecycler.ChatViewHolder> {
    private List<Chat> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ChatViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    /**
     * add all chat objects in chat to the mDataset, and update the ui
     * @param chats chats to add to the ui
     */
    public void addToChat(List<Chat> chats) {
        mDataset.addAll(chats);
        notifyDataSetChanged();
    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public ChatRecycler(List<Chat> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ChatRecycler.ChatViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_text, parent, false);
        ChatViewHolder vh = new ChatViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        Chat message = mDataset.get(position);
        holder.mTextView.setText(message.getMessage());
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.mTextView.getLayoutParams();
        params.setMargins(message.isOutgoing() ? 520 : 10, 10, 10, 10);
        holder.mTextView.setLayoutParams(params);
        holder.mTextView.setBackgroundResource(message.isOutgoing() ? R.drawable.chat_border_right : R.drawable.chat_border_left);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}