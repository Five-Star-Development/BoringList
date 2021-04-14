package dev.five_star.boringlist.model

class InMemoryBoringService(private val boringDao: BoringDao) : BoringRepository {

    override suspend fun fetchBoringItems(): List<BoringItem> {
        return fakeData
    }

    override suspend fun getAllItems(): List<BoringItem> {
        return boringDao.getAll()
    }

    override suspend fun addBoringItem(boringItem: BoringItem) {
        boringDao.addBoringItem(boringItem)
    }


}