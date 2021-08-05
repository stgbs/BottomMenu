package com.anroid.bottommenu

// WIKI 페이지의 리사이클러뷰에서 사용할 모델
// 목차, 해당 목차 내 내용, 접기/펼치기 여부
class Forum (var category: String = "", var content : String ="", var isExpanded: Boolean = false)