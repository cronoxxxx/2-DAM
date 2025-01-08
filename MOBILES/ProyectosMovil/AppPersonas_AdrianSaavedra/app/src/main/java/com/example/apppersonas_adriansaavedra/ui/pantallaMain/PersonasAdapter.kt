package com.example.apppersonas_adriansaavedra.ui.pantallaMain

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apppersonas_adriansaavedra.R
import com.example.apppersonas_adriansaavedra.domain.modelo.Persona

class PersonasAdapter(
    private val context: Context,
    private val actions: PersonaActions
) : ListAdapter<Persona, PersonasViewHolder>(PersonaDiffCallback()) {

    interface PersonaActions {
        fun onDelete(persona: Persona)
        fun onStartSelectMode(persona: Persona)
        fun itemHasClicked(persona: Persona)
        fun onClickItem(personaId: Int)
        fun onClickAcceso(personaId: Int)
        fun onPersonaRemovedFromSelection(persona: Persona)
        fun showSelectedMessage()
    }

    private var selectedPersonas = mutableListOf<Persona>()
    private var selectedMode: Boolean = false

    fun resetSelectMode() {
        val previouslySelectedPositions = selectedPersonas.map { currentList.indexOf(it) }
        selectedMode = false
        selectedPersonas.clear()
        previouslySelectedPositions.forEach { notifyItemChanged(it) }
    }

    fun updateSelectedItems(selected: List<Persona>, selectedMode: Boolean) {
        selectedPersonas.clear()
        selectedPersonas.addAll(selected)
        this.selectedMode = selectedMode
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_persona, parent, false)
        return PersonasViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonasViewHolder, position: Int) {
        val item = getItem(position)
        holder.render(item) { personaId ->
            if (!selectedMode) {
                actions.onClickAcceso(personaId)
            } else{
                actions.showSelectedMessage()
            }
        }

        holder.itemView.setOnClickListener {
            if (selectedMode) {
                val updatedPosition = toggleSelection(item)
                notifyItemChanged(updatedPosition)
                actions.itemHasClicked(item)
            } else {
                actions.onClickItem(item.id)
            }
        }

        holder.itemView.setOnLongClickListener {
            if (!selectedMode) {
                selectedMode = true
                val updatedPosition = toggleSelection(item)
                notifyItemChanged(updatedPosition)
                actions.onStartSelectMode(item)
            }
            true
        }

        updateItemAppearance(holder.itemView, item in selectedPersonas)
    }



    private fun toggleSelection(persona: Persona): Int {
        val position = currentList.indexOf(persona)
        if (persona in selectedPersonas) {
            selectedPersonas.remove(persona)
        } else {
            selectedPersonas.add(persona)
        }
        return position
    }

    private fun updateItemAppearance(itemView: View, isSelected: Boolean) {
        itemView.setBackgroundColor(
            if (isSelected && selectedMode) ContextCompat.getColor(context, R.color.gray)
            else ContextCompat.getColor(context, android.R.color.transparent)
        )
    }

    val swipeGesture = object : SwipeGesture(context) {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            when (direction) {
                ItemTouchHelper.LEFT -> {
                    val position = viewHolder.bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val deletedPersona = getItem(position)
                        actions.onDelete(deletedPersona)
                        if (selectedPersonas.remove(deletedPersona)) {
                            actions.onPersonaRemovedFromSelection(deletedPersona)
                        }
                        val newList = currentList.toMutableList()
                        newList.removeAt(position)
                        submitList(newList)
                    }
                }
            }
        }
    }
}
class PersonaDiffCallback : DiffUtil.ItemCallback<Persona>() {
    override fun areItemsTheSame(oldItem: Persona, newItem: Persona): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Persona, newItem: Persona): Boolean {
        return oldItem == newItem
    }
}

