Gesture recognition through Mediapipe on Android

see these [instructions](https://google.github.io/mediapipe/getting_started/android_archive_library.html) that use Android Archive (AAR) and Gradle，The idea of gesture recognition comes from Simple Hand Gesture Recognition Code - Hand tracking - Mediapipe，Hand movement recognition



# Hand Gesture
## _Using Mediapipe Open_CV Libraries

## what this project about?
- Goal of this project is to recognize ONE, TWO, TREE, FOUR, FIVE, SIX, YEAH, ROCK, SPIDERMAN and OK. We use the LANDMARKS output of the LandmarkLetterboxRemovalCalculator. This output is a landmark list that contains 21 landmark.Each landmark have x, y and z values. But only x, y values are sufficient for our Goal.

## Installation steps

There are some following steps 
- In build.gradle of App, add a Mediapipe and CameraX core library.
- In Android Manifets.xml file add permission for hand tracking in app, Camera permission, hardware camera permission,hardware camera autofocus permission add meta data.
- Add the frame layout to display camera preview. 
- Make folder of asset in app.
- ![image](https://user-images.githubusercontent.com/78479435/126985230-d23bf769-2657-463a-a434-8755c9a86d66.png)
- make jniLibs folder.
- ![image](https://user-images.githubusercontent.com/78479435/126985496-ba24017a-cdcf-454c-a962-a02f57d93c94.png)
- Write down the code in MainActivity as shown in the tutorial.
 ![image](https://user-images.githubusercontent.com/78479435/126985950-5b7b01f4-7186-44a0-9953-234722b4f539.png)
 ![image](https://user-images.githubusercontent.com/78479435/126986062-7dc1b7d9-950e-4dac-9523-b3708f97c206.png)
 ![image](https://user-images.githubusercontent.com/78479435/126986141-09aeb94f-7add-413c-a603-0df10ce4345f.png)
 ![image](https://user-images.githubusercontent.com/78479435/126986346-981d0da6-0c7e-495a-9ff4-5315fda92292.png)


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
```
![image](https://user-images.githubusercontent.com/78479435/126986885-51a3e8b1-d9ad-416c-836a-ec30f68668da.png)

	
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
![image](https://user-images.githubusercontent.com/78479435/126987028-b8298c84-2fbe-4db9-aa51-c72a4f1a3785.png)
![image](https://user-images.githubusercontent.com/78479435/126987078-3497a33c-3d0a-44b1-8159-e6e3ff3bc069.png)

## What we have done?
- We have five finger states.
- thumbIsOpen
- firstFingerIsOpen
- secondFingerIsOpen
- thirdFingerIsOpen
- fourthFingerIsOpen
For exmaple: thumb is open if the x value of landmark 3 and the x value of landmark 4 are less than x value of landmark 2 else it is close

## What will be the output? 
- We recognize gesture such as ONE, TWO, TREE, FOUR, FIVE, SIX, YEAH, ROCK, SPIDERMAN,fist and OK and we can increase/decrease volume programmatically using one finger and fist.
![image](https://user-images.githubusercontent.com/78479435/126987621-9ebd749d-eeb8-4e54-87af-8ca247facce8.png)
![image](https://user-images.githubusercontent.com/78479435/126987775-5502f94d-1335-40fc-ac5f-70da54fe7e4e.png)

 
## What difficulties we faced while working on project?
- To detect the finger landmarks logic was quite difficult to us.

## How we overcome the difficulties?
- we changes our landrmarks values and logics to detect the finger.

## What we tried to do but did not succeed?
- we tried to remove camera prview to detect the finger but we failed.At the end we reduce the size of preview.

## What are our future plan regarding the development of project?
- Our future planning  about this project is to move the objects using touchless hand gesture.
