package groupdelta.trocatroca.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import groupdelta.trocatroca.AdressList;
import groupdelta.trocatroca.DataAccessObject.UserDAO;
import groupdelta.trocatroca.Entities.User;
import groupdelta.trocatroca.R;

import static android.widget.Toast.LENGTH_LONG;

public class PerfilActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextInputLayout Username;
    private TextInputLayout Email;
    private TextInputLayout Password;
    private TextInputEditText EmailEdit;
    private TextInputEditText PasswordEdit;
    private TextInputEditText UsernameEdit;
    private Spinner State;
    private Spinner City;
    //Conection
    private UserDAO userDAO;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private String email = firebaseUser.getEmail();
    //private String uid = firebaseUser.getUid();

    private final static String [] paths = AdressList.StatesList;
    private final static String [][] CityList = AdressList.CitiesList;
    private int spinnerPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Username = findViewById(R.id.Name);
        Email = findViewById(R.id.Email);
        EmailEdit=findViewById(R.id.Email_edit);
        Password = findViewById(R.id.Password);
        PasswordEdit = findViewById(R.id.Password_edit);
        UsernameEdit=findViewById(R.id.NameEdit);
        State = findViewById(R.id.spinnerstate);
        City = findViewById(R.id.spinnercity);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PerfilActivity.this,
                android.R.layout.simple_spinner_item,paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        State.setAdapter(adapter);
        State.setOnItemSelectedListener(this);
        PasswordEdit.setText("**********");
        EmailEdit.setText(email);

        userDAO = new UserDAO();
        userDAO.startFirebaseAuth();
        userDAO.getFirebaseReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                alert(databaseError.toException().getMessage());
            }
        });

    }

    private void showData(DataSnapshot ds) {
                User uInfo = new User();
                //String uid = userDAO.getFirebaseUser().getUid();
                //uInfo = (User) ds.child("Usuarios").child(userDAO.getFirebaseUser().getUid().toString()).getValue();
                uInfo.shapeHashMapIntoUser(userDAO.loadUserHashMap(ds));
                /*uInfo.setNick(ds.child("Usuarios").child(uid).getValue(User.class).getNick()); //set the name
                uInfo.setCity(ds.child("Usuarios").child(uid).getValue(User.class).getCity()); //set the city
                uInfo.setState(ds.child("Usuarios").child(uid).getValue(User.class).getState()); //set the city
                uInfo.setCInfo(ds.child("Usuarios").child(uid).getValue(User.class).getCInfo()); //set the Contact Info
                */

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PerfilActivity.this, android.R.layout.simple_spinner_item, paths);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                State.setAdapter(adapter);
                if (uInfo.getState() != null) {
                    spinnerPosition = adapter.getPosition(uInfo.getState());
                    State.setSelection(spinnerPosition);
                }

                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(PerfilActivity.this, android.R.layout.simple_spinner_item, CityList[adapter.getPosition(uInfo.getState())]);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                City.setAdapter(adapter2);
                if (uInfo.getCity() != null) {
                    int spinnerPosition2 = adapter2.getPosition(uInfo.getCity());
                    City.setSelection(spinnerPosition2);
                }
                UsernameEdit.setText(uInfo.getNick());
                //PasswordEdit.setText("**********")
                //EmailEdit.setText(uInfo.getEmail());
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        City.setVisibility(View.VISIBLE);
        if(spinnerPosition != position) {
            ArrayAdapter<String> adapter3;
            adapter3 = new ArrayAdapter<String>(PerfilActivity.this,
                    android.R.layout.simple_spinner_item, CityList[position]);
            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            City.setAdapter(adapter3);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onModifyProfileButtonClicked(View view) {
        userDAO.getFirebaseUser().updateEmail(Email.getEditText().getText().toString());
        User uInfo = new User();
        uInfo.setNick(Username.getEditText().getText().toString());
        uInfo.setEmail(Email.getEditText().toString());
        uInfo.setCInfo("@Vazio@");
        uInfo.setState(State.getSelectedItem().toString());
        uInfo.setCity(City.getSelectedItem().toString());
        userDAO.updateUser(uInfo);
        Toast.makeText(PerfilActivity.this,"Perfil alterado com sucesso", LENGTH_LONG).show();
        /*

        DbConection.getFirebaseReference().child("Usuarios").child(uid).child("nick").setValue(Username.getText().toString());
        DbConection.getFirebaseReference().child("Usuarios").child(uid).child("email").setValue(Email.getText().toString());
        DbConection.getFirebaseReference().child("Usuarios").child(uid).child("state").setValue(State.getSelectedItem().toString());
        DbConection.getFirebaseReference().child("Usuarios").child(uid).child("city").setValue(City.getSelectedItem().toString());*/
    }

    public void onModifyPasswordButtonClicked(View view) {
        userDAO.getFirebaseUser().updatePassword(Password.getEditText().getText().toString());
        Toast.makeText(PerfilActivity.this,"Senha alterada com sucesso",LENGTH_LONG ).show();

    }

    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}

