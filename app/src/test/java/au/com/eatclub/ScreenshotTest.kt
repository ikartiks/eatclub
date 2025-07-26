package au.com.eatclub

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runComposeUiTest
import com.dropbox.differ.SimpleImageComparator
import com.github.takahirom.roborazzi.DefaultFileNameGenerator.jupiterTestAnnotationOrNull
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.InternalRoborazziApi
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.captureRoboImage
import com.github.takahirom.roborazzi.provideRoborazziContext
import com.github.takahirom.roborazzi.roborazziDefaultNamingStrategy
import java.io.File
import org.junit.Test
import org.robolectric.RuntimeEnvironment

private const val DEFAULT_DEVICE = "default_device"
private const val DEFAULT_DENSITY = "xhdpi"
private const val DEFAULT_FONT_SIZE = 1f
private const val SCREENSHOT_EXTENSION = "png"

@OptIn(ExperimentalTestApi::class)
fun runScreenshotTest(heightDp: Int, block: @Composable () -> Unit) {
  val densities = listOf(
    DEFAULT_DENSITY
  )

  val fonts = listOf(0.85f, DEFAULT_FONT_SIZE, 2f)

  val devices = mapOf(
    DEFAULT_DEVICE to "w411dp-h%heightdp-normal-long-notround-any-%density-keyshidden-nonav"
  )

  densities.forEach { density ->
    val fontSize = 1f
    RuntimeEnvironment.setFontScale(fontSize)
    val finalQualifier =
      devices[DEFAULT_DEVICE]!!.replace("%height", heightDp.toString())
        .replace("%density", density)
    RuntimeEnvironment.setQualifiers(finalQualifier)
    runComposeUiTest {
      setContent {
        block()
      }
      onRoot().captureScreenshot(DEFAULT_DEVICE, density)
    }
  }

  fonts.forEach { fontSize ->
    RuntimeEnvironment.setFontScale(fontSize)
    val finalQualifier =
      devices[DEFAULT_DEVICE]!!.replace("%height", heightDp.toString())
        .replace("%density", DEFAULT_DENSITY)
    RuntimeEnvironment.setQualifiers(finalQualifier)
    runComposeUiTest {
      setContent {
        block()
      }
      onRoot().captureScreenshot(DEFAULT_DEVICE)
    }
  }

  devices.keys.forEach { deviceName ->
    val fontSize = 1f
    RuntimeEnvironment.setFontScale(fontSize)
    val finalQualifier =
      devices[deviceName]!!.replace("%height", heightDp.toString())
        .replace("%density", DEFAULT_DENSITY)
    RuntimeEnvironment.setQualifiers(finalQualifier)
    runComposeUiTest {
      setContent {
        block()
      }
      onRoot().captureScreenshot(deviceName)
    }
  }
}

private fun SemanticsNodeInteraction.captureScreenshot(
  deviceName: String,
  density: String = DEFAULT_DENSITY
) =
  this.captureRoboImage(
    filePath = generateFilePath(deviceName, density),
    roborazziOptions = RoborazziOptions(
      compareOptions = RoborazziOptions.CompareOptions(
        imageComparator = SimpleImageComparator(
          maxDistance = 0.05F
        ),
        changeThreshold = 0.05F
      )
    )
  )

@OptIn(InternalRoborazziApi::class, ExperimentalRoborazziApi::class)
private fun generateFilePath(deviceName: String, density: String): String {
  val roborazziContext = provideRoborazziContext()
  val fileCreator = roborazziContext.fileProvider
  val description = roborazziContext.description
  if (fileCreator != null && description != null) {
    return fileCreator(
      description,
      File(roborazziContext.outputDirectory),
      SCREENSHOT_EXTENSION
    ).absolutePath
  }

  //Store the snapshots outside of build folder so it can be used in CI
  return "snapshots/$deviceName/$density/${generateOutputNameWithStackTrace()}_font${
    RuntimeEnvironment.getFontScale().toString().replace(".", "_")
  }.$SCREENSHOT_EXTENSION"
}

@OptIn(ExperimentalRoborazziApi::class)
private fun generateOutputNameWithStackTrace(): String {
  // Find test method name
  val allStackTraces = Thread.getAllStackTraces()
  val filteredTracces = allStackTraces
    .filterKeys {
      it.name.contains("Main Thread")
          || it.name.contains("Test worker")
    }
  val traceElements = filteredTracces
    .flatMap { it.value.toList() }
  val stackTraceElement = traceElements
    .firstOrNull {
      try {
        val method = Class.forName(it.className).getMethod(it.methodName)
        method
          .getAnnotation(Test::class.java) != null ||
            (jupiterTestAnnotationOrNull != null && (method
              .getAnnotation(jupiterTestAnnotationOrNull)) != null)
      } catch (e: NoClassDefFoundError) {
        false
      } catch (e: Exception) {
        false
      }
    }
    ?: throw IllegalArgumentException("Roborazzi can't find method of test. Please specify file name or use Rule")
  val testName =
    defaultNamingStrategy.generateOutputName(
      stackTraceElement.className.substringAfterLast("."),
      stackTraceElement.methodName
    )
  return testName
}

private val defaultNamingStrategy by lazy {
  roborazziDefaultNamingStrategy()
}
