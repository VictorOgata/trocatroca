package groupdelta.trocatroca.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import groupdelta.trocatroca.DataAccessObject.UserDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import groupdelta.trocatroca.Entities.User;
import groupdelta.trocatroca.AdressList;
import groupdelta.trocatroca.R;

import static android.widget.Toast.LENGTH_LONG;

public class CadastroP2Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    /* Personal data Text View */
    private Spinner mState;
    private Spinner mCity;
    private final static String [] paths = AdressList.StatesList;
    private final static String [][] CityList = AdressList.CitiesList;
    private Button btnCadastrar;
    /* Firebase conection */
    private UserDAO userDAO;

    private User user;
    private String emailReceived;
    private String passReceived;
    private String nickReceived;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro2);
        userDAO = new UserDAO();

        /*Obtaining the references to the views from the XML.*/
        Intent intentR2 = getIntent();
        emailReceived = (String) intentR2.getStringExtra("email");
        passReceived = (String) intentR2.getStringExtra("pass");
        nickReceived = (String) intentR2.getStringExtra("nick");
        mState = (Spinner) findViewById(R.id.edtData);
        mCity = (Spinner) findViewById(R.id.edtCity);
        btnCadastrar = findViewById(R.id.btnOk);
        //
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CadastroP2Activity.this,android.R.layout.simple_spinner_item,paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mState.setAdapter(adapter);
        mState.setOnItemSelectedListener(this);
        //mPersonalDataTextView.setOnItemSelectedListener(this);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user= new User();
                /* Pulling information from screen through references*/
                user.setState(mState.getSelectedItem().toString());
                user.setCity(mCity.getSelectedItem().toString());
                user.setNick(nickReceived);
                user.setEmail(emailReceived);
                user.setCInfo("@Vazio@");
                user.setAvPoints("@Vazio@");
                Toast.makeText(CadastroP2Activity.this,"Procesando...", LENGTH_LONG).show();
                cadastrarNovoUsuario();
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        mCity.setVisibility(View.VISIBLE);
        ArrayAdapter<String> adapter2;
        adapter2 = new ArrayAdapter<String>(CadastroP2Activity.this, android.R.layout.simple_spinner_item,CityList[position]);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCity.setAdapter(adapter2);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private void cadastrarNovoUsuario(){
        userDAO.getFirebaseAuth().createUserWithEmailAndPassword(
                user.getEmail(),
                passReceived).addOnCompleteListener(CadastroP2Activity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    userDAO.saveNewUser(CadastroP2Activity.this,user);
                    Toast.makeText(CadastroP2Activity.this, (String) "Cadastrado com sucesso.", LENGTH_LONG).show();
                    Intent intent = new Intent(CadastroP2Activity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    String errorAuth="";
                    try{
                        throw task.getException();
                    }catch(FirebaseAuthUserCollisionException e){
                        errorAuth="E-mail já cadastrado no sistema.";
                    }catch(Exception e){
                        errorAuth=e.getMessage();
                    }
                    Toast.makeText(CadastroP2Activity.this, errorAuth, LENGTH_LONG).show();
                }
            }
        });
    }
}
