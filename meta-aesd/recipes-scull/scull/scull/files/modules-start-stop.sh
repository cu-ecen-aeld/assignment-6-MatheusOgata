#! /bin/sh

case "$1" in
start)
echo "Starting init script for Module Loading"
start-stop-daemon -S -n init -a /bin/scull_load
start-stop-daemon -S -n init -a /bin/module_load -- faulty
start-stop-daemon -S -n init -a /bin/module_load -- hello
;;
stop)
echo "Removing user modules"
start-stop-daemon -K -n scull_load
start-stop-daemon -K -n module_load
start-stop-daemon -S -n init -a /bin/scull_unload
start-stop-daemon -S -n init -a /bin/module_unload -- hello
start-stop-daemon -S -n init -a /bin/module_unload -- faulty
;;
*)
echo "Usage: $0 {start|stop}"
exit 1
esac
exit 0
