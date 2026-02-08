# expo-cafebazaar-intents

Cafe Bazaar intents for **Expo** (Android): app page, rate/reviews page, developer page, Bazaar login page, and app update page.

Use this in Expo/React Native apps that target the [Cafe Bazaar](https://cafebazaar.ir) (کافه‌بازار) store. Based on the [Bazaar intent documentation](https://developers.cafebazaar.ir/fa/guidelines/feature/intent).

## Platform

- **Android**: supported (opens Bazaar app or optional web fallback)
- **iOS / Web**: no-op; `isBazaarInstalled()` returns `false`, open methods return `false`

## Installation

```bash
npx expo install expo-cafebazaar-intents
```

To use your app’s package name automatically in the examples below, also install `expo-application`:

```bash
npx expo install expo-application
```

Or with npm / pnpm / yarn:

```bash
npm install expo-cafebazaar-intents
npm install expo-application   # optional, for getting package name automatically
```

## Usage

Get your app’s package name (Android application ID) with `expo-application`, then pass it to the intent methods:

```ts
import * as Application from "expo-application";
import CafeBazaarIntents from "expo-cafebazaar-intents";

// Your app's package name (e.g. "com.mycompany.myapp") – use this for app/rate/update intents
const packageId = Application.applicationId ?? "com.your.package";

// Check if Bazaar is installed (e.g. to show "Rate on Bazaar" only when relevant)
const installed = CafeBazaarIntents?.isBazaarInstalled() ?? false;

// Open your app's page in Bazaar (view details)
await CafeBazaarIntents?.openAppPage(packageId);

// Open rate page so the user can rate your app
await CafeBazaarIntents?.openRatePage(packageId);

// If Bazaar is not installed, open the web page in browser
await CafeBazaarIntents?.openRatePage(packageId, {
  openInBrowserIfNotInstalled: true,
});

// Open developer/author page in Bazaar (get your developer ID from https://pishkhan.cafebazaar.ir/settings/developers-titles)
await CafeBazaarIntents?.openDeveloperPage("YOUR_DEVELOPER_ID", {
  openInBrowserIfNotInstalled: true,
});

// Open Bazaar login page
await CafeBazaarIntents?.openLoginPage({ openInBrowserIfNotInstalled: true });

// Open app page so user can update the app
await CafeBazaarIntents?.openAppUpdatePage(packageId, {
  openInBrowserIfNotInstalled: true,
});
```

On Android, `Application.applicationId` is your app’s package name (same as `android.package` in `app.json`). On iOS/Web it returns the bundle identifier or `null`, so the fallback `'com.your.package'` is only used when `applicationId` is not available.

### API

| Method                                     | Description                                                                                                                                                                                             |
| ------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `isBazaarInstalled()`                      | Returns whether the Cafe Bazaar app is installed.                                                                                                                                                       |
| `openAppPage(packageId, options?)`         | Opens the app’s detail page in Bazaar (`bazaar://details?id=...`).                                                                                                                                      |
| `openRatePage(packageId, options?)`        | Opens the app’s page in Bazaar in rate/edit mode.                                                                                                                                                       |
| `openDeveloperPage(developerId, options?)` | Opens the developer page in Bazaar (`bazaar://collection?slug=by_author&aid=...`). Get your developer ID from [Cafe Bazaar Developer Panel](https://pishkhan.cafebazaar.ir/settings/developers-titles). |
| `openLoginPage(options?)`                  | Opens the Bazaar login page (`bazaar://inapplogin`).                                                                                                                                                    |
| `openAppUpdatePage(packageId, options?)`   | Opens the app’s page in Bazaar so the user can update the app.                                                                                                                                          |

**Options** (optional):

- `openInBrowserIfNotInstalled?: boolean` – If `true` and Bazaar is not installed, opens the corresponding page on cafebazaar.ir in the browser. Default: `false`.

All `open*` methods return a `Promise<boolean>`: `true` if the Bazaar intent was started (or the fallback browser was opened when `openInBrowserIfNotInstalled` is set), `false` otherwise.

## Android 11+ (API 30+)

The module declares a `<queries>` entry for `com.farsitel.bazaar` so the app can detect whether Bazaar is installed. No extra config plugin is required.

## License

MIT
