package com.thecodework.handgesture;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.mediapipe.formats.proto.LandmarkProto;
import com.google.mediapipe.formats.proto.LandmarkProto.NormalizedLandmark;
import com.google.mediapipe.formats.proto.LandmarkProto.NormalizedLandmarkList;
import com.google.mediapipe.framework.AndroidPacketCreator;
import com.google.mediapipe.framework.Packet;
import com.google.mediapipe.framework.PacketGetter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main activity of MediaPipe multi-hand tracking app.
 */
public class MainActivity extends com.thecodework.handgesture.basic.BasicActivity {
    private static final String TAG = "MainActivity";
    private List<NormalizedLandmarkList> multiHandLandmarks;
    private static final String INPUT_NUM_HANDS_SIDE_PACKET_NAME = "num_hands";
    private static final String OUTPUT_LANDMARKS_STREAM_NAME = "hand_landmarks";
    private static final int NUM_HANDS = 2;
    private TextView gesture, feature;
    private ImageView imgNormal, imgSilent, imgVibrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gesture = findViewById(R.id.gesture);
        feature = findViewById(R.id.feature);
        imgNormal = findViewById(R.id.imgNormal);
        imgSilent = findViewById(R.id.imgSilent);
        imgVibrate = findViewById(R.id.imgVibrate);

        AndroidPacketCreator packetCreator = processor.getPacketCreator();
        Map<String, Packet> inputSidePackets = new HashMap<>();
        inputSidePackets.put(INPUT_NUM_HANDS_SIDE_PACKET_NAME, packetCreator.createInt32(NUM_HANDS));
        processor.setInputSidePackets(inputSidePackets);

