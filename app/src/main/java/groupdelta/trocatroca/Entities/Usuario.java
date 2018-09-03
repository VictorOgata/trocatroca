package groupdelta.trocatroca.Entities;

import android.content.Context;
import android.text.Editable;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import groupdelta.trocatroca.DataAccessObject.Conexao;

import static android.widget.Toast.LENGTH_LONG;

public class Usuario {

    //User nickname
    private String nick;
    //User email
    private String email;
    //User contact info
    private String CInfo;
    //User state (Country area)
    private String state;
    //User city
    private String city;

    public Usuario() {
    }

    public void saveNewUser(Context context){
        DatabaseReference referenciaFirebase = Conexao.getFirebaseReference();
        FirebaseUser firebaseUser = Conexao.getFirebaseAuth().getCurrentUser();
        if (firebaseUser != null) {
            String uid = firebaseUser.getUid();
            if(!(this.nick.isEmpty() || this.email.isEmpty() || this.CInfo.isEmpty() || this.state.isEmpty() || this.city.isEmpty()))
                referenciaFirebase.child("Usuarios").child(uid).setValue(this);
            else
                Toast.makeText(context, (String) "Empty User Parameters.", LENGTH_LONG).show();
        }
        else{
            Toast.makeText(context, (String) "Usuario nao autenticado.", LENGTH_LONG).show();
        }
    }

    public String getNick() {
        return nick;
    }
    public String getEmail() {
        return email;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public String getCInfo() { return CInfo; }

    public void setNick(String nick) {
        this.nick = nick;
    }
    public void setCInfo(String CInfo) {
        this.CInfo = CInfo;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setCity(String city) {
        this.city = city;
    }
}
