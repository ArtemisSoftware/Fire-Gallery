package com.artemissoftware.data.mappers

import com.artemissoftware.data.firebase.remoteconfig.RemoteConfigContants
import com.artemissoftware.data.firebase.remoteconfig.models.ChipColorRco
import com.artemissoftware.data.firebase.remoteconfig.models.SeasonDetailRco
import com.artemissoftware.data.firebase.remoteconfig.models.SeasonRco
import com.artemissoftware.data.firebase.remoteconfig.models.UserValidationRco
import com.artemissoftware.domain.models.configurations.ChipColorConfig
import com.artemissoftware.domain.models.configurations.SeasonConfig
import com.artemissoftware.domain.models.configurations.SeasonDetailConfig
import com.artemissoftware.domain.models.configurations.UserValidationConfig
import com.artemissoftware.domain.util.SeasonType
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RemoteConfigMapperTest {

    private lateinit var firebaseRemoteConfig: FirebaseRemoteConfig


    @Before
    fun setUp() {
        firebaseRemoteConfig = mock()
        getMockFirebaseRemoteConfig()
    }

    private fun getMockFirebaseRemoteConfig() {
        whenever(firebaseRemoteConfig.getString(RemoteConfigContants.SEASON_CONFIG)).thenReturn("{\n" +
                "  \"backgroundColor\": \"#003371\",\n" +
                "  \"chipColor\": {\n" +
                "    \"start\": \"#ee000e\",\n" +
                "    \"end\": \"#770007\",\n" +
                "    \"icon\": \"#9339ff\"\n" +
                "  },\n" +
                "  \"seasonMessage\": \"Winter galleries are are here\",\n" +
                "  \"spring\": {\n" +
                "    \"chipColor\": {\n" +
                "      \"start\": \"#f6b9ad\",\n" +
                "      \"end\": \"#ee6f68\",\n" +
                "      \"icon\": \"#5e8d5a\"\n" +
                "    },\n" +
                "    \"defaultColor\": \"#c6d7b9\"\n" +
                "  },\n" +
                "  \"summer\": {\n" +
                "    \"chipColor\": {\n" +
                "      \"start\": \"#eae374\",\n" +
                "      \"end\": \"#f9d62e\",\n" +
                "      \"icon\": \"#ff4e50\"\n" +
                "    },\n" +
                "    \"defaultColor\": \"#fc913a\"\n" +
                "  },\n" +
                "  \"autumn\": {\n" +
                "    \"chipColor\": {\n" +
                "      \"start\": \"#f79762\",\n" +
                "      \"end\": \"#f47b20\",\n" +
                "      \"icon\": \"#ffd200\"\n" +
                "    },\n" +
                "    \"defaultColor\": \"#9c5708\"\n" +
                "  },\n" +
                "  \"winter\": {\n" +
                "    \"chipColor\": {\n" +
                "      \"start\": \"#230745\",\n" +
                "      \"end\": \"#e3e3ff\",\n" +
                "      \"icon\": \"#020b36\"\n" +
                "    },\n" +
                "    \"defaultColor\": \"#b3e3f4\"\n" +
                "  }\n" +
                "}")

        whenever(firebaseRemoteConfig.getString(RemoteConfigContants.VALIDATION_CONFIG)).thenReturn("{\n" +
                "  \"passwordMaxLength\": 8,\n" +
                "  \"passwordMinLength\": 3,\n" +
                "  \"usernameMaxLength\": 12,\n" +
                "  \"usernameMinLength\": 8,\n" +
                "  \"usernameRegex\": \"^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*\$\",\n" +
                "  \"emailRegex\": \"^[A-Za-z](.*)([@]{1})(.{1,})(\\\\.)(.{1,})\"\n" +
                "}")
    }


    @Test
    fun `map FirebaseRemoteConfig to SeasonFrc`() {

        val seasonRco = SeasonRco(
            backgroundColor = "#003371",
            chipColor =  ChipColorRco(
                start = "#ee000e",
                end = "#770007",
                icon = "#9339ff"
            ),
            seasonMessage = "Winter galleries are are here",
            spring = SeasonDetailRco(
                ChipColorRco(
                    start  ="#f6b9ad",
                    end  ="#ee6f68",
                    icon  ="#5e8d5a"
                )
            ),
            summer = SeasonDetailRco(
                ChipColorRco(
                    start  ="#eae374",
                    end  ="#f9d62e",
                    icon  ="#ff4e50"
                )
            ),
            autumn = SeasonDetailRco(
                ChipColorRco(
                    start  ="#f79762",
                    end  ="#f47b20",
                    icon  ="#ffd200"
                )
            ),
            winter = SeasonDetailRco(
                ChipColorRco(
                    start  ="#230745",
                    end  ="#e3e3ff",
                    icon  ="#020b36"
                )
            ),
        )

        Assert.assertEquals(seasonRco, firebaseRemoteConfig.toSeasonConfig())
    }

    @Test
    fun `map FirebaseRemoteConfig to UserValidationRco`() {

        val userValidationRco = UserValidationRco(
            emailRegex ="^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})",
            usernameRegex ="^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*$",
            passwordMaxLength = 8,
            passwordMinLength = 3,
            usernameMaxLength = 12,
            usernameMinLength = 8
        )

        Assert.assertEquals(userValidationRco, firebaseRemoteConfig.toUserValidationConfig())
    }

    @Test
    fun `map SeasonDetailFrc to SeasonDetailConfig`() {

        val seasonDetailRco = SeasonDetailRco(
            ChipColorRco(
                start  ="#ee000e",
                end  ="#e3e3ff",
                icon  ="#020b36"
            )
        )

        val seasonDetailConfig = SeasonDetailConfig(
            ChipColorConfig(
                startBorderColor = "#ee000e",
                endBorderColor = "#e3e3ff",
                iconColor = "#020b36"
            )
        )

        Assert.assertEquals(seasonDetailConfig, seasonDetailRco.toSeasonDetailConfig())
    }

    @Test
    fun `map SeasonRco to SeasonConfig`() {

        val seasonRco = getSeasonRco()

        val seasonConfig = SeasonConfig(
            chipColor = ChipColorConfig(
                startBorderColor = "#ee000e",
                endBorderColor = "#770007",
                iconColor = "#9339ff"
            )
        )

        Assert.assertEquals(seasonConfig, seasonRco.toSeasonConfig())
    }

    @Test
    fun `map SeasonRco to SeasonDetailConfig`() {

        val seasonRco = getSeasonRco()

        val springSeasonDetailConfig = SeasonDetailConfig(
            chipColorConfig = ChipColorConfig(
                startBorderColor = "#f6b9ad",
                endBorderColor = "#ee6f68",
                iconColor = "#5e8d5a"
            )
        )

        val summerSeasonDetailConfig = SeasonDetailConfig(
            chipColorConfig = ChipColorConfig(
                startBorderColor = "#eae374",
                endBorderColor = "#f9d62e",
                iconColor = "#ff4e50"
            )
        )

        val autumnSeasonDetailConfig = SeasonDetailConfig(
            chipColorConfig = ChipColorConfig(
                startBorderColor = "#f79762",
                endBorderColor = "#f47b20",
                iconColor = "#ffd200"
            )
        )

        val winterSeasonDetailConfig = SeasonDetailConfig(
            chipColorConfig = ChipColorConfig(
                startBorderColor = "#230745",
                endBorderColor = "#e3e3ff",
                iconColor = "#020b36"
            )
        )

        Assert.assertEquals(springSeasonDetailConfig, seasonRco.toSeasonDetailConfig(SeasonType.SPRING))
        Assert.assertEquals(summerSeasonDetailConfig, seasonRco.toSeasonDetailConfig(SeasonType.SUMMER))
        Assert.assertEquals(autumnSeasonDetailConfig, seasonRco.toSeasonDetailConfig(SeasonType.AUTUMN))
        Assert.assertEquals(winterSeasonDetailConfig, seasonRco.toSeasonDetailConfig(SeasonType.WINTER))
    }

    @Test
    fun `map ChipColorRco to ChipColorConfig`() {

        val chipColorConfig = ChipColorConfig(
            startBorderColor = "#ee000e",
            endBorderColor = "#770007",
            iconColor = "#9339ff"
        )

        val chipColorRco = ChipColorRco(
            start = "#ee000e",
            end = "#770007",
            icon = "#9339ff"
        )

        Assert.assertEquals(chipColorConfig, chipColorRco.toChipColorConfig())
    }

    @Test
    fun `map UserValidationRco to UserValidationConfig`() {

        val userValidationRco = UserValidationRco(
            emailRegex ="^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})",
            usernameRegex ="^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*$",
            passwordMaxLength = 8,
            passwordMinLength = 3,
            usernameMaxLength = 12,
            usernameMinLength = 8
        )

        val userValidationConfig = UserValidationConfig(
            emailRegex ="^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})",
            usernameRegex ="^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*$",
            passwordMaxLength = 8,
            passwordMinLength = 3,
            usernameMaxLength = 12,
            usernameMinLength = 8
        )

        Assert.assertEquals(userValidationConfig, userValidationRco.toUserValidationConfig())
    }

    private fun getSeasonRco() = SeasonRco(
        backgroundColor = "#003371",
        chipColor =  ChipColorRco(
            start = "#ee000e",
            end = "#770007",
            icon = "#9339ff"
        ),
        seasonMessage = "Winter galleries are are here",
        spring = SeasonDetailRco(
            ChipColorRco(
                start  ="#f6b9ad",
                end  ="#ee6f68",
                icon  ="#5e8d5a"
            )
        ),
        summer = SeasonDetailRco(
            ChipColorRco(
                start  ="#eae374",
                end  ="#f9d62e",
                icon  ="#ff4e50"
            )
        ),
        autumn = SeasonDetailRco(
            ChipColorRco(
                start  ="#f79762",
                end  ="#f47b20",
                icon  ="#ffd200"
            )
        ),
        winter = SeasonDetailRco(
            ChipColorRco(
                start  ="#230745",
                end  ="#e3e3ff",
                icon  ="#020b36"
            )
        ),
    )
}