
# Mana Wars

Team project for Software Engineering course.

Single-player game for Android.

<img title="Main Screen" src="/Screenshots/Main.jpg" alt="Main" width="200"/>

The main goal is to develop the collection of skills (passive and active) and their levels,

<div>
  <img title="Skills Inventory Screen" src="/Screenshots/Inventory.jpg" alt="Inventory" width="200"/>
  <img title="General Skills Info Screen" src="/Screenshots/Info.jpg" alt="Info" width="200"/>
</div>


which are then used in 1v1 combats against bots of different levels.

<div>
  <img title="Dungeons Screen" src="/Screenshots/Dungeons.jpg" alt="Dungeons" width="200"/>
  <img title="In Battle" src="/Screenshots/Battle.jpg" alt="Battle" width="200"/>
</div>

New skills can be dropped at random from a case earned during a successful battle,

<img title="Case Opened" src="/Screenshots/CaseOpen.jpg" alt="CaseOpen" width="200"/>

or bought in the store for mana (in-game currency) choosing from a small range of skills on sale.

<img title="Shop Screen" src="/Screenshots/Shop.jpg" alt="Shop" width="200"/>

# Technical Details

The app exploits **LibGDX** library as a front-end tool. In the absence of some vital classes, we built a small library of simple animations, a small library of layout constraints for UI elements and a Table-List class on top of LibGDX.

Since the game is mostly UI based, we designed a reactive event-based architecture using **RxJava**.

The set of skills on sale is planned to be changed every day, and in order to keep this synced we utilized **Volley** to make simple GET requests.

As a data persistence tool, we chose **Android Room** for storing the skills and the bots available.

# How To Build

1. Head to https://arturkasymov.student.tcs.uj.edu.pl/mana_wars_daily_skills.html
  - copy the contents, which is a json object
  - change the keys of the object so that the first one refers to today's date, and so on
  - host the resulting json object as a file on a web server
2. Paste the host address as the value of **DAILY_SKILLS_JSON_URL** field in the file [ApplicationConstants.java](android/src/com/mana_wars/utils/ApplicationConstants.java)
3. Launch the android application (e.g. in Android Studio)

# Team

- Me
- [ArturKasymov](https://github.com/ArturKasymov)
- [WeronikaLorenczyk](https://github.com/WeronikaLorenczyk)
