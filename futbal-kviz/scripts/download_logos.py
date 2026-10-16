#!/usr/bin/env python3
"""Stiahne logá klubov z Wikipédie do app/src/main/assets/logos/<kľúč>.png.

Kľúče musia zodpovedať zoznamu v app/src/main/java/sk/futbalkviz/data/Clubs.kt.
Klub, ktorého logo sa nepodarí stiahnuť, aplikácia v režime lôg vynechá.
"""
import json
import pathlib
import sys
import time
import urllib.parse
import urllib.request

CLUBS = {
    "barcelona": "FC Barcelona",
    "real": "Real Madrid CF",
    "atletico": "Atlético Madrid",
    "sevilla": "Sevilla FC",
    "valencia": "Valencia CF",
    "bayern": "FC Bayern Munich",
    "dortmund": "Borussia Dortmund",
    "leverkusen": "Bayer 04 Leverkusen",
    "schalke": "FC Schalke 04",
    "juventus": "Juventus FC",
    "milan": "AC Milan",
    "inter": "Inter Milan",
    "roma": "AS Roma",
    "napoli": "SSC Napoli",
    "liverpool": "Liverpool F.C.",
    "manutd": "Manchester United F.C.",
    "mancity": "Manchester City F.C.",
    "arsenal": "Arsenal F.C.",
    "chelsea": "Chelsea F.C.",
    "tottenham": "Tottenham Hotspur F.C.",
    "psg": "Paris Saint-Germain F.C.",
    "marseille": "Olympique de Marseille",
    "ajax": "AFC Ajax",
    "psv": "PSV Eindhoven",
    "benfica": "S.L. Benfica",
    "porto": "FC Porto",
    "celtic": "Celtic F.C.",
    "galatasaray": "Galatasaray S.K. (football)",
    "slovan": "ŠK Slovan Bratislava",
    "sparta": "AC Sparta Prague",
    "slavia": "SK Slavia Prague",
}

UA = "FutbalKviz/1.0 (https://github.com/ivanchochula-oss/clauder; build script)"
OUT = pathlib.Path(__file__).resolve().parent.parent / "app/src/main/assets/logos"
MIN_LOGOS = 8


def get(url: str) -> bytes:
    req = urllib.request.Request(url, headers={"User-Agent": UA})
    with urllib.request.urlopen(req, timeout=30) as r:
        return r.read()


def thumbnail_url(title: str) -> str | None:
    params = urllib.parse.urlencode({
        "action": "query",
        "format": "json",
        "prop": "pageimages",
        "piprop": "thumbnail",
        "pithumbsize": "320",
        "pilicense": "any",
        "redirects": "1",
        "titles": title,
    })
    data = json.loads(get("https://en.wikipedia.org/w/api.php?" + params))
    for page in data.get("query", {}).get("pages", {}).values():
        thumb = page.get("thumbnail", {}).get("source")
        if thumb:
            return thumb
    return None


def main() -> int:
    OUT.mkdir(parents=True, exist_ok=True)
    ok, failed = [], []
    for key, title in CLUBS.items():
        try:
            url = thumbnail_url(title)
            if not url:
                raise RuntimeError("článok nemá obrázok")
            (OUT / f"{key}.png").write_bytes(get(url))
            ok.append(key)
        except Exception as e:  # noqa: BLE001 – chýbajúce logo nesmie zhodiť build
            failed.append(f"{key} ({e})")
        time.sleep(0.3)
    print(f"Stiahnuté logá: {len(ok)}/{len(CLUBS)}")
    for f in failed:
        print(f"  chýba: {f}")
    if len(ok) < MIN_LOGOS:
        print(f"Príliš málo lôg (minimum {MIN_LOGOS}).", file=sys.stderr)
        return 1
    return 0


if __name__ == "__main__":
    sys.exit(main())
