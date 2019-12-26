package parking

import java.util.*
import kotlin.system.exitProcess

const val PARK_LOT_SIZE = 20

fun main(args: Array<String>) {
    println("White car has parked.")
    println("Yellow car left the parking lot.")
    println("Green car just parked here.")

    var parkingLot: Array<String?>? = null
    val scanner = Scanner(System.`in`)

    val command = scanner.next()
    when (command) {
        "create" -> {
            val size = scanner.nextInt()

            check(size > 0)

            parkingLot = createParkingLot(size)
        }
        "status" -> {
            checkParkingLotExists(parkingLot)

            printStatus(parkingLot!!)
        }
        "leave" -> {
            val spot = scanner.nextInt()

            check(spot > 0)
            checkParkingLotExists(parkingLot)

            pickUpCar(parkingLot!!, spot)
        }
        "park" -> {
            val plate = scanner.next()
            val color = scanner.next()

            check(!plate.isNullOrEmpty())
            check(!color.isNullOrEmpty())
            checkParkingLotExists(parkingLot)

            parkCar(parkingLot!!, plate.toUpperCase(), color)
        }
        "reg_by_color" -> {
            val color = scanner.next()

            check(!color.isNullOrEmpty())
            checkParkingLotExists(parkingLot)

            queryPlatesByColor(parkingLot!!, color.toUpperCase())
        }
        "spot_by_color" -> {
            val color = scanner.next()

            check(!color.isNullOrEmpty())
            checkParkingLotExists(parkingLot)

            querySpotsByColor(parkingLot!!, color.toUpperCase())
        }
        "spot_by_plate" -> {
            val plate = scanner.next()

            check(!plate.isNullOrEmpty())
            checkParkingLotExists(parkingLot)

            querySpotsByPlate(parkingLot!!, plate.toUpperCase())
        }
        "exit" -> {
            exitProcess(0)
        }
        else -> {
            println("Invalid command")
        }
    }
}

fun querySpotsByPlate(parkingLot: Array<String?>, plate: String) {
    val carsByColor = parkingLot.filter {
        it!!.split(" ").getOrNull(0).equals(plate)
    }
    if (carsByColor.isEmpty()) {
        println("No cars with registration number ${plate.toUpperCase()} were found.")
        return
    }
    val spots = carsByColor.mapIndexed { index: Int, car: String? -> index }
    println(spots.joinToString())
}

fun querySpotsByColor(parkingLot: Array<String?>, color: String) {
    val carsByColor = parkingLot.filter {
        it!!.split(" ").getOrNull(1).equals(color)
    }
    if (carsByColor.isEmpty()) {
        println("No cars with color ${color.toUpperCase()} were found.")
        return
    }
    val spots = carsByColor.mapIndexed { index: Int, car: String? -> index }
    println(spots.joinToString())
}

fun queryPlatesByColor(parkingLot: Array<String?>, color: String) {
    val carsByColor = parkingLot.filter {
        it!!.split(" ").getOrNull(1).equals(color)
    }
    if (carsByColor.isEmpty()) {
        println("No cars with color ${color.toUpperCase()} were found.")
        return
    }
    println(carsByColor.joinToString { it!!.split(" ").get(0) })
}

fun printStatus(parkingLot: Array<String?>) {
    if (parkingLot.filterNotNull().isEmpty()) {
        println("Parking lot is empty.")
        return
    }
    parkingLot.mapIndexedNotNull { index: Int, car: String? ->
        val (plate, color) = car!!.split(" ")
        println("$index $plate $color")
    }
}

fun checkParkingLotExists(parkingLot: Array<String?>?): Boolean {
    if (parkingLot.isNullOrEmpty()) {
        println("Sorry, parking lot is not created.")
        return false
    }
    return true
}

fun createParkingLot(size: Int): Array<String?> {
    println("Created a parking lot with $size spots.")
    return arrayOfNulls(size)
}

fun parkCar(parkingLot: Array<String?>, plate: String, color: String) {
    val freeSpot = parkingLot.indexOfFirst { it.isNullOrBlank() }
    if (freeSpot == -1) {
        println("Sorry, parking lot is full..")
        return
    }
    parkingLot[freeSpot] = "$plate $color"
    println("$color car parked on spot ${freeSpot + 1}")
}

fun pickUpCar(parkingLot: Array<String?>, spot: Int) {
    if (parkingLot[spot].isNullOrEmpty()) {
        println("There is no car in the spot ${spot + 1}.")
        return
    }
    parkingLot[spot] = null
    println("Spot ${spot + 1} is free.")
}
