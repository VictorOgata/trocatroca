package groupdelta.trocatroca.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import groupdelta.trocatroca.DataAccessObject.Conexao;
import groupdelta.trocatroca.Entities.Usuario;
import groupdelta.trocatroca.R;

import static android.widget.Toast.*;

public class CadastroActivity extends AppCompatActivity {

    /* Name edit text*/
    private EditText mNameEditText;
    /* Nickname edit text*/
    private EditText mNickEditText;
    /* E-mail edit text */
    private EditText mEmailEditText;
    /* Password edit text */
    private EditText mPasswordEditText;
    /* Confirm Password edit text */
    private EditText mConfPassEditText;
    /* Personal data Text View */
    private EditText mPhoneEditText;
    /* User class */
    private Usuario user;
    /* Firebase conection */
    private FirebaseAuth autentication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        /*Obtaining the references to the views from the XML.*/
        mNameEditText = findViewById(R.id.edtNomeCadastro);
        mNickEditText = findViewById(R.id.edtApelidoCadastro);
        mEmailEditText = findViewById(R.id.edtEmailCadastro);
        Intent intentMA = getIntent();
        String emailReceived = (String) intentMA.getStringExtra("email");
        mEmailEditText.setText(emailReceived);
        mPasswordEditText = findViewById(R.id.edtSenhaCadastro);
        mConfPassEditText = findViewById(R.id.edtConfSenha);
        mPhoneEditText = findViewById(R.id.edtPhoneCadastro);
    }

    public void onOkButtonClicked(View view) {
        Context context=this;
        /*Checking the password confirmation*/
        if(mPasswordEditText.getText().toString().equals(mConfPassEditText.getText().toString())){

            user= new Usuario();
            /* Pulling information from screen through references*/
            user.setName(mNameEditText.getText().toString());
            user.setNick(mNickEditText.getText().toString());
            user.setEmail(mEmailEditText.getText().toString());
            user.setPassword(mPasswordEditText.getText().toString());
            user.setPhone(mPhoneEditText.getText().toString());

            cadastrarNovoUsuario();
            /*Calling Main screen*/
            Class destinationClass = MainActivity.class;
            Intent intentToStartMainActivity = new Intent(context, destinationClass);
            startActivity(intentToStartMainActivity);
        }else{
            Toast.makeText(context,(String)"Senha não confirmada.", LENGTH_LONG).show();
        }
    }

    private void cadastrarNovoUsuario(){
        Activity activity=this;
        final Context context=this;
        autentication = Conexao.getFirebaseAuth();
        autentication.createUserWithEmailAndPassword(
                user.getEmail(),
                user.getPassword()).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(context, (String) "Cadastrado com sucesso.", LENGTH_LONG).show();
                    user.saveUser();
                }else{
                    String errorAuth="";
                    try{
                        throw task.getException();
                    }catch(FirebaseAuthUserCollisionException e){
                        errorAuth="E-mail já cadastrado no sistema.";
                    }catch(Exception e){
                        errorAuth="Erro ao efetuar o cadastro";
                        e.printStackTrace();
                    }
                    Toast.makeText(context, errorAuth, LENGTH_LONG).show();
                }
            }
        });
    }
}
