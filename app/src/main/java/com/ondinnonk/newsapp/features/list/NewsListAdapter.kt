package com.ondinnonk.newsapp.features.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ondinnonk.newsapp.databinding.ItemNewsListBinding
import com.ondinnonk.newsapp.features.NewsUiModel
import com.ondinnonk.newsapp.utils.ListDiffUtil
import org.koin.core.KoinComponent

class NewsListAdapter(
    val onSelect: (item: NewsUiModel) -> Unit,
) :
    RecyclerView.Adapter<NewsListAdapter.ViewHolder>(),
    KoinComponent {

    private var listData: ArrayList<NewsUiModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(listData[position])

    fun setListData(l: List<NewsUiModel>) {
        val diffCallback = ListDiffUtil(listData, l, ::isContentSame)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listData.clear()
        listData.addAll(l)
        diffResult.dispatchUpdatesTo(this)
    }

    private fun isContentSame(old: NewsUiModel, new: NewsUiModel): Boolean {
        return old.title == new.title &&
                old.author == new.author &&
                old.date == new.date &&
                old.image == new.image
    }

    inner class ViewHolder(val binding: ItemNewsListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: NewsUiModel): Unit = with(itemView) {
            binding.root.setOnClickListener { onSelect(model) }
            binding.title.text = model.title
            binding.author.text = model.author
            binding.date.text = model.date
        }
    }

}