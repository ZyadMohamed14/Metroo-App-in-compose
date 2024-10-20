package com.example.nativemetrooapp.data.local

import com.example.nativemetrooapp.R

import android.content.Context

class CairoLinesCoordinates(private val context: Context) {


    private val metroLine1Coordinates: Map<Int, String> = mapOf(
        R.string.helwan to "29.84903736119006,31.33423752699516",
        R.string.ain_helwan to "29.86267458956478,31.325052634767143",
        R.string.helwan_university to "29.869851393571782,31.319648635072106",
        R.string.wadi_hof to "29.879126538506355,31.313567241694653",
        R.string.hadayek_helwan to "29.897321402536715,31.304206737071468",
        R.string.el_maasara to "29.906392257121617,31.29954396827793",
        R.string.tora_el_balad to "29.946876646437012,31.27298910465672",
        R.string.kozzika to "29.936430585578005,31.281451661253538",
        R.string.tora_el_asmant to "29.925973501152576,31.28754625346665",
        R.string.sakanat_el_maadi to "29.953292118861153,31.26296199720143",
        R.string.maadi to "29.960342917786658,31.257637307857202",
        R.string.hadayek_el_maadi to "29.969992670385217,31.250811476627565",
        R.string.dar_el_salam to "29.982061686271564,31.24231317469022",
        R.string.el_zahraa to "29.995513640789056,31.231180465913802",
        R.string.mar_girgis to "30.00611532143133,31.229627777173285",
        R.string.el_malek_el_saleh to "30.017684059509136,31.231220248191676",
        R.string.al_sayeda_zeinab to "30.02929789246813,31.235438607042855",
        R.string.saad_zaghloul to "30.037029922797835,31.23833804620185",
        R.string.sadat to "30.044129052218047,31.234433850931318",
        R.string.nasser to "30.053497136289057,31.2387399486692",
        R.string.orabi to "30.05668037133046,31.2420699976144",
        R.string.al_shohadaa to "30.06106992046829,31.246057125172463",
        R.string.ghamra to "30.069054704852153,31.26463563213536",
        R.string.el_demerdash to "30.077316353366353,31.27781229124498",
        R.string.manshiet_el_sadr to "30.081997555984643,31.287537692810563",
        R.string.kobri_el_qobba to "30.08718414160959,31.29411744290364",
        R.string.hammamat_el_qobba to "30.091219081333755,31.2989307032774",
        R.string.saray_el_qobba to "30.097632695870367,31.304554147990988",
        R.string.hadayeq_el_zaitoun to "30.10585608498135,31.31048380409617",
        R.string.helmeyet_el_zaitoun to "30.113251031712153,31.31397014843506",
        R.string.el_matareyya to "30.12131861830261,31.313740489882196",
        R.string.ain_shams to "30.13104078468673,31.31909280990519",
        R.string.ezbet_el_nakhl to "30.13929806402633,31.32441992874097",
        R.string.el_marg to "30.152050394378318,31.335682068039823",
        R.string.new_el_marg to "30.163618359624618,31.338345584019827"
    )

    private val metroLine2Coordinates: Map<Int, String> = mapOf(
        R.string.shobra_el_kheima to "30.12224225798257,31.24443716536811",
        R.string.kolleyyet_el_zeraa to "30.113714185018274,31.24868292989835",
        R.string.mezallat to "30.104182586810847,31.24562414920438",
        R.string.khalafawy to "30.09789607555925,31.24539415336153",
        R.string.st_teresa to "30.08795160733788,31.24548959931378",
        R.string.rod_el_farag to "30.080589343190677,31.245412872390176",
        R.string.massara to "30.070901990972583,31.245103301568424",
        R.string.al_shohadaa to "30.06106992046829,31.246057125172463",
        R.string.attaba to "30.05233597308318,31.246795683320414",
        R.string.mohamed_naguib to "30.045325405514745,31.244159745399173",
        R.string.sadat to "30.044129052218047,31.234433850931318",
        R.string.opera to "30.041952643641874,31.22497455031182",
        R.string.dokki to "30.038432079810242,31.212233222386256",
        R.string.bohooth to "30.03580176297431,31.20016697820741",
        R.string.cairo_university to "30.02600373947895,31.20116942825512",
        R.string.faisal to "30.017360945976815,31.203935185750414",
        R.string.giza to "30.01065584720426,31.207087935111314",
        R.string.omm_el_masryeen to "30.00564951534086,31.208124891006083",
        R.string.sakiat_mekki to "29.995491964138203,31.20864929094024",
        R.string.el_mounib to "29.98110225916312,31.212336612229258"
    )

    private val metroLine3Coordinates: Map<Int, String> = mapOf(
        R.string.road_el_farg_corr to "30.10190989339181,31.18443476887509",
        R.string.ring_road to "30.096436204240455,31.19957496154549",
        R.string.el_qumia to "30.093243411138406,31.209015865290002",
        R.string.el_bohy to "30.08212470598578,31.210551397385398",
        R.string.imbaba to "30.075849192590763,31.20745519619157",
        R.string.sudan to "30.070089263060304,31.204705593218478",
        R.string.kitkat to "30.06655933608745,31.21301257656363",
        R.string.safay_hegazy to "30.062268041069913,31.223297859633735",
        R.string.maspero to "30.055701946915768,31.232115481245565",
        R.string.nasser to "30.053507874692166,31.238736914888786",
        R.string.attaba to "30.052359292738558,31.24678431031718",
        R.string.bab_el_shaaria to "30.054132043308126,31.255909339812927",
        R.string.el_geish to "30.06173781897152,31.266885049885264",
        R.string.abdou_pasha to "30.064796568469745,31.27470388360256",
        R.string.abbasia to "30.072005453041363,31.283362803691247",
        R.string.cairo_fair to "30.0733734683471,31.30098199011045",
        R.string.stadium to "30.072943184506745,31.317099714339914",
        R.string.kolleyet_el_banat to "30.084063374530945,31.329004072861526",
        R.string.al_ahram to "30.091783963542465,31.3262925051496",
        R.string.haroun to "30.101537049663246,31.33300219159074",
        R.string.heliopolis to "30.108473252129592,31.338327106406293",
        R.string.alf_maskan to "30.119010620454404,31.340184365632673",
        R.string.nadi_el_shams to "30.125513322722618,31.34891613259467",
        R.string.al_nozha to "30.127959919525487,31.360178589070863",
        R.string.hesham_barkat to "30.13085100901205,31.37289445119273",
        R.string.qubaa to "30.134836167768402,31.383742032938756",
        R.string.omar_ebn_el_khattab to "30.140362112263343,31.394360837128424",
        R.string.el_hayikstep to "30.14387145849703,31.404690072211803",
        R.string.adly_mansour to "30.147068153833473,31.421197940368376"
    )
    fun allMetroCoordinates(): Map<String, String> {
        val combinedCoordinates = mutableMapOf<String, String>()

        // Add coordinates from Metro Line 1
        for ((key, value) in metroLine1Coordinates) {
            combinedCoordinates[context.getString(key)] = value
        }

        // Add coordinates from Metro Line 2
        for ((key, value) in metroLine2Coordinates) {
            combinedCoordinates[context.getString(key)] = value
        }

        // Add coordinates from Metro Line 3
        for ((key, value) in metroLine3Coordinates) {
            combinedCoordinates[context.getString(key)] = value
        }

        return combinedCoordinates
    }

}