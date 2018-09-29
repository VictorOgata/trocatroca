package groupdelta.trocatroca.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import groupdelta.trocatroca.DataAccessObject.UserDAO;
import groupdelta.trocatroca.R;


public class MainActivity extends AppCompatActivity {

    /* E-mail edit text */
    private EditText mEmailEditText;

    /* Password edit text */
    private EditText mPasswordEditText;


    private EditText editEmail, editSenha;
    private Button btnLogar;
    private UserDAO userDAO;
    private Button btnRecuperar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDAO = new UserDAO();

        /*Obtaining the references to the views from the XML.*/
        mEmailEditText = findViewById(R.id.edtEmail);
        mPasswordEditText = findViewById(R.id.edSenha);
        btnLogar = findViewById(R.id.btnLogin);
        btnRecuperar = findViewById(R.id.btnRecuperar);

        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailEditText.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Entre com email, bicho !", Toast.LENGTH_SHORT).show();
                    return;
                }
                userDAO.getFirebaseAuth().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Olha o e-mail lá mano", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Fail to send reset password email!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }


    private void login(String email, String senha){
        if(!email.isEmpty() && !senha.isEmpty()) {
            userDAO.getFirebaseAuth().signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(MainActivity.this, HomescreenActivity.class);
                                startActivity(i);

                            } else
                                alert("E-mail ou Senha Errados ! ");
                        }
                    });
        }else
            alert("Campos não preenchidos.");
    }


    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    protected  void onStart (){
        super.onStart();
    }

    public void onRegisterButtonClicked(View view) {
        Context context = this;

        Class destinationClass = CadastroActivity.class;
        Intent intentToStartRegisterActivity = new Intent(context, destinationClass);
        String tS = mEmailEditText.getText().toString();
        intentToStartRegisterActivity.putExtra("email", mEmailEditText.getText().toString());
        startActivity(intentToStartRegisterActivity);
    }

    public void onLoginButtonClicked(View view) {
        if (mEmailEditText.getText().toString().isEmpty() || mPasswordEditText.getText().toString().isEmpty()){
            Toast.makeText(MainActivity.this, "Campos em Branco.Preencha todos os campos", Toast.LENGTH_SHORT).show();
        } else {
            String email = mEmailEditText.getText().toString().trim();
            String senha = mPasswordEditText.getText().toString().trim();
            login(email,senha);
        }


    }
}
