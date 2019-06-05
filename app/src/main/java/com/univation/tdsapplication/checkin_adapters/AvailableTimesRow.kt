package com.univation.tdsapplication.checkin_adapters

import android.view.View
import com.univation.tdsapplication.R
import com.univation.tdsapplication.objects.ScheduledTimeObject
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.check_in_available_times.view.*

class AvailableTimesRow(val availableTimeObject: ScheduledTimeObject): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.available_time_textview_check_in.text = availableTimeObject.time
        if(!availableTimeObject.uid.isEmpty()){
            viewHolder.itemView.cardview_available_times.visibility = View.GONE
        }
    }

    override fun getLayout(): Int {
        return R.layout.check_in_available_times
    }
}