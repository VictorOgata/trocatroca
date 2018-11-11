package groupdelta.trocatroca.Activity;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import groupdelta.trocatroca.Entities.ChatAdapter;
import groupdelta.trocatroca.Entities.Message;
import groupdelta.trocatroca.R;

public class ChatAcivity extends AppCompatActivity {
        private RecyclerView mMessageRecycler;
        private ChatAdapter mMessageAdapter;
        private List<Message> mMessageList;
        @Override

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat);

            mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
            mMessageAdapter = new ChatAdapter(this,mMessageList);
            mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        }
    }