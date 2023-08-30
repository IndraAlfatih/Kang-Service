package com.airun.bigboss.kangservice;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ServiceAndroidAdapter extends ArrayAdapter<RequestAndroid> {
    private Activity context;
    private int resource;
    private List<RequestAndroid> listImage;

    public ServiceAndroidAdapter (@NonNull Activity context, @LayoutRes int resource, @NonNull List<RequestAndroid> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listImage = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View v = inflater.inflate(resource, null);
        TextView merkname = (TextView) v.findViewById(R.id.merkhp);
        TextView versihp = (TextView) v.findViewById(R.id.versihp);
        TextView deskripsihp = (TextView) v.findViewById(R.id.kerusakanhp);
        ImageView img = (ImageView) v.findViewById(R.id.imgView);

        merkname.setText(listImage.get(position).getMerk());
        versihp.setText(listImage.get(position).getVersi());
        deskripsihp.setText(listImage.get(position).getKerusakan());
        Picasso.with(context).load(listImage.get(position).getUrl()).into(img);

        return v;

    }
}