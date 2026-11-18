import java.util.Properties
plugins { id("com.android.application"); id("org.jetbrains.kotlin.plugin.compose") }
val localProps = Properties().apply {
 val f = rootProject.file("local.properties")
 if (f.exists()) f.inputStream().use { load(it) }
}
android {
 namespace = "com.cerron.campusgps"
 compileSdk = 37
 defaultConfig {
  applicationId = "com.cerron.campusgps"
  minSdk = 26
  targetSdk = 36
  versionCode = 1
  versionName = "1.0"
  manifestPlaceholders["MAPS_API_KEY"] = localProps.getProperty("MAPS_API_KEY", "")
  buildConfigField("boolean", "MAPS_CONFIGURADO", (!localProps.getProperty("MAPS_API_KEY", "").isBlank()).toString())
 }
 buildFeatures { compose = true; buildConfig = true }
 compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }

}
dependencies {
 implementation(platform("androidx.compose:compose-bom:2025.01.00"))
 implementation("androidx.activity:activity-compose:1.9.3")
 implementation("androidx.compose.material3:material3")
 implementation("androidx.compose.ui:ui-tooling-preview")
 debugImplementation("androidx.compose.ui:ui-tooling")
 implementation("com.google.android.gms:play-services-location:21.3.0")
 implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.8.1")
 implementation("com.google.maps.android:maps-compose:8.4.0")
 implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
 testImplementation("junit:junit:4.13.2")
 testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
}


kotlin { compilerOptions { jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17) } }
