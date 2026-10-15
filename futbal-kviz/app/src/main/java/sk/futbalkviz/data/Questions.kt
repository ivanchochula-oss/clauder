package sk.futbalkviz.data

data class Question(
    val text: String,
    val answer: String,
    val wrong: List<String>,
    val explanation: String,
)

object Questions {
    val all: List<Question> = listOf(
        // Štadióny
        Question(
            "Na ktorom štadióne hrá domáce zápasy FC Barcelona?",
            "Camp Nou",
            listOf("Santiago Bernabéu", "Mestalla", "Metropolitano"),
            "Camp Nou je najväčší štadión v Európe. Klub na ňom hrá od roku 1957.",
        ),
        Question(
            "Ako sa volá štadión Realu Madrid?",
            "Santiago Bernabéu",
            listOf("Camp Nou", "Vicente Calderón", "Riazor"),
            "Štadión nesie meno Santiaga Bernabéua, dlhoročného prezidenta klubu.",
        ),
        Question(
            "Ktorý štadión zdieľajú AC Miláno a Inter Miláno?",
            "San Siro",
            listOf("Stadio Olimpico", "Allianz Stadium", "Stadio Diego Armando Maradona"),
            "San Siro sa oficiálne volá Stadio Giuseppe Meazza. Oba milánske kluby na ňom hrajú desaťročia.",
        ),
        Question(
            "Na ktorom štadióne hrá Bayern Mníchov?",
            "Allianz Arena",
            listOf("Signal Iduna Park", "Olympiastadion", "Veltins-Arena"),
            "Allianz Arena bola otvorená v roku 2005. Jej plášť dokáže svietiť rôznymi farbami.",
        ),
        Question(
            "Ktorý klub hrá na štadióne Anfield?",
            "Liverpool FC",
            listOf("Everton FC", "Manchester City", "Leeds United"),
            "Anfield je domovom Liverpoolu od roku 1892. Pôvodne na ňom hral Everton.",
        ),
        Question(
            "Ako sa nazýva štadión Borussie Dortmund?",
            "Signal Iduna Park",
            listOf("Allianz Arena", "Volksparkstadion", "RheinEnergieStadion"),
            "Pôvodný názov je Westfalenstadion. Jeho južná tribúna „Žltá stena“ patrí k najväčším v Európe.",
        ),
        Question(
            "Ktorý klub hrá na štadióne Estádio do Dragão?",
            "FC Porto",
            listOf("Benfica Lisabon", "Sporting CP", "Sporting Braga"),
            "Názov „Dračí štadión“ odkazuje na draka v erbe FC Porto.",
        ),
        Question(
            "Ako sa volá štadión Ajaxu Amsterdam?",
            "Johan Cruijff ArenA",
            listOf("De Kuip", "Philips Stadion", "Abe Lenstra Stadion"),
            "Štadión bol v roku 2018 premenovaný na počesť legendy Johana Cruijffa.",
        ),
        Question(
            "Ktorý klub hrá na štadióne De Kuip v Rotterdame?",
            "Feyenoord",
            listOf("Sparta Rotterdam", "PSV Eindhoven", "AZ Alkmaar"),
            "„De Kuip“ znamená „vaňa“. Prezývku dostal podľa tvaru tribún.",
        ),
        Question(
            "Ako sa volá štadión, na ktorom hrá SSC Neapol?",
            "Stadio Diego Armando Maradona",
            listOf("Stadio Olimpico", "San Siro", "Stadio Artemio Franchi"),
            "Štadión San Paolo bol v roku 2020 premenovaný po smrti Diega Maradonu, legendy Neapola.",
        ),
        Question(
            "Ktoré dva rímske kluby zdieľajú Stadio Olimpico?",
            "AS Rím a Lazio",
            listOf("AS Rím a Fiorentina", "Lazio a Atalanta", "AS Rím a Neapol"),
            "Rímske derby „Derby della Capitale“ sa hrá na spoločnom štadióne.",
        ),
        Question(
            "Na ktorom štadióne hrá Olympique Marseille?",
            "Stade Vélodrome",
            listOf("Parc des Princes", "Stade de France", "Groupama Stadium"),
            "Názov pochádza z čias, keď okolo ihriska viedla cyklistická dráha.",
        ),
        Question(
            "Na ktorom štadióne hrá Chelsea FC?",
            "Stamford Bridge",
            listOf("Craven Cottage", "Selhurst Park", "Emirates Stadium"),
            "Chelsea hrá na Stamford Bridge od svojho založenia v roku 1905.",
        ),

        // Prezývky a symboly
        Question(
            "Aká je prezývka Juventusu Turín?",
            "Stará dáma",
            listOf("Býci", "Nebeskomodrí", "Vlci"),
            "„La Vecchia Signora“. Býci sú prezývkou mestského rivala Torino FC.",
        ),
        Question(
            "Ktorý klub má prezývku „Červení diabli“?",
            "Manchester United",
            listOf("Liverpool FC", "Arsenal FC", "Nottingham Forest"),
            "Diabla má Manchester United aj v klubovom erbe.",
        ),
        Question(
            "Aká je prezývka Arsenalu?",
            "Kanonieri (Gunners)",
            listOf("Kladivári (Hammers)", "Líšky (Foxes)", "Straky (Magpies)"),
            "Klub založili robotníci zbrojovky Royal Arsenal. Preto má v erbe kanón.",
        ),
        Question(
            "Ktorý klub prezývajú „Kladivári“ (The Hammers)?",
            "West Ham United",
            listOf("Sheffield United", "Aston Villa", "Crystal Palace"),
            "Klub vznikol z tímu lodenice Thames Ironworks. Kladivá sú dodnes v erbe.",
        ),
        Question(
            "Prečo prezývajú Atlético Madrid „Los Colchoneros“?",
            "Pruhy na dresoch pripomínali matrace",
            listOf("Klub založili výrobcovia nábytku", "Hráči spávali na štadióne", "Podľa mena prvého trénera"),
            "Červeno-biele pruhy pripomínali lacné poťahy matracov. „Colchoneros“ znamená matracári.",
        ),
        Question(
            "Akú pieseň spievajú fanúšikovia Liverpoolu pred zápasmi?",
            "You'll Never Walk Alone",
            listOf("We Are the Champions", "Glory Glory Man United", "Blue Moon"),
            "Pieseň z muzikálu Carousel preslávila v 60. rokoch liverpoolská skupina Gerry and the Pacemakers.",
        ),
        Question(
            "Aké heslo používa FC Barcelona?",
            "Més que un club",
            listOf("Hala Madrid", "Mia san mia", "Forza e onore"),
            "„Viac ako klub“. Vyjadruje spojenie klubu s katalánskou identitou.",
        ),
        Question(
            "Aká je prezývka SK Slavia Praha?",
            "Sešívaní",
            listOf("Rudí", "Vikingovia", "Ťahači"),
            "Dres Slavie je zošitý z červenej a bielej polovice. Odtiaľ pochádza prezývka.",
        ),

        // Derby
        Question(
            "Ktoré kluby hrajú derby „El Clásico“?",
            "Real Madrid a FC Barcelona",
            listOf("Real Madrid a Atlético Madrid", "FC Barcelona a Espanyol", "Sevilla a Real Betis"),
            "El Clásico patrí k najsledovanejším klubovým zápasom na svete.",
        ),
        Question(
            "Ako sa nazýva derby Celticu a Rangers v Glasgowe?",
            "Old Firm",
            listOf("Merseyside derby", "North London derby", "Der Klassiker"),
            "Old Firm je jedno z najstarších a najvyhrotenejších derby na svete.",
        ),
        Question(
            "Medzi ktorými klubmi sa hrá „Derby della Madonnina“?",
            "AC Miláno a Inter Miláno",
            listOf("Juventus a Torino", "AS Rím a Lazio", "Janov a Sampdoria"),
            "Derby je pomenované po soche Madony na vrchole milánskej katedrály.",
        ),
        Question(
            "Ktoré kluby hrajú nemecký „Der Klassiker“?",
            "Bayern Mníchov a Borussia Dortmund",
            listOf("Schalke 04 a Borussia Dortmund", "Hamburger SV a Werder Brémy", "Bayern a 1860 Mníchov"),
            "Ide o súboj dvoch najúspešnejších klubov posledných desaťročí v Nemecku.",
        ),

        // Európske poháre
        Question(
            "Ktorý klub vyhral prvý ročník Pohára majstrov európskych krajín (1956)?",
            "Real Madrid",
            listOf("Benfica Lisabon", "AC Miláno", "Stade de Reims"),
            "Real porazil vo finále v Paríži Stade de Reims 4:3 a potom vyhral päť ročníkov po sebe.",
        ),
        Question(
            "Ktorý klub má najviac titulov v PMEZ a Lige majstrov?",
            "Real Madrid",
            listOf("AC Miláno", "Bayern Mníchov", "Liverpool FC"),
            "Real Madrid získal 15 titulov (stav k roku 2025). Druhé je AC Miláno so siedmimi.",
        ),
        Question(
            "Ktorý slovenský klub vyhral Pohár víťazov pohárov v roku 1969?",
            "Slovan Bratislava",
            listOf("Spartak Trnava", "Inter Bratislava", "MŠK Žilina"),
            "Slovan porazil vo finále v Bazileji FC Barcelona 3:2. Je to jediná európska trofej slovenského klubu.",
        ),
        Question(
            "Ktorý slovenský klub sa v roku 1969 dostal do semifinále PMEZ?",
            "Spartak Trnava",
            listOf("Slovan Bratislava", "Tatran Prešov", "DAC Dunajská Streda"),
            "Trnava v semifinále tesne vypadla s Ajaxom Amsterdam. Bola to zlatá éra trnavského futbalu.",
        ),
        Question(
            "Ktorý britský klub ako prvý vyhral PMEZ (1967)?",
            "Celtic Glasgow",
            listOf("Manchester United", "Liverpool FC", "Rangers FC"),
            "Tím „Lisabonských levov“ tvorili hráči narodení do 50 km od Glasgowa.",
        ),
        Question(
            "Ktorý rumunský klub vyhral PMEZ v roku 1986?",
            "Steaua Bukurešť",
            listOf("Dinamo Bukurešť", "Rapid Bukurešť", "CFR Kluž"),
            "Vo finále proti Barcelone chytil brankár Helmuth Duckadam v rozstrele všetky štyri penalty.",
        ),
        Question(
            "Ktorý klub vyhral PMEZ v roku 1991?",
            "Crvena zvezda Belehrad",
            listOf("Partizan Belehrad", "Dinamo Záhreb", "Hajduk Split"),
            "Crvena zvezda porazila vo finále v Bari Olympique Marseille po penaltovom rozstrele.",
        ),
        Question(
            "Ktorý anglický klub vyhral PMEZ dvakrát po sebe (1979, 1980) pod trénerom Brianom Cloughom?",
            "Nottingham Forest",
            listOf("Aston Villa", "Leeds United", "Derby County"),
            "Forest postúpil do najvyššej súťaže len v roku 1977. O dva roky bol víťazom Európy.",
        ),
        Question(
            "Ktorý klub vyhral PMEZ tri razy po sebe v rokoch 1971 – 1973?",
            "Ajax Amsterdam",
            listOf("Bayern Mníchov", "Feyenoord", "Benfica Lisabon"),
            "Ajax s Johanom Cruijffom hral „totálny futbal“. Hneď po ňom vyhral tri tituly za sebou Bayern.",
        ),
        Question(
            "Ktorý klub otočil finále Ligy majstrov 2005 v Istanbule zo stavu 0:3?",
            "Liverpool FC",
            listOf("AC Miláno", "Chelsea FC", "FC Porto"),
            "Liverpool vyrovnal na 3:3 za šesť minút a proti AC Miláno vyhral v penaltovom rozstrele.",
        ),
        Question(
            "S ktorým trénerom vyhral Inter Miláno v roku 2010 treble?",
            "José Mourinho",
            listOf("Carlo Ancelotti", "Roberto Mancini", "Fabio Capello"),
            "Inter vtedy vyhral ligu, taliansky pohár aj Ligu majstrov.",
        ),
        Question(
            "Ktorý klub vyhral Ligu majstrov v roku 2004 pod vedením Josého Mourinha?",
            "FC Porto",
            listOf("Benfica Lisabon", "AS Monaco", "Valencia CF"),
            "Porto porazilo vo finále v Gelsenkirchene AS Monaco 3:0.",
        ),
        Question(
            "Ktorý francúzsky klub ako prvý vyhral Ligu majstrov (1993)?",
            "Olympique Marseille",
            listOf("Paris Saint-Germain", "AS Monaco", "Olympique Lyon"),
            "Marseille porazilo vo finále AC Miláno 1:0 gólom Basila Boliho.",
        ),
        Question(
            "Ktorý turecký klub ako prvý získal európsku trofej (Pohár UEFA 2000)?",
            "Galatasaray Istanbul",
            listOf("Fenerbahçe Istanbul", "Beşiktaş Istanbul", "Trabzonspor"),
            "Galatasaray porazil vo finále Arsenal po penaltovom rozstrele.",
        ),

        // História a zaujímavosti
        Question(
            "Ktorý klub je považovaný za najstarší futbalový klub na svete?",
            "Sheffield FC",
            listOf("Notts County", "Genoa CFC", "Queen's Park"),
            "Sheffield FC bol založený v roku 1857. Uznáva ho aj FIFA.",
        ),
        Question(
            "Ktorý klub hrá výhradne s hráčmi z Baskicka?",
            "Athletic Bilbao",
            listOf("Real Sociedad", "Osasuna", "Deportivo Alavés"),
            "Athletic túto filozofiu drží viac ako sto rokov. Napriek tomu nikdy nezostúpil z La Ligy.",
        ),
        Question(
            "Ktorá firma stála pri založení PSV Eindhoven?",
            "Philips",
            listOf("Heineken", "Shell", "ING"),
            "PSV znamená Philips Sport Vereniging. Klub vznikol v roku 1913 ako športový spolok zamestnancov.",
        ),
        Question(
            "Ktorý klub vyhral Bundesligu 2023/24 bez jedinej prehry?",
            "Bayer Leverkusen",
            listOf("Bayern Mníchov", "VfB Stuttgart", "RB Lipsko"),
            "Pod trénerom Xabim Alonsom získal Leverkusen svoj prvý majstrovský titul.",
        ),
        Question(
            "Ktorý klub senzačne vyhral Premier League 2015/16 pri kurze 5000:1?",
            "Leicester City",
            listOf("Tottenham Hotspur", "Southampton FC", "Everton FC"),
            "Tím trénera Claudia Ranieriho viedli Jamie Vardy a Riyad Mahrez.",
        ),
        Question(
            "V ktorom meste sídli SL Benfica?",
            "Lisabon",
            listOf("Porto", "Braga", "Coimbra"),
            "Benfica je jeden z klubov s najväčším počtom členov na svete.",
        ),
        Question(
            "V ktorom roku bol založený Paris Saint-Germain?",
            "1970",
            listOf("1904", "1932", "1958"),
            "PSG je jeden z najmladších veľkých európskych klubov. Ligu majstrov prvýkrát vyhral v roku 2025.",
        ),
    )
}
