import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.selects.select

// Producent i konsument  z wybranym pośrednikiem
// N - liczba posredników

//[

// PRODUCER::
// p: porcja;
//*[   true -> produkuj(p); [(i:0..N-1) POSREDNIK(i)?JESZCZE() -> POSREDNIK(i)!p]      ]

//||POSREDNIK(i:0..N-1)::
// p: porcja;
//*[   true -> PRODUCER!JESZCZE() ; [PRODUCER?p -> CONSUMER!p]    ]

//||CONSUMER::
// p: porcja;
//*[     (i:0..N-1) POSREDNIK(i)?p -> konsumuj(p)      ]

//]

fun main() {
    val n = 10
    val prodChannels = ArrayList<Channel<Int>>()
    val consChannels = ArrayList<Channel<Int>>()
    for (num in 1..n) {
        prodChannels.add(Channel())
        consChannels.add(Channel())
    }
    runBlocking {
        for (num in 0..<n) {
            launch { broke(prodChannels[num], consChannels[num]) }
        }
        launch { producer(prodChannels) }
        launch { consume(consChannels) }
    }
}

suspend fun producer(brokers: ArrayList<Channel<Int>>) {
    for (num in 1..10) {
        delay(1000)
        val value = (0..10).random();
        select {
            brokers.forEach {
                sendChannel -> sendChannel.onSend(value){ println("Value $value produced (sent)") }}
        }
    }
}

suspend fun consume(brokers: ArrayList<Channel<Int>>) {
    for (num in 1..10) {
        select {
            brokers.forEach { receiveChannel -> receiveChannel.onReceive { value -> println("Value $value received") } }
        }
    }
}

suspend fun broke(fromProducer: Channel<Int>, toConsumer: Channel<Int>) {
    while (true) {
        val portion = fromProducer.receive()
        println("Value $portion brokered")
        toConsumer.send(portion)
    }
}
