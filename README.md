<div align="center">

```
  ____  _____ _   _ ____    ___ _   _ _____ _   _ ____  
 |  _ \| ____| | | / ___|  |_ _| \ | |_   _| | | / ___| 
 | | | |  _| | | | \___ \   | ||  \| | | | | | | \___ \ 
 | |_| | |___| |_| |___) |  | || |\  | | | | |_| |___) |
 |____/|_____|\___/|____/  |___|_| \_| |_|  \___/|____/ 
```

### **TECHNOLOGY · PEOPLE · A HIGHER TOMORROW**
**DEUS INTUS // DEVELOPER ARM** — *Brand System v1.0*  
*Digital systems, products, and infrastructure.*

---
</div>

> **Elevator Line:** *"We build intelligent systems, products, and infrastructure that help ideas become operational reality."*

# Deus Tap — NFC Digital Business Card & Contact Exchange

> **Privacy-First, Zero-Friction Contact Sharing via Physical NFC Tap and Dynamic QR**  
> **Live Web App:** [https://deus-intus.github.io/deus-tap/](https://deus-intus.github.io/deus-tap/)

[![PWA](https://img.shields.io/badge/PWA-Installable-0A57D6.svg)](https://deus-intus.github.io/deus-tap/)
[![Offline First](https://img.shields.io/badge/Service%20Worker-Offline%20First-33CFFF.svg)](#)
[![Zero Dependencies](https://img.shields.io/badge/dependencies-0%20(vanilla%20JS)-33CFFF.svg)](#)
[![License: MIT](https://img.shields.io/badge/License-MIT-031B4E.svg)](./LICENSE)

---

## 1. What This Is
Deus Tap is an offline-first Progressive Web Application (PWA) designed for instant, consent-based digital contact exchange. It can be written directly to physical NFC chips or scanned via dynamic QR code.

## 2. Who It Is For
- **Founders and Professionals:** Sharing contact details and meeting links without paper business cards.
- **Enterprise Clients:** Saving verified contact details directly into mobile address books with zero app store downloads.

## 3. Current Status
**Production / Live Public Tool.** Actively served on GitHub Pages at [https://deus-intus.github.io/deus-tap/](https://deus-intus.github.io/deus-tap/).

## 4. What Works Now vs Planned
### What Works Now:
- Pure HTML5, modern vanilla JavaScript (`logic.mjs`), and Service Worker (`sw.js`).
- 100% offline functionality via Service Worker caching.
- 1-Click local preview server via `python3 start.py`.
- Universal agent webhook specification in [`AGENT_SETUP.md`](./AGENT_SETUP.md).
- Zero API keys or private tokens in public assets.

### Planned:
- Direct Apple Wallet (.pkpass) generation.

## 5. How to View or Use It
- **Live URL:** [https://deus-intus.github.io/deus-tap/](https://deus-intus.github.io/deus-tap/)
- **1-Click Local Preview Server:**
  ```bash
  python3 start.py
  # Automatically opens http://localhost:3000
  ```

## 6. What Is in the Repository
- `index.html` — Contact card UI.
- `logic.mjs` — Contact exchange logic and vCard export.
- `sw.js` — Service worker for offline caching.
- `start.py` — 1-click local preview server.
- `AGENT_SETUP.md` — Webhook payload schema and NFC programming guide.
- `manifest.webmanifest` — PWA configuration.

## 7. Connections and Requirements
- Zero external runtime libraries. Submits contact requests to a private Deus Intus n8n automation workflow.

## 8. License and Contribution Information
Open source under the [MIT License](./LICENSE). Copyright © Deus Intus Ltd.

---

## 🛡️ Intellectual Property & Statutory UK Trade Mark Notice

- **Registered UK Trade Mark:** `DEUS INTUS®` is a registered trade mark of **Deus Intus Ltd** in the United Kingdom under **UK Trade Mark No. UK00004416539**. All rights reserved.
- **Corporate Registration:** Deus Intus Ltd is a company registered in England and Wales (Company No. `17209569`), Registered Office: `66 Paul Street, London, EC2A 4NA, United Kingdom`.
- **Statutory IP Architecture:** Software primitives synthesized by Deus Intus combine 20-year statutory public-domain algorithmic cores with modern permissive runtimes into zero-dependency, model-agnostic, sovereign software modules.
- **Support & Inquiries:** Official support at [`support@deusintus.com`](mailto:support@deusintus.com).
