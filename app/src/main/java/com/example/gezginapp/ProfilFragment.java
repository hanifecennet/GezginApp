package com.example.gezginapp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfilFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView profilephoto;
    TextView savePhotoButton;
    TextView selectPhotoButton;
    ImageView changeProfilePhoto;

    private OnFragmentInteractionListener mListener;

    public ProfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilFragment newInstance(String param1, String param2) {
        ProfilFragment fragment = new ProfilFragment();
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
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        // Inflate the layout for this fragment
        ImageView image_profil = view.findViewById(R.id.profil_picture);
        TextView text1 = view.findViewById(R.id.text1);
        TextView text2 = view.findViewById(R.id.text2);
        TextView text3 = view.findViewById(R.id.text3);
        ImageView image_instagram = view.findViewById(R.id.instagram);

      changeProfilePhoto =(ImageView)view.findViewById(R.id.change_profile_photo);

      changeProfilePhoto.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent (getContext(), AddPhotoActivity.class);
              startActivity(intent);
          }
      });


        image_instagram.setOnClickListener ( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToInstagram();
            }

        });


        return view;
    }

    public void goToInstagram() {
        Uri instagramUri = Uri.parse("https://instagram.com/gezlist");
        Intent instagramIntent = new Intent (Intent.ACTION_VIEW, instagramUri);

        instagramIntent.setPackage("com.instagram.android");

        try {
            startActivity(instagramIntent);

        } catch (ActivityNotFoundException e) {
            startActivity(new Intent (Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/gezilist")));
        }
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
 /*       if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");*/
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

        private void showProfilPhoto(){
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageReference = firebaseStorage.getReference();
            storageReference.child("userprofilphoto").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {//profil fotoğrafı değiştirme ekleyeceğimizi fotoğrafla
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(getContext()).load(uri).asBitmap().centerCrop().into(new SimpleTarget<Bitmap>(200,200) {//into ile bipmap resmi alınır
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            profilephoto.setImageBitmap(resource); //bitmap tuurnde bir şey bekeldiğinde resourse gönderilir
                        }
                    });//bitmap resmi bilgisayar diline dönüştütrü
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

            }

    @Override
    public void onResume() {
        super.onResume();
        showProfilPhoto();
    }
}


