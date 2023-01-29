package com.tylllenka.data

import com.tylllenka.data.models.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class MongodbUserDataSource(
    db: CoroutineDatabase
): UserDataSource {

    private val users = db.getCollection<User>()
    override suspend fun getUserByUsername(username: String): User? =
        users.findOne(User::username eq username)

    override suspend fun insertUser(user: User): Boolean =
        users.insertOne(user).wasAcknowledged()

}