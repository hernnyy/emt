package com.example.hernan.esmiturno.adapter;

import android.content.Context;
import android.content.Intent;
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

public class MeetNewHolder extends RecyclerView.ViewHolder {
    private TextView date;
    private TextView hour;
    private Button addButton;
    private Context mctx;
    private ArrayList<Meet> meetList;

    public MeetNewHolder(View v, Context context, ArrayList<Meet> meets) {
        super(v);
        date = (TextView) v.findViewById(R.id.new_date);
        hour = (TextView) v.findViewById(R.id.new_hour);
        addButton = (Button) v.findViewById(R.id.add_meet_button);
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
                    intent.putExtra("idMeet","");
//                    intent.putExtra("oMeet",meetList.get(requestCode));
                    //intent.putExtra("idMeet",meetList.get(requestCode).getId().toString());
                    intent.putExtra("idProvider",meetList.get(requestCode).getProvider().getId().toString());
                    intent.putExtra("idCustomer",meetList.get(requestCode).getCustomer().getId().toString());
                    intent.putExtra("idPlace",meetList.get(requestCode).getMeetPlace().getId().toString());
                    intent.putExtra("idDate",meetList.get(requestCode).getFechaAsString());
                    intent.putExtra("objectDate",meetList.get(requestCode).getFecha());
                    mctx.startActivity(intent);
                } catch (Throwable t) {
                    Log.e("My App", "Could not pass: \"" + meetList + "\"");
                }
            }
        });
       }

    public TextView getDate() {
        return date;
    }

    public void setDate(TextView date) {
        this.date = date;
    }

    public TextView getHour() {
        return hour;
    }

    public void setHour(TextView hour) {
        this.hour = hour;
    }

    public Button getAddButton() {
        return addButton;
    }

    public void setAddButton(Button addButton) {
        this.addButton = addButton;
    }

    public ArrayList<Meet> getMeetList() {
        return meetList;
    }

    public void setMeetList(ArrayList<Meet> meetList) {
        this.meetList = meetList;
    }
}