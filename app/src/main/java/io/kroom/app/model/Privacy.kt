package io.kroom.app.model

data class Privacy (val email: PrivacyEnum,
                    val location: PrivacyEnum,
                    val friends: PrivacyEnum,
                    val musicalPreferencesGenre: PrivacyEnum)

