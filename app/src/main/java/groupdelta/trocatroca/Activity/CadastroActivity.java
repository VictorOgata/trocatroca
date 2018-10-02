package groupdelta.trocatroca.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import groupdelta.trocatroca.R;

import static android.widget.Toast.*;

public class CadastroActivity extends AppCompatActivity {


    /* Nickname edit text*/
    private TextInputLayout mNickEditText;
    /* E-mail edit text */
    private TextInputLayout mEmailEditText;
    private TextInputEditText mEmailEditTextEdit;
    /* Password edit text */
    private TextInputLayout mPasswordEditText;
    /* Confirm Password edit text */
    private TextInputLayout mConfPassEditText;
    private Button btnProximo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        /*Obtaining the references to the views from the XML.*/
        mNickEditText = findViewById(R.id.edtApelidoCadastro);
        mEmailEditText = findViewById(R.id.edtEmailCadastro);
        mEmailEditTextEdit = findViewById(R.id.edtEmailCadastro_edit);

        Intent intentMA = getIntent();
        String emailReceived = (String) intentMA.getStringExtra("email");
        if(!emailReceived.toString().isEmpty()) mEmailEditTextEdit.setText(emailReceived);

        mPasswordEditText = findViewById(R.id.edtSenhaCadastro);
        mConfPassEditText = findViewById(R.id.edtConfSenha);

        btnProximo=findViewById(R.id.btnOk);

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking empty fields
                if(!mNickEditText.getEditText().toString().trim().isEmpty() &&
                    !mEmailEditText.getEditText().toString().trim().isEmpty()&&
                    !mPasswordEditText.getEditText().toString().trim().isEmpty() &&
                    !mConfPassEditText.getEditText().toString().trim().isEmpty()){
                    //Checking the password confirmation
                    if(mPasswordEditText.getEditText().getText().toString().trim().equals(mConfPassEditText.getEditText().getText().toString().trim())){
                        //Calling Main screen
                        Class destinationClass = CadastroP2Activity.class;
                        Intent intentToStartR2 = new Intent(CadastroActivity.this, destinationClass);
                        intentToStartR2.putExtra("nick", mNickEditText.getEditText().getText().toString().trim());
                        intentToStartR2.putExtra("email", mEmailEditText.getEditText().getText().toString().trim());
                        intentToStartR2.putExtra("pass", mPasswordEditText.getEditText().getText().toString().trim());
                        startActivity(intentToStartR2);
                    }else {
                        Toast.makeText(CadastroActivity.this,(String)"Senha não confirmada.", LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(CadastroActivity.this,(String)"Campos não preenchidos.", LENGTH_LONG).show();
                }
            }
        });
    }
    /* Tralhão cagado
    public void onOkButtonClicked(View view) {
        Context context=this;
        int verificacao = 1;
        boolean confirma = false;

        // Baguio zuadasso do Tone
        confirma = confirmaDados(view);
        if(confirma){
            Toast.makeText(context,(String)"Confirmando...", LENGTH_LONG).show();
            //Calling Main screen
            Class destinationClass = CadastroP2Activity.class;
            Intent intentToStartR2 = new Intent(context, destinationClass);
            intentToStartR2.putExtra("nick", mNickEditText.getEditText().getText().toString().trim());
            intentToStartR2.putExtra("email", mEmailEditText.getEditText().getText().toString().trim());
            intentToStartR2.putExtra("pass", mPasswordEditText.getEditText().getText().toString().trim());
            startActivity(intentToStartR2);
        } else Toast.makeText(context,(String)"Campos não preenchidos corretamente.", LENGTH_LONG).show();

        //codigo antigo:
        //Checking non informed parameters
        if(!mNickEditText.getEditText().toString().trim().isEmpty() && !mEmailEditText.getEditText().toString().trim().isEmpty()&&
        !mPasswordEditText.getEditText().toString().trim().isEmpty() && !mConfPassEditText.getEditText().toString().trim().isEmpty()){
            //Checking the password confirmation
            if(mPasswordEditText.getEditText().getText().toString().trim().equals(mConfPassEditText.getEditText().getText().toString().trim())){
                //Calling Main screen
                Class destinationClass = CadastroP2Activity.class;
                Intent intentToStartR2 = new Intent(context, destinationClass);
                intentToStartR2.putExtra("nick", mNickEditText.getEditText().getText().toString().trim());
                intentToStartR2.putExtra("email", mEmailEditText.getEditText().getText().toString().trim());
                intentToStartR2.putExtra("pass", mPasswordEditText.getEditText().getText().toString().trim());
                startActivity(intentToStartR2);
            }else {
                Toast.makeText(context,(String)"Senha não confirmada.", LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(context,(String)"Campos não preenchidos.", LENGTH_LONG).show();
        }
    }

     Continuação do baguio zuadasso do Tone
    private boolean erroEmail(){
        String email = mEmailEditText.getEditText().getText().toString().trim();

        if(email.isEmpty()){
            mEmailEditText.setError("Adicione um email");
            return false;
        } else {
            mEmailEditText.setError(null);
            mEmailEditText.setErrorEnabled(false);
            return true;
        }
    }
    private boolean erroApelido(){
        String nome = mNickEditText.getEditText().getText().toString().trim();

        if(nome.isEmpty()){
            mNickEditText.setError("Adicione um apelido");
            return false;
        } else {
            mNickEditText.setError(null);
            mNickEditText.setErrorEnabled(false);
            return true;
        }
    }
    private boolean erroSenha(){
        String senha = mPasswordEditText.getEditText().getText().toString().trim();
        String confSenha = mConfPassEditText.getEditText().getText().toString().trim();

        if(senha.isEmpty()){
            mPasswordEditText.setError("Adicione uma senha");
            return false;
        }else if(confSenha.isEmpty()){
            mConfPassEditText.setError("Adicione a confirmação de senha");
            return false;
        } else if(!senha.equals(confSenha)){
            mConfPassEditText.setError("As senhas não conferem");
            return false;
        } else {
            mPasswordEditText.setError(null);
            mConfPassEditText.setError(null);
            mPasswordEditText.setErrorEnabled(false);
            mConfPassEditText.setErrorEnabled(false);
            return true;
        }
    }
    public boolean confirmaDados(View v){
        if(!erroApelido() | !erroEmail() | !erroSenha()){
            return false;
        }
        return true;
    }*/
}
