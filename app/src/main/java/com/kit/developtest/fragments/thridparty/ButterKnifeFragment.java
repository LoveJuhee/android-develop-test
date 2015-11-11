package com.kit.developtest.fragments.thridparty;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.kit.developtest.R;
import com.kit.developtest.utils.ComponentUtil;
import com.kit.developtest.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link ButterKnifeFragment.OnFragmentInteractionListener} interface to handle interaction events.
 * Use the {@link ButterKnifeFragment#newInstance} factory method to create an instance of this
 * fragment.
 */
public class ButterKnifeFragment extends Fragment {
  private OnFragmentInteractionListener mListener;

  @Bind(R.id.rbtn1)
  RadioButton rbtn1;
  @Bind(R.id.switch1)
  Switch switch1;
  @Bind(R.id.btn1)
  Button btn1;
  @Bind(R.id.rbtn2)
  RadioButton rbtn2;
  @Bind(R.id.switch2)
  Switch switch2;
  @Bind(R.id.btn2)
  Button btn2;
  @Bind(R.id.rbtn3)
  RadioButton rbtn3;
  @Bind(R.id.switch3)
  Switch switch3;
  @Bind(R.id.btn3)
  Button btn3;
  @Bind(R.id.tvResponse)
  TextView tvResponse;

  @OnClick({R.id.btn1, R.id.btn2, R.id.btn3})
  void btnOnClick(Button btn) {
    final int id = btn.getId();
    String message = String.format("btnOnClick: %s (%d)", btn.getText().toString(), id);
    tvResponse.append(message);
    tvResponse.append("\n");
  }

  @OnCheckedChanged({R.id.rbtn1, R.id.rbtn2,R.id.rbtn3,R.id.rbtn4})
  void rbtnOnCheckedChanged(RadioButton rbtn) {
    if (rbtn.isChecked() == false) {
      return;
    }
    tvResponse.append("rbtnOnCheckedChanged: " + rbtn.getText().toString());
    tvResponse.append("\n");
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @return A new instance of fragment ButterKnifeFragment.
   */
  public static ButterKnifeFragment newInstance() {
    ButterKnifeFragment fragment = new ButterKnifeFragment();
    return fragment;
  }

  public ButterKnifeFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_butter_knife, container, false);
    ButterKnife.bind(this, view);
    ComponentUtil.setScrollable(tvResponse);
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

  public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    public void onFragmentInteraction(Uri uri);
  }

}
