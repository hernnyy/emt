package com.example.hernan.esmiturno.adapter.list;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.hernan.esmiturno.R;
import com.example.hernan.esmiturno.model.list.AutoCompleteDTO;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteArrayAdapter extends ArrayAdapter<AutoCompleteDTO> {
    private final Context mContext;
    private final List<AutoCompleteDTO> suggestedOptions;
    private final List<AutoCompleteDTO> responseList;
    private final int mLayoutResourceId;

    public AutoCompleteArrayAdapter(Context context, int resource, List<AutoCompleteDTO> customers) {
        super(context, resource, customers);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.suggestedOptions = new ArrayList<>(customers);
        this.responseList = new ArrayList<>(customers);
    }

    public int getCount() {
        return suggestedOptions.size();
    }

    public AutoCompleteDTO getItem(int position) {
        return suggestedOptions.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                convertView = inflater.inflate(mLayoutResourceId, parent, false);
            }
            AutoCompleteDTO department = getItem(position);
            TextView name = (TextView) convertView.findViewById(android.R.id.text1);
            name.setText(department.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public String convertResultToString(Object resultValue) {
                return ((AutoCompleteDTO) resultValue).getDescription();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<AutoCompleteDTO> departmentsSuggestion = new ArrayList<>();
                if (constraint != null) {
                    for (AutoCompleteDTO department : responseList) {
                        if (department.getDescription().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            departmentsSuggestion.add(department);
                        }
                    }
                    filterResults.values = departmentsSuggestion;
                    filterResults.count = departmentsSuggestion.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                suggestedOptions.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using suggestedOptions.addAll((ArrayList<Department>) results.values);
                    for (Object object : (List<?>) results.values) {
                        if (object instanceof AutoCompleteDTO) {
                            suggestedOptions.add((AutoCompleteDTO) object);
                        }
                    }
                    notifyDataSetChanged();
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    suggestedOptions.addAll(responseList);
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}