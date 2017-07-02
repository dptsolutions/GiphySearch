package dptsolutions.com.giphysearch;

/**
 * Interface for all presenters
 *
 * Pretty much lifted from Mosby 2 (http://hannesdorfmann.com/mosby/)
 */

public interface MvpPresenter<V extends MvpView> {

    /**
     * Set or attach the view to this presenter
     */
    void attachView(V view);

    /**
     * Will be called if the view has been destroyed. Typically this method will be invoked from
     * <code>Activity.detachView()</code> or <code>Fragment.onDestroyView()</code>
     */
    void detachView();
}
