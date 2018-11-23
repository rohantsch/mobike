package com.hantsch.rossana.mobike.adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hantsch.rossana.mobike.R;
import com.hantsch.rossana.mobike.models.Bikes;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Bikes> implements View.OnClickListener{

    private ArrayList<Bikes> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtTipo;
        TextView txtColor;
        TextView txtMarca;
        TextView txtModelo;
    }

    public CustomAdapter(ArrayList<Bikes> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Bikes dataModel=(Bikes)object;

        switch (v.getId())
        {
            case R.id.item_info:
                Snackbar.make(v, "Release date " +dataModel.getTipo(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Bikes dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtColor = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtMarca = (TextView) convertView.findViewById(R.id.type);
            viewHolder.txtModelo = (TextView) convertView.findViewById(R.id.version_number);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtModelo.setText(dataModel.getColor());
        viewHolder.txtMarca.setText(dataModel.getModelo());
        viewHolder.txtColor.setText(dataModel.getMarca());
        // Return the completed view to render on screen
        return convertView;
    }
}