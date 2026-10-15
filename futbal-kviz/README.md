# Futbalový kvíz – európske kluby

Android aplikácia (Kotlin, Jetpack Compose). Kvíz o európskych futbalových kluboch v slovenčine.

## Ako hra funguje
- 42 otázok: štadióny, prezývky, derby, európske poháre, história.
- Jedno kolo = 10 náhodných otázok. Poradie otázok aj odpovedí sa mieša.
- 15 sekúnd na otázku. Body: 10 + zostávajúce sekundy.
- Po odpovedi sa zobrazí vysvetlenie.
- Najlepšie skóre sa ukladá v telefóne.

## Ako získať APK
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
