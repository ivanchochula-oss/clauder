# Futbalový kvíz – európske kluby

Android aplikácia (Kotlin, Jetpack Compose). Kvíz o európskych futbalových kluboch v slovenčine.

## Ako hra funguje
- 42 otázok: štadióny, prezývky, derby, európske poháre, história.
- Jedno kolo = 10 náhodných otázok. Poradie otázok aj odpovedí sa mieša.
- 15 sekúnd na otázku. Body: 10 + zostávajúce sekundy.
- Po odpovedi sa zobrazí vysvetlenie.
- Najlepšie skóre sa ukladá v telefóne (zvlášť pre každý režim).
- Režim **Hádaj klub podľa loga**: 31 klubov, logo + 4 možnosti.

## Logá klubov
Logá sa do repozitára neukladajú. Pri builde ich stiahne z Wikipédie
`scripts/download_logos.py` do `app/src/main/assets/logos/`.
Pri lokálnom builde ho spusti ručne: `python3 scripts/download_logos.py`.
Logá sú ochranné známky klubov. Aplikácia je určená na súkromné a vzdelávacie použitie.

## Ako získať APK
Priamy odkaz na najnovšiu verziu (otvor v mobile):
https://github.com/ivanchochula-oss/clauder/releases/latest/download/FutbalovyKviz.apk

Alebo cez Actions:
1. GitHub → záložka **Actions** → workflow **Futbalový kvíz – Android build**.
2. Otvor posledný úspešný beh → sekcia **Artifacts** → stiahni `futbal-kviz-apk`.
3. Rozbaľ ZIP, `app-debug.apk` pošli do telefónu a nainštaluj
   (povoľ inštaláciu z neznámych zdrojov).

## Vývoj
Otvor priečinok `futbal-kviz` v Android Studio. Otázky sú v
`app/src/main/java/sk/futbalkviz/data/Questions.kt`.

```
./gradlew testDebugUnitTest   # testy
./gradlew assembleDebug       # APK
```
