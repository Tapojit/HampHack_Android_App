# HampHack_Android_App
This is an android app dedicated to the event HampHack, a yearly hackathon event in Hampshire College. Backend database and authentication(For event organizers only) is implemented in Firebase.

# Fragments
**Feeds-** Contains social media news for event and will display important announcements during the event. Push notifications activated for both. A timer counts down to the event's start. Another timer will start at the beginning of the event until it is over. 

**QR code-**
Creates and internally stores generated QR images for registration and workshops. Number of workshop tickets generated is kept track of in Firebase database. Such tickets can be verified using built-in scanner in **What_You_Can_Do** fragment.

**Website-**
An external intent to HampHack Website

**Authentication-**
Accessed through menu item **For organizers only**. Has email and password authentication exclusive only to event organizers. Successful authentication opens a new fragment **What_You_Can_Do**.

**What_You_Can_Do-** Includes implementation of QR code scanner and Feed populator. QR code scanner scans QR tickets of participants to verify against Firebase database. Feed populator is for inputting details concerning marketing, social media. Appear in feeds.



# Important-Excluded Files and links:
For security issues, **google-services.json** and links to firebase database have been excluded. Instantiation of references to this database have been commented out.
