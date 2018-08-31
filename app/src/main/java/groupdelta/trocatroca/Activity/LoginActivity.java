package groupdelta.trocatroca.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import groupdelta.trocatroca.DataAccessObject.Conexao;
import groupdelta.trocatroca.R;


public class LoginActivity extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private Button btnLogar;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Obtaining the references to the views from the XML.*/
        editEmail = findViewById(R.id.edtEmail);
        editSenha = findViewById(R.id.edSenha);
        btnLogar = findViewById(R.id.btnLogin);
        eventoClicks();

    }

    private void eventoClicks(){

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();
                String senha = editSenha.getText().toString().trim();
                login(email,senha);

            }
        });

    }
    private void login(String email, String senha){
        auth.signInWithEmailAndPassword(email,senha)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent i = new Intent(LoginActivity.this,PerfilActivity.class);
                            startActivity(i);

                        }else{
                            alert("E-mail ou Senha Errados ! ");
                        }
                    }
                });
    }


    private void alert(String s){
        Toast.makeText(LoginActivity.this,s,Toast.LENGTH_SHORT).show();
    }

    protected  void onStart (){
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
}




