package com.example.gezginapp;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AnasayfaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AnasayfaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnasayfaFragment extends Fragment {
    List<ListViewModelClass> listArray = new ArrayList<ListViewModelClass>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AnasayfaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnasayfaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnasayfaFragment newInstance(String param1, String param2) {
        AnasayfaFragment fragment = new AnasayfaFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_anasayfa, container, false);
        final ListView listView = view.findViewById(R.id.listView);


        //getString(R.String.karadeniz)-> karadeniz
        listArray.add(new ListViewModelClass(R.drawable.foto1, "Trabzon", "Karadeniz bölgesindedir"));
        listArray.add(new ListViewModelClass(R.drawable.foto2, "Mardin", "GüneyDoğu bölgesindedir"));
        listArray.add(new ListViewModelClass(R.drawable.foto3, "İzmir",  "Ege bölgesindedir"));
        listArray.add(new ListViewModelClass(R.drawable.foto4, "İstanbul","Marmara bölgesindedir"));

        CustomPostAdapterActivity customPostAdapter = new CustomPostAdapterActivity(getLayoutInflater(),listArray);
        listView.setAdapter(customPostAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Bilgiler");
                String selectedName = listArray.get(position).getCity_name();
                String selectedDescription = listArray.get(position).getCity_description();
                int selectedIcon = listArray.get(position).getCity_picture();

                String message = selectedName + " " + selectedDescription;
                builder.setIcon(selectedIcon);
                builder.setMessage(message);
                builder.setNegativeButton("TAMAM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                    }


                });
                builder.show();
            }
        });
        return view;
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
}
