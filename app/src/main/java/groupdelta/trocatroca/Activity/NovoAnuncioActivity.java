package groupdelta.trocatroca.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import groupdelta.trocatroca.R;

import static android.widget.Toast.LENGTH_LONG;

public class NovoAnuncioActivity extends AppCompatActivity {
    private String[] itens = new String[] {"Game of Thrones", "Age of Empires", "Prototype", "God of War", "God of War2", "god of war", "game of Throne2"};

    /* Nickname edit text*/
    private EditText mItemEditText;
    /* E-mail edit text */
    private EditText mDesejadosEditText;
    /* Password edit text */
    private EditText mDescricaoEditText;
    /* Confirm Password edit text */
    private EditText mCondicoesEditText;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novoanuncio);


        /*Obtaining the references to the views from the XML.*/
        mItemEditText = findViewById(R.id.edtItemCadastro);
        mDesejadosEditText = findViewById(R.id.edtDesejadosCadastro);
        mDescricaoEditText = findViewById(R.id.edtDescricaoCadastro);
        mCondicoesEditText = findViewById(R.id.edtCondicoes);
    }

    public void onOKNewButtonClicked(View view) {
        Context context=this;
        /*Checking non informed parameters*/
        if(!mItemEditText.getText().toString().isEmpty() && !mDesejadosEditText.getText().toString().isEmpty()&&
                !mDescricaoEditText.getText().toString().isEmpty() && !mCondicoesEditText.getText().toString().isEmpty()){

            Toast.makeText(context,(String)"Inserindo Anuncio.", LENGTH_LONG).show();
        }else{
            Toast.makeText(context,(String)"Campos não preenchidos.", LENGTH_LONG).show();
        }
    }
}
