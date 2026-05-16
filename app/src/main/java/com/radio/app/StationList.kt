package com.radio.app

object StationList {
    val stations = listOf(
        // Nacionales
        RadioStation("ser", "Cadena SER", "https://playerservices.streamtheworld.com/api/livestream-redirect/CADENASER.mp3", "Nacionales"),
        RadioStation("cope", "COPE", "https://flucast09-h-cloud.flumotion.com/cope/net1.mp3", "Nacionales"),
        RadioStation("ondacero", "Onda Cero", "https://radio-atres-live.ondacero.es/api/livestream-redirect/OCAAC.aac", "Nacionales"),
        RadioStation("rne1", "Radio Nacional (RNE 1)", "https://rtvelivestream.rtve.es/rtvesec/rne/rne_r1_main.m3u8", "Nacionales"),
        RadioStation("esradio", "esRadio", "https://libertaddigital-radio-live1.flumotion.com/libertaddigital/ld-live1-high.mp3", "Nacionales"),
        RadioStation("radiomarca", "Radio Marca", "https://playerservices.streamtheworld.com/api/livestream-redirect/RADIOMARCA_NACIONAL.mp3", "Nacionales"),
        RadioStation("rne5", "Radio 5 RNE", "https://rtvelivestream.rtve.es/rtvesec/rne/rne_r5_madrid_main.m3u8", "Nacionales"),
        RadioStation("rac1", "RAC1", "https://playerservices.streamtheworld.com/api/livestream-redirect/RAC_1.mp3", "Nacionales"),
        RadioStation("catradio", "Catalunya Ràdio", "https://directes-radio-int.3catdirectes.cat/live-content/catalunya-radio-hls/master.m3u8", "Nacionales"),
        RadioStation("ondamadrid", "Onda Madrid", "https://live.telemadrid.cross-media.es/6389769037112/eu-central-1/6416060453001/eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJob3N0IjoiajI5YjgyLmVncmVzcy5haGc3NmwiLCJhY2NvdW50X2lkIjoiNjQxNjA2MDQ1MzAwMSIsImVobiI6ImxpdmUudGVsZW1hZHJpZC5jcm9zcy1tZWRpYS5lcyIsImlzcyI6ImJsaXZlLXBsYXliYWNrLXNvdXJjZS1hcGkiLCJzdWIiOiJwYXRobWFwdG9rZW4iLCJhdWQiOlsiNjQxNjA2MDQ1MzAwMSJdLCJqdGkiOiI2Mzg5NzY5MDM3MTEyIn0.mSEPQICNIKBAWWzolDpDk6cERGe7k93rkqQunSc6cG4/playlist-hls.m3u8", "Nacionales"),
        RadioStation("canalsur", "Canal Sur Radio", "https://rtva-live-radio.flumotion.com/rtva/csr.mp3", "Nacionales"),
        RadioStation("radioeuskadi", "Radio Euskadi", "https://multimedia.eitb.eus/live-content/radioeuskadi-hls/master.m3u8", "Nacionales"),
        RadioStation("radiogalega", "Radio Galega", "https://crtvg-radiogalega-hls.flumotion.cloud/playlist.m3u8", "Nacionales"),
        RadioStation("radiomaria", "Radio María", "https://dreamsiteradiocp4.com/proxy/rmspain1?mp=/stream/1/;.mp3", "Nacionales"),

        // Musicales
        RadioStation("los40", "LOS40", "https://playerservices.streamtheworld.com/api/livestream-redirect/LOS40AAC.aac", "Musicales"),
        RadioStation("los40classic", "LOS40 Classic", "https://playerservices.streamtheworld.com/api/livestream-redirect/LOS40_CLASSICAAC.aac", "Musicales"),
        RadioStation("los40urban", "LOS40 Urban", "https://playerservices.streamtheworld.com/api/livestream-redirect/LOS40_URBAN.mp3", "Musicales"),
        RadioStation("los40dance", "LOS40 Dance", "https://playerservices.streamtheworld.com/api/livestream-redirect/LOS40_DANCE.mp3", "Musicales"),
        RadioStation("kissfm", "Kiss FM", "https://kissfm.kissfmradio.cires21.com/kissfm.mp3", "Musicales"),
        RadioStation("cadena100", "Cadena 100", "https://cadena100-cope.flumotion.com/chunks.m3u8", "Musicales"),
        RadioStation("cadenadial", "Cadena Dial", "https://playerservices.streamtheworld.com/api/livestream-redirect/CADENADIAL.mp3", "Musicales"),
        RadioStation("rockfm", "Rock FM", "https://rockfm-cope.flumotion.com/playlist.m3u8", "Musicales"),
        RadioStation("europafm", "Europa FM", "https://radio-atres-live.ondacero.es/api/livestream-redirect/EFMAAC.aac", "Musicales"),
        RadioStation("rne3", "Radio 3 RNE", "https://rtvelivestream.rtve.es/rtvesec/rne/rne_r3_main.m3u8", "Musicales"),
        RadioStation("radiole", "Radiolé", "https://playerservices.streamtheworld.com/api/livestream-redirect/RADIOLE.mp3", "Musicales"),
        RadioStation("locafm", "Loca FM", "https://s3.we4stream.com:2020/stream/locafm", "Musicales"),
        RadioStation("hitfm", "Hit FM", "https://adhandler.kissfmradio.cires21.com/get_link?url=https://bbhitfm.kissfmradio.cires21.com/bbhitfm.mp3", "Musicales"),
        RadioStation("melodiafm", "Melodía FM", "https://radio-atres-live.ondacero.es/api/livestream-redirect/MELODIA_FMAAC.aac", "Musicales"),
        RadioStation("canalfiesta", "Canal Fiesta Radio", "https://rtva-live-radio.flumotion.com/rtva/cfr.mp3", "Musicales"),
        RadioStation("megastarfm", "MegaStar FM", "https://megastar-cope.flumotion.com/playlist.m3u8", "Musicales"),
        RadioStation("flaixfm", "Flaix FM", "https://stream.flaixfm.cat/icecast", "Musicales"),
        RadioStation("flaixbac", "Flaixbac", "https://stream.flaixbac.cat/icecast", "Musicales"),
        RadioStation("rac105", "RAC 105", "https://playerservices.streamtheworld.com/api/livestream-redirect/RAC105.mp3", "Musicales"),
        RadioStation("rneclasica", "Radio Clásica RNE", "https://rtvelivestream.rtve.es/rtvesec/rne/rne_r2_main.m3u8", "Musicales"),
        RadioStation("dialbaladas", "Dial Baladas", "https://playerservices.streamtheworld.com/api/livestream-redirect/CADENADIAL_03.mp3", "Musicales"),
        RadioStation("diallatino", "Dial Latino", "https://playerservices.streamtheworld.com/api/livestream-redirect/CADENADIAL_02.mp3", "Musicales"),
        RadioStation("locadrumbass", "Loca FM Drum&Bass", "https://s2.we4stream.com/listen/loca_drum__bass/live", "Musicales"),
        RadioStation("mariskalrock", "MariskalRock Radio", "https://media.profesionalhosting.com:8047/stream", "Musicales"),
        RadioStation("digitalhits", "digitalHits FM", "https://dhits.frilab.com:8443/dhits", "Musicales"),
        RadioStation("rumberos", "RUMBEROS", "https://str1.mediatelekom.net:9952/live", "Musicales"),
        RadioStation("locaurbandup", "LOCAFM URBAN", "https://s1.we4stream.com:2020/stream/locaurban", "Musicales"),
        RadioStation("locadancedup", "LOCAFM DANCE", "https://s2.we4stream.com/listen/loca_dance/live", "Musicales"),
        RadioStation("locarememberdup", "LOCA FM REMEMBER", "https://s2.we4stream.com/listen/loca_remember/live", "Musicales"),
        RadioStation("locaharddup", "LOCAFM HARD", "https://s2.we4stream.com/listen/loca_hard/live", "Musicales"),
        RadioStation("locahousedup", "LOCAFM HOUSE", "https://s2.we4stream.com/listen/loca_house/live", "Musicales"),
        RadioStation("lochilloutdup", "LOCAFM CHILL OUT", "https://s2.we4stream.com/listen/loca_chill_out/live", "Musicales"),
        RadioStation("loctechhousedup", "LOCAFM TECH HOUSE", "https://s2.we4stream.com/listen/loca_tech_house/live", "Musicales"),
        RadioStation("loctrancedup", "LOCAFM TRANCE", "https://s2.we4stream.com/listen/loca_trance/live", "Musicales"),
        RadioStation("loctechnodup", "LOCAFM TECHNO", "https://s2.we4stream.com/listen/loca_techo/live", "Musicales"),
        RadioStation("locbigroomdup", "LOCAFM BIG ROOM", "https://s2.we4stream.com/listen/loca_big_room/live", "Musicales"),
        RadioStation("locdeephousedup", "LOCAFM DEEP HOUSE", "https://s2.we4stream.com/listen/loca_deep_house/live", "Musicales"),
        RadioStation("locsessionsdup", "LOCAFM SESSIONS", "https://s2.we4stream.com/listen/loca_sessions/live", "Musicales"),
        RadioStation("locaambientdup", "LOCAFM AMBIENT", "https://s2.we4stream.com/listen/loca_ambient/live", "Musicales"),
        RadioStation("loca80sdup", "LOCAFM 80", "https://s2.we4stream.com/listen/loca_80s/live", "Musicales"),
        RadioStation("loca90sdup", "LOCAFM 90", "https://s2.we4stream.com/listen/loca_90s_/live", "Musicales"),
        RadioStation("locmelodichousedup", "LOCAFM MELODIC HOUSE", "https://s2.we4stream.com/listen/loca_melodic_house/live", "Musicales"),
        RadioStation("locmelodictechnodup", "LOCAFM MELODIC TECHNO", "https://s2.we4stream.com/listen/loca_melodic_techno/live", "Musicales"),
        RadioStation("lochardtechnodup", "LOCAFM HARD TECHNO", "https://s2.we4stream.com/listen/loca_hard_techno/live", "Musicales"),
        RadioStation("locaindustrialdup", "LOCA FM INDUSTRIAL", "https://s2.we4stream.com/listen/loca_industrial/live", "Musicales"),

        // Deportivas
        RadioStation("radioestadio", "Valencia CF Radio", "https://radio-valencia-direct.flumotion.com/rvfc/shoutcast.mp3", "Deportivas"),
        RadioStation("sevillafc", "Sevilla FC Radio", "https://open.http.mp.streamamg.com/p/3001314/sp/300131400/playManifest/entryId/0_ye0b8tc0/format/applehttp/protocol/https/uiConfId/30026292/a.m3u8", "Deportivas"),
        RadioStation("betisradio", "Real Betis Radio", "https://comcast01-h-cloud.flumotion.com/betistv/radio1.mp3", "Deportivas"),

        // Autonómicas - Andalucía
        RadioStation("flamenco", "Flamenco Radio", "https://rtva-live-radio.flumotion.com/rtva/flamenco.mp3", "Autonómicas"),
        RadioStation("csmusica", "Canal Sur Radio Música", "https://rtva-live-radio.flumotion.com/rtva/csm.mp3", "Autonómicas"),
        RadioStation("rai", "Radio Andalucía Información", "https://rtva-live-radio.flumotion.com/rtva/rai.mp3", "Autonómicas"),

        // Cataluña
        RadioStation("catinfo", "Catalunya Informació", "https://directes-radio-int.3catdirectes.cat/live-content/catalunya-info-hls/master.m3u8", "Autonómicas"),
        RadioStation("catmusica", "Catalunya Música", "https://directes-radio-int.3catdirectes.cat/live-content/catmusica-hls/master.m3u8", "Autonómicas"),
        RadioStation("rac105cat", "RAC 105", "https://playerservices.streamtheworld.com/api/livestream-redirect/RAC105AAC.aac", "Autonómicas"),

        // País Vasco
        RadioStation("eitb", "EITB Radio", "https://multimedia.eitb.eus/live-content/radioeuskadi-hls/master.m3u8", "Autonómicas"),

        // Galicia
        RadioStation("radiogalegamusica", "Radio Galega Música", "https://crtvg-radiogalega-musica-hls.flumotion.cloud/playlist.m3u8", "Autonómicas"),

        // Aragón
        RadioStation("aragonradio", "Aragón Radio", "https://cartv.streaming.aranova.es/hls/live/aragon_radio/index.m3u8", "Autonómicas"),

        // Castilla-La Mancha
        RadioStation("rclm", "Radio Castilla-La Mancha", "https://radio.cmmedia.es:8443/stream", "Autonómicas"),

        // Castilla y León
        RadioStation("rtvcyl", "Radio Castilla y León", "https://streaming2.elitecomunicacion.es:8642/stream", "Autonómicas"),

        // Islas Baleares
        RadioStation("ib3", "IB3 Ràdio", "https://streamingib3.ib3tv.com/ib3radio.mp3", "Autonómicas"),

        // Islas Canarias
        RadioStation("rtvc", "Canarias Radio", "https://rtvc-radio.flumotion.com/rtvc/radio1.mp3", "Autonómicas"),

        // Infantiles
        RadioStation("babyradio", "Babyradio", "https://srv6022.dns-lcinternet.com:8033/stream", "Infantiles"),
        RadioStation("antenita", "Antenita", "https://vps-9f1e2662.vps.ovh.net/antenitafm", "Infantiles"),
    )

    val categories = stations.map { it.category }.distinct()

    fun getByCategory(category: String): List<RadioStation> =
        stations.filter { it.category == category }
}
