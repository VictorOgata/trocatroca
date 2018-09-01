package groupdelta.trocatroca.DataAccessObject;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


//CONFIGURANDO CONEXÃO COM O FIREBASE
public class Conexao {
    public static FirebaseAuth firebaseAuth;
    private static FirebaseAuth.AuthStateListener authStateListener;
    private static FirebaseUser firebaseUser;
    private static DatabaseReference firebaseReference;

    private Conexao(){
    }

    public static DatabaseReference getFirebaseReference(){
        if(firebaseReference==null){
            firebaseReference= FirebaseDatabase.getInstance().getReference();
        }
        return  firebaseReference;
    }

    public static FirebaseAuth getFirebaseAuth(){
        if(firebaseAuth == null){
            inicializarFirebaseAuth();
        }
        return firebaseAuth;
    }

    private static void inicializarFirebaseAuth (){
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!= null){
                    firebaseUser = user;
                }

            }
        };

        firebaseAuth.addAuthStateListener(authStateListener);

    }

    public static FirebaseUser getFirebaseUser(){
        return firebaseUser;
    }

    public static void logOut(){
        firebaseAuth.signOut();
    }
}
