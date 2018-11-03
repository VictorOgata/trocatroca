package groupdelta.trocatroca.DataAccessObject;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import groupdelta.trocatroca.Entities.Chat;

import static android.widget.Toast.LENGTH_LONG;

public class ChatDAO extends DbConection{
    private final String CHAT_ENTITY = "Chat";

    public ChatDAO() {
        super();
    }

    public void saveNewChat(Context context, Chat chat, String chatID){
        if (getFirebaseAuth().getCurrentUser() != null) {
            getFirebaseReference().child(CHAT_ENTITY).child(chatID).setValue(chat);
        }
        else{
            Toast.makeText(context, (String) "Usuario nao autenticado.", LENGTH_LONG).show();
        }
    }

    public void updateChatInfo(Chat chat, String chatID){
        getFirebaseReference().child(CHAT_ENTITY).child(chatID).setValue(chat);
    }

    public void deleteChat(String chatID){
        getFirebaseReference().child(CHAT_ENTITY).child(chatID).removeValue();
    }

    public DatabaseReference makeFbInstanceReference(){
        return getFirebaseInstance().getReference(CHAT_ENTITY);
    }

    public String getCHAT_ENTITY() {
        return CHAT_ENTITY;
    }
}
