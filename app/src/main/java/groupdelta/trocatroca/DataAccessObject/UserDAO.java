package groupdelta.trocatroca.DataAccessObject;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;

import groupdelta.trocatroca.Entities.User;

import static android.widget.Toast.LENGTH_LONG;

public class UserDAO extends DbConection {

    public UserDAO() {
        super();
    }

    public void saveNewUser(final Context context, User user){
        FirebaseUser firebaseUser = getFirebaseAuth().getCurrentUser();
        if (firebaseUser != null) {
            String uid = firebaseUser.getUid();
            getFirebaseReference().child("Usuarios").child(uid).setValue(user);
        }
        else{
            Toast.makeText(context, (String) "User nao autenticado.", LENGTH_LONG).show();
        }
    }

    public void updateUser(User user){
        getFirebaseReference().child("Usuarios").child(getFirebaseUser().getUid().toString()).setValue(user);
    }

    public HashMap<String,String> loadUserHashMap(DataSnapshot ds){
        return (HashMap<String, String>) ds.child("Usuarios").child(this.getFirebaseUser().getUid().toString()).getValue();
    }

}
