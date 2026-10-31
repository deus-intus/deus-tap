# Deus Tap · Android NFC HCE experiment

This is an **experimental Android-only companion** to the existing https://deus-intus.github.io/deus-tap/ contact exchange. It does not change the public website or its n8n workflow.

It emulates an NFC Forum Type 4 NDEF URI tag via Android Host Card Emulation. A receiving phone **may** detect the HTTPS URL automatically when placed against an unlocked sender with the Deus Tap NFC app open. Compatibility with another Android phone or iPhone's background reader is **not guaranteed** until tested with actual hardware; Google Wallet/Google Pay is not used.

This implementation adapts the Apache-2.0 licensed Type 4 APDU protocol and AID routing pattern from MichaelsPlayground/NfcHceNdefEmulator, including the earlier TechBooster sample noted in its README; see UPSTREAM-LICENSE.txt.

To build on a machine with Java 17 and the Android SDK installed: `./gradlew testDebugUnitTest assembleDebug`. The APK is `app/build/outputs/apk/debug/app-debug.apk`.

**Safety:** no payments, no personal contact data, no accounts, no tracking permissions. Emulates only the NFC Forum NDEF application AID. It may conflict with another third-party app emulating the same AID. It does not replace Google Wallet or modify payments.

**Device test checklist:** install on an Android HCE phone, enable NFC, open Deus Tap NFC and keep its screen awake; on a second Android phone or iPhone with NFC enabled, bring NFC antenna areas close together; verify whether a browser link is offered, then verify contact exchange using the existing web form. Test both Android and iPhone receivers and note failure modes. It may require an NFC reader app on the receiving device.
