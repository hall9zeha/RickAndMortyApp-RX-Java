package com.barrygithub.rickandmortyapp.data.localDatasource

import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import io.reactivex.rxjava3.core.SingleOnSubscribe
import kotlinx.parcelize.Parcelize

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 14/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/
data class EntityDb(
    @Embedded val info:MetaData = MetaData(),
    @Relation(
        parentColumn = "idMetadata",
        entityColumn = "idMetadata",
        entity = Character::class
    )
    var results:List<Character> = arrayListOf()
)

@Entity
data class MetaData(@PrimaryKey(autoGenerate = true) var idMetadata: Long=0,
                    var count:Int=0,
                    var pages:Int=0)

@Parcelize
@Entity(
    indices = [Index(value = ["id"], unique = true)],
    foreignKeys = [
        ForeignKey(entity = MetaData::class,
                    parentColumns = ["idMetadata"],
                    childColumns = ["idMetadata"],
                    onDelete = ForeignKey.CASCADE)
    ]
)
data class Character(
    @ColumnInfo(index = true) var idMetadata:Long=0,
    @PrimaryKey var id:Int=0,
    var name:String="",
    var status:String="",
    var species:String="",
    var gender:String="",
    var type:String="",
    var image:String="",
    @Ignore
    var episodes:List<Episode> = arrayListOf()


):Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Character

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}
@Parcelize
data class Episode(
    @SerializedName("id")var id:Int,
    @SerializedName("name") var name:String,
    @SerializedName("air_date")var airDate:String,
    @SerializedName("episode") var episode:String
):Parcelable
