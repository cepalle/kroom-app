package io.kroom.app.view.activitymain.trackvoteevent.event
import io.kroom.app.view.activitymain.trackvoteevent.model.TrackVoteEvent

interface OnTrackVoteEventListener {
   fun OnsTrackVoteEventSelected(trackEventVote: TrackVoteEvent) = UInt

}