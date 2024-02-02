package fanshawe.example.testingdatabaserecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapter(private val dataSet: ArrayList<Person>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewId: TextView
        val textViewAge: TextView
        val textViewName: TextView
        val textViewEmail: TextView
        val progressBarAge: ProgressBar

        init {
            // Define click listener for the ViewHolder's View.
            textViewId = view.findViewById(R.id.textViewRVId)
            textViewAge = view.findViewById(R.id.textViewRVAge)
            textViewName = view.findViewById(R.id.textViewRVName)
            textViewEmail = view.findViewById(R.id.textViewRVEmail)
            progressBarAge = view.findViewById(R.id.progressBarRVAge)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.layout, viewGroup, false)

        // add the 3 lines of code below to show 5 recycler items in the activity at a time
        val lp = view.getLayoutParams()
        lp.height = 512
        view.setLayoutParams(lp)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textViewName.text = dataSet[position].name
        viewHolder.textViewAge.text = dataSet[position].age.toString()
        viewHolder.textViewId.text = (dataSet[position].id).toString()
        viewHolder.textViewEmail.text = dataSet[position].email
        viewHolder.progressBarAge.setProgress(dataSet[position].age)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
