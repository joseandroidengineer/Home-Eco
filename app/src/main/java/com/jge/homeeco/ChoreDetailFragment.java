package com.jge.homeeco;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jge.homeeco.Models.Chore;
import com.jge.homeeco.dummy.DummyContent;

/**
 * A fragment representing a single chore detail screen.
 * This fragment is either contained in a {@link ChoreListActivity}
 * in two-pane mode (on tablets) or a {@link ChoreDetailActivity}
 * on handsets.
 */
public class ChoreDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;
    private Chore choreDetails;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ChoreDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }
        /**This condition is satisfied when a user taps a list item**/
        else if(getArguments().containsKey("bundleChore")){
            choreDetails = getArguments().getBundle("bundleChore").getParcelable("bundleChore");
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(choreDetails.getTitle());
            }
        }
        /***This is triggered when a new chore is made*/
        else if(getArguments().containsKey("createdChoreBundle")){
            choreDetails = getArguments().getBundle("createdChoreBundle").getParcelable("createdChore");
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(choreDetails.getTitle());
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.chore_detail, container, false);
        ImageView imageView;

        if (choreDetails != null) {
            ((TextView) rootView.findViewById(R.id.chore_detail)).setText(choreDetails.getDescription());
            imageView = rootView.findViewById(R.id.checkCloseIV);
            if(choreDetails.isCompleted()){
                imageView.setImageResource(R.drawable.baseline_check_24);
            }else{
                imageView.setImageResource(R.drawable.baseline_close_24);
            }
        }
        return rootView;
    }
}
