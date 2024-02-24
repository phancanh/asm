package com.example.asm.model

import com.google.gson.annotations.Expose

data class KeyValue(
    @Expose
    var key: Int = 0,
    @Expose
    var value: String = "",
    @Expose
    var isChoose: Boolean = false,

    )
