package com.wizeline.viewstatesample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.wizeline.viewstate.State
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showContentWithLoading()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.content -> showContentWithLoading()
            R.id.loading -> showLoading()
            R.id.empty -> showEmptyWithLoading()
            R.id.error -> showErrorWithLoading()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showContentWithLoading() {
        viewState.setState(State.LOADING)
        Timer().schedule(1000) {
            runOnUiThread {
                val adapter = Adapter()
                recyclerView.adapter = adapter
                adapter.setItems(listOf("Item 1", "Item 2", "Item 3"))
                viewState.setState(State.CONTENT)
            }
        }
    }

    private fun showLoading() {
        viewState.setState(State.LOADING)
    }

    private fun showEmptyWithLoading() {
        viewState.setState(State.LOADING)
        Timer().schedule(1000) {
            runOnUiThread {
                val adapter = Adapter()
                recyclerView.adapter = adapter
                adapter.setItems(emptyList())
                viewState.setState(State.EMPTY)
            }
        }
    }

    private fun showErrorWithLoading() {
        viewState.setState(State.LOADING)
        Timer().schedule(1000) {
            runOnUiThread {
                viewState.setState(State.ERROR)
                viewState.setOnRetryClickListener {
                    viewState.setState(State.LOADING)
                    Timer().schedule(1000) {
                        runOnUiThread {
                            val adapter = Adapter()
                            recyclerView.adapter = adapter
                            adapter.setItems(listOf("Item 1", "Item 2", "Item 3"))
                            viewState.setState(State.CONTENT)
                        }
                    }
                }
            }
        }
    }
}
