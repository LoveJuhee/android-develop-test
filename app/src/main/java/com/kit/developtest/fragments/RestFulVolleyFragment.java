package com.kit.developtest.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.kit.developtest.R;
import com.navercorp.volleyextensions.volleyer.Volleyer;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RestFulVolleyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RestFulVolleyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestFulVolleyFragment extends Fragment {
  private OnFragmentInteractionListener mListener;
  private EditText txtRestUrl = null;
  private TextView tvResponse = null;
  private Gson gson = new Gson();


  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @return A new instance of fragment RestFulVolleyFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static RestFulVolleyFragment newInstance() {
    RestFulVolleyFragment fragment = new RestFulVolleyFragment();
    return fragment;
  }

  public RestFulVolleyFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_rest_ful_volley, container, false);

    Button button = (Button) view.findViewById(R.id.btnRequest);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        request();
      }
    });

    txtRestUrl = (EditText) view.findViewById(R.id.txtRestUrl);
    tvResponse = (TextView) view.findViewById(R.id.tvResponse);

    return view;
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
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p/>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    public void onFragmentInteraction(Uri uri);
  }

  private void request() {
    String url = txtRestUrl.getText().toString();
    if (url.isEmpty()) {
      url = "https://httpbin.org/get";
    }
    Volleyer.volleyer().get(url).withListener(listener).withErrorListener(errorListener).execute();
  }

  private Response.Listener<String> listener = new Response.Listener<String>() {
    @Override
    public void onResponse(String response) {
      String message = String.format("%s", response);
      tvResponse.setText(message);
    }
  };

  private Response.ErrorListener errorListener = new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError error) {
      request();
    }
  };

}
