package com.ahrorovk.labwork.core

class Result {
    fun success(): String {
        return "Success"
    }

    fun failure(): String {
        return "Failure"
    }

    fun retry(): String {
        return "Retry"
    }
}