package com.example.softwareengineeringchallengesapp

import android.content.Context
import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiObjectNotFoundException
import androidx.test.uiautomator.UiSelector
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityUITest {
    private var device: UiDevice? = null
    private var context: Context? = null

    @Before
    fun setUp() {
        // Initialize the UiDevice and context
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        context = InstrumentationRegistry.getInstrumentation().getContext()
    }

    @Test
    @kotlin.Throws(UiObjectNotFoundException::class)
    fun testExplicitIntentAndChallengeDisplay() {
        // Launch the app from the home screen
        launchApp()

        // Find and click the "Start Activity Explicitly" button
        val explicitActivityButton: UiObject = device.findObject(
            UiSelector()
                .resourceId(PACKAGE_NAME + ":id/explicit_intent_button")
                .className("android.widget.Button")
        )

        assertTrue("Explicit Intent Button not found", explicitActivityButton.exists())
        explicitActivityButton.click()

        // Wait for the new activity to load
        device.wait(
            android.support.test.uiautomator.UiDevice.findObject(
                UiSelector().packageName(PACKAGE_NAME)
            ),
            LAUNCH_TIMEOUT
        )

        // Check for the presence of one of the mobile software engineering challenges
        val challengeTextView: UiObject = device.findObject(
            UiSelector()
                .resourceId(PACKAGE_NAME + ":id/challenge_text_view")
                .className("android.widget.TextView")
        )

        assertTrue("Challenge text view not found", challengeTextView.exists())


        // List of expected challenges (match these with your actual challenges)
        val expectedChallenges: Array<String> = arrayOf(
            "Fragmentation",
            "Battery Constraints",
            "Performance Optimization",
            "Security Challenges",
            "Offline Functionality",
            "Screen Size Diversity"
        )

        // Check if the displayed text matches one of the expected challenges
        val displayedChallenge: String = challengeTextView.getText()
        var challengeFound: Boolean = false

        for (challenge: String? in expectedChallenges) {
            if (displayedChallenge.contains(challenge)) {
                challengeFound = true
                break
            }
        }

        assertTrue("No valid mobile software engineering challenge found", challengeFound)
    }

    @kotlin.Throws(UiObjectNotFoundException::class)
    private fun launchApp() {
        // Press the home button to ensure we start from the home screen
        device.pressHome()

        // Wait for launcher
        val launcherPackage: String = device.getLauncherPackageName()
        assertNotNull("Launcher package is null", launcherPackage)

        // Wait for home screen
        device.wait(
            android.support.test.uiautomator.UiDevice.findObject(
                UiSelector().packageName(launcherPackage)
            ), LAUNCH_TIMEOUT
        )

        // Find the app icon and launch it
        val appIcon: UiObject = device.findObject(
            UiSelector()
                .description("Software Engineering Challenges")
                .className("android.widget.TextView")
        )

        if (appIcon.waitForExists(LAUNCH_TIMEOUT)) {
            appIcon.clickAndWaitForNewWindow()
        } else {
            // Alternative method: launch via package name if icon not found
            val launchIntent: Intent = context.getPackageManager()
                .getLaunchIntentForPackage(PACKAGE_NAME)
            assertNotNull("Launch intent is null", launchIntent)
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(launchIntent)
        }

        // Wait for the app to launch
        device.wait(
            android.support.test.uiautomator.UiDevice.findObject(
                UiSelector().packageName(PACKAGE_NAME)
            ), LAUNCH_TIMEOUT
        )
    }

    companion object {
        private const val PACKAGE_NAME: String = "com.example.softwareengineeringchallengesapp"
        private const val LAUNCH_TIMEOUT: Int = 5000
        private const val UI_INTERACTION_TIMEOUT: Int = 3000
    }
}