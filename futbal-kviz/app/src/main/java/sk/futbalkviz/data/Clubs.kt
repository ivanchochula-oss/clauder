package sk.futbalkviz.data

/** Klub pre režim „Hádaj klub podľa loga“. Logo je v assets/logos/<key>.png (sťahuje scripts/download_logos.py). */
data class Club(
    val key: String,
    val name: String,
    val country: String,
    val fact: String,
)

object Clubs {
    val all: List<Club> = listOf(
        Club("barcelona", "FC Barcelona", "Španielsko", "Barcelona, Španielsko. Založený v roku 1899."),
        Club("real", "Real Madrid", "Španielsko", "Madrid, Španielsko. Založený v roku 1902."),
        Club("atletico", "Atlético Madrid", "Španielsko", "Madrid, Španielsko. Založený v roku 1903."),
        Club("sevilla", "Sevilla FC", "Španielsko", "Sevilla, Španielsko. Založený v roku 1890."),
        Club("valencia", "Valencia CF", "Španielsko", "Valencia, Španielsko. Založený v roku 1919."),
        Club("bayern", "Bayern Mníchov", "Nemecko", "Mníchov, Nemecko. Založený v roku 1900."),
        Club("dortmund", "Borussia Dortmund", "Nemecko", "Dortmund, Nemecko. Založený v roku 1909."),
        Club("leverkusen", "Bayer Leverkusen", "Nemecko", "Leverkusen, Nemecko. Založený v roku 1904."),
        Club("schalke", "Schalke 04", "Nemecko", "Gelsenkirchen, Nemecko. Založený v roku 1904."),
        Club("juventus", "Juventus Turín", "Taliansko", "Turín, Taliansko. Založený v roku 1897."),
        Club("milan", "AC Miláno", "Taliansko", "Miláno, Taliansko. Založený v roku 1899."),
        Club("inter", "Inter Miláno", "Taliansko", "Miláno, Taliansko. Založený v roku 1908."),
        Club("roma", "AS Rím", "Taliansko", "Rím, Taliansko. Založený v roku 1927."),
        Club("napoli", "SSC Neapol", "Taliansko", "Neapol, Taliansko. Založený v roku 1926."),
        Club("liverpool", "Liverpool FC", "Anglicko", "Liverpool, Anglicko. Založený v roku 1892."),
        Club("manutd", "Manchester United", "Anglicko", "Manchester, Anglicko. Založený v roku 1878 ako Newton Heath."),
        Club("mancity", "Manchester City", "Anglicko", "Manchester, Anglicko. Založený v roku 1880."),
        Club("arsenal", "Arsenal FC", "Anglicko", "Londýn, Anglicko. Založený v roku 1886."),
        Club("chelsea", "Chelsea FC", "Anglicko", "Londýn, Anglicko. Založený v roku 1905."),
        Club("tottenham", "Tottenham Hotspur", "Anglicko", "Londýn, Anglicko. Založený v roku 1882."),
        Club("psg", "Paris Saint-Germain", "Francúzsko", "Paríž, Francúzsko. Založený v roku 1970."),
        Club("marseille", "Olympique Marseille", "Francúzsko", "Marseille, Francúzsko. Založený v roku 1899."),
        Club("ajax", "Ajax Amsterdam", "Holandsko", "Amsterdam, Holandsko. Založený v roku 1900."),
        Club("psv", "PSV Eindhoven", "Holandsko", "Eindhoven, Holandsko. Založený v roku 1913."),
        Club("benfica", "Benfica Lisabon", "Portugalsko", "Lisabon, Portugalsko. Založený v roku 1904."),
        Club("porto", "FC Porto", "Portugalsko", "Porto, Portugalsko. Založený v roku 1893."),
        Club("celtic", "Celtic Glasgow", "Škótsko", "Glasgow, Škótsko. Založený v roku 1887."),
        Club("galatasaray", "Galatasaray", "Turecko", "Istanbul, Turecko. Založený v roku 1905."),
        Club("slovan", "Slovan Bratislava", "Slovensko", "Bratislava, Slovensko. Založený v roku 1919."),
        Club("sparta", "Sparta Praha", "Česko", "Praha, Česko. Založený v roku 1893."),
        Club("slavia", "Slavia Praha", "Česko", "Praha, Česko. Založený v roku 1892."),
    )
}
