package com.thecodework.handgesture;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
    // Max number of hands to detect/process.
    private static final int NUM_HANDS = 2;
    private TextView gesture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gesture = findViewById(R.id.gesture);

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
            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            // Hand gesture recognition
            if (thumbIsOpen && firstFingerIsOpen && secondFingerIsOpen && thirdFingerIsOpen && fourthFingerIsOpen) {
                Log.d(TAG, "Five");
                return "FIVE";
            } else if (!thumbIsOpen && firstFingerIsOpen && secondFingerIsOpen && thirdFingerIsOpen && fourthFingerIsOpen) {
                Log.d(TAG, "Four");
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                return "FOUR";
            } else if (!thumbIsOpen && firstFingerIsOpen && secondFingerIsOpen && thirdFingerIsOpen && !fourthFingerIsOpen) {
                Log.d(TAG, "Three");
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                return "THREE";
            } else if (!thumbIsOpen && firstFingerIsOpen && secondFingerIsOpen && !thirdFingerIsOpen && !fourthFingerIsOpen) {
                Log.d(TAG, "Two");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && !notificationManager.isNotificationPolicyAccessGranted()) {

                    Intent intent = new Intent(
                            android.provider.Settings
                                    .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    startActivity(intent);
                }
                if(notificationManager.isNotificationPolicyAccessGranted()){
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                }
                return "TWO";
            } else if (!thumbIsOpen && firstFingerIsOpen && !secondFingerIsOpen && !thirdFingerIsOpen && !fourthFingerIsOpen) {
                Log.d(TAG, "One");
                if (audioManager != null) {
                    audioManager.adjustStreamVolume(
                            AudioManager.STREAM_MUSIC,
                            AudioManager.ADJUST_RAISE,
                            AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
                }
                return "ONE";
            } else if (thumbIsOpen && firstFingerIsOpen && !secondFingerIsOpen && !thirdFingerIsOpen && !fourthFingerIsOpen) {
                Log.d(TAG, "Yeah");
                return "YEAH";
            } else if (!thumbIsOpen && firstFingerIsOpen && !secondFingerIsOpen && !thirdFingerIsOpen && fourthFingerIsOpen) {
                Log.d(TAG, "Rock");
                return "ROCK";
            } else if (thumbIsOpen && firstFingerIsOpen && !secondFingerIsOpen && !thirdFingerIsOpen && fourthFingerIsOpen) {
                Log.d(TAG, "Spiderman");
                return "Spider-Man";
            } else if (!thumbIsOpen && !firstFingerIsOpen && !secondFingerIsOpen && !thirdFingerIsOpen && !fourthFingerIsOpen) {
                Log.d(TAG, "fist");
                if (audioManager != null) {
                    audioManager.adjustStreamVolume(
                            AudioManager.STREAM_MUSIC,
                            AudioManager.ADJUST_LOWER,
                            AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
                }
                return "fist";
            } else if (!firstFingerIsOpen && secondFingerIsOpen && thirdFingerIsOpen && fourthFingerIsOpen &&
                    isThumbNearFirstFinger(landmarkList.get(4), landmarkList.get(8))) {
                Log.d(TAG, "ok");
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