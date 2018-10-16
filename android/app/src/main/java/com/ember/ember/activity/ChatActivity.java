package com.ember.ember.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.View;

import com.ember.ember.R;
import com.ember.ember.adapter.ChatRecycler;
import com.ember.ember.helper.http.ErrorHelper;
import com.ember.ember.helper.http.HttpHelper;
import com.ember.ember.model.Chat;
import com.ember.ember.model.UserDetails;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private UserDetails me;
    private UserDetails other;
    private List<Chat> chats;
    private ChatRecycler chatRecycler;
    private Date lastUpdate;
    private DateFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        me = (UserDetails) getIntent().getSerializableExtra("user");
        other = (UserDetails) getIntent().getSerializableExtra("other");
        setTitle(other.getName());
        chats = new ArrayList<>();
        setRecycler();
        lastUpdate = null;
        df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        while (true) {
            try {
                updateChat();
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateChat() {
        Call<List<String>> call = HttpHelper.getChat(me.getUsername(), other.getUsername());
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    try {
                        List<String> newChats = response.body();
                        if (lastUpdate != null) {
                            newChats = findNewChats(newChats);
                        }
                        for (String chatStr : newChats) {
                            String[] chatArr = chatStr.split("\\|");
                            Date timestamp = df.parse(chatArr[0] + " " + chatArr[1]);
                            String sender = chatArr[2];
                            String message = chatArr[4];
                            Chat chat = new Chat(sender.equals(me.getUsername()), message, timestamp);
                            chatRecycler.addToChat(chat);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    ErrorHelper.raiseToast(ChatActivity.this, ErrorHelper.Problem.CALL_FAILED);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                ErrorHelper.raiseToast(ChatActivity.this, ErrorHelper.Problem.CALL_FAILED);
            }
        });
    }

    private List<String> findNewChats(List<String> newChats) throws ParseException {
        int i;
        for (i = newChats.size() - 1; i >= 0; i--) {
            String[] curChat = newChats.get(i).split("\\|");
            if (df.parse(curChat[0] + " " + curChat[1]).before(lastUpdate)) {
                break;
            }
        }
        return newChats.subList(i, newChats.size());
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
