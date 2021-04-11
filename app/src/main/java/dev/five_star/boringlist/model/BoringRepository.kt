package dev.five_star.boringlist.model

public val fakeData = mutableListOf(
    BoringItem("Read Book A"),
    BoringItem("Clean the Office"),
    BoringItem("Write a blog post", "a blog about how to be boring"),
    BoringItem("Fix the Table", "once of the legs is broken"),
    BoringItem("Watch the documentation about Malcolm X", "on Netflix"),
    BoringItem("Write another cool app!", "maybe something useful this time")
)

interface BoringRepository {

    suspend fun fetchBoringItems(): List<BoringItem>

}

