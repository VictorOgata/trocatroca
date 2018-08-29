package groupdelta.trocatroca.Entities;

import com.google.firebase.database.DatabaseReference;

import java.security.CryptoPrimitive;

import groupdelta.trocatroca.DataAccessObject.Conexao;

public class Usuario {

    private String name;
    private String nick;
    private String email;
    private String password;
    private String phone;

    public Usuario() {
    }

    public void saveUser(){
        DatabaseReference referenciaFirebase = Conexao.getFirebaseReference();
        referenciaFirebase.child("User").push().setValue(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
