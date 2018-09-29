package groupdelta.trocatroca.DataAccessObject;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;

import groupdelta.trocatroca.Entities.Usuario;

import static android.widget.Toast.LENGTH_LONG;

public class UserDAO extends Conexao{

    public UserDAO() {
        super();
    }

    public void saveNewUser(final Context context, Usuario user){
        FirebaseUser firebaseUser = getFirebaseAuth().getCurrentUser();
        if (firebaseUser != null) {
            String uid = firebaseUser.getUid();
            getFirebaseReference().child("Usuarios").child(uid).setValue(user);
        }
        else{
            Toast.makeText(context, (String) "Usuario nao autenticado.", LENGTH_LONG).show();
        }
    }

    public void updateUser(Usuario user){
        getFirebaseReference().child("Usuarios").child(getFirebaseUser().getUid().toString()).setValue(user);
    }

    public HashMap<String,String> loadUserHashMap(DataSnapshot ds){
        return (HashMap<String, String>) ds.child("Usuarios").child(this.getFirebaseUser().getUid().toString()).getValue();
    }

}
