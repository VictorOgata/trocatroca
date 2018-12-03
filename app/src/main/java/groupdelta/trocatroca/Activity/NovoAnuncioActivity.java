package groupdelta.trocatroca.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import groupdelta.trocatroca.DataAccessObject.AdvertisementDAO;
import groupdelta.trocatroca.DataAccessObject.UserDAO;
import groupdelta.trocatroca.Entities.Advertisement;
import groupdelta.trocatroca.Entities.User;
import groupdelta.trocatroca.R;

import static android.widget.Toast.LENGTH_LONG;

public class NovoAnuncioActivity extends AppCompatActivity {
    public static final String[] itemType = {"Jogo ou Livro?","Livro","Jogo"};

    /* Itemname edit text*/
    private TextInputLayout mItemEditText;
    /* E-mail edit text */
    private TextInputLayout mDesejadosEditText;
    /* Password edit text */
    private TextInputLayout mDescricaoEditText;
    /* Confirm Password edit text */
    private Spinner mTypeSpinner;
    private Advertisement ad;
    private AdvertisementDAO adDAO;
    private UserDAO userDAO;
    private String State, City;
    private Button btnOfertar;

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
        btnOfertar=findViewById(R.id.btnNewAd);
        mTypeSpinner.setAdapter(adapter);
        userDAO = new UserDAO();
        userDAO.startFirebaseAuth();

        userDAO.getFirebaseReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnOfertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Checking non informed parameters*/
                if(!mItemEditText.getEditText().getText().toString().isEmpty()&&
                    !mDesejadosEditText.getEditText().getText().toString().isEmpty()&&
                    !mDescricaoEditText.getEditText().getText().toString().isEmpty()&&
                    !mTypeSpinner.getSelectedItem().toString().equals(itemType[0])){
                    Toast.makeText(NovoAnuncioActivity.this,(String)"Criando Anuncio.", LENGTH_LONG).show();
                    ad= new Advertisement();
                    adDAO = new AdvertisementDAO();

                    ad.setHost(userDAO.getFirebaseUser().getUid());
                    /* Pulling information from screen through references*/
                    ad.setItem(mItemEditText.getEditText().getText().toString());
                    ad.setDescription(mDescricaoEditText.getEditText().getText().toString());
                    ad.setState(State);
                    ad.setCity(City);
                    ad.setType(mTypeSpinner.getSelectedItem().toString());

                    String [] wList = mDesejadosEditText.getEditText().getText().toString().replace(", ", ",").replace(" ", "_").toUpperCase().split(",");
                    ad.setWishList(ad.makeWishList(wList));

                    adDAO.saveNewAd(NovoAnuncioActivity.this,ad);

                    //onBackPressed();
                    Intent i = new Intent( NovoAnuncioActivity.this, HomescreenActivity.class);
                    startActivity(i);

                }else{
                    Toast.makeText(NovoAnuncioActivity.this,(String)"Campos não preenchidos.", LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }

    private void showData(DataSnapshot ds) {
        User uInfo= new User();
        uInfo.shapeHashMapIntoUser(userDAO.loadUserHashMap(ds));
        State = uInfo.getState(); //set the state
        City = uInfo.getCity(); //set the city
    }
}