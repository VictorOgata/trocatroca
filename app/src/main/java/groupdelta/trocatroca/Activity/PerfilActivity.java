package groupdelta.trocatroca.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import groupdelta.trocatroca.AdressList;
import groupdelta.trocatroca.DataAccessObject.Conexao;
import groupdelta.trocatroca.Entities.Usuario;
import groupdelta.trocatroca.R;

public class PerfilActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText Username;
    private EditText Email;
    private EditText Password;
    private Spinner State;
    private Spinner City;
    private static FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private String email = firebaseUser.getEmail();
    private String uid = firebaseUser.getUid();
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private final static String [] paths = AdressList.StatesList;
    private final static String [][] CityList = AdressList.CitiesList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Username = findViewById(R.id.Name);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        State = findViewById(R.id.spinnerstate);
        City = findViewById(R.id.spinnercity);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PerfilActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        State.setAdapter(adapter);
        State.setOnItemSelectedListener(this);
        Password.setText("**********");
        Email.setText(email);
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

    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            Usuario uInfo = new Usuario();
            uInfo.setNick(ds.child(uid).getValue(Usuario.class).getNick()); //set the name
            uInfo.setCity(ds.child(uid).getValue(Usuario.class).getCity()); //set the city
            uInfo.setState(ds.child(uid).getValue(Usuario.class).getState()); //set the city
            uInfo.setCInfo(ds.child(uid).getValue(Usuario.class).getCInfo()); //set the Contact Info

            //display all the information

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(PerfilActivity.this,android.R.layout.simple_spinner_item,paths);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            State.setAdapter(adapter);
            if (uInfo.getState()!= null) {
                int spinnerPosition = adapter.getPosition(uInfo.getState());
                State.setSelection(spinnerPosition);
            }
            City.setAdapter(adapter);
            if (uInfo.getCity()!= null) {
                int spinnerPosition = adapter.getPosition(uInfo.getCity());
                City.setSelection(spinnerPosition);
            }
            Username.setText(uInfo.getNick());
            
        }
    }



    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        City.setVisibility(View.VISIBLE);
        ArrayAdapter<String> adapter2;
        adapter2 = new ArrayAdapter<String>(PerfilActivity.this,
                android.R.layout.simple_spinner_item,CityList[position]);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        City.setAdapter(adapter2);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        
    }

    public void onModifyProfileButtonClicked(View view) {
        firebaseUser.updateEmail(Email.getText().toString());
        Conexao.getFirebaseReference().child("Usuarios").child(uid).child("nick").setValue(Username.getText().toString());
        Conexao.getFirebaseReference().child("Usuarios").child(uid).child("email").setValue(Email.getText().toString());
        Conexao.getFirebaseReference().child("Usuarios").child(uid).child("state").setValue(State.getSelectedItem().toString());
        Conexao.getFirebaseReference().child("Usuarios").child(uid).child("city").setValue(City.getSelectedItem().toString());
    }
    public void onModifyPasswordButtonClicked(View view) {
        firebaseUser.updatePassword(Password.getText().toString());
    }

}

