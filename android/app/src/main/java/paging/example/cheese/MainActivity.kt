package paging.example.cheese

import android.arch.lifecycle.Observer
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.widget.LinearLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val cheeseApi = CheeseApi.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DividerItemDecoration(recyclerView.context, LinearLayout.VERTICAL).apply {
            setDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.divider)!!)
            recyclerView.addItemDecoration(this)
            recyclerView.setHasFixedSize(true)
        }

        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(20)
                .setPageSize(10)
                .setPrefetchDistance(5)
                .setEnablePlaceholders(false)
                .build()

        val builder = LivePagedListBuilder<Int, Cheese>(object: DataSource.Factory<Int, Cheese>() {
            override fun create(): DataSource<Int, Cheese> {
                return CheeseItemKeyedDataSource(cheeseApi)
            }
        }, config)

        val adapter = CheeseAdapter({ it ->
            cheeseApi.like(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ ->
                    (recyclerView.adapter as CheeseAdapter).currentList?.dataSource?.invalidate()
                }
        })
        recyclerView.adapter = adapter
        builder.build()
            .observe(this, Observer {
                adapter.submitList(it)
            })
    }
}
