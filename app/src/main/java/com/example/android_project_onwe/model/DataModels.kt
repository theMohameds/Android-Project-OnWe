package com.example.android_project_onwe.model

import java.util.*

/**
 * Represents a single user (member) in a group or receipt.
 */
data class User(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val isOwed: Boolean = false,
    val owedAmount: Double = 0.0

)

/**
 * Represents a single group that holds members and receipts.
 */
data class Group(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val members: List<String>,
    val totalBalance: Double = 0.0,
    val userOwes: Double = 0.0,
    val userIsOwed: Double = 0.0
)

/**
 * Represents a single receipt or expense.
 */
data class Receipt(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val expenseDetails: String,
    val amount: Double,
    val paidById: String,
    val splitMemberIds: List<String>,
    val splitAmountPerPerson: Double,
    val place: String,
    val date: Date = Date(), // Use java.util.Date instead of LocalDate
    val receiptImageUri: String? = null
)

/**
 * Dummy data for previewing the UI screens.
 */
object DummyData {
    val john = User(name = "John", owedAmount = 567.89)
    val alice = User(name = "Alice", owedAmount = 567.89)
    val bob = User(name = "Bob", isOwed = true, owedAmount = 75.25)
    val carol = User(name = "Carol", isOwed = true, owedAmount = 75.25)

    val expenseGroup = Group(
        name = "Oslo Trip 2024",
        members = listOf(john.id, alice.id, bob.id, carol.id),
        totalBalance = 5133.75,
        userOwes = 75.25,
        userIsOwed = 0.0
    )

    val sampleReceipt = Receipt(
        title = "Groceries",
        expenseDetails = "Paid for dinner ingredients",
        amount = 567.89,
        paidById = john.id,
        splitMemberIds = listOf(john.id, alice.id),
        splitAmountPerPerson = 283.94,
        place = "Netto"
    )

    val usersMap = mapOf(
        john.id to john,
        alice.id to alice,
        bob.id to bob,
        carol.id to carol
    )
}