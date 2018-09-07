package groupdelta.trocatroca.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import groupdelta.trocatroca.R;

public class HomescreenActivity extends AppCompatActivity {
    private Button btnPerfil;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        /*Obtaining the references to the views from the XML.*/
        btnPerfil = findViewById(R.id.btnPerfil);

    }
    public void onPerfilButtonClicked(View view) {
        Intent i = new Intent(HomescreenActivity.this, PerfilActivity.class);
        startActivity(i);
    }
}
