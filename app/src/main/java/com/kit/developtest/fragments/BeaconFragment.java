package com.kit.developtest.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.kit.developtest.R;
import com.kit.developtest.models.beacon.Beacon;
import com.kit.developtest.services.BeaconIntentService;
import com.kit.developtest.thirdparties.reco.BeaconRecoSdkService;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BeaconFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BeaconFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeaconFragment extends Fragment {
  private List<Beacon> beaconList = null;
  private OnFragmentInteractionListener mListener;
  private RadioGroup rgroup = null;

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @return A new instance of fragment BeaconFragment.
   */
  public static BeaconFragment newInstance() {
    BeaconFragment fragment = new BeaconFragment();
    return fragment;
  }

  public BeaconFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_beacon, container, false);
    rgroup = (RadioGroup) view.findViewById(R.id.rgrpBeaconGroup);
    Button btnProcess = (Button) view.findViewById(R.id.btnProcess);
    btnProcess.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        process();
      }
    });

    // beaconList
    beaconList = new ArrayList<>();
    beaconList.add(new Beacon("97009700-9700-9700-9700-970097009700", 861, 1001));
    beaconList.add(new Beacon("97009700-9700-9700-9700-970097009700", 861, 1002));
    beaconList.add(new Beacon("97009700-9700-9700-9700-970097009700", 861, 1003));
    BeaconIntentService.addMonitoringBeacons(beaconList);

    return view;
  }

  boolean started = false;

  private void process() {
    int id = rgroup.getCheckedRadioButtonId();
    if (started == false) {
      startup(id);
    } else  {
      shutdown(id);
    }
  }

  private void shutdown(int id) {
    switch (id) {
      case R.id.rbtnReco:
        break;
    }
    BeaconIntentService.stopBeaconService(getActivity());
  }

  private void startup(int id) {
    switch (id) {
      case R.id.rbtnReco:
        BeaconIntentService.setBeaconServiceClass(BeaconRecoSdkService.class);
        break;
    }
    BeaconIntentService.addMonitoringBeacons(beaconList);
    BeaconIntentService.startBeaconService(getActivity());
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
      throw new ClassCastException(activity.toString()
          + " must implement OnFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    public void onFragmentInteraction(Uri uri);
  }

}
