/* Copyright 2022 Lawnchair

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. */

package app.lawnchair.ossnotices

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

fun Context.getOssLibraries(thirdPartyLicenseMetadataId: Int): List<OssLibrary> {
    val bufferedReader = BufferedReader(InputStreamReader(resources.openRawResource(thirdPartyLicenseMetadataId)))
    val ossLibraries = bufferedReader.readLines().map { line ->
        val parts = line.split(" ")
        val noticeInfo = parts[0].split(":")
        val noticeStartIndex = noticeInfo[0].toLong()
        val noticeLength = noticeInfo[1].toInt()
        val name = parts.subList(
            fromIndex = 1,
            toIndex = parts.size,
        ).joinToString(separator = " ")
        OssLibrary(
            name = name,
            noticeStartIndex = noticeStartIndex,
            noticeLength = noticeLength,
        )
    }.sortedBy { it.name.lowercase() }
    return ossLibraries
}
