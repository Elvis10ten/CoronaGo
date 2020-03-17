import org.gradle.api.JavaVersion

object ModuleConfigs {

    // Supported Java Version
    val JAVA_VERSION = JavaVersion.VERSION_1_8

    // Supported Android SDKs
    const val COMPILE_SDK_VERSION = 29
    const val BUILD_TOOLS_VERSION = "29.0.3"
    const val TARGET_SDK_VERSION = 29
    const val MINIMUM_SDK_VERSION = 21
}
