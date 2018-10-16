package com.ember.ember.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.View;

import com.ember.ember.AsyncTasks.PollChatAsyncTask;
import com.ember.ember.R;
import com.ember.ember.adapter.ChatRecycler;
import com.ember.ember.helper.http.ErrorHelper;
import com.ember.ember.helper.http.HttpHelper;
import com.ember.ember.model.Chat;
import com.ember.ember.model.UserDetails;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private UserDetails me;
    private UserDetails other;
    private List<Chat> chats;
    private PollChatAsyncTask pollChatAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        me = (UserDetails) getIntent().getSerializableExtra("user");
        other = (UserDetails) getIntent().getSerializableExtra("other");
        setTitle(other.getName());
        chats = new ArrayList<>();
        RecyclerView recyclerView = setRecycler();
        pollChatAsyncTask = (PollChatAsyncTask) new PollChatAsyncTask().execute(recyclerView, me.getUsername(), other.getUsername(), this);
    }

    public void send(View v) {
        TextInputEditText toSend = findViewById(R.id.message);
        if (toSend.getText().toString().trim().length() == 0) {
            return;
        }
        Call<Void> call = HttpHelper.sendChat(new Chat(true, toSend.getText().toString(), new Date()), me.getUsername(), other.getUsername());
        toSend.setText("");
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    ErrorHelper.raiseToast(ChatActivity.this, ErrorHelper.Problem.CALL_FAILED);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ErrorHelper.raiseToast(ChatActivity.this, ErrorHelper.Problem.CALL_FAILED);
            }
        });
    }

    private RecyclerView setRecycler() {
        RecyclerView mRecyclerView = findViewById(R.id.chat_recycler);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(new ChatRecycler(chats));
        return mRecyclerView;
    }
}
