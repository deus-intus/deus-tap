# Deus Tap — NFC Digital Business Card & Contact Exchange

> **Privacy-First, Zero-Friction Contact Sharing via NFC Tap and Dynamic QR**  
> **Live PWA:** [https://deus-intus.github.io/deus-tap/](https://deus-intus.github.io/deus-tap/)

---

## 1. What This Is
Deus Tap is a lightweight, zero-dependency Progressive Web Application (PWA) designed for instant, consent-based digital contact exchange. It can be written directly to physical NFC chips or shared via a responsive QR code.

## 2. Who It Is For
- **Founders and Professionals:** Sharing contact details and scheduling meetings without paper cards or clunky apps.
- **Prospective Clients:** Saving verified contact details directly into phone address books with a single tap.

## 3. Current Status
**Production / Live Public Tool.** Published and actively served via GitHub Pages at `https://deus-intus.github.io/deus-tap/`.

## 4. What Works Now vs Planned
### What Works Now:
- Zero-dependency HTML5, modern vanilla JavaScript (`logic.mjs`), and Service Worker (`sw.js`).
- Complete offline capability via Service Worker caching.
- Dynamic vCard download and direct contact form submission to a private n8n webhook.
- Installable PWA with full web app manifest (`manifest.webmanifest`).
- Public static files contain zero API keys or private tokens.

### Planned:
- Apple Wallet (.pkpass) and Google Wallet pass generation.

## 5. How to View or Use It
- **View Live App:** Visit [https://deus-intus.github.io/deus-tap/](https://deus-intus.github.io/deus-tap/) on any mobile device.
- **Local Testing:**
  ```bash
  npx serve .
  # Open http://localhost:3000
  ```
- **Program an NFC Tag:** Write the live URL `https://deus-intus.github.io/deus-tap/` to any standard NTAG213/215/216 NFC card or sticker using NFC Tools.

## 6. What Is in the Repository
- `index.html` — Contact card UI and interactive contact form.
- `logic.mjs` — Client logic, vCard export, and webhook dispatch.
- `sw.js` — Service worker enabling offline availability.
- `manifest.webmanifest` — Progressive Web App metadata.
- `privacy.html` — Consent declaration and privacy terms.
- `android/` — Android PWA shortcut configurations.

## 7. Branches and Experiments
- `main` (Canonical): Live production branch serving GitHub Pages.

## 8. Connections and Requirements
- Zero external runtime libraries. Submits contact requests to a private Deus Intus n8n automation workflow.

## 9. License and Contribution Information
Open source under the [MIT License](./LICENSE). Copyright © Deus Intus Ltd.
