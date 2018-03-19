# Android HampHack App
This is an android app dedicated to the event HampHack, a yearly hackathon event in Hampshire College. Backend database and authentication(For event organizers only) was implemented using *Firebase*. Link to download app from Google Play: https://play.google.com/store/apps/details?id=com.hampshire.tapojit.hamphack

**[UPDATE]: This app in Google Play is disabled as of June 2017.**

# Fragments
**Feeds-** Contains social media news for event and will display important announcements during the event. Push notifications activated for both. There is a timer located in the top right corner, which counts down to the event's start. It will reset when the event starts and count down to its end.

![Alt text](https://lh3.googleusercontent.com/t3GxUmiKBppdrcwZ9GepSU-wwZ5q9e8lNOOJhHmtEhOq1hzWCUOBZ0h2rFkKc2u5zokQ=h900-rw "Optional text")

**QR code-**
"QR tickets" for registration and workshops are created and internally stored here. Firebase database is used to keep track of limited number of workshop tickets made available and to cross-verify participants who registered through our website. These tickets can be verified using built-in scanner in **What_You_Can_Do** *fragment*.

![Alt text](https://lh3.googleusercontent.com/zfuqAuLp5SbKI1JDLWMGdFrBtRulOMjRV35n6IDBdSAlTHNUW87VEWXH-0MXRoTtW0gS=h900-rw "Optional text")
![Alt text](https://lh3.googleusercontent.com/1HhkObDskZeJTLMMevjpsJ12nfWQ1zwTxvToWszqSQOf71MZTNWgP501cQyGIt6Nl1o=h900-rw "Optional text")

**Website-**
An external intent to HampHack Website

![Alt text](https://lh3.googleusercontent.com/bXsdg6LJZZJdc6hqbEm8GsBkI6zskDpul7pxR98Q6yTxdS9wC0iLJu9TkBqpKgZ7dA=h900-rw "Optional text")

**Authentication-**
It can be accessed through menu item **For organizers only**. Allows email and password authentication exclusive to event organizers. Successful authentication opens a new *fragment* **What_You_Can_Do**.


![Alt text](https://lh3.googleusercontent.com/7PQEK99NEIqARoHzs5bq325c3_Lm_wy2KVVF78D5WikJP6zr3Vv1r1ZiOg9_wSLZ8bc=h900-rw "Optional text")

**What_You_Can_Do-** Allow organizers to scan QR tickets to verify against Firebase database. They can also update/add announcements and feeds.

<img src="https://raw.githubusercontent.com/Tapojit/HampHack_Android_App/master/wycando_and.png" alt="alt text" width="506" height="900">

# Important-Excluded Files and links:
For security, **google-services.json** and links to firebase database have been excluded.
