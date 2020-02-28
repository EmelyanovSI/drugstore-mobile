package by.gsu.drugstore.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.gsu.drugstore.R
import by.gsu.drugstore.model.Drug

class DrugsAdapter(
    val drugs: List<Drug>,
    private val rowLayout: Int
) : RecyclerView.Adapter<DrugsAdapter.DrugViewHolder>() {

    class DrugViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.title)
        val txtComposition: TextView = itemView.findViewById(R.id.active_substance)
        val txtCountry: TextView = itemView.findViewById(R.id.country)
        val txtId: TextView = itemView.findViewById(R.id.id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrugViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
        return DrugViewHolder(v)
    }

    override fun onBindViewHolder(holder: DrugViewHolder, position: Int) {
        holder.txtName.text = drugs[position].getName()
        holder.txtComposition.text = drugs[position].getComposition()
        holder.txtCountry.text = drugs[position].getCountry()
        holder.txtId.text = drugs[position].getId().toString()
    }

    override fun getItemCount(): Int {
        return drugs.size
    }

}
