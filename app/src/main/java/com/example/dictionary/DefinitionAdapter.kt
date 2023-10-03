import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.R

class DefinitionAdapter : RecyclerView.Adapter<DefinitionAdapter.ViewHolder>() {

    private var definitions: List<Definition> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.definition_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val definition = definitions[position]
        holder.bind(definition)
    }

    override fun getItemCount(): Int {
        return definitions.size
    }

    fun setData(definitions: List<Definition>) {
        this.definitions = definitions
        notifyDataSetChanged()
    }

    fun clearData() {
        this.definitions = mutableListOf<Definition>()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.word)
        private val pos: TextView = itemView.findViewById(R.id.pos)
        private val def: TextView = itemView.findViewById(R.id.definition)

        fun bind(definition: Definition) {
            title.text = definition.title
            pos.text = definition.pos
            def.text = definition.definition
        }
    }
}
