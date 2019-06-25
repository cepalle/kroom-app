package io.kroom.app.view.activitymain.playlisteditor.tabs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import io.kroom.app.R

class PlaylistPublicAdapter(private val dataSet: MutableList<Todo>, val mContext: Context)
    : ArrayAdapter<Todo>(mContext, R.layout.adapter_playlist_editor_public, dataSet) {

    companion object {
    }

    fun updateDataSet(todos: List<Todo>) {
        dataSet.clear()
        dataSet.addAll(todos)
    }

    private class ViewHolder {
        var cacheSpinnerColor: Spinner? = null
        var cacheValueTodo: TextView? = null
        var cacheButtonDel: Button? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val dataModel = dataSet[position]
        val viewHolder: ViewHolder // view lookup cache stored in tag

        if (convertView == null) {
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            convertView = inflater.inflate(R.layout.adapter_playlist_editor_public, parent, false)
            viewHolder.cacheSpinnerColor = convertView.findViewById(R.id.color_spinner)
            viewHolder.cacheValueTodo = convertView.findViewById(R.id.valueTodo)
            viewHolder.cacheButtonDel = convertView.findViewById(R.id.buttonDel)

            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder // can throw
        }

        viewHolder.cacheValueTodo?.text = dataModel.value
        viewHolder.cacheButtonDel?.setOnClickListener {
            GraphqlServer.delTodo(dataModel.id)
        }

        val adapter = ArrayAdapter.createFromResource(mContext,
            R.array.color_array, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        viewHolder.cacheSpinnerColor?.adapter = adapter
        viewHolder.cacheSpinnerColor?.setSelection(dataModel.color.toSpinnerPos())
        viewHolder.cacheSpinnerColor?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != dataModel.color.toSpinnerPos()) {
                    GraphqlServer.updateColorTodo(dataModel.id, p2.toColor())
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        convertView?.setBackgroundColor(dataModel.color.toColor())
        return convertView!!
    }
}
