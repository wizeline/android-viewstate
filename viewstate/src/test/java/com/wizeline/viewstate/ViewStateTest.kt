package com.wizeline.viewstate

import android.content.Context
import android.os.Build
import android.view.View
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.android.synthetic.main.view_state.view.*
import kotlinx.android.synthetic.main.view_state_empty.view.*
import kotlinx.android.synthetic.main.view_state_error.view.*
import kotlinx.android.synthetic.main.view_state_loading.view.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class ViewStateTest {
    private lateinit var viewState: ViewState

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        viewState = ViewState(context)
    }

    @Test
    fun setLoadingTitleText() {
        val title = "the loading title"

        viewState.setLoadingTitleText(title)

        assertEquals(title, viewState.loadingTitle.text)
    }

    @Test
    fun setEmptyImageResource() {
        val imageResource = R.drawable.ic_list

        viewState.setEmptyImageResource(imageResource)

        assertEquals(
            imageResource,
            Shadows.shadowOf(viewState.emptyImage.drawable).createdFromResId
        )
    }

    @Test
    fun setEmptyTitleText() {
        val title = "the empty title"

        viewState.setEmptyTitleText(title)

        assertEquals(title, viewState.emptyTitle.text)
    }

    @Test
    fun setEmptyDescriptionText() {
        val description = "empty description"

        viewState.setEmptyDescriptionText(description)

        assertEquals(description, viewState.emptyDescription.text)
    }

    @Test
    fun setErrorImageResource() {
        val imageResource = R.drawable.ic_list

        viewState.setErrorImageResource(imageResource)

        assertEquals(
            imageResource,
            Shadows.shadowOf(viewState.errorImage.drawable).createdFromResId
        )
    }

    @Test
    fun setErrorTitleText() {
        val title = "error title"

        viewState.setErrorTitleText(title)

        assertEquals(title, viewState.errorTitle.text)
    }

    @Test
    fun setErrorDescriptionText() {
        val description = "error description"

        viewState.setErrorDescriptionText(description)

        assertEquals(description, viewState.errorDescription.text)
    }

    @Test
    fun setErrorRetryText() {
        val text = "error retry text"

        viewState.setErrorRetryText(text)

        assertEquals(text, viewState.errorRetry.text)
    }

    @Test
    fun setOnRetryClickListener() {
        var listenerIsCalled = false
        val listener = { listenerIsCalled = true }

        viewState.setOnRetryClickListener(listener)
        viewState.errorRetry.performClick()

        assertTrue(listenerIsCalled)
    }

    @Test
    fun `setState for loading`() {
        viewState.setState(State.LOADING)

        assertEquals(View.VISIBLE, viewState.viewLoading.visibility)
        assertEquals(View.GONE, viewState.viewEmpty.visibility)
        assertEquals(View.GONE, viewState.viewError.visibility)
        assertEquals(0, viewState.displayedChild)
    }

    @Test
    fun `setState for content`() {
        viewState.setState(State.CONTENT)

        assertEquals(View.GONE, viewState.viewLoading.visibility)
        assertEquals(View.GONE, viewState.viewEmpty.visibility)
        assertEquals(View.GONE, viewState.viewError.visibility)
        assertEquals(1, viewState.displayedChild)
    }

    @Test
    fun `setState for empty`() {
        viewState.setState(State.EMPTY)

        assertEquals(View.VISIBLE, viewState.viewEmpty.visibility)
        assertEquals(View.GONE, viewState.viewLoading.visibility)
        assertEquals(View.GONE, viewState.viewError.visibility)
        assertEquals(0, viewState.displayedChild)
    }

    @Test
    fun `setState for error`() {
        viewState.setState(State.ERROR)

        assertEquals(View.VISIBLE, viewState.viewError.visibility)
        assertEquals(View.GONE, viewState.viewLoading.visibility)
        assertEquals(View.GONE, viewState.viewEmpty.visibility)
        assertEquals(0, viewState.displayedChild)
    }
}