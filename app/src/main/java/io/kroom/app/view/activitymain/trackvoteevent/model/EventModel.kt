package io.kroom.app.view.activitymain.trackvoteevent.model


data class EventModel @JvmOverloads constructor(
    val id: Int,
    val userMasterName: String,
    val name: String,
    val public: Boolean,
    val scheduleBegin: Long,
    val scheduleEnd: Long,
    val latitude: Float?,
    val longitude: Float?
) {
    constructor(id: Int, userMasterName: String, name : String, public : Boolean, scheduleBegin : Long, scheduleEnd : Long, latitude : Float, longitude : Float)
            : this(id, "", "", false, -1, -1, null, null)



}