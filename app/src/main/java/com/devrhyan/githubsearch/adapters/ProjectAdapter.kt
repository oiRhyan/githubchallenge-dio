package com.devrhyan.githubsearch.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.devrhyan.githubsearch.R
import com.devrhyan.githubsearch.models.UserItem
import kotlin.coroutines.coroutineContext

class ProjectAdapter() : Adapter<ProjectAdapter.ProjectViewHolder>() {

    private var projectList : List<UserItem> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setUserProjectList(list : List<UserItem>) {
        projectList = list;
        notifyDataSetChanged()
    }

    var sharedLinkList : (UserItem) -> Unit = {}

    inner class ProjectViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val projectName : TextView = view.findViewById(R.id.projectName)
        val shareProjectButton : ImageView = view.findViewById(R.id.sharedActionImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val inflater = layoutInflater.inflate(R.layout.recycler_view_item, parent, false)
        return ProjectViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val listItem = projectList[position]
        holder.projectName.text = listItem.name

        holder.shareProjectButton.setOnClickListener {
             sharedLinkList(listItem)
        }
    }
}