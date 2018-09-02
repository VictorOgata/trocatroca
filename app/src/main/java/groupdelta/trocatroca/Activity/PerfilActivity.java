package groupdelta.trocatroca.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import groupdelta.trocatroca.DataAccessObject.Conexao;
import groupdelta.trocatroca.Entities.UserInformation;
import groupdelta.trocatroca.Entities.Usuario;
import groupdelta.trocatroca.R;

public class PerfilActivity extends AppCompatActivity{

    private EditText Username;
    private EditText Email;
    private EditText Password;
    private EditText Tel;
    private EditText State;
    private EditText City;
    FirebaseAuth aut;
    FirebaseUser firebaseUser = aut.getInstance().getCurrentUser();
    String email = firebaseUser.getEmail();
    String uid = firebaseUser.getUid();
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private  String userID;
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Username = findViewById(R.id.Name);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        Tel = findViewById(R.id.Telephone);
        State = findViewById(R.id.State);
        City = findViewById(R.id.City);
        Password.setText("**********");
        Email.setText(email);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            UserInformation uInfo = new UserInformation();
            uInfo.setName(ds.child(uid).getValue(Usuario.class).getNick()); //set the name
            uInfo.setCity(ds.child(uid).getValue(Usuario.class).getCity()); //set the city
            uInfo.setState(ds.child(uid).getValue(Usuario.class).getState()); //set the city
            uInfo.setPhone_num(ds.child(uid).getValue(Usuario.class).getPhone()); //set the phone_num

            //display all the information
            Tel.setText(uInfo.getPhone_num());
            State.setText(uInfo.getState());
            City.setText(uInfo.getCity());
            Username.setText(uInfo.getName());
        }
    }


    public void onModifyUserNameButtonClicked(View view) {
     Conexao.getFirebaseReference().child("Usuarios").child(uid).child("nick").setValue(Username.getText().toString());
    }

    public void onModifyEmailButtonClicked(View view) {
        firebaseUser.updateEmail(Email.getText().toString());
        Conexao.getFirebaseReference().child("Usuarios").child(uid).child("email").setValue(Email.getText().toString());
    }

    public void onModifyPasswordButtonClicked(View view) {
        firebaseUser.updatePassword(Password.getText().toString());
    }

    public void onModifyPhoneButtonClicked(View view) {
        Conexao.getFirebaseReference().child("Usuarios").child(uid).child("phone").setValue(Tel.getText().toString());
    }
    public void onModifyStateButtonClicked(View view) {
        Conexao.getFirebaseReference().child("Usuarios").child(uid).child("state").setValue(State.getText().toString());
    }
    public void onModifyCityButtonClicked(View view) {
        Conexao.getFirebaseReference().child("Usuarios").child(uid).child("city").setValue(City.getText().toString());
    }

}

