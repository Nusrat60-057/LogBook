# LogBook (2023-3-60-057)

An Android address book app built in Java with login/signup authentication and local SQLite storage.

## Overview

This project is an Android application named `LogBook(2023-3-60-057)`.
It includes a simple user registration and login flow, and a contact/address book that stores entries locally in an SQLite database.

## Features

- User signup with user ID, name, email, phone, and password.
- Login with saved credentials.
- Remember user ID and login state with `SharedPreferences`.
- Contact/address book list with add, view, edit, and delete operations.
- Contact details include name, email, phone number, date of birth, present address, and permanent address.
- Local data persistence using SQLite via `EventDB`.

## App Flow

1. `Signup` activity - first screen for new user registration.
2. `Login` activity - login screen for existing users.
3. `AddressBookList` activity - displays saved contacts in a list.
4. `AddressBookDetails` activity - add a new contact or edit an existing one.

## Project Structure

- `app/src/main/java/com/example/logbook2023_3_60_057/`
  - `Signup.java` - registration logic, input validation, preferences storage.
  - `Login.java` - authentication and remember-me behavior.
  - `AddressBookList.java` - contact list display, delete action, navigation to detail screen.
  - `AddressBookDetails.java` - add/edit contact form validation and save/update logic.
  - `EventDB.java` - SQLite helper class for create, insert, update, delete, and query operations.
  - `ActivityAdapter.java` and `ActivityList.java` - list adapter and data model for displaying contacts.

- `app/src/main/res/layout/`
  - `activity_signup.xml`
  - `activity_login.xml`
  - `activity_address_book_list.xml`
  - `activity_address_book_details.xml`
  - `activity_main.xml`
  - `row_address.xml`

- `app/src/main/AndroidManifest.xml` - app activities and launcher configuration.

## Build Information

- Android Gradle Plugin configured via `app/build.gradle.kts`.
- Compile SDK: 36
- Target SDK: 36
- Minimum SDK: 27
- Java compatibility: Java 11

## Dependencies

- AndroidX AppCompat
- Material Components
- ConstraintLayout
- AndroidX Activity KTX
- JUnit for unit testing
- Espresso and AndroidX JUnit for instrumentation tests

## Running the App

1. Open the project in Android Studio.
2. Sync Gradle.
3. Build and run the app on an emulator or connected device.

## Notes

- The app uses `SharedPreferences` for storing user credentials and remember-me flags.
- Contacts are stored in an SQLite table named `events` with fields for ID, name, email, phone, DOB, present address, and permanent address.
- The signup screen redirects to login if a user account already exists, and automatically navigates to the address book if login is remembered.

## Potential Improvements

- Add password hashing instead of plain-text storage.
- Add search and filter support for contacts.
- Add support for multiple user accounts.
- Improve UI with modern Android components and styling.
