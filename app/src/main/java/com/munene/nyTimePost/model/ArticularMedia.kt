package com.munene.nyTimePost.model

import com.google.gson.annotations.SerializedName

data class ArticularMedia(
    @SerializedName("type")
    val type: String,
    @SerializedName("subtype")
    val subtype: String,
    @SerializedName("caption")
    val caption: String,
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("approved_for_syndication")
    val approved_for_syndication: Int,
    @SerializedName("media-metadata")
    val mediaMetadata: List<ArticleMetaData>
)
