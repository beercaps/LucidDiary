package com.luciddreamfactory.luciddiary.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luciddreamfactory.luciddiary.R;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by kevinwetzel on 09.09.16.
 */
public class TimeLineItem  extends AbstractFlexibleItem<TimeLineItem.MyViewHolder> {

    //TODO https://github.com/davideas/FlexibleAdapter/wiki/5.x-%7C-Setting-Up

    private String id;
    private String title;

    public TimeLineItem(String id, String title) {
        this.id = id;
        this.title = title;
    }

    /**
     * When an item is equals to another?
     * Write your own concept of equals, mandatory to implement.
     * This will be explained in next Wiki page.
     */
    @Override
    public boolean equals(Object inObject) {
        if (inObject instanceof TimeLineItem) {
            TimeLineItem inItem = (TimeLineItem) inObject;
            return this.id.equals(inItem.id);
        }
        return false;
    }

    /**
     * For the item type we need an int value: the layoutResID is sufficient.
     */
    @Override
    public int getLayoutRes() {
        return R.layout.card_dream_time_line;
    }

    /**
     * The Adapter is provided, because it will become useful for the MyViewHolder.
     * The unique instance of the LayoutInflater is also provided to simplify the
     * creation of the VH.
     */
    @Override
    public MyViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater,
                                         ViewGroup parent) {
        return new MyViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    /**
     * Also here the Adapter is provided to get more specific information from it.
     * NonNull Payload is provided as well, you should use it more often.
     */
    @Override
    public void bindViewHolder(FlexibleAdapter adapter, MyViewHolder holder, int position,
                               List payloads) {
        holder.mTitle.setText(title);
        //Appear disabled if item is disabled
        holder.mTitle.setEnabled(isEnabled());
    }

    /**
     * The MyViewHolder used by this item.
     * Extending from FlexibleViewHolder is recommended especially when you will use
     * more advanced features.
     */
    public class MyViewHolder extends FlexibleViewHolder {

        public TextView mTitle;

        public MyViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            mTitle = (TextView) view.findViewById(R.id.title);
        }
    }
}
