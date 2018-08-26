package groupdelta.trocatroca;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CadastroActivity extends AppCompatActivity {

    /* Nickname edit text*/
    private EditText mNickEditText;

    /* E-mail edit text */
    private EditText mEmailEditText;

    /* Password edit text */
    private EditText mPasswordEditText;

    /* Personal data Text View */
    private TextView mPersonalDataTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        /*Obtaining the references to the views from the XML.*/
        mNickEditText = findViewById(R.id.edtApelidoadastro);
        mEmailEditText = findViewById(R.id.edtEmailCadastro);
        Intent intentMA = getIntent();
        String emailReceived = (String) intentMA.getStringExtra("email");
        mEmailEditText.setText(emailReceived);
        mPasswordEditText = findViewById(R.id.edtSenhaCadastro);
        mPersonalDataTextView = findViewById(R.id.edtData);
    }

    public void onOkButtonClicked(View view) {
    }
}
