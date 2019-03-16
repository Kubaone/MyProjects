import java.io.File
import java.util.*


fun main() {
    //zczytywanie danych treningowych
    val temp = File(System.getProperty("user.dir") + "/mpp1/iris_training.txt").readLines().toMutableList()
    val trainingList = mutableListOf<String>()
    temp.forEach { string -> trainingList.add(string.filter { it != ' ' }.replace(',', '.')) }

    //zczytywanie danych testowych
    val temp2 = File(System.getProperty("user.dir") + "/mpp1/iris_test.txt").readLines().toMutableList()
    val checkList = mutableListOf<String>()
    temp2.forEach { string -> checkList.add(string.filter { it != ' ' }.replace(',', '.')) }


    val scores: MutableList<MutableList<String>> =
        MutableList(checkList.size) { MutableList(trainingList.size) { String() } }
    scores.forEach { it.clear() }

//obliczanie odległosci danych testowych od danych treningowych
// i zapisanie ich w formie listy[index wiersza danych testowych][wszystkie wyniki dla danego wiersza]

    trainingList.forEach {
        val tmpList = it.split("\t")
        checkList.forEachIndexed { index, line ->
            val listOfStrings = line.split("\t")
            var score = 0.0

            listOfStrings.forEachIndexed { index2, s ->
                if (s[0].isDigit())
                    score += Math.pow(tmpList[index2].toDouble() - s.toDouble(), 2.0)
                else {
                    scores[index].add(Math.sqrt(score).toString())
                    scores[index].add(tmpList.last())
                }
            }

        }
    }

//pobranie parametru k

    println("Wpisz parametr k ")
    val sc = Scanner(System.`in`)
    val k = sc.next()


    val answers = MutableList(checkList.size) { MutableList(k.toInt()) { String() } }
    answers.forEach { it.clear() }

    // wyliczenie najmniejszej odległości k razy
    var x = k.toInt()
    while (x > 0) {

        scores.forEachIndexed { index, mutableList ->
            var min = Double.MAX_VALUE
            var value = ""
            mutableList.forEachIndexed { index2, string ->
                if (string[0].isDigit() && string.toDouble() < min) {
                    min = string.toDouble()
                    value = mutableList[index2 + 1]
                    //println("Index $index String $string Min $min value $value")
                }
            }
            answers[index].add(value)
            mutableList.remove(min.toString())
        }
        x--
    }


    var positive = 0
    var negative = 0

//sprawdzenie ile wyników zgadza się z odpowiedziami

    //answers.forEach{it.remove(it[0]) ;it.remove(it[41])}
    //answers.forEach{ println(it.groupingBy { it }.eachCount())}

    answers.forEachIndexed { index, mutableList ->
        val entry = mutableList.groupingBy { it }.eachCount().maxBy { it.value }!!.key
        if (entry == checkList[index].split("\t").last())
            positive++
        else {
            negative++
            println(checkList[index])
        }
    }

    println(
        "Wyników pozytywnych: $positive \nWyników negatywnych: $negative \nSkuteczność algorytmu: "
                + positive.toDouble() / (positive.toDouble() + negative.toDouble()) + "%"
    )


    var decision = sc.nextLine()

    while (decision != "N") {
        println("Do you want to write new vector? [Y/N]")
        decision = sc.nextLine()

        if (decision == "Y") {
            println("Write 4 attributes divided by space ")
            val values = sc.nextLine().split(' ')

            //obliczanie odległosci wektora od danych treningowych
            // i zapisanie ich w formie listy[wszystkie wyniki dla danego wiersza]

            var scoreList = mutableListOf<String>()

            trainingList.forEach {
                val tmp1 = it.split("\t")
                var score = 0.0
                values.forEachIndexed { index, s ->

                    score += Math.pow(tmp1[index].toDouble() - s.toDouble(), 2.0)
                }
                scoreList.add(Math.sqrt(score).toString())
                scoreList.add(tmp1.last())
            }

            var answerList = mutableListOf<String>()
            // wyliczenie najmniejszej odległości k razy

            x = k.toInt()
            while (x > 0) {
                var min = Double.MAX_VALUE
                var value = ""

                scoreList.forEachIndexed { i, s ->
                    if (s[0].isDigit() && s.toDouble() < min) {
                        min = s.toDouble()
                        value = scoreList[i + 1]
                    }
                }
                answerList.add(value)
                scoreList.remove(min.toString())
                x--
            }

            val entry = answerList.groupingBy { it }.eachCount().maxBy { it.value }!!.key

            println("Answer for $values: $entry")

        }

    }


}