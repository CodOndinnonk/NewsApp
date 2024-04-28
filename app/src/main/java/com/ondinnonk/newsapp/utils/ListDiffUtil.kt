package com.ondinnonk.newsapp.utils

import androidx.recyclerview.widget.DiffUtil

/**
 * Class to implement compare changes for lists
 *
 * Use in setting data list for adapter
 * [<p>] val diffResult = DiffUtil.calculateDiff(diffCallback)
 * [<p>] diffResult.dispatchUpdatesTo(this)
 *
 * @param contentComparator compare objects content and return are they same
 */
class ListDiffUtil<Type>(
    private val oldList: List<Type>,
    private val newList: List<Type>,
    private val contentComparator: (old: Type, new: Type) -> Boolean
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList.get(newItemPosition)
    }

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return contentComparator.invoke(oldList[oldItemPosition], newList[newItemPosition])
    }
}