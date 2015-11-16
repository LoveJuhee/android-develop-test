package com.kit.developtest.fragments;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kit.developtest.R;
import com.kit.developtest.utils.ToastUtil;

import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link GoogleMapFragment.OnFragmentInteractionListener} interface to handle interaction events.
 * Use the {@link GoogleMapFragment#newInstance} factory method to create an instance of this
 * fragment.
 */
public class GoogleMapFragment extends Fragment implements LocationListener, OnMapReadyCallback {
  private static final String TAG = GoogleMapFragment.class.getSimpleName();
  private OnFragmentInteractionListener mListener;

  MapFragment mapFragment;
  GoogleMap googleMap;
  Marker marker;
  LocationManager locationManager;

  @OnClick({R.id.btnMapStyle, R.id.btnMarker})
  void btnOnClick(Button btn) {
    Context context = getActivity().getApplicationContext();
    final int id = btn.getId();
    switch (id) {
      case R.id.btnMapStyle:
        ToastUtil.showShortToast(context, btn.getText() + " Clicked.", true);
        break;
      case R.id.btnMarker:
        ToastUtil.showShortToast(context, btn.getText() + " Clicked.", true);
        break;
      default:
        ToastUtil.showShortToast(context, "Unknown Button(" + btn.getText() + ") Clicked.", true);
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
  public View onCreateView(LayoutInflater inflater,
                           ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_google_map, container, false);
    try {
      ButterKnife.bind(this, view);
      initializeMap();
      /*
      // GoogleMap 설정.
      googleMap = getMapFragment().getMap();
      */
      // 위치정보 관리자를 생성한다.
      locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return view;
  }

  private void initializeMap() {
    try {
      if (googleMap == null) {
        FragmentManager myFragmentManager;
        myFragmentManager = getFragmentManager();
        MapFragment mySupportMapFragment = (MapFragment) myFragmentManager.findFragmentById(R.id.fmGoogleMaps);
        googleMap = mySupportMapFragment.getMap();

        if (googleMap == null) {
          ToastUtil.showShortToast(getActivity().getApplicationContext(),
                                   "Sorry! unable to create maps",
                                   true);
        }
      }
    } catch (Exception e) {
      ToastUtil.showShortToast(getActivity().getApplicationContext(), e.toString(), true);
    }
  }

  private MapFragment getMapFragment() {
    android.app.FragmentManager fm = null;

    Log.d(TAG, "sdk: " + Build.VERSION.SDK_INT);
    Log.d(TAG, "release: " + Build.VERSION.RELEASE);

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      Log.d(TAG, "using getFragmentManager");
      fm = getFragmentManager();
    } else {
      Log.d(TAG, "using getChildFragmentManager");
      fm = getChildFragmentManager();
    }

    try {
      mapFragment = (MapFragment) fm.findFragmentById(R.id.fmGoogleMaps);
      mapFragment.getMapAsync(this);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return mapFragment;
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    mapFragment.onLowMemory();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mapFragment.onDestroy();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
    Log.d(TAG, "onDestroyView()");
    /*
    try {
      Fragment fragment = (getFragmentManager().findFragmentById(R.id.fmGoogleMaps));
      FragmentTransaction ft = getActivity().getFragmentManager()
                                            .beginTransaction();
      ft.remove(fragment);
      ft.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    */
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
    mapFragment.onDetach();
    mListener = null;
  }

  @Override
  public void onResume() {
    super.onResume();
    try {
      resumeWork();
      mapFragment.onResume();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @SuppressWarnings("ResourceType")
  private void resumeWork() {
    // 일단 GPS로 사용하도록 설정
    String provider = LocationManager.GPS_PROVIDER;

    // GPS의 사용 가능 여부 조회
    // --> 사용이 불가능할 경우 통신망의 IP주소 기반으로 변경
    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
      provider = LocationManager.NETWORK_PROVIDER;
    }

    Context context = getActivity().getApplicationContext();
    ToastUtil.showShortToast(context, provider, true);

    // 위치정보 취득 시작
    // --> 하드웨어이름, 갱신시간주기, 갱신거리주기(m), 이벤트핸들러
    locationManager.requestLocationUpdates(provider, 400, 1, this);
  }

  /**
   * 위도와 경도 기반으로 주소를 리턴하는 메서드
   */
  public String getAddress(double lat, double lng) {
    String address = null;

    // 위치정보를 활용하기 위한 구글 API 객체
    Context context = getActivity().getApplicationContext();
    Locale locale = Locale.getDefault();
    Geocoder geocoder = new Geocoder(context, locale);

    // 주소 목록을 담기 위한 List
    List<Address> list = null;

    try {
      // 주소 목록을 가져온다. --> 위도,경도,조회 갯수
      list = geocoder.getFromLocation(lat, lng, 1);
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (list == null) {
      Log.e("getAddress", "주소 데이터 얻기 실패");
      return null;
    }

    if (list.size() > 0) {
      // getFromLocation() 메서드에서 결과를 하나만 요청했기 때문에,
      // 반복처리는 필요 없다.
      Address addr = list.get(0);
      address = addr.getCountryName() + " " + addr.getAdminArea() + " " + addr.getLocality() + " " + addr.getThoroughfare() + " " + addr.getFeatureName();
    }

    return address;
  }


  /**
   * 구글 맵에 위치 출력하기
   */
  public void setMapPosition(double lat, double lng) {
    LatLng position = new LatLng(lat, lng);

    if (marker == null) {
      // 마커가 없는 경우 새로 생성하여 지도에 추가
      MarkerOptions options = new MarkerOptions();
      options.position(position);
      options.title(getAddress(lat, lng));
      marker = googleMap.addMarker(options);
    } else {
      // 이미 존재하는 경우, 위치만 갱신
      marker.setPosition(position);
      marker.setTitle(getAddress(lat, lng));
    }

    // zoom : 1~21 (값이 커질 수록 확대)
    CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(position, 18.5f);
    googleMap.animateCamera(camera);
  }

  @Override
  public void onLocationChanged(Location arg0) {
    double lat = arg0.getLatitude();
    double lng = arg0.getLongitude();
    setMapPosition(lat, lng);
  }

  /**
   * 위치 정보 관련 하드웨어가 사용 불가능하게 된 경우 호출된다.
   */
  @Override
  public void onProviderDisabled(String arg0) {
    Context context = getActivity().getApplicationContext();
    ToastUtil.showShortToast(context, "onProviderDisabled : " + arg0, true);
  }

  /**
   * 위치 정보 관련 하드웨어가 사용 가능하게 된 경우 호출된다.
   */
  @Override
  public void onProviderEnabled(String arg0) {
    Context context = getActivity().getApplicationContext();
    ToastUtil.showShortToast(context, "onProviderEnabled : " + arg0, true);
  }

  /**
   * 위치 정보 하드웨어의 상태가 변경된 경우 호출된다.
   */
  @Override
  public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
    Context context = getActivity().getApplicationContext();
    ToastUtil.showShortToast(context, "onStatusChanged : " + arg0, true);
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    setMapPosition(37.494583, 127.029727);
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
