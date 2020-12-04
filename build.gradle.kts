import java.net.URI

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.gradle}")
        classpath(kotlin("gradle-plugin", version = Versions.kotlin))
        classpath(Dep.get("Navigation","gradle","plugin"))
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url = URI("https://storage.googleapis.com/snap-kit-build/maven") }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}