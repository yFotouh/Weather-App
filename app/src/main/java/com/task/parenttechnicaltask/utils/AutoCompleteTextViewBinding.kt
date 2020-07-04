package com.task.parenttechnicaltask.utils

import android.os.Build
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ListView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData
import com.task.parenttechnicaltask.R
object AutoCompleteTextViewBinding
{

//    @BindingAdapter("valueAttrChanged")
//    fun AutoCompleteTextView.setListener(listener: InverseBindingListener?) {
//        this.onItemSelectedListener = if (listener != null) {
//            object : AdapterView.OnItemSelectedListener {
//                override fun onNothingSelected(parent: AdapterView<*>?) {
//                    listener.onChange()
//                }
//
//                override fun onItemSelected(
//                    parent: AdapterView<*>?,
//                    view: View?,
//                    position: Int,
//                    id: Long
//                ) {
//                    listener.onChange()
//                }
//            }
//        } else {
//            null
//        }
//    }

//    @BindingAdapter("valueAttrChanged")
//    fun AutoCompleteTextView.setListener(listener: InverseBindingListener?) {
//        onItemClickListener = listener?.let {
//            AdapterView.OnItemClickListener { _, _, position, _ ->
//                setTag(R.id.autoCompleteTextViewListSelection, position)
//                it.onChange()
//            }
//        }
//    }
@get:InverseBindingAdapter(attribute = "value")
@set:BindingAdapter("value")
@JvmStatic
var AutoCompleteTextView.selectedValue: Any?
    get() = if (listSelection != ListView.INVALID_POSITION) adapter.getItem(listSelection) else null
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    set(value) {
        val newValue = value ?: adapter.getItem(0)
        setText(newValue.toString(), true)
        if (adapter is ArrayAdapter<*>) {
            val position = (adapter as ArrayAdapter<Any?>).getPosition(newValue)
            listSelection = position
        }
    }


@BindingAdapter("entries", "itemLayout", "textViewId","testAtt", requireAll = false)
@JvmStatic
fun AutoCompleteTextView.bindAdapter(
    entries: List<Any?>?,
//    entries: MutableLiveData<List<Any>?>?,
    @LayoutRes itemLayout: Int?,
    @IdRes textViewId: Int?,
    testAtt :String?
) {
    val adapter = when {
        itemLayout == null -> {
            ArrayAdapter(
                context, android.R.layout.simple_list_item_1
//                    , R.id.dropdownText
                , entries
            )
        }
        textViewId == null -> {
            ArrayAdapter(context, itemLayout, entries)
        }
        else -> {
            ArrayAdapter(context, itemLayout, textViewId, entries)
        }
    }
    setAdapter(adapter)
}
}
