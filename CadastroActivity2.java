package groupdelta.trocatroca;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CadastroActivity2 extends AppCompatActivity {


    /* Personal data Text View */
    private Spinner mPersonalDataTextView;
    private static final String[] paths = {"SP", "RJ", "MG"};



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro2);

        /*Obtaining the references to the views from the XML.*/
        Intent intentMA = getIntent();
        mPersonalDataTextView = findViewById(R.id.edtData);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CadastroActivity2.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPersonalDataTextView.setAdapter(adapter);
        //mPersonalDataTextView.setOnItemSelectedListener(this);
    }
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;

        }
    }

    public void onOkButtonClicked(View view) {
    }
}
