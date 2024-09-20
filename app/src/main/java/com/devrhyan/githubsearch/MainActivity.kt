package com.devrhyan.githubsearch

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.devrhyan.githubsearch.adapters.ProjectAdapter
import com.devrhyan.githubsearch.databinding.ActivityMainBinding
import com.devrhyan.githubsearch.models.UserItem

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val adapter = ProjectAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val ListProjectsTest : MutableList<UserItem> = mutableListOf(
            UserItem(1, "Projeto 1", "ID 1", "URL 1"),
            UserItem(2, "Projeto 2", "ID 2", "URL 2"),
            UserItem(3, "Projeto 3", "ID 3", "URL 3"),
            UserItem(4, "Projeto 4", "ID 4", "URL 4"),
            UserItem(5, "Projeto 5", "ID 5", "URL 5")
        )

        adapter.setUserProjectList(ListProjectsTest)
        binding.userProjectsRecyclerView.adapter = adapter
        binding.userProjectsRecyclerView.layoutManager = LinearLayoutManager(this);
    }
}