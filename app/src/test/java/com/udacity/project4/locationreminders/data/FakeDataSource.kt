package com.udacity.project4.locationreminders.data

import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result

//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource(var reminders: MutableList<ReminderDTO>? = mutableListOf()) : ReminderDataSource {

//    TODO: Create a fake data source to act as a double to the real data source
    private var shouldReturnError = false

    fun setShouldReturnError(shouldReturn: Boolean) {
        this.shouldReturnError = shouldReturn
    }


    override suspend fun getReminders(): Result<List<ReminderDTO>> {
        TODO("Return the reminders")
        reminders?.let { return Result.Success(ArrayList(it)) }
        return Error(
                Exception("Reminders not found")
        )
    }

    override suspend fun saveReminder(reminder: ReminderDTO) {
        TODO("save the reminder")
        reminders?.add(reminder)
    }

    override suspend fun getReminder(id: String): Result<ReminderDTO> {
        TODO("return the reminder with the id")

    }

    override suspend fun deleteAllReminders() {
        TODO("delete all the reminders")
        reminders?.clear()
    }
}