package groupdelta.trocatroca.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import groupdelta.trocatroca.R;

public class NovoAnuncioActivity extends AppCompatActivity {
    private String[] itens = new String[] {"Game of Thrones", "Age of Empires", "Prototype", "God of War", "God of War2", "god of war", "game of Throne2"};

    /* Nickname edit text*/
    private EditText mNickEditText;
    /* E-mail edit text */
    private EditText mEmailEditText;
    /* Password edit text */
    private EditText mPasswordEditText;
    /* Confirm Password edit text */
    private EditText mConfPassEditText;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novoanuncio);


        /*Obtaining the references to the views from the XML.*/
        mNickEditText = findViewById(R.id.edtApelidoCadastro);
        mEmailEditText = findViewById(R.id.edtEmailCadastro);
        Intent intentMA = getIntent();
        String emailReceived = (String) intentMA.getStringExtra("email");
        mEmailEditText.setText(emailReceived);
        mPasswordEditText = findViewById(R.id.edtSenhaCadastro);
        mConfPassEditText = findViewById(R.id.edtConfSenha);
    }
}
