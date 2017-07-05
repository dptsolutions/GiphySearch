package dptsolutions.com.giphysearch.recyclerview;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import timber.log.Timber;

/**
 * CoordinatorLayout behavior for animating the FAB in/out when scrolling.
 * From http://androidessence.com/hide-the-floatingactionbutton-when-scrolling-a-recyclerview/
 */

public class ScrollAwareFabBehavior extends FloatingActionButton.Behavior {

    public ScrollAwareFabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,
                dyUnconsumed);
        boolean show = false;

        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            show = false;
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            show = true;
        }
        Timber.d("dyConsumed[%d] dyUnconsumed[%d] dxConsumed[%d] dxUnconsumed[%d] child.getVisibility[%s] result[%s]",
                dyConsumed, dyUnconsumed, dxConsumed, dxUnconsumed, child.getVisibility(), show ? "show" : "hide");

        if(show) {
            child.show();
        } else {
            child.hide();
        }

//        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
//            child.hide();
//        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
//            child.show();
//        }
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }
}
