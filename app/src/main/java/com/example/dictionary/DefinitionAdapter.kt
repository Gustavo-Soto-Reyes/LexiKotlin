import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.R
import kotlin.coroutines.coroutineContext

class DefinitionAdapter(
    val context: Context
) : RecyclerView.Adapter<DefinitionAdapter.ViewHolder>() {

    private var definitions: List<MeaningCard> = emptyList()

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

    fun setData(definitions: List<MeaningCard>) {
        this.definitions = definitions
        notifyDataSetChanged()
    }

    fun clearData() {
        this.definitions = mutableListOf<MeaningCard>()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.word)
        private val pos: TextView = itemView.findViewById(R.id.pos)
        private val def: TextView = itemView.findViewById(R.id.definition)

        fun bind(definition: MeaningCard) {
            title.text = definition.title
            pos.text = definition.pos
            val definitionsList = definition.definition.joinToString("\n- ")

            def.text = if (definitionsList.isNotEmpty()){
                "- " + definitionsList
            } else{
                ""
            }
            itemView.setOnClickListener{
                View.OnClickListener {
                    Toast.makeText(context,"Definition Clicked", Toast.LENGTH_LONG)
                }
            }
        }
    }
}
