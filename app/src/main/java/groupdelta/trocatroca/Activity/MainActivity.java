package groupdelta.trocatroca.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.support.annotation.Nullable;

import groupdelta.trocatroca.R;


public class MainActivity extends AppCompatActivity {

    /* E-mail edit text */
    private EditText mEmailEditText;

    /* Password edit text */
    private EditText mPasswordEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Obtaining the references to the views from the XML.*/
        mEmailEditText = findViewById(R.id.edtEmail);
        mPasswordEditText = findViewById(R.id.edSenha);
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
    }
}
