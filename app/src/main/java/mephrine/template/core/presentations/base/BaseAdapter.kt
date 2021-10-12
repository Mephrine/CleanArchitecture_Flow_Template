package mephrine.template.core.presentations.base

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import mephrine.template.core.extensions.debounce
import mephrine.template.core.utils.DiffCallback
import mephrine.template.core.utils.L
import kotlinx.coroutines.ObsoleteCoroutinesApi

abstract class BaseAdapter<T, H : RecyclerView.ViewHolder> :
  RecyclerView.Adapter<H>() {

  var onItemClickListener: OnItemClickListener? = null
  protected var itemList: MutableList<T>? = null
  var clickable = true

  override fun getItemCount(): Int = itemList?.size ?: 0

  fun getList(): List<T>? = itemList

  fun setItems(items: List<T>) {
    itemList = items.toMutableList()

    notifyDataSetChanged()
  }

  fun updateItems(items: List<T>) {
    itemList?.let { list ->
      list.clear()
      list.addAll(items)
    } ?: run {
      itemList = items.toMutableList()
    }

    notifyDataSetChanged()
  }

  fun updateItemsWithDiffUtil(items: List<T>) {
    val diffResult = DiffUtil.calculateDiff(DiffCallback(itemList ?: listOf(), items))
    itemList = items.toMutableList()
    diffResult.dispatchUpdatesTo(this)
  }

  fun addItems(items: List<T>) {
    if (itemList == null) {
      itemList = items.toMutableList()
    } else {
      itemList!!.addAll(items)
    }

    notifyDataSetChanged()
  }

  fun replaceItem(index: Int, item: T) {
    itemList!![index] = item
    notifyItemChanged(index)
  }

  @ObsoleteCoroutinesApi
  override fun onBindViewHolder(holder: H, position: Int) {
    if (clickable) {
      holder.itemView.debounce(300L) {
        try {
          onItemClickListener?.onItemClick(
            holder.itemView,
            holder.adapterPosition
          )
        } catch (exception: Exception) {
          L.e(exception)
        }
      }
    }
  }

  interface OnItemClickListener {
    fun onItemClick(view: View, position: Int)
  }
}