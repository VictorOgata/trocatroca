package groupdelta.trocatroca.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import groupdelta.trocatroca.DataAccessObject.Conexao;
import groupdelta.trocatroca.Entities.Usuario;
import groupdelta.trocatroca.R;

import static android.widget.Toast.LENGTH_LONG;

public class CadastroP2Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    /* Personal data Text View */
    private Spinner mState;
    private Spinner mCity;
    private static final String[] paths = {"AC","AL","AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE"};
    private static  final String[] AC = { "Acrelândia",	"Assis Brasil", "Brasiléia", "Bujari", "Capixaba", "Cruzeiro do Sul", "Epitaciolândia", "Feijó", "Jordão", "Mâncio Lima", "Manoel Urbano", "Marechal Thaumaturgo", "Plácido de Castro", "Porto Acre", "Porto Walter", "Rio Branco", "Rodrigues Alves", "Santa Rosa do Purus", "Sena Madureira", "Senador Guiomard", "Tarauacá", "Xapuri"};
    private static  final String[] AL= { "teste",	"Assis dsdsdBrasil", "Basasarasiléia", "Bujari", "Capixaba", "Cruzeiro do Sul", "Epitaciolândia", "Feijó", "Jordão", "Mâncio Lima", "Manoel Urbano", "Marechal Thaumaturgo", "Plácido de Castro", "Porto Acre", "Porto Walter", "Rio Branco", "Rodrigues Alves", "Santa Rosa do Purus", "Sena Madureira", "Senador Guiomard", "Tarauacá", "Xapuri"};

    private Usuario user;
    /* Firebase conection */
    private FirebaseAuth autentication;
    String emailReceived;
    String passReceived;
    String nickReceived;

    String phoneReceived;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro2);

        /*Obtaining the references to the views from the XML.*/
        Intent intentR2 = getIntent();
        emailReceived = (String) intentR2.getStringExtra("email");
        passReceived = (String) intentR2.getStringExtra("pass");
        nickReceived = (String) intentR2.getStringExtra("nick");
        phoneReceived = (String) intentR2.getStringExtra("phone");
        mState = (Spinner) findViewById(R.id.edtData);
        mCity = (Spinner) findViewById(R.id.edtCity);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CadastroP2Activity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mState.setAdapter(adapter);
        mState.setOnItemSelectedListener(this);
        //mPersonalDataTextView.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        mCity.setVisibility(View.VISIBLE);
        ArrayAdapter<String> adapter2;

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                adapter2 = new ArrayAdapter<String>(CadastroP2Activity.this,
                        android.R.layout.simple_spinner_item,AC);

                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mCity.setAdapter(adapter2);
                break;
            case 1:
                adapter2 = new ArrayAdapter<String>(CadastroP2Activity.this,
                        android.R.layout.simple_spinner_item,AL);

                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mCity.setAdapter(adapter2);
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void onOkButtonClicked(View view) {
        user= new Usuario();
        /* Pulling information from screen through references*/
        String state = mState.getSelectedItem().toString();
        user.setNick(nickReceived);
        user.setEmail(emailReceived);
        user.setPassword(passReceived);
        user.setPhone(phoneReceived);

        cadastrarNovoUsuario();
    }

    private void cadastrarNovoUsuario(){
        Activity activity=this;
        final Context context=this;
        autentication = Conexao.getFirebaseAuth();
        autentication.createUserWithEmailAndPassword(
                user.getEmail(),
                user.getPassword()).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(context, (String) "Cadastrado com sucesso.", LENGTH_LONG).show();
                    user.saveUser();
                }else{
                    String errorAuth="";
                    try{
                        throw task.getException();
                    }catch(FirebaseAuthUserCollisionException e){
                        errorAuth="E-mail já cadastrado no sistema.";
                    }catch(Exception e){
                        errorAuth="Erro ao efetuar o cadastro";
                        e.printStackTrace();
                    }
                    Toast.makeText(context, errorAuth, LENGTH_LONG).show();
                }
            }
        });
    }


}
