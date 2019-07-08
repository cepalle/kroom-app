package io.kroom.app.view.activitymain.model

import io.kroom.app.graphql.TrackVoteEventAddOrUpdateVoteMutation

data class TrackVoteEvent(
    val id: Int,
    val userMaster: User,
    val name: String,
    val public: Boolean,
    val locAndSchRestriction: Boolean,
    val currentTrack: Track,
    val trackWithVote: TrackVoteEventAddOrUpdateVoteMutation.TrackWithVote,
    val scheduleBegin: Long,
    val scheduleEnd: Long,
    val latitude: Float,
    val longitude: Float,
    val userInvited: User
)