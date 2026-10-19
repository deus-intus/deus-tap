# AI Agent Integration Guide — Deus Tap NFC/QR PWA

This guide explains how AI agents and automation daemons programmatically generate, encode, and read Deus Tap digital contact cards.

---

## 1. Webhook Payload Specification
Deus Tap submits contact exchanges via HTTP POST to an n8n webhook or agent ingress endpoint:

```json
{
  "timestamp": "2026-10-19T12:00:00Z",
  "full_name": "Jane Doe",
  "email": "jane.doe@example.com",
  "phone": "+44 7700 900077",
  "organization": "Acme Corp",
  "source": "nfc_tap",
  "consent": true
}
```

---

## 2. 1-Click NFC Programming Format
To program any standard NTAG213/215/216 NFC tag using NFC Tools or Android/iOS NFC writers:
* **Record Type:** URI / Link
* **Target URI:** `https://deus-intus.github.io/deus-tap/`
* **Lock State:** Read-only (recommended for public physical cards)
