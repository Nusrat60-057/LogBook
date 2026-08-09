# Plan to Fix Multi-User Support and Data Isolation

This plan addresses two critical issues:
1. **Overwriting Accounts**: Currently, `Signup.java` saves user data to `SharedPreferences`, which only holds one user at a time.
2. **Data Leaks**: All contacts are stored in one table in `EventDB.java` with no link to a specific user, so every user sees all contacts.

## User Review Required

> [!IMPORTANT]
> This change will transition your user accounts from `SharedPreferences` to the SQLite database. **Existing user accounts stored in SharedPreferences will need to be re-created** (Signed up again) once this change is applied, as they won't be in the new database table.

## Proposed Changes

### Database Layer

#### [MODIFY] [EventDB.java](file:///C:/Users/zionb/Downloads/CSE489/lab/LogBook(2023-3-60-057)/app/src/main/java/com/example/logbook2023_3_60_057/EventDB.java)
- Add a `users` table to store multiple accounts (UserID, Name, Email, Phone, Password).
- Update the `events` table schema to include a `USER_ID` column.
- Update `insertEvent` and `updateEvent` to accept and store the `USER_ID`.
- Add a method to verify user credentials during login.

---

### Authentication Flow

#### [MODIFY] [Signup.java](file:///C:/Users/zionb/Downloads/CSE489/lab/LogBook(2023-3-60-057)/app/src/main/java/com/example/logbook2023_3_60_057/Signup.java)
- Change the save logic to insert the new user into the `EventDB` users table instead of `SharedPreferences`.
- Keep `SharedPreferences` only for the "Remember Me" feature.

#### [MODIFY] [Login.java](file:///C:/Users/zionb/Downloads/CSE489/lab/LogBook(2023-3-60-057)/app/src/main/java/com/example/logbook2023_3_60_057/Login.java)
- Change the login logic to check the `EventDB` users table for matching credentials.
- When successful, save the current `USER_ID` in `SharedPreferences` so the rest of the app knows who is logged in.

---

### Contact Management

#### [MODIFY] [AddressBookList.java](file:///C:/Users/zionb/Downloads/CSE489/lab/LogBook(2023-3-60-057)/app/src/main/java/com/example/logbook2023_3_60_057/AddressBookList.java)
- Retrieve the current `USER_ID` from `SharedPreferences`.
- Update the query to only fetch contacts where `USER_ID` matches the logged-in user.

#### [MODIFY] [AddressBookDetails.java](file:///C:/Users/zionb/Downloads/CSE489/lab/LogBook(2023-3-60-057)/app/src/main/java/com/example/logbook2023_3_60_057/AddressBookDetails.java)
- Retrieve the current `USER_ID` from `SharedPreferences`.
- Pass this ID when calling `db.insertEvent` to link the new contact to the user.

## Verification Plan

### Manual Verification
1. **Signup User A**: Create an account and add 2 contacts.
2. **Logout/Exit**: Go back to the Login screen.
3. **Signup User B**: Create a different account and add 1 contact.
4. **Verify Isolation**: Ensure User B only sees their 1 contact.
5. **Switch Back**: Log in as User A and ensure they still see their original 2 contacts.
