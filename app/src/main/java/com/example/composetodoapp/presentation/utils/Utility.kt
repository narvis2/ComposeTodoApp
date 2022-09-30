package com.example.composetodoapp.presentation.utils

import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter


fun timeFormatter(
    pattern: String = "yyyy-MM-dd HH:mm:ss"
): DateTimeFormatter = DateTimeFormat.forPattern(pattern)
    .withZone(DateTimeZone.forID("Asia/Seoul"))