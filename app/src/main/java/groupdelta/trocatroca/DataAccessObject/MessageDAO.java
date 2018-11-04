package groupdelta.trocatroca.DataAccessObject;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import groupdelta.trocatroca.Entities.Message;

import static android.widget.Toast.LENGTH_LONG;

public class MessageDAO extends ChatDAO{
    private final String MESSAGE_ENTITY = "Mensagens";

    public MessageDAO() {
        super();
    }

    public void saveNewMessage(Context context, Message message,String chatID){
        if (getFirebaseAuth().getCurrentUser() != null) {
            getFirebaseReference().child(super.getCHAT_ENTITY()).child(chatID).child(MESSAGE_ENTITY).push().setValue(message);
        }
        else{
            Toast.makeText(context, (String) "Usuario nao autenticado.", LENGTH_LONG).show();
        }
    }

    public void updateMessageInfo(Message message, String messageID, String chatID){
        getFirebaseReference().child(super.getCHAT_ENTITY()).child(chatID).child(MESSAGE_ENTITY).child(messageID).setValue(message);
    }

    public void deleteMessage(String messageID,String chatID){
        getFirebaseReference().child(super.getCHAT_ENTITY()).child(chatID).child(MESSAGE_ENTITY).child(messageID).removeValue();
    }

    public DatabaseReference makeFbInstanceReference(String chatID){
        return getFirebaseInstance().getReference(super.getCHAT_ENTITY()).child(chatID).child(MESSAGE_ENTITY);
    }
}
