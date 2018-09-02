package groupdelta.trocatroca.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import groupdelta.trocatroca.DataAccessObject.Conexao;
import groupdelta.trocatroca.Entities.Usuario;
import groupdelta.trocatroca.R;

public class PerfilActivity extends AppCompatActivity{


    private EditText Username;
    private EditText Email;
    private EditText Password;
    private EditText Tel;
    private EditText State;
    private EditText City;
    DatabaseReference referenciaFirebase = Conexao.getFirebaseReference();
    FirebaseAuth aut;
    FirebaseUser firebaseUser = aut.getInstance().getCurrentUser();
    String email = firebaseUser.getEmail();
    String uid = firebaseUser.getUid();
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
    }
    public void onModifyUserNameButtonClicked(View view) {

    }
}

