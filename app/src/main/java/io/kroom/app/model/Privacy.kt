package io.kroom.app.model

class Privacy (val email: PrivacyEnum){


        location: PrivacyEnum!
        friends: PrivacyEnum!
        musicalPreferencesGenre: PrivacyEnum!
    }*/
}

enum class PrivacyEnum {
    PUBLIC,
    FRIENDS,
    PRIVATE;
}