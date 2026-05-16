#!/bin/bash
set -e

# Start X virtual framebuffer
export DISPLAY=:0
PORT=${PORT:-8080}

echo "Starting Xvfb on display $DISPLAY..."
Xvfb :0 -screen 0 1024x768x24 &
sleep 1

echo "Starting window manager (fluxbox)..."
fluxbox &
sleep 1

echo "Starting x11vnc on display $DISPLAY..."
# run x11vnc in background, no password, persistent
x11vnc -display $DISPLAY -nopw -forever -shared -rfbport 5900 -bg
sleep 1

echo "Starting websockify/noVNC on port $PORT..."
# serve noVNC web files and proxy to x11vnc's 5900
websockify --web=/opt/novnc $PORT localhost:5900 &
sleep 1

echo "Starting Java application..."
java -cp out Main
