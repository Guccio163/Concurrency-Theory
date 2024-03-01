//
//import kotlinx.coroutines.*
//import kotlinx.coroutines.channels.Channel
//import kotlinx.coroutines.channels.ReceiveChannel
//import kotlinx.coroutines.channels.SendChannel
//import kotlinx.coroutines.channels.produce
//import kotlinx.coroutines.selects.select
//
//const val providersCount = 10
//const val maxRepeats = 20
//
//suspend fun main(args: Array<String>) {
//    /// N ///
//    val producerChannels = List(providersCount) { Channel<Int>() }
//    val consumerChannels = List(providersCount) { Channel<Int>() }
//
//    runBlocking {
//        launch {
//            producer(producerChannels)
//        }
//
//        repeat(providersCount) {
//                id -> launch {
//            provider(id, producerChannels, consumerChannels)
//        }
//        }
//
//
//        launch {
//            consumer(consumerChannels)
//        }
//    }
//}
//
//@OptIn(ExperimentalCoroutinesApi::class)
//fun CoroutineScope.producer(
//    producerChannels: List<SendChannel<Int>>
//) = produce<Int> {
//    repeat(maxRepeats) {
//        val randomId = (producerChannels.indices).random()
//        val channel = producerChannels[randomId]
//
//        val randomValue = (1..100).random()
//
////        select<Unit> {
////            channel.onSend(randomValue) {
////                println("Producer sent $randomValue to provider $randomId.")
////            }
////        }
//
//        select <Unit> {
//            producerChannels.forEach {
//                    channel -> channel.onSend(randomValue) {
//                println("Producer sent $randomValue to provider $randomId.")
//            }
//            }
//        }
//
//        delay(1000)
//    }
//
//    for (channel in producerChannels) {
//        channel.close()
//    }
//}
//
//@OptIn(ExperimentalCoroutinesApi::class)
//fun CoroutineScope.provider(
//    providerId: Int,
//    producerChannels: List<ReceiveChannel<Int>>,
//    consumerChannels: List<SendChannel<Int>>
//) = produce<Int>{
//
//    for (value in producerChannels[providerId]) {
//        println("Provider received $value")
//        delay(1000)
//        consumerChannels[providerId].send(value)
//    }
//
//    for (channel in consumerChannels)
//        channel.close()
//
//}
//
//suspend fun consumer(
//    consumerChannels: List<ReceiveChannel<Int>>
//){
//    repeat(maxRepeats) {
//        select<Unit> {
//            consumerChannels.forEach{
//                    channel -> channel.onReceive {
//                    value -> println("Consumer received $value")
//            }
//            }
//        }
//    }
//}
