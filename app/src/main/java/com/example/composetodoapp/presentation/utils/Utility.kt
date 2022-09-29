package com.example.composetodoapp.presentation.utils

import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat


fun timeFormatter() = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
    .withZone(DateTimeZone.forID("Asia/Seoul"))