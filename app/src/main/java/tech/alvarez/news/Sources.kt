package tech.alvarez.news

enum class Sources(val value: String, val logo: Int, val homeURL: String) {

    LOS_TIEMPOS("lostiemposcom", R.drawable.lostiemposcom, "http://www.lostiempos.com/"),
    LA_RAZON("larazoncom", R.drawable.larazoncom, "http://www.la-razon.com/"),
    PAGINA_SIETE("paginasietebo", R.drawable.paginasietebo, "https://www.paginasiete.bo/"),
    EL_DEBER("eldeber.com.bo", R.drawable.lostiemposcom, "https://www.eldeber.com.bo/")

}