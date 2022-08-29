package com.danibelmonte.marvelcompose.data.entities

import androidx.annotation.StringRes
import com.danibelmonte.marvelcompose.R

data class Comic(
    override val id: Int,
    override val title: String,
    override val description: String,
    override val thumbnail: String,
    val format: Format,
    override val references: List<ReferenceList>,
    override val urls: List<Url>
) : MarvelItem {
    enum class Format(val stringRes: Int) {
        COMIC(R.string.comic),
        MAGAZINE(R.string.magazine),
        TRADE_PAPERBACK(R.string.trade_paperback),
        HARDCOVER(R.string.hardcover),
        DIGEST(R.string.digest),
        GRAPHIC_NOVEL(R.string.graphics_novel),
        DIGITAL_COMIC(R.string.digital_comics),
        INFINITE_COMIC(R.string.infinite_comics)
    }
}