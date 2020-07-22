package com.intellcap.showroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class CustomAdapter extends ArrayAdapter<CustomData> {
    Context mContext;
    int mLayoutResource;
    CustomData mData[] = null;

    public CustomAdapter(Context context, int resource, CustomData[] objects){
        super(context, resource, objects);
        this.mContext = context;
        this.mLayoutResource = resource;
        this.mData = objects;
    }

    @Override
    public CustomData getItem(int position) {
        return super.getItem(position);
    }

    //View.OnClickListener PopupListener = new View.OnClickListener() {
    //    @Override
    //    public void onClick(View view) {
    //        Integer viewPosition = (Integer) view.getTag();
    //        CustomData p = mData[viewPosition];
    //        Toast.makeText(getContext(), p.mText, Toast.LENGTH_SHORT).show();
    //    }
    //};


    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        row = inflater.inflate(mLayoutResource,parent,false);
        //get a reference to the different view elements we wish to update
        TextView textView = (TextView) row.findViewById(R.id.customRowText);
        ImageView imageView = (ImageView) row.findViewById(R.id.customRowImage);

        CustomData data = mData[position];
        textView.setText(String.valueOf(data.mText));

        Picasso.get().load(data.mImageURL).into(imageView);


        return row;

    }

}
