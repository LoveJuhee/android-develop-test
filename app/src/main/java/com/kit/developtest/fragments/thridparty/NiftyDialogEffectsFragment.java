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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.kit.developtest.R;
import com.kit.developtest.utils.ComponentUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link NiftyDialogEffectsFragment.OnFragmentInteractionListener} interface to handle interaction
 * events. Use the {@link NiftyDialogEffectsFragment#newInstance} factory method to create an
 * instance of this fragment.
 */
public class NiftyDialogEffectsFragment extends Fragment {
  private OnFragmentInteractionListener mListener;

  @Bind(R.id.tvResponse) TextView tvResponse;

  @OnCheckedChanged({R.id.rbtnChk1, R.id.rbtnChk2, R.id.rbtnChk3, R.id.rbtnChk4}) void onCheckedChanged(
      RadioButton rbtn) {
  }

  @OnClick({R.id.btn1}) void onClick1(Button btn) {
    Context context = getActivity().getApplicationContext();
    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);

    dialogBuilder
        .withTitle("Modal Dialog")
        .withMessage("This is a modal Dialog.")
        .show();
  }

  @OnClick({R.id.btn2, R.id.btn3}) void onClick2(Button btn) {
    Context context = getActivity().getApplicationContext();
    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);

    dialogBuilder.withTitle("Modal Dialog")
                 .withTitleColor("#FFFFFF")
                 .withDividerColor("#11000000")
                 .withMessage("This is a modal Dialog.")
                 .withMessageColor("#FFFFFFFF")
                 .withDialogColor("#FFE74C3C")
                 .withIcon(getResources().getDrawable(R.drawable.ic_cast_dark))
                 .withDuration(700)
                 .withEffect(Effectstype.Fadein)
                 .withButton1Text("OK")
                 .withButton2Text("Cancel")
                 .isCancelableOnTouchOutside(true)
                 .setButton1Click(new View.OnClickListener() {
                   @Override public void onClick(View v) {
                     Toast.makeText(v.getContext(), "i'm btn1", Toast.LENGTH_SHORT).show();
                   }
                 })
                 .setButton2Click(new View.OnClickListener() {
                   @Override public void onClick(View v) {
                     Toast.makeText(v.getContext(), "i'm btn2", Toast.LENGTH_SHORT).show();
                   }
                 })
                 .show();
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   */
  public static NiftyDialogEffectsFragment newInstance() {
    NiftyDialogEffectsFragment fragment = new NiftyDialogEffectsFragment();
    return fragment;
  }

  public NiftyDialogEffectsFragment() {
    // Required empty public constructor
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_nifty_dialog_effects, container, false);
    ButterKnife.bind(this, view);
    ComponentUtil.setScrollable(tvResponse);
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
