Gesture recognition through Mediapipe on Android

see these [instructions](https://google.github.io/mediapipe/getting_started/android_archive_library.html) that use Android Archive (AAR) and Gradle，The idea of gesture recognition comes from Simple Hand Gesture Recognition Code - Hand tracking - Mediapipe，Hand movement recognition



# Hand Gesture
## Using [Mediapipe Open_CV Libraries](https://google.github.io/mediapipe/solutions/hands.html)

## what this project about?
- Goal of this project is to recognize ONE, TWO, TREE, FOUR, FIVE, SIX, YEAH, ROCK, SPIDERMAN and OK. We use the LANDMARKS output of the LandmarkLetterboxRemovalCalculator. This output is a landmark list that contains 21 landmark.Each landmark have x, y and z values. But only x, y values are sufficient for our Goal.

## Installation steps

There are some following steps 
- In build.gradle of App, add a [Mediapipe](https://google.github.io/mediapipe/solutions/hands.html) and [CameraX core](https://developer.android.com/jetpack/androidx/releases/camera) library.
- In Android Manifets.xml file add permission for hand tracking in app, [Camera permission](https://developer.android.com/guide/topics/media/camera), hardware camera permission, [hardware camera autofocus](https://developer.android.com/reference/android/hardware/Camera) permission add meta data.
- Add the frame layout to display camera preview. 
- Make folder of asset in app.
- ![image](https://user-images.githubusercontent.com/78479435/126985230-d23bf769-2657-463a-a434-8755c9a86d66.png)
- Make jniLibs folder.
- ![image](https://user-images.githubusercontent.com/78479435/126985496-ba24017a-cdcf-454c-a962-a02f57d93c94.png)

- Run the App.

## Dependencies with their versions :
```sh
    //Mediapipe
    implementation 'com.google.flogger:flogger:0.3.1'
    implementation 'com.google.flogger:flogger-system-backend:0.3.1'
    implementation 'com.google.code.findbugs:jsr305:3.0.2'
    implementation 'com.google.guava:guava:27.0.1-android'
    implementation 'com.google.guava:guava:27.0.1-android'
    implementation 'com.google.protobuf:protobuf-java:3.11.4'
```
```sh
    //CameraX core Library
    def camerax_version = "1.0.0-beta10"
    implementation "androidx.camera:camera-core:$camerax_version"
    implementation "androidx.camera:camera-camera2:$camerax_version"
    implementation "androidx.camera:camera-lifecycle:$camerax_version"
``
	
## Mainifest :
```sh
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-feature android:name="android.hardware.camera" />
    <uses-feature android:name="android.hardware.camera.autofocus" />
```
```sh
        <meta-data android:name="cameraFacingFront" android:value="true"/>
        <meta-data android:name="binaryGraphName" android:value="hand_tracking_mobile_gpu.binarypb"/>
        <meta-data android:name="inputVideoStreamName" android:value="input_video"/>
        <meta-data android:name="outputVideoStreamName" android:value="output_video"/>
        <meta-data android:name="flipFramesVertically" android:value="true"/>
```

## What will be the output? 
- We recognize gesture such as ONE, TWO, TREE, FOUR, FIVE, SIX, YEAH, ROCK, SPIDERMAN,fist and OK and We can increase/decrease volume of our device using one finger and fist.
-![ezgif com-gif-maker]https://gist.githubusercontent.com/TheJLifeX/74958cc59db477a91837244ff598ef4a/raw/088f3995801c58f79f0a79086f1cd4cc176396d3/00-hand-gesture-recognition.gif

 
## What difficulties we faced while working on project?
-  Mediapipe Hands consists of two different models working together namely Palm Detection Model in which a full image is identified and it draws a box around the hand, and Hand   Landmark Model operates on this boxed image formed by Palm Detector and provides high fidelity 2D hand keypoint coordinates.We have five finger states.
  thumbIsOpen, firstFingerIsOpen, secondFingerIsOpen, thirdFingerIsOpen, fourthFingerIsOpen. For exmaple: thumb is open if the x value of landmark 3 and the x value of landmark 4 are less than x value of landmark 2 else it is close. To detect the finger landmarks logic was quite difficult to us.

## How we overcome the difficulties?
- At the beginning detecting fingers were difficult, We changes our landrmarks values and logics to detect the finger and follow this [documentation](https://gist.github.com/TheJLifeX/74958cc59db477a91837244ff598ef4a) from there we find ideas to detect finger using hand gesture.

## What we tried to do but did not succeed?
- We tried to remove camera preview to detect the finger but after removing preview we are not able to detect finger and we failed in this idea. At the end we reduce the size of preview.

## What are our future plan regarding the development of project?
- Our future planning  about this project is to move the objects using touchless hand gesture.
