#!/usr/bin/env python3
"""Deus Tap — 1-Click Local Preview Server
Zero dependencies. Uses Python standard library to serve PWA and open browser.
"""
import http.server, socketserver, webbrowser, os

PORT = 3000
os.chdir(os.path.dirname(os.path.abspath(__file__)))

class Handler(http.server.SimpleHTTPRequestHandler):
    def end_headers(self):
        self.send_header('Cache-Control', 'no-store, must-revalidate')
        super().end_headers()

print(f"⚡ Serving Deus Tap locally at http://localhost:{PORT}")
print("Press Ctrl+C to stop.")
webbrowser.open(f"http://localhost:{PORT}")

with socketserver.TCPServer(("", PORT), Handler) as httpd:
    try:
        httpd.serve_forever()
    except KeyboardInterrupt:
        print("\nServer stopped.")
