package what.gui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @author Gwindow
 * @since May 17, 2012 7:00:04 PM
 */
public class MyScrollView extends ScrollView {
	private Scrollable scrollable;
	private int pages = 1;

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * @param context
	 */
	public MyScrollView(Context context) {
		super(context);

	}

	public void attachScrollable(Scrollable scrollable) {
		this.scrollable = scrollable;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		if (scrollable != null) {
			int calc = getChildAt(getChildCount() - 1).getBottom() - getBottomY();
			boolean hitBottom = calc <= 15;
			if (hitBottom) {
				pages++;
				scrollable.scrolledToBottom();
				hitBottom = false;
			}
			super.onScrollChanged(l, t, oldl, oldt);
		}
	}

	/*
	 * public void jump() { int calc = getChildAt(getChildCount() - 1).getBottom() - getBottomY(); if (calc >
	 * getChildAt(getChildCount() - 1).getBottom() / 2) { scrollToTop(); } else { scrollToBottom(); } }
	 */
	public void scrollToTop() {
		scrollTo(0, 0);
	}

	public void jumpUp() {
		scrollBy(0, -getHeight());
	}

	public void jumpDown() {
		scrollBy(0, getHeight());
	}

	public void scrollToBottom() {
		scrollTo(0, getHeight() + getBottom());
	}

	public int getBottomY() {
		return getHeight() + getScrollY();
	}

	public int getPages() {
		return pages;
	}

}
