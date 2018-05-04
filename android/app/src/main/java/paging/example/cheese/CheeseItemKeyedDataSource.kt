package paging.example.cheese

import android.arch.paging.ItemKeyedDataSource
import android.util.Log

class CheeseItemKeyedDataSource(private val cheeseApi: CheeseApi) : ItemKeyedDataSource<Int, Cheese>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Cheese>) {
        Log.d("DataSource", "loadInitial: ${params.requestedInitialKey} + ${params.requestedLoadSize}")

        if (params.requestedInitialKey == null) {
            cheeseApi.getCheeses(since = params.requestedInitialKey, until = null, limit = params.requestedLoadSize)
                .blockingGet()
                .response()?.body()?.let {
                    callback.onResult(it.cheeses)
                }
        } else {
            cheeseApi.getCheeses(params.requestedInitialKey, limit = params.requestedLoadSize)
                .blockingGet()
                .response()?.body()?.let {
                    callback.onResult(it.cheeses)
                }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Cheese>) {
        Log.d("DataSource", "loadAfter: ${params.key} + ${params.requestedLoadSize}")

        cheeseApi.getCheeses(since = params.key, until = null, limit = params.requestedLoadSize)
            .subscribe({ result ->
                result.response()?.body()?.let {
                    callback.onResult(it.cheeses)
                }
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Cheese>) {
        Log.d("DataSource", "loadBefore: ${params.key} + ${params.requestedLoadSize}")

        cheeseApi.getCheeses(since = null, until = params.key, limit = params.requestedLoadSize)
            .subscribe({ result ->
                result.response()?.body()?.let {
                    callback.onResult(it.cheeses)
                }
            })

    }

    override fun getKey(item: Cheese): Int {
        return item.id
    }
}