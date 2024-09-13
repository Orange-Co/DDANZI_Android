package co.orange.core.util

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LinearInfiniteScrollListener(
    private val loadMore: () -> Unit,
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(
        recyclerView: RecyclerView,
        dx: Int,
        dy: Int,
    ) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0 && !recyclerView.canScrollVertically(1)) {
            (recyclerView.layoutManager as? LinearLayoutManager)?.let { layoutManager ->
                if (layoutManager.findLastVisibleItemPosition() == recyclerView.adapter?.itemCount?.minus(1)) {
                    loadMore()
                }
            }
        }
    }
}

class GridInfiniteScrollListener(
    private val loadMore: () -> Unit,
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(
        recyclerView: RecyclerView,
        dx: Int,
        dy: Int,
    ) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0 && !recyclerView.canScrollVertically(1)) {
            (recyclerView.layoutManager as? GridLayoutManager)?.let { layoutManager ->
                if (layoutManager.findLastVisibleItemPosition() == recyclerView.adapter?.itemCount?.minus(1)) {
                    loadMore()
                }
            }
        }
    }
}
