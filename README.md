# Harmonix - Endlessly groove to your favorite tracks

The purpose of this project is to create a music management app that allows users to listen to their favourite music, create playlists, and share songs with their friends. The app has a user-friendly interface that allows users to easily navigate through the app and find the music they love.

Take a peek at our website: https://harmonixweb.netlify.app/

## How to run the app

Download the .zip file from this release, and open it into android studio.

Unit Tests & Integration Tests: The application's logic and data layer is tested using "AllUnitTests.java" and "AllIntegrationTests.java" files, before running the app please run the tests and make sure all of them pass.

System Tests: The application's UI is tested using Espresso, to run the system tests, please go to the "androidTest" folder and run the "AllSystemTests" from there.

Device: Please use Google Pixel Tablet API or connect a tablet using USB (in portrait mode) for testing/running
Press run on Android Studio and the app will start!

There is also a Harmonix.apk that is built from the revision of this code couldn't be attached to this release directly since the file size was above the allowed limit so we have attached it in the main branch, with the URL to it given below.

## Documentation

- The vision statement can be found [here](https://code.cs.umanitoba.ca/comp3350-winter2024/spectacular6-a01-6/-/blob/main/VISION.md?ref_type=heads).
- The architecture and design of the app can be found in the [Architecture](https://code.cs.umanitoba.ca/comp3350-winter2024/spectacular6-a01-6/-/tree/main/Architecture?ref_type=heads) folder
- Each iteration's retrospective can be found in the [Docs](https://code.cs.umanitoba.ca/comp3350-winter2024/spectacular6-a01-6/-/tree/main/Docs?ref_type=heads) folder.

## Current state of the app

1.  The Harmonix app starts with a Homepage which has a "Start Listening" button.
2.  The Welcome page (Feature 1) of the app allows the users to signup/login or skip and directly go into the app

         Users can create an account which gets stored into a database. (Completed)
         Login feature is complete, and allows users to log in to previously created account. (Completed)
         Skip for now is for users that just want an overview of the app and the music it has! (Completed)

3.  App's main page contains a menu that lets the users navigate through home, library, radio, search and account management options. Account Management (Feature 2) is implemented and allows for changing of account details. (Completed)

4.  Home and Search fragments make up the 3rd & 4th feature for the app. The Home fragment contains all the current songs the User can choose from, and when a song is clicked from the list, a music player pops up and begins playing music. The volume of the song, and the current position of the song can be changed by sliders in the player, as well as allowing for pause/playing of the current song, and going to the next or previous song if they exist. The search fragment allows for searching of specified songs by name, and allows the user to click on the filtered list and play songs from there. (Completed).

5.  Songs can be downloaded from the music player via a button in the top right which makes up (feature 5). Once a song is downloaded, it will appear in the users library downloads section. (Completed)

6.  Currently, the creation of customizable playlists per user has been completed which adds the ability to save songs via playlist option as well. (Feature 6) (Completed)

7.  Users now can also add songs to a queue where they don't need to go back and forth looking for their favourites! (Feature 7) (Completed)

8.  Some features like the radio, music recommendations and social media sharing are still in progress and will be completed in the future releases.
