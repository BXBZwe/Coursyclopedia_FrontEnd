import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.courscyclopedia.R
import com.example.courscyclopedia.model.Subject

class WishlistAdapter(private val onItemClick: (Subject) -> Unit) :
    RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    private var wishlist: List<Subject> = emptyList()

    fun submitList(wishlist: List<Subject>) {
        this.wishlist = wishlist
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wishlist, parent, false)
        return WishlistViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        holder.bind(wishlist[position])
    }

    override fun getItemCount(): Int = wishlist.size

    class WishlistViewHolder(itemView: View, private val onItemClick: (Subject) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val textViewSubjectName: TextView = itemView.findViewById(R.id.textViewSubjectName)

        fun bind(subject: Subject) {
            textViewSubjectName.text = subject.subjectname
            itemView.setOnClickListener { onItemClick(subject) }
        }
    }
}
