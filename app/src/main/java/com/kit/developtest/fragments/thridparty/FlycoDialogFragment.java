package com.kit.developtest.fragments.thridparty;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.FadeExit.FadeExit;
import com.flyco.animation.FlipEnter.FlipVerticalSwingEnter;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.kit.developtest.R;
import com.kit.developtest.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link FlycoDialogFragment.OnFragmentInteractionListener} interface to handle interaction events.
 * Use the {@link FlycoDialogFragment#newInstance} factory method to create an instance of this
 * fragment.
 */
public class FlycoDialogFragment extends Fragment {
  private OnFragmentInteractionListener mListener;

  @Bind(R.id.tvResponse) TextView tvResponse;

  BaseAnimatorSet bas_in = new FlipVerticalSwingEnter();
  BaseAnimatorSet bas_out = new FadeExit();
  Context context = null;

  @OnClick({R.id.btn1}) void onClick1(Button btn) {
    final NormalDialog dialog = new NormalDialog(context);
    dialog.content("let's go?")//
        .showAnim(bas_in)//
        .dismissAnim(bas_out)//
        .show();

    dialog.setOnBtnClickL(new OnBtnClickL() {
      @Override public void onBtnClick() {
        ToastUtil.showShortToast(context, true, "left");
        dialog.dismiss();
      }
    }, new OnBtnClickL() {
      @Override public void onBtnClick() {
        ToastUtil.showShortToast(context, true, "right");
        dialog.dismiss();
      }
    });

  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @return A new instance of fragment FlycoDialogFragment.
   */
  public static FlycoDialogFragment newInstance() {
    FlycoDialogFragment fragment = new FlycoDialogFragment();
    return fragment;
  }

  public FlycoDialogFragment() {
    // Required empty public constructor
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_flyco_dialog, container, false);
    ButterKnife.bind(this, view);
    context = getActivity().getApplicationContext();
    return view;
  }

  @Override public void onDestroyView() {
    ButterKnife.unbind(this);
    super.onDestroyView();
  }

  // TODO: Rename method, update argument and hook method into UI event
  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onFragmentInteraction(uri);
    }
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      mListener = (OnFragmentInteractionListener) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(
          activity.toString() + " must implement OnFragmentInteractionListener");
    }
  }

  @Override public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  /**
   * This interface must be implemented by activities that contain this fragment to allow an
   * interaction in this fragment to be communicated to the activity and potentially other fragments
   * contained in that activity. <p> See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html" >Communicating with
   * Other Fragments</a> for more information.
   */
  public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    public void onFragmentInteraction(Uri uri);
  }

}
