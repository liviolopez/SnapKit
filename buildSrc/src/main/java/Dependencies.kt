object Sdk {
    const val compile = 30
    const val target = 30
    const val min = 22

    const val buildTool = "30.0.2"
    const val ndk = "22.0.6917172 rc1"
}

object Versions {
    const val kotlin = "1.4.20"
    const val gradle = "4.1.1"
}

object Dep {
    fun get(group:String, type:String, name:String):String = (libs[group] ?: error(""))[type]!![name] ?: error("")

    fun allLibs(excludeGroup: List<String> = emptyList()): MutableList<Pair<String, String>> {
        val allLibs = mutableListOf<Pair<String, String>>()

        libs.filter { !excludeGroup.contains(it.key) }.forEach { d ->
            d.value.filter { it.key != "gradle" }.forEach { (type, libs) ->
                libs.forEach { lib ->
                    allLibs.add(Pair(type, lib.value))
                    println("${if(type == "impl") "implementation" else "kapt"}(\"${lib.value}\")")
                }
            }
        }

        return allLibs
    }

    private const val gradle = "gradle"
    private const val impl = "impl"
    private const val kapt = "kapt"

    private object V {
        const val coroutines = "1.4.1"
        const val navigation = "2.3.2"
        const val lifecycle = "2.2.0"
        const val livedata = "2.2.0"
        const val retrofit = "2.9.0"
        const val glide = "4.11.0"
        const val snapchat = "1.6.7"
    }

    private val libs = mapOf(
        "Kotlin" to hashMapOf(
            impl to mapOf(
            "kotlin" to "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}", // =====
            "coroutinesAndroid" to "org.jetbrains.kotlinx:kotlinx-coroutines-android:${V.coroutines}")
        ),

        "Basic" to hashMapOf(
            impl to mapOf(
            "gson" to "com.google.code.gson:gson:2.8.6",
            "coreKtx" to "androidx.core:core-ktx:1.3.2", // =====
            "appcompat" to "androidx.appcompat:appcompat:1.2.0", // =====
            "constraintlayout" to "androidx.constraintlayout:constraintlayout:2.0.4",  // =====
            "legacy" to "androidx.legacy:legacy-support-v4:1.0.0",
            "rxkotlin" to "io.reactivex.rxjava3:rxkotlin:3.0.1",
            "material" to "com.google.android.material:material:1.2.1") // =====
        ),

        "Navigation" to mapOf(
            gradle to mapOf(
            "plugin" to "androidx.navigation:navigation-safe-args-gradle-plugin:${V.navigation}"), // =====

            impl to mapOf(
            "navigation" to "androidx.navigation:navigation-fragment-ktx:${V.navigation}", // =====
            "navigationUi" to "androidx.navigation:navigation-ui-ktx:${V.navigation}") // =====
        ),

        "Lifecycle" to mapOf(
            impl to mapOf(
            "lifecycleExt" to "androidx.lifecycle:lifecycle-extensions:${V.lifecycle}",
            "viewmodel" to "androidx.lifecycle:lifecycle-viewmodel-ktx:${V.lifecycle}", // =====
            "livedata" to "androidx.lifecycle:lifecycle-livedata-ktx:${V.livedata}", // =====
            "runtime" to "androidx.lifecycle:lifecycle-runtime-ktx:${V.livedata}")
        ),

        "Retrofit" to mapOf(
            impl to mapOf(
            "retrofit" to "com.squareup.retrofit2:retrofit:${V.retrofit}",
            "converter" to "com.squareup.retrofit2:converter-gson:${V.retrofit}",
            "interceptor" to "com.squareup.okhttp3:logging-interceptor:4.9.0")
        ),

        "Glide" to mapOf(
            impl to mapOf(
            "glide" to "com.github.bumptech.glide:glide:${V.glide}",
            "okhttp3" to "com.github.bumptech.glide:okhttp3-integration:${V.glide}"),

            kapt to mapOf(
            "compiler" to "com.github.bumptech.glide:compiler:${V.glide}")
        ),

        "SnapChat" to mapOf(
            impl to mapOf(
            "core" to "com.snapchat.kit.sdk:core:${V.snapchat}",
            "login" to "com.snapchat.kit.sdk:login:${V.snapchat}",
            "bitmoji" to "com.snapchat.kit.sdk:bitmoji:${V.snapchat}",
            "creative" to "com.snapchat.kit.sdk:creative:${V.snapchat}")
        )
    )

    object Test {
        const val junit = "junit:junit:4.13.1"
        const val extJunit = "androidx.test.ext:junit:1.1.2"
        const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"
    }
}