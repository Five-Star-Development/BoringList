package dev.five_star.boringlist.model

val fakeData = mutableListOf(
    BoringItem(todo = "Read Book A"),
    BoringItem(todo = "Clean the Office"),
    BoringItem(todo = "Write a blog post", description = "a blog about how to be boring"),
    BoringItem(todo = "Fix the Table", description =  "once of the legs is broken"),
    BoringItem(todo = "Watch the documentation about Malcolm X", description =  "on Netflix"),
    BoringItem(todo = "Write another cool app!", description = "maybe something useful this time")
)

interface BoringRepository {

    suspend fun fetchBoringItems(): List<BoringItem>

    suspend fun getAllItems(): List<BoringItem>

    suspend fun addBoringItem(boringItem: BoringItem)
}

