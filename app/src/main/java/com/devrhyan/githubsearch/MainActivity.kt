package com.devrhyan.githubsearch

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.devrhyan.githubsearch.adapters.ProjectAdapter
import com.devrhyan.githubsearch.databinding.ActivityMainBinding
import com.devrhyan.githubsearch.models.UserItem
import com.devrhyan.githubsearch.services.data.RetrofitService
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val adapter = ProjectAdapter()
    private val retrofit = RetrofitService.getServiceInstance()

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

        binding.userProjectsRecyclerView.adapter = adapter
        binding.userProjectsRecyclerView.layoutManager = LinearLayoutManager(this);

        binding.requireUserButton.setOnClickListener {
            lifecycleScope.launch {
                try {
                    binding.userProjectsRecyclerView.visibility = LinearLayout.GONE
                    binding.progressBar.visibility = LinearLayout.VISIBLE
                    binding.gitHubIcon.visibility = LinearLayout.GONE
                    execServerAction(binding.userGitEntry.text.toString())
                    binding.userProjectsRecyclerView.visibility = LinearLayout.VISIBLE
                } catch (e : Exception) {
                    Toast.makeText(applicationContext, "Usuário não encontrado", Toast.LENGTH_SHORT).show()
                    binding.gitHubIcon.visibility = LinearLayout.VISIBLE
                }

            }
        }

        adapter.sharedLinkList = { item ->
            val Intent = Intent(Intent.ACTION_VIEW)
            Intent.data = item.html_url.toUri()
            startActivity(Intent)
        }

    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun execServerAction(user: String) {
        val response = retrofit.getPosts(user)
            try {
                if(response.isSuccessful) {
                    val body = response.body()
                    adapter.setUserProjectList(body!!)
                    binding.progressBar.visibility = LinearLayout.GONE
                }
            } catch (e : Exception) {
                Log.e("Error", "Erro ao recuperar projetos")
            }
    }
}