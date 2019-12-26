package com.project.oddbid.utils

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun showDialogJustPositive(context: Context, title: String, message: String, positive: String, onClickListener: DialogInterface.OnClickListener) {
    val dialog = AlertDialog.Builder(context)
    dialog.setTitle(title)
    dialog.setCancelable(false)
    dialog.setMessage(message)
    dialog.setPositiveButton(positive, onClickListener)
    dialog.show()
}

fun showDateTimePicker(context: Context, editText: EditText?) {
    val currentDate = Calendar.getInstance()
    val date = Calendar.getInstance()
    DatePickerDialog(context, { _, year, monthOfYear, dayOfMonth ->
        date.set(year, monthOfYear, dayOfMonth)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val format = sdf.format(date.time)
        editText?.setText(format)
    }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show()
}