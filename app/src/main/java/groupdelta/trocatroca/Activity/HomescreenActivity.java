package groupdelta.trocatroca.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import groupdelta.trocatroca.R;

public class HomescreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        //declaracao e referencias do BottomNavigationView
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //indica qual o fragment inicial
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new HomeFragment())
                .commit();

    }

    //Atributo BottomNavigationView e Reacao aos clicks nos icones do bottomNavigationView
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment fragmentSelecionado = null;

                    //define em fragmentoSelecionado a classe java do fragment associado ao icone
                    //icones definidos no arquivo @menu/bottom_navigation_menu
                    switch (menuItem.getItemId()){
                        case R.id.menu_home: fragmentSelecionado = new HomeFragment(); break;
                        case R.id.menu_anunciar: fragmentSelecionado = new AnunciarFragment(); break;
                        //case R.id.menu_arquivo: fragmentSelecionado = new ArquivoFragment(); break;
                        case R.id.menu_perfil: fragmentSelecionado = new PerfilFragment(); break;
                    }
                    //mostra o fragment seleciodo
                    //parametros: onde o fragment vai ser exibido e o fragment
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragmentSelecionado)
                            .commit();

                    return true;
                }
            };
}
