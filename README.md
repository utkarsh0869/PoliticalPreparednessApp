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

## Video Demo

[![Political Preparedness Android Application](https://i.ytimg.com/vi/aTo7uIUB7UQ/maxresdefault.jpg)](https://youtu.be/aTo7uIUB7UQ?si=4y0PSMUpOYZDYLqw "Political Preparedness Android Application")

# Screenshots

<img src="https://github.com/utkarsh0869/PoliticalPreparednessApp/assets/44482062/2401fc37-7a67-4ce3-b85a-b51091f4b311" alt="Image Title" width="360">

<img src="https://github.com/utkarsh0869/PoliticalPreparednessApp/assets/44482062/e13e1cbc-f8e7-4790-a314-631d89d7ffb3" alt="Image 1" width="360">

<img src="https://github.com/utkarsh0869/PoliticalPreparednessApp/assets/44482062/e7d9fd84-ff6b-4c21-9874-019cf97a866b" alt="Image 2" width="360">

<img src="https://github.com/utkarsh0869/PoliticalPreparednessApp/assets/44482062/3c2d834b-ee19-41e0-b0cc-48a8fecca11f" alt="Image 3" width="360">

<img src="https://github.com/utkarsh0869/PoliticalPreparednessApp/assets/44482062/b258e67c-abfc-46fe-8b67-468be7c00dbc" alt="Image 4" width="360">

<img src="https://github.com/utkarsh0869/PoliticalPreparednessApp/assets/44482062/ce5b7bc3-97d2-486c-aeb8-5c6f0d874242" alt="Image 5" width="360">
