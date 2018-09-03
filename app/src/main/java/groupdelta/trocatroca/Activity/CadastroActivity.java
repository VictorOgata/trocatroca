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



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        /*Obtaining the references to the views from the XML.*/
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
        int verificacao = 1;

        //verifica campo do apelido
        if(mNickEditText.getText().toString().equals("")) { verificacao = 0; }
        //verifica campo do email
        if(mEmailEditText.getText().toString().equals("")){ verificacao = 0; }
        //verificacao do telefone
        if(mPhoneEditText.getText().toString().equals("")){ verificacao = 0; }
        //verificacao do campo da senha
        if(mPasswordEditText.getText().toString().equals("")){ verificacao = 0; }

        if(verificacao == 1){
            if(mPasswordEditText.getText().toString().equals(mConfPassEditText.getText().toString())){
                /*Calling Main screen*/
                Class destinationClass = CadastroP2Activity.class;
                Intent intentToStartR2 = new Intent(context, destinationClass);
                intentToStartR2.putExtra("nick",mNickEditText.getText().toString());
                intentToStartR2.putExtra("email",mEmailEditText.getText().toString());
                intentToStartR2.putExtra("pass",mPasswordEditText.getText().toString());
                intentToStartR2.putExtra("phone",mPhoneEditText.getText().toString());
                startActivity(intentToStartR2);
            } else {
                Toast.makeText(context, (String) "Campos de senha inválidos.", LENGTH_LONG).show();
            }
        } else if(verificacao == 0){
            Toast.makeText(context,(String)"Campos incompletos.", LENGTH_LONG).show();
        }
    }


}
