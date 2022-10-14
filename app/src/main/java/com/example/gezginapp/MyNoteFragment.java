package com.example.gezginapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyNoteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyNoteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ListView myNotesListView;  //xml deki component
    ArrayAdapter<String> arrayAdapter; //veri listesi(diziyi listview w aktarmak için kullanılan adapter
    ArrayList<String> myNotesList = new ArrayList<>();  //dizi
    String myPlaces;
    ProgressDialog progressDialog; //dönen simge
    public MyNoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyNoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyNoteFragment newInstance(String param1, String param2) {
        MyNoteFragment fragment = new MyNoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        myNotesList = getMyNotes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_my_note, container, false);

        Button addNotesBtn=(Button) view.findViewById(R.id.fragment_add_notes_btn);
        myNotesListView = view.findViewById(R.id.my_notes_lv);//id çağırma
        arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,android.R.id.text1,myNotesList ); //getcontext nerede olşturulacağı, simple_list.. satır layoutunu anroiddne alıyor, sonuncusuda bu listeye hangi verileirn gönderilceğinin tutuyro
        myNotesListView.setAdapter(arrayAdapter);
        addNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent (getContext(), AddNoteActivity.class);
                startActivity(i);
            }
        });

        return view;
    }

    private ArrayList<String> getMyNotes(){//fonk oluşturduk
        showProgressDialog();
        final ArrayList<String> myNotes = new ArrayList<>();//yerel bir liste tutmak içiçn açılır, firebaseden çekme için ,
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("GezdigimYerler");//firebaseden çekeceğimiz için baynı adı verrirz

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { // yeni veri ekleyince bu fonk dan takip edicek
                progressDialog.dismiss(); //yükleniyor ifadesi dursun diye
                myNotesList.clear();//girilen notu tekrarlamamaya yara
                for (DataSnapshot ds: dataSnapshot.getChildren()){//datanın anlık olarak verisini çeker snapshot
                    myPlaces =ds.child("sehirAdi").getValue().toString();// not stringi tutmak içiçn string tanımlanır
                    myNotesList.add(myPlaces);//notlist içine leman ekledik

                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {  //veri çekme işi biitnce durdurma için yükleniyor simgesi
                progressDialog.dismiss();

            }
        });//refereans nesneye ulaşmak için, bu metod listeye yeni eklenen elemanları ekler listener dna dolayı dinleme şillemi yapar
        return myNotesList;
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void showProgressDialog(){
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Yükleniyor...");
        progressDialog.setCancelable(false); // false çünkü nerde alçılıp kapancağını bz belirliyoruz
        progressDialog.show();
    }
}
