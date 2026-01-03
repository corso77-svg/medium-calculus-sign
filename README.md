medium-calculus-sign
Initial commit.

## Gesture capture

The app renders a full-screen `GestureCaptureView` that listens for touch events. Each down/move/up event appends a `GesturePoint` with the finger's x/y coordinates and the event timestamp (`t`). On a new touch down the list is cleared, so each gesture is stored as a fresh sequence of points that can be retrieved with `getGesturePoints()`.
