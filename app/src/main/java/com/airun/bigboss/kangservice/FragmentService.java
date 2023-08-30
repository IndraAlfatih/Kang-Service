package com.airun.bigboss.kangservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class FragmentService extends Fragment {
    View view;
    ViewFlipper v_flipper;

    public FragmentService() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_service,container,false);

        int images[] ={R.drawable.image1,R.drawable.image2,R.drawable.image3};
        v_flipper = (ViewFlipper) view.findViewById(R.id.m_flipper);

        for (int image: images){
             flipperimages(image);
        }

        ImageView btnandroid = (ImageView) view.findViewById(R.id.btnandroid);
        btnandroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), AndroidActivity.class);
                startActivity(in);
            }
        });

        ImageView btnlaptop = (ImageView) view.findViewById(R.id.btnlaptop);
        btnlaptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), LaptopActivity.class);
                startActivity(in);
            }
        });

        ImageView btnpc = (ImageView) view.findViewById(R.id.btnpc);
        btnpc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), KomputerActivity.class);
                startActivity(in);
            }
        });

        ImageView btntv = (ImageView) view.findViewById(R.id.btntv);
        btntv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), TelevisiActivity.class);
                startActivity(in);
            }
        });

        ImageView btnkipas = (ImageView) view.findViewById(R.id.btnkipas);
        btnkipas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), KipasActivity.class);
                startActivity(in);
            }
        });

        ImageView btnac = (ImageView) view.findViewById(R.id.btnac);
        btnac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), AcActivity.class);
                startActivity(in);
            }
        });
        return view;

    }

    public void flipperimages(int image){
        ImageView imageView = new ImageView(getActivity());
        imageView.setBackgroundResource(image);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(4000);
        v_flipper.setAutoStart(true);

        v_flipper.setInAnimation(getActivity(), android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(getActivity(), android.R.anim.slide_out_right);
    }
}
