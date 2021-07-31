Gesture recognition through Mediapipe on Android

see these [instructions](https://google.github.io/mediapipe/getting_started/android_archive_library.html) that use Android Archive (AAR) and Gradle，The idea of gesture recognition comes from Simple Hand Gesture Recognition Code - Hand tracking - Mediapipe，Hand movement recognition



# Hand Gesture
## Using [Mediapipe Open_CV Libraries](https://google.github.io/mediapipe/solutions/hands.html)

## What this project about?
- Goal of this project is to recognize different gestures using fingers & thumb of our hand. We use the LANDMARKS output of the LandmarkLetterboxRemovalCalculator. This output is a landmark list that contains 21 landmark. Each landmark have x, y and z values. But only x, y values are sufficient for our Goal.
- We have five finger states-

- thumbIsOpen
- firstFingerIsOpen
- secondFingerIsOpen
- thirdFingerIsOpen
- fourthFingerIsOpen
- For exmaple: thumb is open if the x value of landmark 3 and the x value of landmark 4 are less than x value of landmark 2 else it is close

## Installation steps

There are some following steps 
- In build.gradle of App, add a [Mediapipe](https://google.github.io/mediapipe/solutions/hands.html) and [CameraX core](https://developer.android.com/jetpack/androidx/releases/camera) library.
- In Android Manifets.xml file add permission for hand tracking in app, [Camera permission](https://developer.android.com/guide/topics/media/camera), [hardware Camera permission](https://developer.android.com/reference/android/hardware/Camera), [hardware camera autofocus](https://developer.android.com/reference/android/hardware/Camera) permission add meta data.
- Add the frame layout to display camera preview. 
- Make folder of asset in app.
- ![image](https://user-images.githubusercontent.com/78479435/126985230-d23bf769-2657-463a-a434-8755c9a86d66.png)
- Make jniLibs folder.
- ![image](https://user-images.githubusercontent.com/78479435/126985496-ba24017a-cdcf-454c-a962-a02f57d93c94.png)

- Run the App.

## Dependencies/ Labraries with their versions :
- [Mediapipe](https://google.github.io/mediapipe/solutions/hands.html)- The ability to perceive the shape and motion of hands can be a vital component in improving the user experience across a variety of technological domains and platforms. We are using flogger:0.3.1, flogger-system-backend:0.3.1, findbugs:jsr305:3.0.2, guava:27.0.1-android, protobuf-java:3.11.4 versions.
- [CameraX core Library](https://developer.android.com/jetpack/androidx/releases/camera)- CameraX is an addition to Jetpack that makes it easier to add camera capabilities to your app. The library provides a number of compatibility fixes and workarounds to help make the developer experience consistent across many devices. We are using 1.0.0-beta10 versions.
	
## Mainifest :
- We are using [Camera permission](https://developer.android.com/guide/topics/media/camera), [android.hardware.Camera](https://developer.android.com/reference/android/hardware/Camera) and [android.hardware.camera.autofocus permission](https://developer.android.com/reference/android/hardware/Camera).

## What will be the output? 
- We recognize gesture such as ONE, TWO, TREE, FOUR, FIVE, SIX, YEAH, ROCK, SPIDERMAN,fist and OK and We can increase/decrease volume of our device using one finger and fist.
- ![ezgif com-gif-maker (2)](https://user-images.githubusercontent.com/78479435/127730764-c7688669-c608-4cf0-97fd-a32ccc2a25b7.gif)

 
## What difficulties we faced while working on project?
-  [Mediapipe](https://google.github.io/mediapipe/solutions/hands.html) Hands consists of two different models working together namely Palm Detection Model in which a full image is identified and it draws a box around the hand, and Hand   Landmark Model operates on this boxed image formed by Palm Detector and provides high fidelity 2D hand keypoint coordinates.We have five finger states.
  thumbIsOpen, firstFingerIsOpen, secondFingerIsOpen, thirdFingerIsOpen, fourthFingerIsOpen. For exmaple: thumb is open if the x value of landmark 3 and the x value of landmark 4 are less than x value of landmark 2 else it is close. To detect the finger landmarks logic was quite difficult to us.

## How we overcome the difficulties?
- At the beginning detecting fingers were difficult, We changes our landrmarks values and logics to detect the finger and follow this [documentation](https://gist.github.com/TheJLifeX/74958cc59db477a91837244ff598ef4a) from there we find ideas to detect finger using hand gesture.

## What we tried to do but did not succeed?
- We tried to remove Camera preview to detect the finger but after removing preview we are not able to detect finger so we have tos how camera preview. At the end we reduce the size of camera preview.

## What are our future plan regarding the development of project?
- Our future planning about this project is to control our smartphone using touchless hand gesture.
