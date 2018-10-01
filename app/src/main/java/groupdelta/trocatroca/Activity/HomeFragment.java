package groupdelta.trocatroca.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import groupdelta.trocatroca.R;

public class HomeFragment extends Fragment {

    Context context;
    private Button btnNewItem;
    private AutoCompleteTextView PesquisaItens;
    private String[] itens = new String[] {"Game of Thrones", "Age of Empires", "Prototype", "God of War", "God of War2", "god of war", "game of Throne2"};
    ArrayAdapter<String> adapter;


    // The onCreate method is called when the Fragment instance is being created, or re-created.
    // Use onCreate for any standard setup that does not require the activity to be fully created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //troquei this por getActivity()
        adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, itens);




    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = view.getContext();
        return view;



    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        PesquisaItens = view.findViewById(R.id.pesquisaItens);
        PesquisaItens.setAdapter(adapter);

        btnNewItem = view.findViewById(R.id.btnNewItem);
        btnNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pass the context and the Activity class you need to open from the Fragment Class, to the Intent
                Intent intent = new Intent(context,NovoAnuncioActivity.class);
                startActivity(intent);
            }
        });
    }
}
