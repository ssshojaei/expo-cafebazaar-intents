import type { OpenIntentOptions } from './CafeBazaarIntents.types';
export type { OpenIntentOptions } from './CafeBazaarIntents.types';
export interface CafeBazaarIntentsModule {
    /**
     * Returns whether Cafe Bazaar (Bazaar app) is installed on the device.
     * Android only; returns false on other platforms.
     */
    isBazaarInstalled(): boolean;
    /**
     * Opens the app page in Cafe Bazaar (view app details).
     * @param packageId - Android application ID (e.g. "com.example.app")
     * @param options - Optional behavior when Bazaar is not installed
     */
    openAppPage(packageId: string, options?: OpenIntentOptions): Promise<boolean>;
    /**
     * Opens the app page in Cafe Bazaar in "rate" mode (user can rate the app).
     * @param packageId - Android application ID (e.g. "com.example.app")
     * @param options - Optional behavior when Bazaar is not installed
     */
    openRatePage(packageId: string, options?: OpenIntentOptions): Promise<boolean>;
    /**
     * Opens the developer/author page in Cafe Bazaar.
     * @param developerId - Cafe Bazaar developer ID (aid)
     * @param options - Optional behavior when Bazaar is not installed
     */
    openDeveloperPage(developerId: string, options?: OpenIntentOptions): Promise<boolean>;
    /**
     * Opens the Bazaar login page (in-app login / sign-in screen).
     * @param options - Optional behavior when Bazaar is not installed
     */
    openLoginPage(options?: OpenIntentOptions): Promise<boolean>;
    /**
     * Opens the app page in Bazaar so the user can update the app (same as app details page).
     * @param packageId - Android application ID (e.g. "com.example.app")
     * @param options - Optional behavior when Bazaar is not installed
     */
    openAppUpdatePage(packageId: string, options?: OpenIntentOptions): Promise<boolean>;
}
declare const NativeModule: CafeBazaarIntentsModule | null;
export default NativeModule;
//# sourceMappingURL=index.d.ts.map