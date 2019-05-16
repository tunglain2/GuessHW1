package com.tunglain.guesswh1

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MaterialActivityTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<MaterialActivity>(MaterialActivity::class.java)

    @Test
    fun guessWrong(){
        val secretNumber = activityTestRule.activity.secretNumber.secret
        val resource = activityTestRule.activity.resources
//        val n = 5
        for (n in 1..10) {
            if (n != secretNumber) {
                onView(withId(R.id.ed_number)).perform(clearText())
                onView(withId(R.id.ed_number)).perform(closeSoftKeyboard())
                onView(withId(R.id.ed_number)).perform(typeText(n.toString()))
                onView(withId(R.id.ok_button)).perform(click())
                val message = if (n < secretNumber) resource.getString(R.string.bigger)
                else resource.getString(R.string.smaller)
                onView(withText(message)).check(ViewAssertions.matches(isDisplayed()))
                onView(withText(resource.getString(R.string.ok))).perform(click())
            }
        }
    }

    @Test
    fun replay() {
        val secretNumber = activityTestRule.activity.secretNumber
        val resource = activityTestRule.activity.resources
        onView(withId(R.id.fab)).perform(click())
        onView(withText(resource.getString(R.string.ok))).perform(click())
        check(secretNumber.count == 0)
    }
}