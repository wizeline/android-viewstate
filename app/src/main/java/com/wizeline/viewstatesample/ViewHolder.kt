package com.wizeline.viewstatesample

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_holder_item.view.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val textView = itemView.textView

    fun bind(string: String) {
        textView.text = string
    }
}
