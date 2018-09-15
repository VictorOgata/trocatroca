package groupdelta.trocatroca.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import groupdelta.trocatroca.DataAccessObject.Conexao;
import groupdelta.trocatroca.Entities.Anuncio;
import groupdelta.trocatroca.Entities.Usuario;
import groupdelta.trocatroca.R;

import static android.widget.Toast.LENGTH_LONG;

public class NovoAnuncioActivity extends AppCompatActivity {
    public static final String[] itemType = {"Jogo ou Livro?","Livro","Jogo"};
    private String[] itens = new String[] {"Game of Thrones", "Age of Empires", "Prototype", "God of War", "God of War2", "god of war", "game of Throne2"};

    /* Itemname edit text*/
    private EditText mItemEditText;
    /* E-mail edit text */
    private EditText mDesejadosEditText;
    /* Password edit text */
    private EditText mDescricaoEditText;
    /* Confirm Password edit text */
    private Spinner mTypeSpinner;
    private FirebaseAuth autentication;
    Anuncio ad;
    private FirebaseDatabase mFirebaseDatabase;
    private String uid = firebaseUser.getUid();
    private DatabaseReference myRef;
    private String State, City;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novoanuncio);


        /*Obtaining the references to the views from the XML.*/
        mItemEditText = findViewById(R.id.edtItemCadastro);
        mDesejadosEditText = findViewById(R.id.edtDesejadosCadastro);
        mDescricaoEditText = findViewById(R.id.edtDescricaoCadastro);
        mTypeSpinner = findViewById(R.id.edtCondicoes);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(NovoAnuncioActivity.this,
                android.R.layout.simple_spinner_item,itemType);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeSpinner.setAdapter(adapter);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void showData(DataSnapshot ds) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();
        State = ds.child("Usuarios").child(uid).getValue(Usuario.class).getState(); //set the state
        City = ds.child("Usuarios").child(uid).getValue(Usuario.class).getCity(); //set the city
    }

    public void onOKNewButtonClicked(View view) {
        Context context=this;
        /*Checking non informed parameters*/
        if(!mItemEditText.getText().toString().isEmpty() && !mDesejadosEditText.getText().toString().isEmpty()&&
                !mDescricaoEditText.getText().toString().isEmpty() && !mTypeSpinner.getSelectedItem().toString().equals(itemType[0])){

            Toast.makeText(context,(String)"Inserindo Anuncio.", LENGTH_LONG).show();
            ad= new Anuncio();
            FirebaseUser firebaseUser = Conexao.getFirebaseAuth().getCurrentUser();
            ad.setHost(firebaseUser.getUid());
            /* Pulling information from screen through references*/
            ad.setItem(mItemEditText.getText().toString());

            String [] wList = mDesejadosEditText.getText().toString().replace(", ", ",").replace(" ", "_").toUpperCase().split(",");



            ad.setDescription(mDescricaoEditText.getText().toString());
            ad.setState(State);
            ad.setCity(City);
            ad.setType(mTypeSpinner.getSelectedItem().toString());
            ad.setWishList(wList);
            ad.saveNewAd(context);
            Intent i = new Intent( NovoAnuncioActivity.this, HomescreenActivity.class);
            startActivity(i);
        }else{
            Toast.makeText(context,(String)"Campos não preenchidos.", LENGTH_LONG).show();
        }
    }
}
