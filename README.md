# NWA

Submission for KJSCE Hackathon 2018 [Voices of Mumbai]

## VoicesOfMumbai

The Android App **VoicesOfMumbai** is to be used by the users to voice the problems they're facing in their daily routine.

**Features:**

* Users can post problems pertaining to their Locality
* Official Authorities monitor these reports and provide status updates related to a problem.
* Problem Heatmap
    - Users are provided with a Map, which contains location markers where a problem is reported by the User.
    - Users get an overview of problems which are located in, as well as around the locality
* User Authenticity: Users have to provide their Mobile Number, through which an OTP verification is done.

This project would serve as a platform for the authorities as well as residents to monitor their locality. It also acts as an Indicator for other Users, who may think of residing in the Locality. The locality of the user is taken from his current location while posting a complaint regarding the problem he/she is facing.

## Screenshots
<p float="left">
<img height="350" src="/static/new.png"/>
<img height="350" src="/static/map.png"/>
<img height="350" src="/static/map1.png"/>
</p>

## Video (Demo)
<img height="350" src="/static/video.gif"/>

## Setup

#### API
API is GraphQL middleware

- run ```npm install```
- create a project on Heroku
- login with heroku
- ```git push heroku master``` to deploy on heroku

#### Electron-App
It is our desktop client for our project

- run ```npm install```
- ```npm install -g electron```
- ```npm start``` to start electron app

<br>
default login: <br>
username: admin <br>
password: password
