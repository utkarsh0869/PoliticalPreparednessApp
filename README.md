## Political Preparedness

PoliticalPreparedness is an application that provides civic data intended to provide educational opportunities to the U.S. electorate using data provided by the Google Civic Information API. It will allow users to track information on target representatives and voting initiatives where applicable.
Users will be able to target a specific location and retrieve the associated civic data, displaying it to the user, and providing a clean user experience for consumption. Users will be able to save predefined locations for quick access and mark preferred representatives and policy outcomes. Where available, elections and voter information will also be provided, notifying the users on upcoming elections and providing access to associated information and saved data.

This app demonstrates the following views and techniques:

* [Retrofit](https://square.github.io/retrofit/) to make api calls to an HTTP web service.
* [Moshi](https://github.com/square/moshi) which handles the deserialization of the returned JSON to Kotlin data objects. 
* [Glide](https://bumptech.github.io/glide/) to load and cache images by URL.
* [Room](https://developer.android.com/training/data-storage/room) for local database storage.
  
It leverages the following components from the Jetpack library:

* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
* [Data Binding](https://developer.android.com/topic/libraries/data-binding/) with binding adapters
* [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) with the SafeArgs plugin for parameter passing between fragments

## Video Demo and Screenshots

[![Political Preparedness Android Application](https://i.ytimg.com/vi/aTo7uIUB7UQ/maxresdefault.jpg)](https://youtu.be/aTo7uIUB7UQ?si=4y0PSMUpOYZDYLqw "Political Preparedness Android Application")