        processor.addPacketCallback(
                OUTPUT_LANDMARKS_STREAM_NAME,
                (packet) -> {
                    multiHandLandmarks =
                            PacketGetter.getProtoVector(packet, NormalizedLandmarkList.parser());

                    runOnUiThread(() -> gesture.setText(handGestureCalculator(multiHandLandmarks)));
                });
    }

    private String handGestureCalculator(List<NormalizedLandmarkList> multiHandLandmarks) {
        if (multiHandLandmarks.isEmpty()) {
            return "No hand deal";
        }
        boolean thumbIsOpen = false;
        boolean firstFingerIsOpen = false;
        boolean secondFingerIsOpen = false;
        boolean thirdFingerIsOpen = false;
        boolean fourthFingerIsOpen = false;

        for (NormalizedLandmarkList landmarks : multiHandLandmarks) {

            List<NormalizedLandmark> landmarkList = landmarks.getLandmarkList();
            float pseudoFixKeyPoint = landmarkList.get(2).getX();
            if (pseudoFixKeyPoint < landmarkList.get(9).getX()) {
                if (landmarkList.get(3).getX() < pseudoFixKeyPoint && landmarkList.get(4).getX() < pseudoFixKeyPoint) {
                    thumbIsOpen = true;
                }
            }
            if (pseudoFixKeyPoint > landmarkList.get(9).getX()) {
                if (landmarkList.get(3).getX() > pseudoFixKeyPoint && landmarkList.get(4).getX() > pseudoFixKeyPoint) {
                    thumbIsOpen = true;
                }
            }
            pseudoFixKeyPoint = landmarkList.get(6).getY();
            if (landmarkList.get(7).getY() < pseudoFixKeyPoint && landmarkList.get(8).getY() < landmarkList.get(7).getY()) {
                firstFingerIsOpen = true;
            }
            pseudoFixKeyPoint = landmarkList.get(10).getY();
            if (landmarkList.get(11).getY() < pseudoFixKeyPoint && landmarkList.get(12).getY() < landmarkList.get(11).getY()) {
                secondFingerIsOpen = true;
            }
            pseudoFixKeyPoint = landmarkList.get(14).getY();
            if (landmarkList.get(15).getY() < pseudoFixKeyPoint && landmarkList.get(16).getY() < landmarkList.get(15).getY()) {
                thirdFingerIsOpen = true;
            }
            pseudoFixKeyPoint = landmarkList.get(18).getY();
            if (landmarkList.get(19).getY() < pseudoFixKeyPoint && landmarkList.get(20).getY() < landmarkList.get(19).getY()) {
                fourthFingerIsOpen = true;
            }

            AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            if (thumbIsOpen && firstFingerIsOpen && secondFingerIsOpen && thirdFingerIsOpen && fourthFingerIsOpen) {
                feature.setText(" ");
                imgVibrate.setVisibility(View.GONE);
                imgNormal.setVisibility(View.GONE);
                imgSilent.setVisibility(View.GONE);
                return "FIVE";
            } else if (!thumbIsOpen && firstFingerIsOpen && secondFingerIsOpen && thirdFingerIsOpen && fourthFingerIsOpen) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE) {
                    feature.setText("Vibrate Mode");
                    imgVibrate.setVisibility(View.VISIBLE);
                    imgNormal.setVisibility(View.GONE);
                    imgSilent.setVisibility(View.GONE);
                }
                return "FOUR";
            } else if (!thumbIsOpen && firstFingerIsOpen && secondFingerIsOpen && thirdFingerIsOpen && !fourthFingerIsOpen) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
                    feature.setText("Normal Mode");
                    imgVibrate.setVisibility(View.GONE);
                    imgNormal.setVisibility(View.VISIBLE);
                    imgSilent.setVisibility(View.GONE);
                }
                return "THREE";
            } else if (!thumbIsOpen && firstFingerIsOpen && secondFingerIsOpen && !thirdFingerIsOpen && !fourthFingerIsOpen) {
                if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
                audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
                feature.setText("Silent Mode");
                imgVibrate.setVisibility(View.GONE);
                imgNormal.setVisibility(View.GONE);
                imgSilent.setVisibility(View.VISIBLE);
                return "TWO";
            } else if (!thumbIsOpen && firstFingerIsOpen && !secondFingerIsOpen && !thirdFingerIsOpen && !fourthFingerIsOpen) {
                if (audioManager != null) {
                    audioManager.adjustStreamVolume(
                            AudioManager.STREAM_MUSIC,
                            AudioManager.ADJUST_RAISE,
                            AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
                }
                feature.setText(" ");
                imgVibrate.setVisibility(View.GONE);
                imgNormal.setVisibility(View.GONE);
                imgSilent.setVisibility(View.GONE);
                return "ONE";
            } else if (thumbIsOpen && firstFingerIsOpen && !secondFingerIsOpen && !thirdFingerIsOpen && !fourthFingerIsOpen) {
                feature.setText(" ");
                imgVibrate.setVisibility(View.GONE);
                imgNormal.setVisibility(View.GONE);
                imgSilent.setVisibility(View.GONE);
                return "YEAH";
            } else if (!thumbIsOpen && firstFingerIsOpen && !secondFingerIsOpen && !thirdFingerIsOpen && fourthFingerIsOpen) {
                imgVibrate.setVisibility(View.GONE);
                imgNormal.setVisibility(View.GONE);
                imgSilent.setVisibility(View.GONE);
                feature.setText(" ");
                return "ROCK";
            } else if (thumbIsOpen && firstFingerIsOpen && !secondFingerIsOpen && !thirdFingerIsOpen && fourthFingerIsOpen) {
                imgVibrate.setVisibility(View.GONE);
                imgNormal.setVisibility(View.GONE);
                imgSilent.setVisibility(View.GONE);
                feature.setText(" ");
                return "Spider-Man";
            } else if (!thumbIsOpen && !firstFingerIsOpen && !secondFingerIsOpen && !thirdFingerIsOpen && !fourthFingerIsOpen) {
                if (audioManager != null) {
                    audioManager.adjustStreamVolume(
                            AudioManager.STREAM_MUSIC,
                            AudioManager.ADJUST_LOWER,
                            AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
                }
                feature.setText(" ");
                imgVibrate.setVisibility(View.GONE);
                imgNormal.setVisibility(View.GONE);
                imgSilent.setVisibility(View.GONE);
                return "fist";
            } else if (!firstFingerIsOpen && secondFingerIsOpen && thirdFingerIsOpen && fourthFingerIsOpen &&
                    isThumbNearFirstFinger(landmarkList.get(4), landmarkList.get(8))) {
                imgVibrate.setVisibility(View.GONE);
                imgNormal.setVisibility(View.GONE);
                imgSilent.setVisibility(View.GONE);
                feature.setText(" ");
                return "OK";
            } else {
                return "___";
            }
        }
        return "___";
    }

    private boolean isThumbNearFirstFinger(LandmarkProto.NormalizedLandmark point1, LandmarkProto.NormalizedLandmark point2) {
        double distance = getEuclideanDistanceAB(point1.getX(), point1.getY(), point2.getX(), point2.getY());
        return distance < 0.1;
    }

    private double getEuclideanDistanceAB(double a_x, double a_y, double b_x, double b_y) {
        double dist = Math.pow(a_x - b_x, 2) + Math.pow(a_y - b_y, 2);
        return Math.sqrt(dist);
    }
}