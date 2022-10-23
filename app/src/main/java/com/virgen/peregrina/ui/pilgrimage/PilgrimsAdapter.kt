package com.virgen.peregrina.ui.pilgrimage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.virgen.peregrina.data.model.UserModel
import com.virgen.peregrina.util.EMPTY_STRING

class PilgrimsAdapter(
    context: Context,
    private val layoutResource: Int,
    private val list: List<String>
) : ArrayAdapter<String>(context, layoutResource, list), Filterable {


    //    private val listToString = list.map { "${it.name} ${it.last_name} ${it.email}" }
    private var listAux = list

    override fun getCount(): Int {
        return listAux.size
    }

    override fun getItem(position: Int): String? {
        return super.getItem(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: TextView = convertView as TextView? ?: LayoutInflater.from(context)
            .inflate(layoutResource, parent, false) as TextView
        view.text = listAux[position]
        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val query: String =
                    if (p0.isNullOrEmpty()) EMPTY_STRING else p0.toString().lowercase()
                val filterResults = FilterResults()
                filterResults.values = if (query.isEmpty()) list
                else list.filter {
                    it.contains(query)
                }
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                listAux = p1?.values as List<String>
                notifyDataSetChanged()
            }
        }
    }


}