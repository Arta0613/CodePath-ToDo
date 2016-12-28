# Pre-work - *Codepath Todo*

**Codepath Todo** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Mohammed Amin**

Time spent: **24** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [~] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [~] Add support for completion due dates for todo items (and display within listview item)
* [x] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [~] Add support for selecting the priority of each todo item (and display in listview item)
* [~] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/RKKzSow.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

The first challenge I faced was correctly implementing a DialogFragment. The implementation itself is
simple enough but figuring out how to make it modular enough to be reusable and not reusing code was 
something that I had a lot of fun doing. I still need to learn how to apply styles correctly as it's
sizing the fragment programmatically in the onResume method.

A smaller but related challenge was getting the Toolbar on the the DialogFragment. It's not possible
to just say i have an option but luckily the process was almost the same. I had to explicitly 
initilize the component so it worked out.

Another challenege I've faced had to do with updating the list with changes made to the todo in the 
fragment. At first glance it was simple, all I had to do was to notify that the dataset was changed.
But as I was thinking about edge cases I realized/found that if I rotated the device, saved the todo
I was editing, then the list would not be updated even though I was calling notifydataset changed.
This was due to the way I had implemented the fragment. It is sent a reference to the todo requested
which it does it's edits on. So when the user rotates the screen, everything is recreated as well as 
new references to the items and so whatever the user saved was not being reflected in the new reference.
The way I solved it was to override the orientation and screenSize config changes in the manifest.
This makes sure that the activity is not recreated and hence holds the same reference. Thinking back,
I could have possibly used the position of the list to update the user text in the main activity 
instead of doing it in the fragment but then I would be doing the save logic in two places. And it 
would have added a lot of code for the simple goal of trying to save an item. 

## License

    Copyright 2016 Mohammed Amin

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.