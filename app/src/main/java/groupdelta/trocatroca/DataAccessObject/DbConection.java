package groupdelta.trocatroca.DataAccessObject;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


//CONFIGURANDO CONEXÃO COM O FIREBASE
public class DbConection {
    private static FirebaseAuth firebaseAuth;
    private static FirebaseAuth.AuthStateListener authStateListener;
    private static FirebaseUser firebaseUser;
    private static DatabaseReference firebaseReference;
    private static FirebaseDatabase firebaseInstance;

    public DbConection(){
    }

    public DatabaseReference getFirebaseReference(){
        if(firebaseReference==null){
            firebaseReference= getFirebaseInstance().getReference();
        }
        return  firebaseReference;
    }

    public FirebaseDatabase getFirebaseInstance(){
        if(firebaseInstance==null){
            firebaseInstance= FirebaseDatabase.getInstance();
        }
        return firebaseInstance;
    }

    public FirebaseAuth getFirebaseAuth(){
        if(firebaseAuth == null){
            startFirebaseAuth();
        }
        return firebaseAuth;
    }

    public void startFirebaseAuth (){
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

    public FirebaseUser getFirebaseUser(){
        return firebaseUser;
    }

    public static void logOut(){
        firebaseAuth.signOut();
    }
}
