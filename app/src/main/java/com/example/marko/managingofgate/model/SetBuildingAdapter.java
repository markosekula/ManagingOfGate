package com.example.marko.managingofgate.model;

import android.content.Context;
import android.view.ViewGroup;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class SetBuildingAdapter extends  RecyclerViewAdapterBase<GateObject, GateObjectItemView> {

    @RootContext
    Context context;

    @Override
    protected GateObjectItemView onCreateItemView(ViewGroup parent, int viewType) {
        return GateObjectItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<GateObjectItemView> holder, int position) {

        GateObjectItemView gateObjectItemView = holder.getView();
        GateObject gateObject = items.get(position);

        int new_position = position + 1;
        gateObjectItemView.bind(gateObject, new_position);
    }

}



