package io.kroom.app.view.activitymain.trackvoteevent.model

import io.kroom.app.graphql.TrackVoteEventByIdQuery


data class TrackVoteEvent(
    val id: Int,
    val userMaster: String,
    val name: String,
    val public: Boolean,
  //  val locAndSchRestriction: Boolean,
    val currentTrack: TrackVoteEventByIdQuery.Track?,
//    val currentTrackTitle: String,
    val trackWithVote:List<TrackVoteEventByIdQuery.TrackWithVote>,
   /* val trackWithVoteTrackId: Int,
    val trackWithVoteTrackTitle: String,
    val trackWithVoteTrackArtisteId: Int,
    val trackWithVoteTracktArtisteName: String,*/
    val scheduleBegin: Long,
    val scheduleEnd: Long,
    val latitude: Float,
    val longitude: Float,
    val  userInvited : List<TrackVoteEventByIdQuery.UserInvited>
 //   val userInvitedId: Int,
 //   val userInvitedUserName: String,
  //  val userInvitedEmail: String
)
