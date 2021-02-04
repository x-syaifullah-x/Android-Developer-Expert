package com.the.movie.db.source.local.entity.search

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.the.movie.db.source.local.base.IEntity
import com.the.movie.db.source.local.entity.search.SearchEntity.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
)
data class SearchEntity(

    @PrimaryKey(autoGenerate = false)
    override val primaryKey: Int,

    val mediaType: String,

    @ColumnInfo(name = TITLE)
    val title: String

) : IEntity<Int> {
    companion object {
        const val TABLE_NAME = "search"
        const val TITLE = "title"
    }
}