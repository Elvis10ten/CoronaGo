object Dependencies {

    const val ANDROID_BUILD_TOOLS = "com.android.tools.build:gradle:3.5.3"

    // Kotlin
    private const val KOTLIN_VERSION = "1.3.70"
    const val KOTLIN_STANDARD_LIBRARY = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$KOTLIN_VERSION"
    const val KOTLIN_ANDROID_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION"

    // General AndroidX
    const val ANDROIDX_APPCOMPAT = "androidx.appcompat:appcompat:1.1.0"
    const val ANDROIDX_KTX = "androidx.core:core-ktx:1.1.0"
    const val ANDROIDX_CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val ANDROIDX_ANNOTATIONS = "androidx.annotation:annotation:1.1.0"
    const val ANDROIDX_CORE = "androidx.core:core-ktx:1.2.0"

    // Google tools
    const val ANDROID_MATERIAL_COMPONENTS = "com.google.android.material:material:1.2.0-alpha03"
    const val GSON = "com.google.code.gson:gson:2.8.6"

    // Dev
    const val JAKE_TIMBER = "com.jakewharton.timber:timber:4.7.1"

    // Java
    const val JAVAX_ANNOTATION = "javax.annotation:javax.annotation-api:1.2"

    // Tests
    const val JUNIT = "junit:junit:4.12"
    const val ANDROIDX_JUNITX = "androidx.test.ext:junit:1.1.0"
    const val ANDROIDX_TEST_RUNNER = "androidx.test:runner:1.2.0"
    const val ANDROIDX_EXPRESSO_CORE = "androidx.test.espresso:espresso-core:3.1.1"
    const val ANDROIDX_ORCHESTRATOR = "androidx.test:orchestrator:1.2.0"
    const val ANDROIDX_UI_AUTOMATOR = "androidx.test.uiautomator:uiautomator:2.2.0"
    const val ANDROIDX_TEST_CORE_KTX = "androidx.test:core-ktx:1.2.0"
    const val ANDROIDX_JUNIT_EXT_KTX = "androidx.test.ext:junit-ktx:1.1.2-alpha04"
}
