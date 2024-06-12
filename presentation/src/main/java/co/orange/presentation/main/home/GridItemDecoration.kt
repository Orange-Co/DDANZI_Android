package co.orange.presentation.main.home

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import co.orange.presentation.main.home.HomeAdapter.Companion.HEADER_COUNT
import co.orange.presentation.main.home.HomeAdapter.Companion.VIEW_TYPE_PRODUCT

class GridItemDecoration(
    private val spanCount: Int,
    private val spacing: Int,
    private val bottomPadding: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val viewType = parent.adapter?.getItemViewType(position)

        if (viewType == VIEW_TYPE_PRODUCT) {
            val column = (position - HEADER_COUNT) % spanCount
            outRect.right = column * spacing / spanCount
            outRect.left = spacing - (column + 1) * spacing / spanCount
        } else {
            outRect.setEmpty()
        }

        if (position == parent.adapter?.itemCount?.minus(1)) {
            outRect.bottom = bottomPadding
        }
    }
}