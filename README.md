# HampHack_Android_App
This is an android app dedicated to the event HampHack, a yearly hackathon event in Hampshire College. Backend database and authentication(For event organizers only) is implemented in Firebase. Link to download app from Google Play: https://play.google.com/store/apps/details?id=com.hampshire.tapojit.hamphack

# Fragments
**Feeds-** Contains social media news for event and will display important announcements during the event. Push notifications activated for both. A timer counts down to the event's start. Another timer will start at the beginning of the event until it is over. 

![Alt text](https://lh3.googleusercontent.com/t3GxUmiKBppdrcwZ9GepSU-wwZ5q9e8lNOOJhHmtEhOq1hzWCUOBZ0h2rFkKc2u5zokQ=h900-rw "Optional text")

**QR code-**
Creates and internally stores generated QR images for registration and workshops. Number of workshop tickets generated is kept track of in Firebase database. Such tickets can be verified using built-in scanner in **What_You_Can_Do** fragment.

![Alt text](https://lh3.googleusercontent.com/zfuqAuLp5SbKI1JDLWMGdFrBtRulOMjRV35n6IDBdSAlTHNUW87VEWXH-0MXRoTtW0gS=h900-rw "Optional text")
![Alt text](https://lh3.googleusercontent.com/1HhkObDskZeJTLMMevjpsJ12nfWQ1zwTxvToWszqSQOf71MZTNWgP501cQyGIt6Nl1o=h900-rw "Optional text")

**Website-**
An external intent to HampHack Website

![Alt text](https://lh3.googleusercontent.com/bXsdg6LJZZJdc6hqbEm8GsBkI6zskDpul7pxR98Q6yTxdS9wC0iLJu9TkBqpKgZ7dA=h900-rw "Optional text")

**Authentication-**
Accessed through menu item **For organizers only**. Has email and password authentication exclusive only to event organizers. Successful authentication opens a new fragment **What_You_Can_Do**.


![Alt text](https://lh3.googleusercontent.com/7PQEK99NEIqARoHzs5bq325c3_Lm_wy2KVVF78D5WikJP6zr3Vv1r1ZiOg9_wSLZ8bc=h900-rw "Optional text")

**What_You_Can_Do-** Includes implementation of QR code scanner and Feed populator. QR code scanner scans QR tickets of participants to verify against Firebase database. Feed populator is for inputting details concerning marketing, social media. Appear in feeds.  

![Alt text](https://raw.githubusercontent.com/Tapojit/HampHack_Android_App/master/wycando_and.png =506×900)

# Important-Excluded Files and links:
For security issues, **google-services.json** and links to firebase database have been excluded. Instantiation of references to this database have been commented out.
