package com.example.investmentguidevtb.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.investmentguidevtb.data.source.api.PracticeApi
import com.example.investmentguidevtb.ui.practice.models.GameArticle
import com.example.investmentguidevtb.ui.practice.models.GameDelta
import com.example.investmentguidevtb.ui.practice.models.GameSituation
import com.example.investmentguidevtb.ui.practice.models.GameSolution
import kotlinx.coroutines.delay
import javax.inject.Inject

class PracticeRepository @Inject constructor(
    private val practiceApi: PracticeApi
) {

    val requestToChangeCardVisible = MutableLiveData<Boolean>()

    suspend fun requestSolutionFromServer(): GameSituation? {
        requestToChangeCardVisible.postValue(false)
        val result = practiceApi.getSolution(
            "",
            "",
            "",
            ""
        )

        requestToChangeCardVisible.postValue(true)

        //return if (result.isSuccessful) result.body() else null
        return createMoc()
    }

    suspend fun processSelectedSolution(selectedSolution: GameSolution?) {
        // обработка данных о пользователе для отправки на сервер

        // парс данных из выбранного пути

        // отправка данных на сервер, для олучения новой карточки
        requestSolutionFromServer()
    }

    companion object {
        fun createMoc() = GameSituation(
            "В мире началась глобальная пандемия, все границы закрыты.\nЛюди начинают паниковать\n",
            "https://static.euronews.com/articles/stories/04/56/26/60/1440x810_cmsv2_6118ec02-3213-5526-b0e8-bd2e796ee852-4562660.jpg",
            listOf(
                GameSolution(
                    "Купить акции медицинского сектора",
                    "Круто! Это может принести тебе большой доход в пандемию.",
                    GameArticle(
                        "www.interfax.ru",
                        "Коронавирус. Инвестиции в безопасность",
                        textArticle
                    ), GameDelta(1,1,1,)
                ),
                GameSolution(
                    "Купить акции медицинского сектора",
                    "Круто! Это может принести тебе большой доход в пандемию.",
                    GameArticle(
                        "www.interfax.ru",
                        "Коронавирус. Инвестиции в безопасность",
                        textArticle
                    ), GameDelta(1,1,1,)
                ),GameSolution(
                    "Купить акции медицинского сектора",
                    "Круто! Это может принести тебе большой доход в пандемию.",
                    GameArticle(
                        "www.interfax.ru",
                        "Коронавирус. Инвестиции в безопасность",
                        textArticle
                    ), GameDelta(1,1,1,)
                )
            )
        )

        val textArticle = """
            Вторая волна коронавирусной инфекции, которую прогнозировали и которой опасались эксперты, уже захлестнула многие страны. Число заболевших стремительно растет, что приводит к усилению ограничительных мер.
Вакцина как панацея
Одержать победу над COVID-19 поможет появление вакцины. Сегодня в нашей стране уже проходят третью стадию клинических испытаний два препарата, готовится к регистрации третий. Но точную дату начала массовой вакцинации эксперты пока не готовы назвать, кто-то говорит про начало декабря 2020 г., а кто-то про весну 2021-го.
Новое, ставшее привычным
Удаленный режим работы, онлайн совещания, дистанционное обучение – многие вещи, которые раньше являлись скорее исключением, чем правилом, прочно вошли в нашу жизнь и стали новым стандартом. Действительно, сейчас мы делаем то, о чем в начале года не могли и подумать: привычно надеваем маску, держим дистанцию, отказываемся от рукопожатий и объятий с коллегами и друзьями.
В поисках совершенной защиты
В то время как одни заведения ставят рециркуляторы просто для галочки, другие подходят к этой проблеме серьезнее, выбирая технологию, которая не только дезинфицирует, но и эффективно очищает воздух от всех видов загрязнений. Предложений на рынке много, поэтому выбрать систему достаточно сложно. Давайте попробуем разобраться на что необходимо обращать внимание.
Эффективность и универсальность. Проверяйте способность оборудования удалять из воздуха микробиологические, химические и аэрозольные загрязнители. При этом система должна неселективно уничтожать (инактивировать) все микробиологические загрязнители, в том числе устойчивые к озону и УФ-излучению. Не стесняетесь требовать заключения, подтверждающие эффективность очистки и обеззараживания.
Безопасность используемой технологии для людей. Здесь необходимо обращать внимание на возможность эксплуатации системы в присутствии людей. Наиболее яркий пример – кварцевание. Эффективно, но во время процедуры человека в помещении быть не должно. А если в промежутке между сеансами пришёл больной и чихнул?

            """
    }
}