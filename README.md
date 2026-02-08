# expo-cafebazaar-intents

Cafe Bazaar intents for **Expo** (Android): open app page, rate app, and open developer page in the Bazaar app.

Use this in Expo/React Native apps that target the [Cafe Bazaar](https://cafebazaar.ir) (کافه‌بازار) store. Based on the [Bazaar intent documentation](https://developers.cafebazaar.ir/fa/guidelines/feature/intent).

## Platform

- **Android**: supported (opens Bazaar app or optional web fallback)
- **iOS / Web**: no-op; `isBazaarInstalled()` returns `false`, open methods return `false`

## Installation

```bash
npx expo install expo-cafebazaar-intents
```

Or with npm / pnpm / yarn:

```bash
npm install expo-cafebazaar-intents
```

## Usage

```ts
import CafeBazaarIntents from 'expo-cafebazaar-intents';

// Check if Bazaar is installed (e.g. to show "Rate on Bazaar" only when relevant)
const installed = CafeBazaarIntents?.isBazaarInstalled() ?? false;

// Open your app's page in Bazaar (view details)
await CafeBazaarIntents?.openAppPage('com.your.package');

// Open rate page so the user can rate your app
await CafeBazaarIntents?.openRatePage('com.your.package');

// If Bazaar is not installed, open the web page in browser
await CafeBazaarIntents?.openRatePage('com.your.package', {
  openInBrowserIfNotInstalled: true,
});

// Open developer/author page in Bazaar
await CafeBazaarIntents?.openDeveloperPage('YOUR_DEVELOPER_ID', {
  openInBrowserIfNotInstalled: true,
});
```

### API

| Method | Description |
|--------|-------------|
| `isBazaarInstalled()` | Returns whether the Cafe Bazaar app is installed. |
| `openAppPage(packageId, options?)` | Opens the app’s detail page in Bazaar (`bazaar://details?id=...`). |
| `openRatePage(packageId, options?)` | Opens the app’s page in Bazaar in rate/edit mode. |
| `openDeveloperPage(developerId, options?)` | Opens the developer page in Bazaar (`bazaar://collection?slug=by_author&aid=...`). |

**Options** (optional):

- `openInBrowserIfNotInstalled?: boolean` – If `true` and Bazaar is not installed, opens the corresponding page on cafebazaar.ir in the browser. Default: `false`.

All `open*` methods return a `Promise<boolean>`: `true` if the Bazaar intent was started (or the fallback browser was opened when `openInBrowserIfNotInstalled` is set), `false` otherwise.

## Android 11+ (API 30+)

The module declares a `<queries>` entry for `com.farsitel.bazaar` so the app can detect whether Bazaar is installed. No extra config plugin is required.

## Publishing

This package is suitable for npm. To publish:

1. Set `repository`, `bugs`, `homepage` and `author` in `package.json` if needed.
2. Run `npm publish` (or use a scope like `@yourname/expo-cafebazaar-intents`).

## License

MIT
