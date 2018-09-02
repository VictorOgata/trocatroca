package groupdelta.trocatroca.Entities;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.security.CryptoPrimitive;

import groupdelta.trocatroca.DataAccessObject.Conexao;

import static android.widget.Toast.LENGTH_LONG;

public class Usuario {

    private String nick;
    private String email;
    private String phone;
    private String state;
    private String city;

    public Usuario() {
    }

    public void saveUser(Context context){
        DatabaseReference referenciaFirebase = Conexao.getFirebaseReference();
        FirebaseUser firebaseUser = Conexao.getFirebaseAuth().getCurrentUser();
        if (firebaseUser != null) {
            String uid = firebaseUser.getUid();
            referenciaFirebase.child("Usuarios").child(uid).child("nick").setValue(nick);
            referenciaFirebase.child("Usuarios").child(uid).child("email").setValue(email);
            referenciaFirebase.child("Usuarios").child(uid).child("phone").setValue(phone);
            referenciaFirebase.child("Usuarios").child(uid).child("state").setValue(state);
            referenciaFirebase.child("Usuarios").child(uid).child("city").setValue(city);
        }
        else{
            Toast.makeText(context, (String) "Usuario nao autenticado.", LENGTH_LONG).show();
        }
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setCity(String city) {
        this.city = city;
    }
}
