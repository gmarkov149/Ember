package com.ember.ember;

import com.ember.ember.adapter.ChatRecycler;
import com.ember.ember.async.PollChatAsyncTask;
import com.ember.ember.model.Chat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PollChatAsyncTaskTest {
    @Mock
    RecyclerView recyclerViewMock;

    @Mock
    ChatRecycler chatRecyclerMock;

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void PollChatAsyncTask_isCorrect() throws java.text.ParseException {
        PollChatAsyncTask pollChatAsyncTask = new PollChatAsyncTask();
        List<String> allChats = Arrays.asList("2019-12-31|23:59:59|me||first message", "2020-01-01|00:00:00|other||second message");
        when(chatRecyclerMock.getItemCount()).thenReturn(10);
        when(recyclerViewMock.canScrollVertically(1)).thenReturn(false);
        List<Chat> expectedChats = Arrays.asList(new Chat(true, "first message", df.parse("2019-12-31 23:59:59")),
                new Chat(false, "second message", df.parse("2020-01-01 00:00:00")));

        pollChatAsyncTask.addToChat(allChats, recyclerViewMock, chatRecyclerMock, "me");

        verify(chatRecyclerMock).addToChat(expectedChats);
        verify(recyclerViewMock).scrollToPosition(9);
    }

    @Test(expected = java.text.ParseException.class)
    public void PollChatAsyncTask_throwsParseExceptionWhenInvalidDate() throws java.text.ParseException {
        PollChatAsyncTask pollChatAsyncTask = new PollChatAsyncTask();
        List<String> allChats = Arrays.asList("2019/12/31|23:59:59|me||first message");
        pollChatAsyncTask.addToChat(allChats, recyclerViewMock, chatRecyclerMock, "me");
    }

    @Test
    public void PollChatAsyncTask_handlesEmptyList() throws java.text.ParseException {
        PollChatAsyncTask pollChatAsyncTask = new PollChatAsyncTask();
        List<String> allChats = new ArrayList<>();
        when(recyclerViewMock.canScrollVertically(1)).thenReturn(true);
        pollChatAsyncTask.addToChat(allChats, recyclerViewMock, chatRecyclerMock, "me");
        verify(chatRecyclerMock).addToChat(new ArrayList<>());
        verify(recyclerViewMock, never()).scrollToPosition(anyInt());
    }
}