package com.igdev.secondhand.ui


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.igdev.secondhand.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        Thread.sleep(5000)
        val appCompatImageView = onView(
            allOf(
                withId(R.id.ic_next),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

        Thread.sleep(5000)
        val appCompatImageView2 = onView(
            allOf(
                withId(R.id.next2),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageView2.perform(click())

        Thread.sleep(5000)
        val appCompatImageView3 = onView(
            allOf(
                withId(R.id.next3),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageView3.perform(click())

        Thread.sleep(5000)
        val appCompatImageView4 = onView(
            allOf(
                withId(R.id.finish),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageView4.perform(click())

        Thread.sleep(5000)
        val materialTextView = onView(
            allOf(
                withId(R.id.btn_masuk), withText("Daftar atau Masuk"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragmentContainerView),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        Thread.sleep(5000)
        val textInputEditText = onView(
            allOf(
                withId(R.id.et_email),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.ti_user_name_layout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("johndoe@mail.com"), closeSoftKeyboard())

        Thread.sleep(5000)
        val textInputEditText2 = onView(
            allOf(
                withId(R.id.et_password),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.ti_user_password_layout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("123456"), closeSoftKeyboard())

        Thread.sleep(5000)
        val materialTextView2 = onView(
            allOf(
                withId(R.id.btn_login), withText("Masuk"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragmentContainerView),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        materialTextView2.perform(click())

        Thread.sleep(5000)
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.notificationFragment), withContentDescription("Notification"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.menuNavigation),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        Thread.sleep(5000)
        val bottomNavigationItemView2 = onView(
            allOf(
                withId(R.id.homeFragment), withContentDescription("Home"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.menuNavigation),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView2.perform(click())

        Thread.sleep(5000)
        val bottomNavigationItemView3 = onView(
            allOf(
                withId(R.id.notificationFragment), withContentDescription("Notification"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.menuNavigation),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView3.perform(click())

        Thread.sleep(5000)
        val bottomNavigationItemView4 = onView(
            allOf(
                withId(R.id.sellFragment), withContentDescription("Sell"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.menuNavigation),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView4.perform(click())

        Thread.sleep(5000)
        val bottomNavigationItemView5 = onView(
            allOf(
                withId(R.id.transactionFragment), withContentDescription("Transaction"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.menuNavigation),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView5.perform(click())

        Thread.sleep(5000)
        val bottomNavigationItemView6 = onView(
            allOf(
                withId(R.id.accountFragment), withContentDescription("Account"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.menuNavigation),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView6.perform(click())

        Thread.sleep(5000)
        val view = onView(
            allOf(
                withId(R.id.bg_section_logout), withContentDescription("background"),
                childAtPosition(
                    allOf(
                        withId(R.id.menuContainer),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            4
                        )
                    ),
                    12
                ),
                isDisplayed()
            )
        )
        view.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
