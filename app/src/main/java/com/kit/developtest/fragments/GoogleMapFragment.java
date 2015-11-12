package com.kit.developtest.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kit.developtest.R;
import com.kit.developtest.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link GoogleMapFragment.OnFragmentInteractionListener} interface to handle interaction events.
 * Use the {@link GoogleMapFragment#newInstance} factory method to create an instance of this
 * fragment.
 */
public class GoogleMapFragment extends Fragment {
  private OnFragmentInteractionListener mListener;

  //@Bind(R.id.fmGoogleMaps)
  //Fragment fmGoogleMaps;

  @OnClick({R.id.btnMapStyle, R.id.btnMarker})
  void btnOnClick(Button btn) {
    Context context = getActivity().getApplicationContext();
    final int id = btn.getId();
    switch (id) {
      case R.id.btnMapStyle:
        ToastUtil.showShortToast(context, true, btn.getText() + " Clicked.");
        break;
      case R.id.btnMarker:
        ToastUtil.showShortToast(context, true, btn.getText() + " Clicked.");
        break;
      default:
        ToastUtil.showShortToast(context, true, "Unknown Button(" + btn.getText() + ") Clicked.");
        break;
    }
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @return A new instance of fragment GoogleMapFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static GoogleMapFragment newInstance() {
    GoogleMapFragment fragment = new GoogleMapFragment();
    return fragment;
  }

  public GoogleMapFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_google_map, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onDestroyView() {
    ButterKnife.unbind(this);
    super.onDestroyView();
  }

  // TODO: Rename method, update argument and hook method into UI event
  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onFragmentInteraction(uri);
    }
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      mListener = (OnFragmentInteractionListener) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach() {
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
