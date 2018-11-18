package groupdelta.trocatroca.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import groupdelta.trocatroca.AdressList;
import groupdelta.trocatroca.DataAccessObject.DbConection;
import groupdelta.trocatroca.DataAccessObject.UserDAO;
import groupdelta.trocatroca.Entities.User;
import groupdelta.trocatroca.R;

import static android.widget.Toast.LENGTH_LONG;

public class PerfilFragment extends Fragment implements AdapterView.OnItemSelectedListener  {

    //private Button btnPerfil;
    private Button btnLogout;
    private Button btnModSenha;
    private Button btnEditPerfil;
    Context context;

    //////coisas do PerfilActivity
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
    ///////////

    //abre o xml
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        context = view.getContext();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        //botoes e acoes
        //btnPerfil = view.findViewById(R.id.btnPerfil);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnEditPerfil = view.findViewById(R.id.button_editPerfil);
        btnModSenha = view.findViewById(R.id.button_modifSenha);
        Username = view.findViewById(R.id.NameF);
        Email = view.findViewById(R.id.EmailF);
        EmailEdit= view.findViewById(R.id.Email_editF);
        Password = view.findViewById(R.id.PasswordF);
        PasswordEdit = view.findViewById(R.id.Password_editF);
        UsernameEdit= view.findViewById(R.id.NameEditF);
        State = view.findViewById(R.id.spinnerstateF);
        City = view.findViewById(R.id.spinnercityF);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PerfilFragment.super.getContext(),
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

        btnEditPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onModifyProfileButtonClicked(v);
            }
        });

        btnModSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onModifyPasswordButtonClicked(v);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pass the context and the Activity class you need to open from the Fragment Class, to the Intent
                Intent intent = new Intent(PerfilFragment.super.getContext(),MainActivity.class);
                DbConection.logOut();
                startActivity(intent);
            }
        });

        /*btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pass the context and the Activity class you need to open from the Fragment Class, to the Intent
                Intent intent = new Intent(context,PerfilActivity.class);
                startActivity(intent);
            }
        });*/

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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PerfilFragment.super.getContext(), android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        State.setAdapter(adapter);
        if (uInfo.getState() != null) {
            spinnerPosition = adapter.getPosition(uInfo.getState());
            State.setSelection(spinnerPosition);
        }

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(PerfilFragment.super.getContext(), android.R.layout.simple_spinner_item, CityList[adapter.getPosition(uInfo.getState())]);
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
            adapter3 = new ArrayAdapter<String>(PerfilFragment.super.getContext(),
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
        Toast.makeText(PerfilFragment.super.getContext(),"Perfil alterado com sucesso", LENGTH_LONG).show();
        /*

        DbConection.getFirebaseReference().child("Usuarios").child(uid).child("nick").setValue(Username.getText().toString());
        DbConection.getFirebaseReference().child("Usuarios").child(uid).child("email").setValue(Email.getText().toString());
        DbConection.getFirebaseReference().child("Usuarios").child(uid).child("state").setValue(State.getSelectedItem().toString());
        DbConection.getFirebaseReference().child("Usuarios").child(uid).child("city").setValue(City.getSelectedItem().toString());*/
    }

    public void onModifyPasswordButtonClicked(View view) {
        userDAO.getFirebaseUser().updatePassword(Password.getEditText().getText().toString());
        Toast.makeText(PerfilFragment.super.getContext(),"Senha alterada com sucesso",LENGTH_LONG ).show();

    }

    private void alert(String s){
        Toast.makeText(PerfilFragment.super.getContext(),s,Toast.LENGTH_SHORT).show();
    }

}
