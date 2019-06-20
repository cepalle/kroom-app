package io.kroom.app.model

import io.kroom.app.graphql.TrackVoteEventAddOrUpdateVoteMutation

 class TrackVoteEvent (
    val id: Int,
    val userMaster: User,
    val name: String,
    val public: Boolean,
    val currentTrack: Track,
    val trackWithVote: TrackVoteEventAddOrUpdateVoteMutation.TrackWithVote,
    val shedule: String,
    val location: String,
    val userInvited: User
)