package expo.modules.cafebazaarintents

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import expo.modules.kotlin.Promise
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition

/** Official Cafe Bazaar app package name (Bazaar / کافه‌بازار). */
private const val BAZAAR_PACKAGE = "com.farsitel.bazaar"

private const val SCHEME_BAZAAR = "bazaar"
private const val URI_DETAILS = "$SCHEME_BAZAAR://details"
private const val URI_COLLECTION = "$SCHEME_BAZAAR://collection"
private const val URI_INAPP_LOGIN = "$SCHEME_BAZAAR://inapplogin"
private const val WEB_APP_BASE = "https://cafebazaar.ir/app/"
private const val WEB_DEVELOPER_BASE = "https://cafebazaar.ir/developer/"
private const val WEB_SIGNIN = "https://cafebazaar.ir/signin"

/**
 * Cafe Bazaar intents for Expo (Android).
 * Opens app page, rate page, or developer page in the Cafe Bazaar app.
 * Based on [Bazaar intent documentation](https://developers.cafebazaar.ir/fa/guidelines/feature/intent).
 */
class CafeBazaarIntentsModule : Module() {
  override fun definition() = ModuleDefinition {
    Name("CafeBazaarIntents")

    Function("isBazaarInstalled") {
      val ctx = appContext.reactContext ?: return@Function false
      return@Function try {
        @Suppress("DEPRECATION")
        ctx.packageManager.getPackageInfo(BAZAAR_PACKAGE, 0)
        true
      } catch (_: PackageManager.NameNotFoundException) {
        false
      }
    }

    AsyncFunction("openAppPage") { packageId: String, options: Map<String, Any>?, promise: Promise ->
      promise.resolve(
        openIntent(
          action = Intent.ACTION_VIEW,
          uri = Uri.parse("$URI_DETAILS?id=$packageId"),
          webFallback = "$WEB_APP_BASE$packageId",
          openInBrowserIfNotInstalled = (options?.get("openInBrowserIfNotInstalled") as? Boolean) == true
        )
      )
    }

    AsyncFunction("openRatePage") { packageId: String, options: Map<String, Any>?, promise: Promise ->
      promise.resolve(
        openIntent(
          action = Intent.ACTION_EDIT,
          uri = Uri.parse("$URI_DETAILS?id=$packageId"),
          webFallback = "$WEB_APP_BASE$packageId",
          openInBrowserIfNotInstalled = (options?.get("openInBrowserIfNotInstalled") as? Boolean) == true
        )
      )
    }

    AsyncFunction("openDeveloperPage") { developerId: String, options: Map<String, Any>?, promise: Promise ->
      promise.resolve(
        openIntent(
          action = Intent.ACTION_VIEW,
          uri = Uri.parse("$URI_COLLECTION?slug=by_author&aid=$developerId"),
          webFallback = "$WEB_DEVELOPER_BASE$developerId",
          openInBrowserIfNotInstalled = (options?.get("openInBrowserIfNotInstalled") as? Boolean) == true
        )
      )
    }

    AsyncFunction("openLoginPage") { options: Map<String, Any>?, promise: Promise ->
      promise.resolve(
        openIntent(
          action = Intent.ACTION_VIEW,
          uri = Uri.parse(URI_INAPP_LOGIN),
          webFallback = WEB_SIGNIN,
          openInBrowserIfNotInstalled = (options?.get("openInBrowserIfNotInstalled") as? Boolean) == true
        )
      )
    }

    AsyncFunction("openAppUpdatePage") { packageId: String, options: Map<String, Any>?, promise: Promise ->
      promise.resolve(
        openIntent(
          action = Intent.ACTION_VIEW,
          uri = Uri.parse("$URI_DETAILS?id=$packageId"),
          webFallback = "$WEB_APP_BASE$packageId",
          openInBrowserIfNotInstalled = (options?.get("openInBrowserIfNotInstalled") as? Boolean) == true
        )
      )
    }
  }

  private fun openIntent(
    action: String,
    uri: Uri,
    webFallback: String,
    openInBrowserIfNotInstalled: Boolean
  ): Boolean {
    val ctx = appContext.reactContext ?: return false
    val bazaarInstalled = try {
      @Suppress("DEPRECATION")
      ctx.packageManager.getPackageInfo(BAZAAR_PACKAGE, 0)
      true
    } catch (_: PackageManager.NameNotFoundException) {
      false
    }
    return if (bazaarInstalled) {
      try {
        val intent = Intent(action).apply {
          data = uri
          setPackage(BAZAAR_PACKAGE)
          addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        ctx.startActivity(intent)
        true
      } catch (_: Exception) {
        if (openInBrowserIfNotInstalled) {
          openInBrowser(ctx, webFallback)
        }
        false
      }
    } else {
      if (openInBrowserIfNotInstalled) {
        openInBrowser(ctx, webFallback)
      }
      false
    }
  }

  private fun openInBrowser(context: android.content.Context, url: String) {
    try {
      val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      }
      context.startActivity(intent)
    } catch (_: Exception) {
      // No browser or security restriction
    }
  }
}
