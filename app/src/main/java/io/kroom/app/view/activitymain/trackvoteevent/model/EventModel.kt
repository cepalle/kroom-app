package io.kroom.app.view.activitymain.trackvoteevent.model


data class EventModel constructor(
    val id: Int,
    val userMasterId: Int,
    val userMasterName: String,
    val name: String,
    val public: Boolean,
    val scheduleBegin: Long,
    val scheduleEnd: Long,
    val latitude: Float,
    val longitude:Float
)