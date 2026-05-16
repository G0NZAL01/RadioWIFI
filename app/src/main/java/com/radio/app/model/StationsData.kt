package com.radio.app.model

import android.graphics.Color

object StationsData {
    val stations: List<Station> = listOf(
        Station("Los 40 Principales", "https://playerservices.streamtheworld.com/api/livestream-redirect/LOS40_SC", "Música", 0xFFE53935.toInt(), "https://www.los40.com/favicon.ico"),
        Station("Cadena SER", "https://playerservices.streamtheworld.com/api/livestream-redirect/CADENASER_SC", "General", 0xFF1565C0.toInt()),
        Station("Cadena COPE", "https://playerservices.streamtheworld.com/api/livestream-redirect/COPE_SC", "General", 0xFFE65100.toInt()),
        Station("Onda Cero", "https://playerservices.streamtheworld.com/api/livestream-redirect/ONDACERO_SC", "General", 0xFF00838F.toInt()),
        Station("RNE Radio Nacional", "https://rtvelivestream.akamaized.net/rtvesec/rne/rne_main.m3u8", "General", 0xFF4CAF50.toInt()),
        Station("Radio 3 (RNE)", "https://rtvelivestream.akamaized.net/rtvesec/rne/rne3/main.m3u8", "Cultural", 0xFF9C27B0.toInt()),
        Station("Europa FM", "https://playerservices.streamtheworld.com/api/livestream-redirect/EUROPAFM_SC", "Música", 0xFF2196F3.toInt()),
        Station("Rock FM", "https://playerservices.streamtheworld.com/api/livestream-redirect/ROCKFM_SC", "Rock", 0xFF212121.toInt()),
        Station("Kiss FM", "https://kissfm.kissfmradio.cires21.com/kissfm.mp3", "Música", 0xFFE91E63.toInt()),
        Station("Radio Marca", "https://playerservices.streamtheworld.com/api/livestream-redirect/RADIOMARCA_SC", "Deportes", 0xFFFF6F00.toInt()),
        Station("esRadio", "https://libertaddigital-radio-live1.flumotion.com/libertaddigital/ld-live1-low.aac", "General", 0xFF1B5E20.toInt()),
        Station("Cadena 100", "https://playerservices.streamtheworld.com/api/livestream-redirect/CADENA100_SC", "Música", 0xFF0288D1.toInt()),
        Station("Radiolé", "https://playerservices.streamtheworld.com/api/livestream-redirect/RADIOLE_SC", "Flamenco", 0xFFD32F2F.toInt()),
        Station("Máxima FM", "https://playerservices.streamtheworld.com/api/livestream-redirect/MAXIMAFM_SC", "Música", 0xFFFF4081.toInt()),
        Station("Melodía FM", "https://playerservices.streamtheworld.com/api/livestream-redirect/MELODIAFM_SC", "Música", 0xFF7B1FA2.toInt()),
        Station("Loca FM", "https://locafm-live.flumotion.com/locafm/live.mp3", "Música", 0xFFFF5722.toInt()),
        Station("MegaStar FM", "https://playerservices.streamtheworld.com/api/livestream-redirect/MEGASTAR_FM_SC", "Música", 0xFFFFD600.toInt()),
        Station("RAC 105", "https://streaming.rac105.cat/", "Música", 0xFFE040FB.toInt(), "https://www.rac105.cat/favicon.ico"),
        Station("Canal Sur Radio", "https://canalsur.radio.com/stream.mp3", "General", 0xFF2E7D32.toInt()),
        Station("Radio Galega", "https://streaming.radiogalega.com/stream.mp3", "General", 0xFF1976D2.toInt()),
        Station("IB3 Ràdio", "https://ib3radio.stream/ib3radio.mp3", "General", 0xFF00897B.toInt()),
        Station("Onda Vasca", "https://www.ondavasca.com:8443/stream", "General", 0xFF5D4037.toInt()),
        Station("RPA (Radio del Principado)", "https://playerservices.streamtheworld.com/api/livestream-redirect/RPA_SC", "General", 0xFF4527A0.toInt()),
    )
}
