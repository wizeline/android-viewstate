package com.wizeline.viewstate

import android.content.Context
import android.util.AttributeSet
import android.widget.ViewFlipper
import kotlinx.android.synthetic.main.view_state.view.*
import kotlinx.android.synthetic.main.view_state_empty.view.*
import kotlinx.android.synthetic.main.view_state_error.view.*
import kotlinx.android.synthetic.main.view_state_loading.view.*

/**
 * An user interface element that should be used to hold a simple child with content,
 * this element can be used to give visual feedback to the users, for instance is can be used
 * to notify about the state of a network request.
 *
 * @attr ref R.styleable.ViewState_loadingTitleText
 * @attr ref R.styleable.ViewState_emptyImageResource
 * @attr ref R.styleable.ViewState_emptyTitleText
 * @attr ref R.styleable.ViewState_emptyDescriptionText
 * @attr ref R.styleable.ViewState_errorImageResource
 * @attr ref R.styleable.ViewState_errorTitleText
 * @attr ref R.styleable.ViewState_errorDescriptionText
 * @attr ref R.styleable.ViewState_errorRetryText
 */
class ViewState @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewFlipper(
        context,
        attrs
) {

    /**
     * Sets the default layout, animations and attributes
     */
    init {
        inflate(context, R.layout.view_state, this)
        setOutAnimation(context, android.R.anim.fade_out)
        setInAnimation(context, android.R.anim.fade_in)

        attrs?.let {
            val array = context.obtainStyledAttributes(attrs, R.styleable.ViewState)

            // Loading attributes
            array.getString(R.styleable.ViewState_loadingTitleText)?.let { setLoadingTitleText(it) }

            // Empty attributes
            setEmptyImageResource(array.getResourceId(R.styleable.ViewState_emptyImageResource, R.drawable.ic_list))
            array.getString(R.styleable.ViewState_emptyTitleText)?.let { setEmptyTitleText(it) }
            array.getString(R.styleable.ViewState_emptyDescriptionText)?.let { setEmptyDescriptionText(it) }

            // Error attributes
            setErrorImageResource(array.getResourceId(R.styleable.ViewState_errorImageResource, R.drawable.ic_wifi))
            array.getString(R.styleable.ViewState_errorTitleText)?.let { setErrorTitleText(it) }
            array.getString(R.styleable.ViewState_errorDescriptionText)?.let { setErrorDescriptionText(it) }
            array.getString(R.styleable.ViewState_errorRetryText)?.let { setErrorRetryText(it) }

            array.recycle()
        }
    }


    /**
     * Finalize inflating a view from XML.  This is called as the last phase
     * of inflation, after all child views have been added.
     *
     * The CONTENT state is set by default
     */
    override fun onFinishInflate() {
        super.onFinishInflate()
        setState(State.CONTENT)
    }

    /**
     * Sets the loading title text
     */
    fun setLoadingTitleText(title: String) {
        loadingTitle.text = title
    }

    /**
     * Sets the empty image using a local resource
     */
    fun setEmptyImageResource(resId: Int) {
        emptyImage.setImageResource(resId)
    }

    /**
     * Sets the empty title text
     */
    fun setEmptyTitleText(title: String) {
        emptyTitle.text = title
    }

    /**
     * Sets the empty description text
     */
    fun setEmptyDescriptionText(description: String) {
        emptyDescription.text = description
    }

    /**
     * Sets the error image using a local resource
     */
    fun setErrorImageResource(resId: Int) {
        errorImage.setImageResource(resId)
    }

    /**
     * Sets the error title text
     */
    fun setErrorTitleText(title: String) {
        errorTitle.text = title
    }

    /**
     * Sets the error description text
     */
    fun setErrorDescriptionText(description: String) {
        errorDescription.text = description
    }

    /**
     * Sets the error button text
     */
    fun setErrorRetryText(title: String) {
        errorTitle.text = title
    }

    /**
     * Sets the error button listener
     */
    fun setOnRetryClickListener(block: () -> Unit) {
        errorRetry.setOnClickListener { block() }
    }

    /**
     * Sets the current view state, there are 4 states LOADING, CONTENT, EMPTY and ERROR
     */
    fun setState(state: State) {
        when (state) {
            State.LOADING -> setLoading()
            State.CONTENT -> setContent()
            State.EMPTY -> setEmpty()
            State.ERROR -> setError()
        }
    }

    /**
     * Sets visible the loading view
     */
    private fun setLoading() {
        clearViews()
        viewLoading.visibility = VISIBLE
        displayedChild = 0
    }

    /**
     * Sets visible the content view
     */
    private fun setContent() {
        clearViews()
        displayedChild = 1
    }

    /**
     * Sets visible the empty view
     */
    private fun setEmpty() {
        clearViews()
        viewEmpty.visibility = VISIBLE
        displayedChild = 0
    }

    /**
     * Sets visible the error view
     */
    private fun setError() {
        clearViews()
        viewError.visibility = VISIBLE
        displayedChild = 0
    }

    /**
     * Removes all views parent view state
     */
    private fun clearViews() {
        viewLoading.visibility = GONE
        viewEmpty.visibility = GONE
        viewError.visibility = GONE
    }
}
