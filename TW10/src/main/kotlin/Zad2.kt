import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Producent i konsumer  oraz procesy przetwarzające
// N - liczba przetwarzaczy;

//[
// PRODUCER::
// p: porcja;
//*[  true -> produkuj(p); PRZETWARZACZ(0)!p  ]

//||PRZETWARZACZ(i:O..N-l)::
// p: porcja;
//*[
// true -> [i = 0 -> PRODUCER?p []i <> 0 -> PRZETWARZACZ(i-l)?p];
//[i = N-l -> CONSUMER!p []i <> N-l -> PRZETWARZACZ(i+l)!p]
//]

//|| CONSUMER::
// p: porcja;
//*[PRZETWARZACZ(N-l)?p -> konsumuj(p)]
//]

fun main() {
    val n = 10
    val channels = ArrayList<Channel<Int>>()
    for (num in 0..n) {
        channels.add(Channel())
    }
    runBlocking {
        // czyli 10 odpalonych przetwarzaczy, z czego 9 ma i nadawace i odbiorcę,
        // nadawcą pierwszego jest producer a konsumentem ostatniego consumer
        for (num in 0..<n) {
            launch { medium(channels[num], channels[num+1], num) }
        }
        launch { produce(channels[0]) }
        launch{consume(channels[n]) }
    }
}

suspend fun produce(broker: Channel<Int>) {
    for (num in 1..10) {
        delay(1000)
        val value = (0..10).random()
        broker.send(value)
        println("Value $value produced (sent)")
    }
}

suspend fun medium(from: Channel<Int>, to: Channel<Int>, i: Int) {
    for (num in 1..10) {
        val value = from.receive()
        to.send(value)
        println("Value $value mediumed by $i ")
    }
}

suspend fun consume(broker: Channel<Int>) {
    for (num in 1..10) {
        println("Value ${broker.receive()} received")
    }
}
