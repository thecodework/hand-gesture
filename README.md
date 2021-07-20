Gesture recognition through Mediapipe on Android

see these [instructions](https://google.github.io/mediapipe/getting_started/android_archive_library.html) that use Android Archive (AAR) and Gradle，The idea of gesture recognition comes from [Simple Hand Gesture Recognition Code - Hand tracking - Mediapipe](https://gist.github.com/TheJLifeX/74958cc59db477a91837244ff598ef4a)， [Hand movement recognition](https://github.com/TheJLifeX/mediapipe/tree/hand-mouvement-recognition)


![](https://oscimg.oschina.net/oscnet/up-3536fb9dd63dfb49e388960512498489620.JPEG)


# Hand Gesture
## _Using Mediapipe Open_CV Libraries

## what this project about?
- Goal of this project is to recognize ONE, TWO, TREE, FOUR, FIVE, SIX, YEAH, ROCK, SPIDERMAN and OK. We use the LANDMARKS output of the LandmarkLetterboxRemovalCalculator. This output is a landmark list that contains 21 landmark.Each landmark have x, y and z values. But only x, y values are sufficient for our Goal.

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
 
## What difficulties we faced while working on project?
- To detect the finger landmarks logic was quite difficult to us.

## How we overcome the difficulties?
- we changes our landrmarks values and logics to detect the finger.

## What we tried to do but did not succeed?
- we tried to remove camera prview to detect the finger but we failed.At the end we reduce the size of preview.

## What are our future plan regarding the development of project?
- Our future planning  about this project is to move the objects using touchless hand gesture.




