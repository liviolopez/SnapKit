package dev.all4.snapkit.utils

import android.util.Log
import com.google.gson.Gson
import dev.all4.snapkit.BuildConfig

/**
 * Created by Livio Lopez on 12/05/20.
 */

private const val TAG_LOG = "SNAP-KIT"
private const val PACKAGE_PATH = "dev.all4.snapkit"

data class LogcatDebug(val level:Int = Log.DEBUG, val link:Boolean = true)

private fun tagLog(TAG: String): String{
    return if(TAG.isNotEmpty()){ "$TAG_LOG:$TAG" } else TAG_LOG
}

private fun lastTraceElement(): StackTraceElement? = Thread.currentThread().stackTrace.filter { it.className.startsWith(
    PACKAGE_PATH
) }.firstOrNull{ it.fileName != "Logcat.kt" }

private fun classPath(trace: StackTraceElement?): String? {
    return (trace?.className?.split('$')?.firstOrNull() ?: trace?.className)
        ?.replace('.', '/')
        ?.replace(PACKAGE_PATH.replace(".","/"),"")
        ?.split("/")?.dropLast(1)?.joinToString("/")
}

private fun methodFlow(trace: StackTraceElement?): String? {
    var methodFlow = trace?.className?.split(".")?.last()?.replace("$1", "")?.split('$')?.joinToString(" -> ")
    if(methodFlow != null) methodFlow += " -> "; methodFlow += trace?.methodName
    return methodFlow
}

private fun logcatLink(trace: StackTraceElement?) = ("${trace?.fileName}:${trace?.lineNumber}").let { if(LogcatDebug().link) "(${it})" else it }

private fun headerLog(): String {
    val logcatConfig = LogcatDebug()

    val trace = lastTraceElement()

    val headerInfo = when(logcatConfig.level){
        Log.INFO -> "${logcatLink(trace)} :: ${trace?.methodName}"
        Log.DEBUG -> "${classPath(trace)}/${logcatLink(trace)} :: $${methodFlow(trace)}"
        else -> "USE YOUR FORMAT"
    }

    return "$headerInfo => "
}

fun Any._log(TAG:String = "", priority: Int = Log.DEBUG) {
    //The logging tag can be at most 23 characters
    val tagLog = tagLog(TAG).let { if (it.length > 23) { it.substring(0,22) } else it }
    val header = if (BuildConfig.DEBUG) headerLog() else ""
    val logLines = formatLog(this)

    if(priority in arrayOf(Log.INFO, Log.ERROR) || BuildConfig.DEBUG) {
        if (logLines.size == 1) {
            Log.println(priority, tagLog, header + logLines[0])
        } else {
            Log.println(priority, tagLog, header + "\n" + logLines[0])
            logLines.forEachIndexed { index, logLine ->
                if (index > 0) Log.println(priority, tagLog, logLine)
            }
        }
    }
}

fun String._logline(){
    val trace = lastTraceElement()
    println("${tagLog("")} ${logcatLink(trace)}:${trace?.methodName} ${this.repeat(100)}")
}

fun _printTraceLog(TAG:String = "", onlyApp:Boolean = true){
    if (BuildConfig.DEBUG) {
        val startWith = if(onlyApp) PACKAGE_PATH else ""

        Thread.currentThread().stackTrace
            .filter { it.className.startsWith(startWith) && !it.className.endsWith("LogcatKt") }
            .toList().asReversed()
            .forEachIndexed { index, it ->
                if (index == 0) println("${tagLog(TAG)} :: START TRACE HERE")
                println("${tagLog(TAG)} :: --> ${classPath(it)}/${it.fileName} :: ${methodFlow(it)} :: ${logcatLink(it)}")
            }
            .let { println("${tagLog(TAG)} :: END TRACE HERE") }
    }
}

private fun formatLog(any: Any): List<String>{
    val logString: String
    logString = when(any){
        is String, Int, Double -> any.toString()
        else -> {
            try { Gson().toJson(any) ?: "" }
            catch (e: Exception) { "LocalDebug error to convert Json: ${e.message}" }
        }
    }

    return splitLogcat(logString)
}

private fun splitLogcat(logString: String): List<String> {
    val logLines = mutableListOf<String>()
    val maxLogSize = 2000
    val stringLength = logString.length
    for (i in 0..stringLength / maxLogSize) {
        val start = i * maxLogSize
        var end = (i + 1) * maxLogSize
        end = if (end > logString.length) logString.length else end
        logLines += logString.substring(start, end)
    }

    return logLines
}