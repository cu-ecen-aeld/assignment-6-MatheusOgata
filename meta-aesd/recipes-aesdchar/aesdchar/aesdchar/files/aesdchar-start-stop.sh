#! /bin/sh

case "$1" in
start)
echo "Starting init script for aesdchar Loading"
start-stop-daemon -S -n init -a /bin/aesdchar_load
;;
stop)
echo "Removing user modules"
start-stop-daemon -K -n aesdchar_unload
start-stop-daemon -S -n init -a /bin/aesdchar_unload
;;
*)
echo "Usage: $0 {start|stop}"
exit 1
esac
exit 0
