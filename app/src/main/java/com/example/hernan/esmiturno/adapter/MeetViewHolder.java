package com.example.hernan.esmiturno.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hernan.esmiturno.MeetDetailFastActivity;
import com.example.hernan.esmiturno.R;
import com.example.hernan.esmiturno.model.Meet;

import java.util.ArrayList;

/**
 * Created by Hernan on 8/11/2017.
 */

public class MeetViewHolder extends RecyclerView.ViewHolder {
    private TextView initial;
    private TextView name;
    private Button deleteButton;
    private Context mctx;
    private ArrayList<Meet> meetList;

    public MeetViewHolder(View v,Context context, ArrayList<Meet> meets) {
        super(v);
        initial = (TextView) v.findViewById(R.id.initial);
        name = (TextView) v.findViewById(R.id.name);
        deleteButton = (Button) v.findViewById(R.id.delete_button);
        mctx = context;
        meetList = meets;
//        deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                animateCircularDelete(itemView, getAdapterPosition());
//            }
//        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Pair<View, String> p1 = Pair.create((View) initial, SampleMaterialActivity.TRANSITION_INITIAL);
//                Pair<View, String> p2 = Pair.create((View) name, SampleMaterialActivity.TRANSITION_NAME);
//                Pair<View, String> p3 = Pair.create((View) deleteButton, SampleMaterialActivity.TRANSITION_DELETE_BUTTON);
//
//                ActivityOptionsCompat options;
//                Activity act = (AppCompatActivity) context;
//                options = ActivityOptionsCompat.makeSceneTransitionAnimation(act, p1, p2, p3);
//
                int requestCode = getAdapterPosition();
//                String name = meetList.get(requestCode).getName();
//                int color = meetList.get(requestCode).getColorResource();
//
//                Log.d(DEBUG_TAG, "MeetSimpleAdapter itemView listener for Edit adapter position " + requestCode);
//
//                Intent transitionIntent = new Intent(context, TransitionEditActivity.class);
//                transitionIntent.putExtra(SampleMaterialActivity.EXTRA_NAME, name);
//                transitionIntent.putExtra(SampleMaterialActivity.EXTRA_INITIAL, Character.toString(name.charAt(0)));
//                transitionIntent.putExtra(SampleMaterialActivity.EXTRA_COLOR, color);
//                transitionIntent.putExtra(SampleMaterialActivity.EXTRA_UPDATE, false);
//                transitionIntent.putExtra(SampleMaterialActivity.EXTRA_DELETE, false);
//                ((AppCompatActivity) context).startActivityForResult(transitionIntent, requestCode, options.toBundle());
                try {
                    Intent intent = new Intent(mctx, MeetDetailFastActivity.class);
                    intent.putExtra("idMeet",meetList.get(requestCode).getId().toString());
                    mctx.startActivity(intent);
                } catch (Throwable t) {
                    Log.e("My App", "Could not pass: \"" + meetList + "\"");
                }
            }
        });
       }

    public TextView getInitial() {
        return initial;
    }
    public void setInitial(TextView initial) {
        this.initial = initial;
    }
    public TextView getName() {
        return name;
    }
    public void setName(TextView name) {
        this.name = name;
    }
    public Button getDeleteButton() {
        return deleteButton;
    }
    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }
}