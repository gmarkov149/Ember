package com.ember.ember.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ember.ember.R;
import com.ember.ember.adapter.ChatRecycler;
import com.ember.ember.model.Chat;
import com.ember.ember.model.UserDetails;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private UserDetails me;
    private UserDetails other;
    private List<Chat> chats;
    private ChatRecycler chatRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        me = (UserDetails) getIntent().getSerializableExtra("user");
        other = (UserDetails) getIntent().getSerializableExtra("other");
        setTitle(other.getName());
        chats = new ArrayList<>();
        setRecycler();
    }

    public void send(View v) {
        TextInputEditText toSend = findViewById(R.id.message);
        if (toSend.getText().toString().trim().length() == 0) {
            return;
        }
        chatRecycler.addToChat(new Chat(true, toSend.getText().toString()));
        toSend.setText("");
    }

    private void setRecycler() {
        RecyclerView mRecyclerView = findViewById(R.id.chat_recycler);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        chatRecycler = new ChatRecycler(chats);
        mRecyclerView.setAdapter(chatRecycler);
    }
}
