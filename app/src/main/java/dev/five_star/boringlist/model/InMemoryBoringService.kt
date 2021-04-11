package dev.five_star.boringlist.model

class InMemoryBoringService : BoringRepository {

    override suspend fun fetchBoringItems(): List<BoringItem> {
        return fakeData
    }


}