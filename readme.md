## Dog API Android Project


## Overview
MVVM design architecture with clean architecture so the project be testable and scalable for new features.
Stack: Room, Dagger hilt, DataBinding, navigation component, MockK, LiveData and Coroutine.

The app works on both landscape and portrait mode also in dark mode without losing states.


## Approach & decisions 

- MVVM design architecture to hold UI state.
- ViewModel must be 100% free of Android framework code to be unit testable.
- Coroutine for concurrency.
- Dagger Hilt for less boilerplate code
- UI state enum and screen state object to update the UI for less boilerplate and readability.
- DataBinding the recyclerView for cleaner fragments
- Unregister listeners to avoid memory leaks
- Apply DRY and SOLID principles

## Time Invested

It toke me almost 6 hours to complete the project.
- 1 hour planning the architecture, app concept and design.
- 1.5 hour setup the project architecture and data layer with dagger hilt.
- 1.5 hour implementing layouts and UI elements.
- 1.5 hours implementing the logic.
- 30 minutes writing unit-tests for repositories and viewmodels.

## Future Improvements

- Improve the UI/UX.
- Increase unit tests coverage.
- Implement UI tests.