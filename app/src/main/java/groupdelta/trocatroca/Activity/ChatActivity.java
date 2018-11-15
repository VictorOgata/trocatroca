package groupdelta.trocatroca.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import groupdelta.trocatroca.DataAccessObject.UserDAO;
import groupdelta.trocatroca.Entities.ChatAdapter;
import groupdelta.trocatroca.Entities.Message;
import groupdelta.trocatroca.R;

public class ChatActivity extends AppCompatActivity {
        private RecyclerView mMessageRecycler;
        private ChatAdapter mMessageAdapter;
        private List<Message> mMessageList = new ArrayList<>();
        private EditText message;
        private Button btn;
        private Message mens = new Message();
        private UserDAO user;


    @Override

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat);
            message = findViewById(R.id.edittext_chatbox);
            btn = findViewById(R.id.button_chatbox_send);
            mMessageRecycler =  findViewById(R.id.reyclerview_message_list);
            mens.setTextMessage(message.getText().toString());
        mMessageList.add(mens);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                mMessageList.add(mens);
                }
            });
            mMessageAdapter = new ChatAdapter(this,mMessageList);
            mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        }
    }