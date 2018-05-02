package paging.example.cheese

import android.arch.paging.PositionalDataSource
import android.util.Log

class CheesePositionalDataSource(private val cheeseApi: CheeseApi) : PositionalDataSource<Cheese>() {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Cheese>) {
        val firstLoadPosition = PositionalDataSource.computeInitialLoadPosition(params, computeCount())
        val firstLoadSize = PositionalDataSource.computeInitialLoadSize(params, firstLoadPosition, 654)

        Log.d("DataSource", "$firstLoadPosition + ${firstLoadPosition + firstLoadSize}")

        cheeseApi.getCheeses(offset = firstLoadPosition, limit = firstLoadSize)
            .subscribe({ result ->
                result.response()?.body()?.let {
                    callback.onResult(it.cheeses, firstLoadPosition, it.total)
                }
            })
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Cheese>) {
        Log.d("DataSource", "${params.startPosition} + ${params.startPosition + params.loadSize}")

        cheeseApi.getCheeses(offset = params.startPosition, limit = params.loadSize)
            .subscribe({ result ->
                result.response()?.body()?.let {
                    callback.onResult(it.cheeses)
                }
            })
    }

    private fun computeCount(): Int {
        return cheeseApi.getCount()
            .blockingGet().response()?.body() ?: 0
    }
}
