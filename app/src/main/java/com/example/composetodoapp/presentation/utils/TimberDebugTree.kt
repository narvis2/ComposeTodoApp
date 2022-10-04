package com.example.composetodoapp.presentation.utils

import timber.log.Timber

class TimberDebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        return "파일 이름(${element.fileName}):라인 넘버(${element.lineNumber}) -> 함수 이름(${element.methodName})"
    }
}