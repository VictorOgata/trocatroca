package groupdelta.trocatroca.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.annotation.Nullable;
import android.widget.Toast;

//import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import groupdelta.trocatroca.DataAccessObject.UserDAO;
import groupdelta.trocatroca.R;

import static android.widget.Toast.LENGTH_LONG;


public class MainActivity extends AppCompatActivity {

    /* E-mail edit text */
    private TextInputLayout mEmailEditText;
    private TextInputEditText mEmailEditTextEdit;
    /* Password edit text */
    private TextInputLayout mPasswordEditText;
    private UserDAO userDAO;
    private Button btnLogar;
    private Button btnRegistrar;
    private Button btnRecuperar;
    private AdView mAdView;

    protected  void onStart (){
        super.onStart();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        userDAO = new UserDAO();

        /*Obtaining the references to the views from the XML.*/
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mEmailEditText = findViewById(R.id.edtEmail);
        mEmailEditTextEdit = findViewById(R.id.edtEmailedit);
        mPasswordEditText = findViewById(R.id.edSenha);
        btnLogar = findViewById(R.id.btnLogin);
        btnRegistrar=findViewById(R.id.btnCadastro);
        btnRecuperar = findViewById(R.id.btnRecuperar);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailEditText.getEditText().getText().toString().trim();
                String senha = mPasswordEditText.getEditText().getText().toString().trim();
                login(email,senha);
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class destinationClass = CadastroActivity.class;
                Intent intentToStartRegisterActivity = new Intent(MainActivity.this, destinationClass);
                intentToStartRegisterActivity.putExtra("email", mEmailEditText.getEditText().getText().toString());
                startActivity(intentToStartRegisterActivity);
            }
        });

        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailEditText.getEditText().getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(MainActivity.this, "Insira o e-mail vinculado a conta!", LENGTH_LONG).show();
                }
                else{
                userDAO.getFirebaseAuth().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Um e-mail de recuperação foi enviado", LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Falha ao enviar e-mail de recuperação!", LENGTH_LONG).show();
                        }
                    }
                });
            }
            }
        });

    }

    private void login(String email, String senha){
        if(!email.isEmpty() && !senha.isEmpty()) {
            userDAO.getFirebaseAuth().signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(MainActivity.this, HomescreenActivity.class);
                                Toast.makeText(MainActivity.this,"Efetuando Login...", LENGTH_LONG).show();
                                startActivity(i);
                            } else
                                Toast.makeText(MainActivity.this,"E-mail ou Senha Errados!", LENGTH_LONG).show();
                        }
                    });
        }else
            Toast.makeText(MainActivity.this,"Campos não preenchidos.", LENGTH_LONG).show();
    }

}
