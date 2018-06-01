package com.example.hernan.esmiturno.adapter;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hernan.esmiturno.AddMeetActivity;
import com.example.hernan.esmiturno.R;
import com.example.hernan.esmiturno.model.Meet;
import com.example.hernan.esmiturno.model.User;
import com.example.hernan.esmiturno.util.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MeetNewAdapter extends RecyclerView.Adapter<MeetNewHolder> {
    private static final String DEBUG_TAG = "MeetNewAdapter";

    public Context context;
    public ArrayList<Meet> meetList;
    public User user;

    public MeetNewAdapter(Context context, ArrayList<Meet> meetList ,User user) {
        this.context = context;
        this.meetList = meetList;
        this.user = user;
    }

    @Override
    public void onBindViewHolder(MeetNewHolder viewHolder, int position) {
        TextView initial = viewHolder.getDate();
        TextView nameTextView = viewHolder.getHour();

        meetList.get(position).setFechaAsString(Util.getDateAsStringDefault(meetList.get(position).getFecha()));
        nameTextView.setText(meetList.get(position).getFechaAsString());

        initial.setBackgroundColor(meetList.get(position).getColorResource());
//        initial.setText(String.valueOf(meetList.get(position).getId()));
    }

    @Override
    public void onViewDetachedFromWindow(MeetNewHolder viewHolder) {
        super.onViewDetachedFromWindow(viewHolder);
        viewHolder.itemView.clearAnimation();
    }

    @Override
    public void onViewAttachedToWindow(MeetNewHolder viewHolder) {
        super.onViewAttachedToWindow(viewHolder);
        animateCircularReveal(viewHolder.itemView);
    }

    public void animateCircularReveal(View view) {
        int centerX = 0;
        int centerY = 0;
        int startRadius = 0;
        int endRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animation = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
        view.setVisibility(View.VISIBLE);
        animation.start();
    }

    public void animateCircularDelete(final View view, final int list_position) {
        int centerX = view.getWidth();
        int centerY = view.getHeight();
        int startRadius = view.getWidth();
        int endRadius = 0;
        Animator animation = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);

        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                Log.d(DEBUG_TAG, "MeetNewAdapter onAnimationEnd for Edit adapter position " + list_position);
                Log.d(DEBUG_TAG, "MeetNewAdapter onAnimationEnd for Edit cardId " + getItemId(list_position));

                view.setVisibility(View.INVISIBLE);
                meetList.remove(list_position);
                notifyItemRemoved(list_position);
            }
        });
        animation.start();
    }

    public void addCard(String name, int color) {
//        Card card = new Card();
//        card.setName(name);
//        card.setColorResource(color);
//        card.setId(getItemCount());
//        meetList.add(card);
        ((AddMeetActivity) context).doSmoothScroll(getItemCount());
        notifyItemInserted(getItemCount());
    }

    public void updateCard(String name, int list_position) {
//        meetList.get(list_position).setName(name);
        Log.d(DEBUG_TAG, "list_position is " + list_position);
        notifyItemChanged(list_position);
    }

    public void deleteCard(View view, int list_position) {
        animateCircularDelete(view, list_position);
    }

    @Override
    public int getItemCount() {
        if (meetList.isEmpty()) {
            return 0;
        } else {
            return meetList.size();
        }
    }

    @Override
    public long getItemId(int position) {
        return meetList.get(position).getId();
    }

    @Override
    public MeetNewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater li = LayoutInflater.from(viewGroup.getContext());
        View v = li.inflate(R.layout.card_meet_new_holder, viewGroup, false);
        return new MeetNewHolder(v,context,meetList,user);
    }

}
