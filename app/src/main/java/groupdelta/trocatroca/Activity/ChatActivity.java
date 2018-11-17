package groupdelta.trocatroca.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;
import java.util.List;

import groupdelta.trocatroca.DataAccessObject.ChatDAO;
import groupdelta.trocatroca.DataAccessObject.MessageDAO;
import groupdelta.trocatroca.DataAccessObject.UserDAO;
import groupdelta.trocatroca.Entities.Chat;
import groupdelta.trocatroca.Entities.ChatAdapter;
import groupdelta.trocatroca.Entities.Message;
import groupdelta.trocatroca.Entities.Trade;
import groupdelta.trocatroca.Entities.User;
import groupdelta.trocatroca.R;

public class ChatActivity extends AppCompatActivity {
        private RecyclerView mMessageRecycler;
        private ChatAdapter mMessageAdapter;
        private List<Message> mMessageList = new ArrayList<>();
        private EditText message;
        private Button btn;
        private Context context = this;
        private Message mens = new Message();
        private String tradeID;
        private ChatDAO chatDAO;
        private UserDAO userDAO;
        private MessageDAO messageDAO = new MessageDAO();
        //private TextView teste;

    @Override

        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            tradeID = bundle.getString("IDtrade");
            setContentView(R.layout.activity_chat);
            //teste = findViewById(R.id.josue);
            message = findViewById(R.id.edittext_chatbox);
            btn = findViewById(R.id.button_chatbox_send);
            mMessageRecycler =  findViewById(R.id.reyclerview_message_list);
            chatDAO = new ChatDAO();
            chatDAO.startFirebaseAuth();
            chatDAO.getFirebaseReference().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot!=null) {
                        Chat cInfo = new Chat();
                        userDAO = new UserDAO();
                        userDAO.startFirebaseAuth();
                        String uid = userDAO.getFirebaseUser().getUid();
                        String cid = dataSnapshot.child("Troca").child(tradeID).getValue(Trade.class).getChatID();
                        mens.setChatid(cid);
                       for (DataSnapshot postSnapshot: dataSnapshot.child("Chat").child(cid).child("Mensagens").getChildren()) {
                            mMessageList.add(postSnapshot.getValue(Message.class));
                        }
                        if(dataSnapshot.child("Chat").child(cid).getValue(Chat.class).getUserID1().equals(uid)) {
                            cInfo.setUserID1(dataSnapshot.child("Chat").child(cid).getValue(Chat.class).getUserID1());
                            cInfo.setUserID2(dataSnapshot.child("Chat").child(cid).getValue(Chat.class).getUserID2());
                            mens.setUserID(cInfo.getUserID1());
                        }
                        else{
                            cInfo.setUserID2(dataSnapshot.child("Chat").child(cid).getValue(Chat.class).getUserID1());
                            cInfo.setUserID1(dataSnapshot.child("Chat").child(cid).getValue(Chat.class).getUserID2());
                            mens.setUserID(cInfo.getUserID1());
                        }
                        }
                    }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }

            });
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message mens1 =new Message();
                    mens1.setUserID(mens.getUserID());
                    mens1.setUserMessage(message.getText().toString());
                    messageDAO.saveNewMessage(ChatActivity.this,mens1,mens.getChatid());
                    mMessageList.add(mens1);
                    mMessageAdapter.notifyDataSetChanged();

                }
            });
            mMessageAdapter = new ChatAdapter(this,mMessageList);
            mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
            mMessageRecycler.setAdapter(mMessageAdapter);

        }
    }